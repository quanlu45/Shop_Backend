package com.cdd.eshop.service;

import com.cdd.eshop.bean.dto.ResponseDTO;
import com.cdd.eshop.bean.po.GoodsType;

import java.util.List;

/**
 * 商品类型服务
 *
 * @author quan
 * @date 2020/12/22
 */
public interface GoodsTypeService {

    /**
     * 商品类型列表
     *
     * @return {@link ResponseDTO}
     */
    ResponseDTO listGoodsType(String parentTypeCode);


    /**
     * 保存或更新商品类型
     *
     * @return {@link ResponseDTO}
     */
    ResponseDTO saveOrUpdateGoodsType(GoodsType goodsType);


    /**
     * 批量保存或更新商品类型
     *
     * @param goodsType 商品类型
     * @return {@link ResponseDTO}
     */
    ResponseDTO batchSaveOrUpdateGoodsType(List<GoodsType> goodsType);

    /**
     * 通过类型Id删除商品类型
     *
     * @return {@link ResponseDTO}
     */
    ResponseDTO deleteGoodsTypeById(Integer typeId);


    /**
     * 得到货物类型通过Id
     *
     * @param typeId 类型Id
     * @return {@link ResponseDTO}
     */
    ResponseDTO getGoodsTypeById(Integer typeId);


    /**
     * 返回类型树
     *
     * @param parentTypeCode 父类型代码
     * @return {@link ResponseDTO}
     */
    ResponseDTO treeType(String parentTypeCode);
}
