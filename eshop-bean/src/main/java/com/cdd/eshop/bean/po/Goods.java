package com.cdd.eshop.bean.po;


import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * 商品
 *
 * @author quan
 * @date 2020/12/29
 */
@Data
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "tb_goods")
public class Goods {

    /**
     * 商品Id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer goodsId;

    /**
     * 商品的名字
     */
    private String goodsName;

    /**
     * 商品描述
     */
    private String goodsDesc;

    /**
     * 商品类型Id
     */
    private String goodsTypeCode;

    /**
     * 商品库存
     */
    private Integer goodsStock;

    /**
     * 商品出售数量
     */
    private Integer goodsSold;

    /**
     * 商品价格
     */
    private BigDecimal goodsPrice;

    /**
     * 状态
     */
    private Short status;
}
