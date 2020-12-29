package com.cdd.eshop.service;

import com.cdd.eshop.bean.dto.ResponseDTO;

/**
 * 商品服务
 *
 * @author quan
 * @date 2020/12/29
 */
public interface GoodsService {


    /**
     * 得到商品通过Id
     *
     * @param goodsId 商品Id
     * @return {@link ResponseDTO}
     */
    public ResponseDTO getGoodsById(Integer goodsId);


    /**
     * search商品通过name
     *
     * @param goodsName 商品name
     * @return {@link ResponseDTO}
     */
    public ResponseDTO searchGoodsByName(String goodsName,Integer pageNumber, Integer pageSize);


    /**
     * 分页列表商品通过类型代码
     *
     * @param goodsTypeCode 商品类型代码
     * @param pageNumber    页码
     * @param pageSize      页面大小
     * @return {@link ResponseDTO}
     */
    public ResponseDTO listGoodsByTypeCode(String goodsTypeCode, Integer pageNumber, Integer pageSize);

    /**
     * 上架商品
     *
     * @param goodsId 商品Id
     * @return {@link ResponseDTO}
     */
    public ResponseDTO putOn(Integer goodsId);


    /**
     * 下架商品
     *
     * @param goodsId 商品Id
     * @return {@link ResponseDTO}
     */
    public ResponseDTO putOff(Integer goodsId);

    /**
     * 删除商品通过Id
     *
     * @param goodsId 商品Id
     * @return {@link ResponseDTO}
     */
    public ResponseDTO deleteGoodsById(Integer goodsId);


}
