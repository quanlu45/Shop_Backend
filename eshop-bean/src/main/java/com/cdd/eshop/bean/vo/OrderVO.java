package com.cdd.eshop.bean.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 订单视图对象
 *
 * @author quan
 * @date 2021/01/06
 */
@Data
@ApiModel
public class OrderVO{

    /**
     * 订单编号
     */
    @ApiModelProperty(name = "订单编号")
    private String orderNumber;

    /**
     * 总支付
     */
    @ApiModelProperty(name = "总支付")
    private BigDecimal totalPay;

    /**
     * 创建时间
     */
    @ApiModelProperty(name = "创建时间")
    private Date createTime;

    /**
     * 状态
     */
    @ApiModelProperty(name = "状态")
    private Short Status;

    /**
     * 第一个商品的img url
     */
    @ApiModelProperty(name = "第一个商品url")
    private String goodsImgUrl;


}
