package com.cdd.eshop.bean.bo;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * 商品业务对象
 *
 * @author quan
 * @date 2021/01/08
 */
@Data
@ApiModel
public class GoodsBO {
    /**
     * 商品Id
     */
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
    private Integer goodsTypeId;

    /**
     * 商品库存
     */
    private Integer goodsStock;

    /**
     * 商品价格
     */
    private BigDecimal goodsPrice;

    /**
     * 状态
     */
    private Short status;

    /**
     * 商品img
     */
    private List<String> goodsImgUrls;
}
