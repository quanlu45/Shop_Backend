package com.cdd.eshop.controller;


import com.cdd.eshop.bean.dto.ResponseDTO;
import com.cdd.eshop.bean.po.User;
import com.cdd.eshop.common.StatusEnum;
import com.cdd.eshop.filter.LoginHandlerInterceptor;
import com.cdd.eshop.service.LoginService;
import com.cdd.eshop.utils.JwtUtil;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;


@RestController("/api/user")
public class UserController {

    @Autowired
    LoginService loginService;

    @Value("${token.private-key}")
    String tokenPrivateKey;

    @PostMapping("/login")
    @ApiOperation("用户登录接口，返回用户信息")
    ResponseDTO login(@RequestParam(value = "userName")String userName,
                      @RequestParam(value = "password")String password){

        ResponseDTO dto = loginService.login(userName,password);
        LoginHandlerInterceptor.blackTokenList.remove(((User)dto.getData()).getUserId());
        return dto;
    }


    @PostMapping("/register")
    @ApiOperation("用户登录接口，返回注册结果")
    ResponseDTO register(@RequestBody User user){
        if (StringUtils.isBlank(user.getUserName()) ||
            StringUtils.isBlank(user.getPwd()))
            return ResponseDTO.error(StatusEnum.PARAM_ERROR,"用户名或密码不可为空！请重拾");
        return loginService.register(user);
    }

    @GetMapping("/logout")
    @ApiOperation("注销登录接口")
    ResponseDTO logout(HttpServletRequest request){
        Integer userId = JwtUtil.verifyAndGetId(tokenPrivateKey,request.getHeader("accesstoken"));
        LoginHandlerInterceptor.blackTokenList.put(userId,true);
        return loginService.logout();
    }

}
