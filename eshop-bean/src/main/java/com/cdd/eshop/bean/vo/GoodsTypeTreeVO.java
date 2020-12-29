package com.cdd.eshop.bean.vo;

import com.cdd.eshop.bean.po.GoodsType;
import lombok.Data;

import java.util.List;

/**
 * 商品类型树视图对象
 *
 * @author quan
 * @date 2020/12/28
 */
@Data
public class GoodsTypeTreeVO extends GoodsType {

    /**
     * 子节点
     */
    List<GoodsTypeTreeVO> children;
}
