package com.cdd.eshop.bean.bo.order;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 订单项业务对象
 *
 * @author quan
 * @date 2021/01/08
 */
@Data
@ApiModel
public class OrderItemBO {
    /**
     * 商品Id
     */
    @ApiModelProperty(name = "商品Id")
    private Integer goodsId;

    /**
     * 数量
     */
    @ApiModelProperty(name = "数量")
    private Integer amount;

    /**
     * 地址Id
     */
    @ApiModelProperty(name = "数量")
    private Integer addressId;

}
