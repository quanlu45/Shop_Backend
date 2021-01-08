package com.cdd.eshop.bean.vo;

import com.cdd.eshop.bean.po.Goods;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 商品视图对象
 *
 * @author quan
 * @date 2021/01/08
 */
@Data
@ApiModel
public class GoodsVO extends Goods {
    /**
     * 商品相册列表
     */
    @ApiModelProperty(name = "商品相册列表")
    List<String> goodsImgs ;
}
