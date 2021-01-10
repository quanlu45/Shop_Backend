package com.cdd.eshop.controller;

import com.cdd.eshop.bean.dto.ResponseDTO;
import com.cdd.eshop.common.BaseController;
import com.cdd.eshop.service.OrderService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * 订单控制器
 *
 * @author NeJan
 * @date 2020/12/29
 */
@RestController
@RequestMapping("/v1/order")
public class OrderManagerController extends BaseController {

    @Autowired
    OrderService orderService;

    @GetMapping("/list")
    @ApiOperation(value = "列出订单")
    ResponseDTO listOrder(@RequestParam(value = "status",required = false) Short status,HttpServletRequest request) {
        return orderService.listOrder(this.getUserId(request),status);
    }

    @PostMapping("/cancle")
    @ApiOperation(value = "取消订单")
    ResponseDTO cancelOrder(@RequestParam(value = "orderNumber",required = true) String orderNumber,HttpServletRequest request) {
        return orderService.cancelOrder(this.getUserId(request),orderNumber);
    }

    @GetMapping("/detail")
    @ApiOperation(value = "列出订单详细")
    ResponseDTO detailOrder(@RequestParam(value = "orderNumber",required = true) String orderNumber,HttpServletRequest request) {
        return orderService.detailOrder(this.getUserId(request),orderNumber);
    }

    @PostMapping("/ship")
    @ApiOperation(value = "发货")
    ResponseDTO shipOrder(@RequestParam(value = "orderNumber") String orderNumber) {
        return orderService.shipOrder(orderNumber);
    }

    @PostMapping("/refund")
    @ApiOperation(value = "退款")
    ResponseDTO refundOrder(@RequestParam(value = "orderNumber") String orderNumber) {
        return orderService.refundOrder(orderNumber);
    }


    @PostMapping("/receipt")
    @ApiOperation(value = "签收订单")
    ResponseDTO receiptOrder(String orderNumber,HttpServletRequest request) {
        return orderService.receiptOrder(this.getUserId(request),orderNumber);
    }


}
