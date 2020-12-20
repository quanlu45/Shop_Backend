package com.cdd.eshop.service;

import com.cdd.eshop.bean.dto.ResponseDTO;
import com.cdd.eshop.bean.po.User;


public interface LoginService {
    ResponseDTO login(String userName,String password);
    ResponseDTO logout();
    ResponseDTO register(User user);
}
