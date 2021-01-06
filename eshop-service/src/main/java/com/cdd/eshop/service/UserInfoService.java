package com.cdd.eshop.service;

import com.cdd.eshop.bean.dto.ResponseDTO;
import com.cdd.eshop.bean.po.Address;
import com.cdd.eshop.bean.po.User;


/**
 * 用户信息服务
 *
 * @author quan
 * @date 2020/12/21
 */
public interface UserInfoService {


    /**
     * 得到用户信息通过Id
     *
     * @param userId 用户Id
     * @return {@link ResponseDTO}
     */
    ResponseDTO getUserInfoById(Integer userId);


    /**
     * 更新用户信息通过Id
     *
     * @param user 用户
     * @return {@link ResponseDTO}
     */
    ResponseDTO updateUserInfoById(User user);


    /**
     * 保存或更新交货地址
     *
     * @param address 地址
     * @return {@link ResponseDTO}
     */
    ResponseDTO saveOrUpdateDeliveryAddress(Address address);


    /**
     * 通过Id删除配送地址
     *
     * @param addressId 地址Id
     * @return {@link ResponseDTO}
     */
    ResponseDTO deleteDeliveryAddressById(Integer userId ,Integer addressId);


    /**
     * 通过Id设置默认的地址
     *
     * @param addressId 地址Id
     * @return {@link ResponseDTO}
     */
    ResponseDTO setDefaultAddressById(Integer userId,Integer addressId);


    /**
     * 地址列表
     *
     * @param userId 用户Id
     * @return {@link ResponseDTO}
     */
    ResponseDTO listAddress(Integer userId);


}



