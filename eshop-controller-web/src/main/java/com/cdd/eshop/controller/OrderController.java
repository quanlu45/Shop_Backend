package com.cdd.eshop.controller;

import com.cdd.eshop.bean.bo.order.OrderBO;
import com.cdd.eshop.bean.dto.ResponseDTO;
import com.cdd.eshop.common.BaseController;
import com.cdd.eshop.common.StatusEnum;
import com.cdd.eshop.service.OrderService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/list")
    @ApiOperation(value = "列出订单")
    ResponseDTO listOrder(@RequestParam(value = "status",required = false) Short status,HttpServletRequest request) {
        return orderService.listOrder(this.getUserId(request),status);
    }


    @PostMapping("/pay")
    @ApiOperation(value = "支付订单")
    ResponseDTO payOrder(@RequestParam(value = "orderNumber",required = true) String orderNumber,HttpServletRequest request) {
        return orderService.payOrder(this.getUserId(request),orderNumber);
    }

    @PostMapping("/cancle")
    @ApiOperation(value = "取消订单")
    ResponseDTO cancelOrder(@RequestParam(value = "orderNumber",required = true) String orderNumber,HttpServletRequest request) {
        return orderService.cancelOrder(this.getUserId(request),orderNumber);
    }

    @PostMapping("/delete")
    @ApiOperation(value = "删除订单")
    ResponseDTO deleteOrder(@RequestParam(value = "orderNumber",required = true) String orderNumber,HttpServletRequest request) {
        return orderService.deleteOrder(this.getUserId(request),orderNumber);
    }

    @GetMapping("/detail")
    @ApiOperation(value = "列出订单详细")
    ResponseDTO detailOrder(@RequestParam(value = "orderNumber",required = true) String orderNumber,HttpServletRequest request) {
        return orderService.detailOrder(this.getUserId(request),orderNumber);
    }

    @GetMapping("/receipt")
    @ApiOperation(value = "签收订单")
    ResponseDTO receiptOrder(@RequestParam(value = "orderNumber",required = true) String orderNumber,HttpServletRequest request) {
        return orderService.detailOrder(this.getUserId(request),orderNumber);
    }


}
