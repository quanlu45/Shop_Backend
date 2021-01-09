package com.cdd.eshop.service.impl;

import com.cdd.eshop.bean.bo.order.OrderBO;
import com.cdd.eshop.bean.bo.order.OrderItemBO;
import com.cdd.eshop.bean.dto.ResponseDTO;
import com.cdd.eshop.bean.po.*;
import com.cdd.eshop.bean.po.activity.Activity;
import com.cdd.eshop.bean.po.activity.ActivityRule;
import com.cdd.eshop.bean.po.activity.ActivityRuleType;
import com.cdd.eshop.bean.vo.OrderDetailVO;
import com.cdd.eshop.bean.vo.OrderItemVO;
import com.cdd.eshop.bean.vo.OrderVO;
import com.cdd.eshop.common.StatusEnum;
import com.cdd.eshop.mapper.*;
import com.cdd.eshop.mapper.activity.ActivityRepository;
import com.cdd.eshop.mapper.activity.ActivityRuleRepository;
import com.cdd.eshop.service.OrderService;
import com.cdd.eshop.utils.SequenceUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;

/**
 * 订单服务实现类
 *
 * @author quan
 * @date 2021/01/06
 */
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    GoodsRepository goodsRepository;

    @Autowired
    GoodsImgRepository goodsImgRepository;

    @Autowired
    ActivityRepository activityRepository;

    @Autowired
    ActivityRuleRepository activityRuleRepository;

    @Autowired
    OrderItemRepository orderItemRepository;

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    ShopCartRepository shopCartRepository;

    @Autowired
    AddressRepository addressRepository;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseDTO placeOrder(Integer userId, OrderBO orderBO) {

        //创建订单
        Order order = new Order();
        order.setUserId(userId);
        order.setOrderNumber(SequenceUtil.get());
        Date now = new Date();
        order.setCreateTime(now);

        List<OrderItemBO> orderItemBOList = orderBO.getOrderItemList();

        //填充封面图
        Integer goodsId = orderItemBOList.get(0).getGoodsId();
        Optional<GoodsImg> imgOptional = goodsImgRepository.findById(goodsId);
        if (!imgOptional.isPresent()){
            return ResponseDTO.error(StatusEnum.PARAM_ERROR,"商品图片不存在 "+goodsId);
        }
        order.setGoodsImgUrl(imgOptional.get().getImgUrl());

        //查询数据库中的价格
        //<id , item>,减少遍历次数
        HashMap<Integer,OrderItem> itemHashMap = new HashMap<>();

        List<OrderItem> orderItemList = new LinkedList<>();

        // id列表，批量查数据库
        List<Integer> goodsIds = new LinkedList<>();

        for (OrderItemBO bo : orderItemBOList){
            goodsIds.add(bo.getGoodsId());
            OrderItem orderItem = new OrderItem();
            orderItem.setGoodsId(bo.getGoodsId());
            orderItem.setAmount(bo.getAmount());
            orderItem.setFreight(BigDecimal.ZERO);
            orderItem.setIsDelete(Boolean.FALSE);
            orderItem.setOrderNumber(order.getOrderNumber());
            orderItem.setAddressId(bo.getAddressId());
            orderItemList.add(orderItem);
            itemHashMap.put(bo.getGoodsId(),orderItem);
        }

        //总价
        BigDecimal totalPrice = new BigDecimal(0);

        //运费
        BigDecimal freight = new BigDecimal(0);

        //折扣
        BigDecimal totalDiscount = new BigDecimal(0);

        List<Goods> goodsList = goodsRepository.findAllByGoodsIdIn(goodsIds);
        //<id , goods>,计算活动的时候要用
        HashMap<Integer,Goods> goodsHashMap = new HashMap<>();
        for (Goods goods : goodsList){
            if (!goods.getStatus().equals(GoodsStatus.ON.getCode())){
                return ResponseDTO
                        .error()
                        .msg("商品状态异常!" + GoodsStatus.getStatusFromCode(goods.getStatus()).getDesc());
            }
            //计算总价
            totalPrice=totalPrice
                    .add(goods.getGoodsPrice()
                            .multiply(BigDecimal.valueOf(itemHashMap.get(goods.getGoodsId()).getAmount())));

            goodsHashMap.put(goods.getGoodsId(),goods);
            //todo 计算运费，运费应调用第三方物流系统的接口
        }
        order.setTotalPrice(totalPrice);
        order.setTotalFreight(freight);


        List<Integer> activityIds = orderBO.getActivityList();
        StringBuilder idBuilders = new StringBuilder();

        if (activityIds !=null && activityIds.size() > 0 ){
            List<Activity> activityList = activityRepository.findAllById(activityIds);

            //活动状态校验
            for (Activity activity : activityList){
                if (activity.getStatus().intValue() !=1 || activity.getEndTime().after(now)){
                    return ResponseDTO
                            .error()
                            .msg("活动状态异常！activityId= %s activity = %s",
                                    activity.getActivityId(),activity.getStatus());
                }
                idBuilders.append(activity.getActivityId());
                idBuilders.append(",");
            }
            //设置订单的关联id
            idBuilders.deleteCharAt(idBuilders.length()-1);
            order.setActivityIds(idBuilders.toString());

            List<ActivityRule> activityRuleList =activityRuleRepository.findAllByActivityIdInOrderByRuleType(activityIds);
            for (ActivityRule rule : activityRuleList){

                //如果本规则不适用，则忽略
                if (totalPrice.doubleValue() < rule.getRuleLimit()) continue;
                Float ruleVal = rule.getRuleVal();

                //不同规则不同处理，这里为减少复杂度，不做适用商品的验证
                switch (ActivityRuleType.getTypeFromCode(rule.getRuleType())){
                    case DISCOUNT:
                        totalDiscount=totalDiscount.add(totalPrice.multiply(BigDecimal.valueOf(1-ruleVal)));
                        break;
                    case FULL_REDUCE:
                        totalDiscount=totalDiscount.add(BigDecimal.valueOf(ruleVal));
                        break;
                    case ATTACH:
                        //因为规则是按类型降序的，故在这里将前面附送商品加上的价格减掉
                        //没加购物车,则为不参加活动
                        goodsId = ruleVal.intValue();
                        if (goodsHashMap.containsKey(goodsId)){
                            totalPrice=totalPrice.subtract(goodsHashMap.get(goodsId).getGoodsPrice());
                        }
                        break;

                }
            }

        }
        order.setTotalDiscount(totalDiscount);

        //计算实付金额
        BigDecimal totalPay = totalPrice
                .subtract(totalDiscount)
                .add(freight);
        if (totalPay.compareTo(BigDecimal.ZERO)<0){
            totalPay = BigDecimal.ZERO;
        }
        order.setTotalPay(totalPay);

        try {

            orderRepository.saveAndFlush(order);
            orderItemRepository.saveAll(orderItemList);
            orderItemRepository.flush();

            return ResponseDTO.success()
                    .msg("下单成功！")
                    .withKeyValueData("orderNumber",order.getOrderNumber());
        }catch (Exception e){
            return ResponseDTO.error().msg(e.getMessage());
        }
    }

    @Override
    public ResponseDTO listOrder(Integer userId, Short status) {

        List<Order> orderList = orderRepository.findAllByUserIdAndStatus(userId,status);
        if (orderList.size() < 1){
            return ResponseDTO.success().msg("订单列表为空!").data("[]");
        }
        List<OrderVO> voList = new LinkedList<>();
        orderList.forEach(order -> {
            OrderVO vo = new OrderVO();
            vo.setOrderNumber(order.getOrderNumber());
            vo.setCreateTime(order.getCreateTime());
            vo.setGoodsImgUrl(order.getGoodsImgUrl());
            vo.setStatus(order.getStatus());
            vo.setTotalPay(order.getTotalPay());
            voList.add(vo);
        });
        return ResponseDTO.success().data(voList);
    }


    /**
     * 改变状态
     *
     * @param userId       用户Id
     * @param orderNumber  订单编号
     * @param targetStatus 目标状态
     * @return {@link ResponseDTO}
     */
    private ResponseDTO changeStatus(Integer userId,String orderNumber,OrderStatusEnum targetStatus){

        //订单编号校验
        Optional<Order> optionalOrder = orderRepository.findById(orderNumber);
        if (!optionalOrder.isPresent() || !optionalOrder.get().getUserId().equals(userId)){
            return ResponseDTO.error(StatusEnum.PARAM_ERROR,"订单不存在！");
        }

        Order order = optionalOrder.get();

        //状态校验,订单未完成之前不可删除
        if (targetStatus.equals(OrderStatusEnum.DELETED)){
            if (order.getStatus() < OrderStatusEnum.RECEIPTED.getCode()){
                return ResponseDTO.error().msg("订单状态流转失败！关闭之前不可删除！");
            }
        }

        //未发货之前不可确认收货
        if (targetStatus.equals(OrderStatusEnum.RECEIPTED)){
            if (!order.getStatus().equals(OrderStatusEnum.WAIT_RECEIPT.getCode())){
                return ResponseDTO.error().msg("订单状态流转失败！未发货之前不可确认收货！");
            }
        }
        order.setStatus(targetStatus.getCode());
        orderRepository.saveAndFlush(order);
        return ResponseDTO.success();
    }

    @Override
    public ResponseDTO payOrder(Integer userId, String orderNumber) {

        //todo 拉起第三方支付调用

        //todo 异步完成订单流转，这里不接入支付，故直接模拟流转
        return this.changeStatus(userId,orderNumber,OrderStatusEnum.WAIT_SHIP);
    }

    @Override
    public ResponseDTO cancelOrder(Integer userId, String orderNumber) {
        return this.changeStatus(userId,orderNumber,OrderStatusEnum.CANCELED);
    }

    @Override
    public ResponseDTO deleteOrder(Integer userId, String orderNumber) {
        return this.changeStatus(userId,orderNumber,OrderStatusEnum.DELETED);
    }

    @Override
    public ResponseDTO detailOrder(Integer userId, String orderNumber) {

        //订单编号校验
        Optional<Order> optionalOrder = orderRepository.findById(orderNumber);
        if (!optionalOrder.isPresent() || !optionalOrder.get().getUserId().equals(userId)){
            return ResponseDTO.error(StatusEnum.PARAM_ERROR,"订单不存在！");
        }

        Order order = optionalOrder.get();

        //填充基本订单属性
        OrderDetailVO detailVO = new OrderDetailVO();
        BeanUtils.copyProperties(order,detailVO);


        //填充orderItem
        List<OrderItem> orderItemList = orderItemRepository.findAllByOrderNumber(orderNumber);
        HashMap<Integer,OrderItem> itemHashMap = new HashMap<>();
        List<Integer> goodsIds = new LinkedList<>();

        for (OrderItem item:orderItemList){
            goodsIds.add(item.getGoodsId());
            itemHashMap.put(item.getGoodsId(),item);
        }
        List<Goods> goodsList = goodsRepository.findAllByGoodsIdIn(goodsIds);
        List<GoodsImg> goodsImgList = goodsImgRepository.findAllByGoodsIdIn(goodsIds);

        List<OrderItemVO> itemVOList = new LinkedList<>();
        for (Goods goods : goodsList){
             OrderItemVO vo = new OrderItemVO();
             OrderItem item = itemHashMap.get(goods.getGoodsId());
             vo.setAmount(item.getAmount());
             vo.setGoodsName(goods.getGoodsName());
             vo.setGoodsId(goods.getGoodsId());
             for (GoodsImg img : goodsImgList){
                 if (img.getGoodsId().equals(goods.getGoodsId())){
                     vo.setGoodsImgUrl(img.getImgUrl());
                     break;
                 }
             }
             itemVOList.add(vo);
        }
        detailVO.setOrderItemList(itemVOList);

        //填充活动列表
        String[] activityArray = null;
        if(order.getActivityIds()!=null){
            activityArray = order.getActivityIds().split(",");
            if (activityArray.length >1){
                List<Integer> activities = new LinkedList<>();
                for (String s : activityArray) {
                    try {
                        activities.add(Integer.parseInt(s));
                    } catch (Exception ignored) {
                    }
                }
                List<Activity> activityList = activityRepository.findAllById(activities);
                detailVO.setActivityList(activityList);
            }

        }
        return ResponseDTO.success().data(detailVO);
    }

    @Override
    public ResponseDTO receiptOrder(Integer userId, String orderNumber) {
        return this.changeStatus(userId,orderNumber,OrderStatusEnum.RECEIPTED);
    }

    @Override
    public ResponseDTO shipOrder(String orderNumber) {
        //订单编号校验
        Optional<Order> optionalOrder = orderRepository.findById(orderNumber);
        if (!optionalOrder.isPresent()){
            return ResponseDTO.error(StatusEnum.PARAM_ERROR,"订单不存在！");
        }
        Order order = optionalOrder.get();

        if (!order.getStatus().equals(OrderStatusEnum.WAIT_SHIP.getCode())){
            return ResponseDTO.error().msg("订单状态流转失败！订单未在等待发货中！");
        }
        order.setStatus(OrderStatusEnum.RECEIPTED.getCode());
        orderRepository.saveAndFlush(order);
        return ResponseDTO.success();
    }

    @Override
    public ResponseDTO refundOrder(String orderNumber) {
        //订单编号校验
        Optional<Order> optionalOrder = orderRepository.findById(orderNumber);
        if (!optionalOrder.isPresent()){
            return ResponseDTO.error(StatusEnum.PARAM_ERROR,"订单不存在！");
        }
        Order order = optionalOrder.get();

        if (!order.getStatus().equals(OrderStatusEnum.WAIT_SHIP.getCode())){
            return ResponseDTO.error().msg("订单状态流转失败！订单未在等待退款中！");
        }
        order.setStatus(OrderStatusEnum.RECEIPTED.getCode());
        orderRepository.saveAndFlush(order);
        return ResponseDTO.success();
    }
}

