package com.cdd.eshop.bean.vo;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 订单项视图对象
 *
 * @author quan
 * @date 2021/01/07
 */
@Data
@ApiModel
public class OrderItemVO {
    /**
     * 商品的名字
     */
    private String goodsName;

    /**
     * 商品img url
     */
    private String goodsImgUrl;

    /**
     * 商品Id
     */
    private Integer goodsId;

    /**
     * 数量
     */
    private Integer amount;

    /**
     * 价格
     */
    private BigDecimal price;

    /**
     * 地址
     */
    private String address;
}
