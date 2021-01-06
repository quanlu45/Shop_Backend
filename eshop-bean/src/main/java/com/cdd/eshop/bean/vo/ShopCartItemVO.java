package com.cdd.eshop.bean.vo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 购物车车项视图对象
 *
 * @author quan
 * @date 2021/01/06
 */
@Data
public class ShopCartItemVO {

    /**
     * 商店车Id
     */
    private Integer shopCartId;

    /**
     * 商品Id
     */
    private Integer goodsId;

    /**
     * 商品的名字
     */
    private String goodsName;

    /**
     * 商品imgurl
     */
    private String goodsImgUrl;

    /**
     * 价格
     */
    private BigDecimal price;

    /**
     * 数量
     */
    private Integer amount;

}
