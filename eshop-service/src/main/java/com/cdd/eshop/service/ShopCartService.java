package com.cdd.eshop.service;


import com.cdd.eshop.bean.dto.ResponseDTO;
import com.cdd.eshop.bean.po.ShopCart;

/**
 * 商店购物车服务
 *
 * @author quan
 * @date 2021/01/06
 */
public interface ShopCartService {

    /**
     * 添加物品
     *
     * @param shopCartItem 商店购物车条目
     * @return {@link ResponseDTO}
     */
    ResponseDTO addItem(ShopCart shopCartItem);

    /**
     * 删除项目
     *
     * @param userId     用户Id
     * @param shopCartId 商店车Id
     * @return {@link ResponseDTO}
     */
    ResponseDTO deleteItem(Integer userId,Integer shopCartId);

    /**
     * 列表项
     *
     * @param userId 用户Id
     * @return {@link ResponseDTO}
     */
    ResponseDTO listItem(Integer userId);

    /**
     * 更新项目
     *
     * @param shopCartItem 商店购物车条目
     * @return {@link ResponseDTO}
     */
    ResponseDTO updateItem(ShopCart shopCartItem);
}
