package com.cdd.eshop.bean.po;

import lombok.Data;

import javax.persistence.*;

/**
 * 商店购物车
 *
 * @author quan
 * @date 2021/01/06
 */
@Data
@Entity
@Table(name = "tb_shop_cart")
public class ShopCart {

    /**
     * 购物车Id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer cartId;

    /**
     * 用户Id
     */
    private Integer userId;

    /**
     * 商品Id
     */
    private Integer goodsId;

    /**
     * 数量
     */
    private Integer amount;
}
