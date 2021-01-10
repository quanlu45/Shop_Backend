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
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


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

    @Override
    public ResponseDTO listUser(Boolean isAdmin,Integer pageNumber,Integer pageSize,Short status) {

        User condition = new User();
        condition.setIsAdmin(isAdmin);
        condition.setStatus(status);

        //组装分页参数
        Pageable pageable = PageRequest.of(pageNumber,pageSize, Sort.Direction.ASC,"userId");

        Page<User> userPage = userRepository.findAll(Example.of(condition),pageable);

        Page<UserInfoVO> voPage = userPage.map(user -> {
            UserInfoVO vo = new UserInfoVO();
            BeanUtils.copyProperties(user,vo);
            return vo;
        });

        return ResponseDTO.success().data(voPage);
    }


    /**
     * 改变用户状态
     *
     * @param userId 用户Id
     * @param target 目标
     * @return {@link ResponseDTO}
     */
    private ResponseDTO changeUserStatus(Integer userId,Short target){

        User condition = new User();
        condition.setUserId(userId);

        Optional<User> userOptional = userRepository.findById(userId);
        if (!userOptional.isPresent() || userOptional.get().getStatus() == 2) {
            return ResponseDTO.error(StatusEnum.PARAM_ERROR,"该用户不存在或者已删除！");
        }
        User user = userOptional.get();

        if (user.getStatus().equals(target)){
            return ResponseDTO.error().msg("已经是此状态！不可再重复改变！");
        }
        user.setStatus(target);
        userRepository.saveAndFlush(user);

        return ResponseDTO.success().data(userId);
    }

    @Override
    public ResponseDTO blockUser(Integer userId) {
        return this.changeUserStatus(userId,(short)1);
    }

    @Override
    public ResponseDTO unblockUser(Integer userId) {
        return this.changeUserStatus(userId,(short)0);
    }
}
