package com.cdd.eshop.bean.bo;

import com.cdd.eshop.bean.po.GoodsType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 批处理商品类型业务对象
 *
 * @author quan
 * @date 2021/01/08
 */
@Data
@ApiModel
public class BatchGoodsTypeBO {
    /**
     * 商品类型列表
     */
    @ApiModelProperty(value = "商品类型列表")
    private List<GoodsType> goodsTypeList;
}
