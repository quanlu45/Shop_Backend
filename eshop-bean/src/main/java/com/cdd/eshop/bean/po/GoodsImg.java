package com.cdd.eshop.bean.po;

import lombok.Data;

import javax.persistence.*;

/**
 * 商品img
 *
 * @author quan
 * @date 2021/01/06
 */
@Data
@Entity
@Table(name = "tb_goods_img")
public class GoodsImg {

    /**
     * imgId
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer imgId;

    /**
     * 商品Id
     */
    private Integer goodsId;

    /**
     * img url
     */
    private String imgUrl;


}
