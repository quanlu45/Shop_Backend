package com.cdd.eshop.bean.vo;

import com.cdd.eshop.bean.po.activity.Activity;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 订单细节视图对象
 *
 * @author quan
 * @date 2021/01/07
 */
@Data
public class OrderDetailVO extends OrderVO{

    /**
     * 完成时间
     */
    private Date finishTime;


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
     * 活动列表
     */
    private List<Activity> activityList;

    /**
     * 订单项列表
     */
    private List<OrderItemVO> orderItemList;

}
