package com.cdd.eshop.bean.po;

import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 订单
 *
 * @author quan
 * @date 2021/01/06
 */
@Data
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "tb_order")
public class Order {

    /**
     * 订单编号
     */
    @Id
    private String orderNumber;


    /**
     * 用户Id
     */
    private Integer userId;


    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 完成时间
     */
    private Date finishTime;

    /**
     * 活动id
     */
    private String activityIds;


    /**
     * 订单封面图url
     */
    private String goodsImgUrl;

    /**
     * 运费
     */
    private BigDecimal totalFreight;


    /**
     * 总价格
     */
    private BigDecimal totalPrice;

    /**
     * 总折扣
     */
    private BigDecimal totalDiscount;


    /**
     * 总支付
     */
    private BigDecimal totalPay;

    /**
     * 状态
     */
    private Short status;
}
