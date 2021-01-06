package com.cdd.eshop.service.impl;

import com.cdd.eshop.bean.bo.order.OrderBO;
import com.cdd.eshop.bean.dto.ResponseDTO;
import com.cdd.eshop.bean.po.Goods;
import com.cdd.eshop.bean.po.GoodsStatus;
import com.cdd.eshop.bean.po.Order;
import com.cdd.eshop.bean.po.OrderItem;
import com.cdd.eshop.bean.po.activity.Activity;
import com.cdd.eshop.bean.po.activity.ActivityRule;
import com.cdd.eshop.bean.po.activity.ActivityRuleType;
import com.cdd.eshop.common.StatusEnum;
import com.cdd.eshop.mapper.GoodsRepository;
import com.cdd.eshop.mapper.OrderItemRepository;
import com.cdd.eshop.mapper.OrderRepository;
import com.cdd.eshop.mapper.activity.ActivityRepository;
import com.cdd.eshop.mapper.activity.ActivityRuleRepository;
import com.cdd.eshop.service.OrderService;
import com.cdd.eshop.utils.SequenceUtil;
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
    ActivityRepository activityRepository;

    @Autowired
    ActivityRuleRepository activityRuleRepository;

    @Autowired
    OrderItemRepository orderItemRepository;

    @Autowired
    OrderRepository orderRepository;


    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseDTO placeOrder(Integer userId, OrderBO orderBO) {

        //创建订单
        Order order = new Order();
        order.setUserId(userId);
        order.setOrderNumber(SequenceUtil.get());

        Date now = new Date();
        order.setCreateTime(now);

        //查询数据库中的价格
        List<OrderItem> orderItemList = orderBO.getOrderItemList();

        //<id , item>,减少遍历次数
        HashMap<Integer,OrderItem> itemHashMap = new HashMap<>();

        // id列表，批量查数据库
        List<Integer> goodsIds = new LinkedList<>();

        for (OrderItem item : orderItemList){
            goodsIds.add(item.getGoodsId());
            itemHashMap.put(item.getGoodsId(),item);
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
            goods.getGoodsPrice()
                    .multiply(BigDecimal.valueOf(itemHashMap.get(goods.getGoodsId()).getAmount()))
                    .add(totalPrice);

            goodsHashMap.put(goods.getGoodsId(),goods);
            //todo 计算运费，运费应调用第三方物流系统的接口
        }
        order.setTotalPrice(totalPrice);
        order.setTotalFreight(totalPrice);


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
                        totalDiscount.add(totalPrice.multiply(BigDecimal.valueOf(1-ruleVal)));
                        break;
                    case FULL_REDUCE:
                        totalDiscount.add(BigDecimal.valueOf(ruleVal));
                        break;
                    case ATTACH:
                        //因为规则是按类型降序的，故在这里将前面附送商品加上的价格减掉
                        //没加购物车,则为不参加活动
                        Integer goodsId = ruleVal.intValue();
                        if (goodsHashMap.containsKey(goodsId)){
                            totalPrice.subtract(goodsHashMap.get(goodsId).getGoodsPrice());
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
}
