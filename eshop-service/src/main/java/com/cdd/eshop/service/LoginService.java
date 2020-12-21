package com.cdd.eshop.service;

import com.cdd.eshop.bean.dto.ResponseDTO;
import com.cdd.eshop.bean.po.User;


/**
 * 登录服务
 *
 * @author quan
 * @date 2020/12/21
 */
public interface LoginService {


    /**
     * 登录
     *
     * @param userName 用户名
     * @param password 密码
     * @return {@link ResponseDTO}
     */
    ResponseDTO login(String userName,String password);


    /**
     * 注销
     *
     * @return {@link ResponseDTO}
     */
    ResponseDTO logout();


    /**
     * 注册
     *
     * @param user 用户
     * @return {@link ResponseDTO}
     */
    ResponseDTO register(User user);
}
