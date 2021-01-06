package com.cdd.eshop.controller;

import com.cdd.eshop.bean.bo.order.OrderBO;
import com.cdd.eshop.bean.dto.ResponseDTO;
import com.cdd.eshop.common.BaseController;
import com.cdd.eshop.common.StatusEnum;
import com.cdd.eshop.service.OrderService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * 订单控制器
 *
 * @author quan
 * @date 2020/12/29
 */
@RestController
@RequestMapping("/v1/order")
public class OrderController extends BaseController {

    @Autowired
    OrderService orderService;

    @PostMapping("/place")
    @ApiOperation(value = "下单")
    public ResponseDTO place(@RequestBody OrderBO orderBO, HttpServletRequest request) {

        if (null == orderBO.getOrderItemList() || orderBO.getOrderItemList().size() <1){
            return ResponseDTO.error(StatusEnum.PARAM_ERROR,"订单商品不可为空!");
        }
        return orderService.placeOrder(this.getUserId(request),orderBO);
    }



}
