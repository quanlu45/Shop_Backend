package com.cdd.eshop.service;


import com.cdd.eshop.bean.bo.order.OrderBO;
import com.cdd.eshop.bean.dto.ResponseDTO;

/**
 * 订单服务
 *
 * @author quan
 * @date 2021/01/06
 */
public interface OrderService {

    /**
     * 下单
     *
     * @param orderBO 订单业务对象
     * @return {@link ResponseDTO}
     */
    ResponseDTO placeOrder(Integer userId,OrderBO orderBO);


    /**
     * 列出订单
     *
     * @param userId 用户Id
     * @param status 状态
     * @return {@link ResponseDTO}
     */
    ResponseDTO listOrder(Integer userId,Short status);

    /**
     * 支付订单
     *
     * @param orderNumber 订单编号
     * @return {@link ResponseDTO}
     */
    ResponseDTO payOrder(Integer userId,String orderNumber);


    /**
     * 关闭订单
     *
     * @param userId      用户Id
     * @param orderNumber 订单编号
     * @return {@link ResponseDTO}
     */
    ResponseDTO cancelOrder(Integer userId,String orderNumber);

    /**
     * 删除订单
     *
     * @param userId      用户Id
     * @param orderNumber 订单编号
     * @return {@link ResponseDTO}
     */
    ResponseDTO deleteOrder(Integer userId,String orderNumber);

    /**
     * 细节订单
     *
     * @param userId      用户Id
     * @param orderNumber 订单编号
     * @return {@link ResponseDTO}
     */
    ResponseDTO detailOrder(Integer userId,String orderNumber);

}
