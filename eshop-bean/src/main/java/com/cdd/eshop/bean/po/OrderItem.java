package com.cdd.eshop.bean.po;


import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;

/**
 * 订单项
 *
 * @author quan
 * @date 2021/01/07
 */
@Data
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "tb_order_item")
public class OrderItem {

    /**
     * 订单数量
     */
    @Id
    private String orderNumber;

    /**
     * 商品Id
     */
    private Integer goodsId;

    /**
     * 数量
     */
    private Integer amount;

    /**
     * 地址Id
     */
    private Integer addressId;

    /**
     * 运费
     */
    private BigDecimal freight;


    /**
     * 是否删除
     */
    private Boolean isDelete;
}
