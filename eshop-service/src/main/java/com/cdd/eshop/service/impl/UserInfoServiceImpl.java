package com.cdd.eshop.service.impl;


import com.alibaba.fastjson.JSON;
import com.cdd.eshop.bean.dto.ResponseDTO;
import com.cdd.eshop.bean.po.Address;
import com.cdd.eshop.bean.po.User;
import com.cdd.eshop.bean.vo.UserInfoVO;
import com.cdd.eshop.common.StatusEnum;
import com.cdd.eshop.mapper.AddressRepository;
import com.cdd.eshop.mapper.UserRepository;
import com.cdd.eshop.service.UserInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


/**
 * 用户信息服务实现类
 *
 * @author quan
 * @date 2020/12/21
 */
@Service
@Slf4j
public class UserInfoServiceImpl implements UserInfoService {


    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AddressRepository addressRepository;


    @Override
    public ResponseDTO getUserInfoById(Integer userId) {

        User user = userRepository.getOne(userId);
        UserInfoVO vo = new UserInfoVO();
        BeanUtils.copyProperties(user,vo);
        return ResponseDTO.success().data(vo);
    }

    @Override
    public ResponseDTO updateUserInfoById(User user) {

        user.setPwd(null);
        user.setRegTime(null);
        try {
            userRepository.saveAndFlush(user);
            return ResponseDTO.success().withKeyValueData("userId",user.getUserId());
        }catch (DataIntegrityViolationException e){
            log.info("updateUserInfoById===> {} ,user ={}","用户名重复", JSON.toJSONString(user));
            return ResponseDTO.error(StatusEnum.PARAM_ERROR,"用户名重复！请修改后再试！");
        }catch (Exception e){
            log.error("updateUserInfoById===> {} ,user ={}",e.getMessage(), JSON.toJSONString(user));
            return ResponseDTO.error(StatusEnum.SERVER_ERROR,e.getCause().getMessage());
        }
    }

    @Override
    public ResponseDTO saveOrUpdateDeliveryAddress(Address address) {
        try {
            address.setStatus(null);
            addressRepository.saveAndFlush(address);
            return ResponseDTO.success().withKeyValueData("addressId",address.getAddressId());
        }catch (Exception e){
            log.error("saveOrUpdateDeliveryAddress===> {} ,address ={}",e.getCause().getMessage(),JSON.toJSONString(address));
            return ResponseDTO.error().msg(e.getCause().getMessage());
        }
    }

    @Override
    public ResponseDTO deleteDeliveryAddressById(Integer userId , Integer addressId) {

        try {
            addressRepository.deleteAddressById(addressId);
        }catch (Exception e){
            log.error("deleteDeliveryAddressById===> {} ,addressId ={}",e.getMessage(), addressId);
            return ResponseDTO.error(StatusEnum.SERVER_ERROR,e.getCause().getMessage());
        }
        return ResponseDTO.success();
    }


    @Override
    public ResponseDTO setDefaultAddressById(Integer userId, Integer addressId) {

        List<Address> list = new ArrayList<>(2);

        /**去除以前的默认地址*/
        Address oldDefaultAddress = addressRepository.selectDefaultAddress(userId);
        if (oldDefaultAddress != null){
            oldDefaultAddress.setStatus((short)1);
            list.add(oldDefaultAddress);
        }

        /**新的默认地址**/
        Address newDefaultAddress = new Address();
        newDefaultAddress.setAddressId(addressId);
        newDefaultAddress.setStatus((short)2);
        list.add(newDefaultAddress);

        try {
            addressRepository.saveAll(list);
            return ResponseDTO.success();
        }catch (Exception e){
            log.error("setDefaultAddressById===> {} ,addressId ={}",e.getMessage(), addressId);
            return ResponseDTO.error(StatusEnum.SERVER_ERROR,e.getCause().getMessage());
        }

    }

    @Override
    public ResponseDTO listAddress(Integer userId) {
        List<Address> addressList = addressRepository.selectAllByUserId(userId);
        return ResponseDTO.success().data(addressList);
    }
}
