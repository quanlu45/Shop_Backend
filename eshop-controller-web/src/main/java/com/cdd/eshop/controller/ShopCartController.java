package com.cdd.eshop.controller;


import com.cdd.eshop.bean.dto.ResponseDTO;
import com.cdd.eshop.bean.po.ShopCart;
import com.cdd.eshop.common.BaseController;
import com.cdd.eshop.service.ShopCartService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController("v1/cart")
public class ShopCartController extends BaseController {

    @Autowired
    ShopCartService shopCartService;

    @PostMapping("/add")
    @ApiOperation(value = "添加到购物车项")
    ResponseDTO addItem(@RequestBody ShopCart shopCartItem, HttpServletRequest request) {
        shopCartItem.setUserId(this.getUserId(request));
        return shopCartService.addItem(shopCartItem);
    }

    @GetMapping("/delete")
    @ApiOperation(value = "删除购物车项")
    ResponseDTO deleteItem(@RequestParam(value = "shopCartId",required = true) Integer shopCartId,
                           HttpServletRequest request) {
        return shopCartService.deleteItem(this.getUserId(request),shopCartId);
    }

    @GetMapping("/list")
    @ApiOperation(value = "列出购物车")
    ResponseDTO listItem(HttpServletRequest request) {
        return shopCartService.listItem(this.getUserId(request));
    }

    @PostMapping("/update")
    @ApiOperation(value = "更新购物车项")
    ResponseDTO updateItem(@RequestBody ShopCart shopCartItem) {
        return shopCartService.updateItem(shopCartItem);
    }
}
