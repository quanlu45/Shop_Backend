package com.cdd.eshop.bean.bo.order;

import com.cdd.eshop.bean.po.OrderItem;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 订单业务对象
 *
 * @author quan
 * @date 2020/12/29
 */
@Data
@ApiModel
public class OrderBO {

    @ApiModelProperty(value = "商品列表对")
    private List<OrderItem> orderItemList;

    @ApiModelProperty(value = "是否来自购物车结算")
    private Boolean isFromShopCart;

    @ApiModelProperty(value = "活动列表")
    private List<Integer> activityList;

}
