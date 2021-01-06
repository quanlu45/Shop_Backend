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

}
