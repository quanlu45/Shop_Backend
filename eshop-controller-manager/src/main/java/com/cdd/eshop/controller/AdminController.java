package com.cdd.eshop.controller;


import com.cdd.eshop.bean.bo.LoginBO;
import com.cdd.eshop.bean.dto.ResponseDTO;
import com.cdd.eshop.bean.po.Address;
import com.cdd.eshop.bean.po.User;
import com.cdd.eshop.bean.vo.UserInfoVO;
import com.cdd.eshop.common.BaseController;
import com.cdd.eshop.common.StatusEnum;
import com.cdd.eshop.filter.LoginHandlerInterceptor;
import com.cdd.eshop.service.LoginService;
import com.cdd.eshop.service.UserInfoService;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;


/**
 * 用户控制器
 *
 * @author quan
 * @date 2020/12/21
 */
@RestController
@RequestMapping("/v1/admin")
public class AdminController extends BaseController {

    @Autowired
    LoginService loginService;

    @Autowired
    UserInfoService userInfoService;


    @PostMapping("/login")
    @ApiOperation("用户登录接口，返回用户信息")
    ResponseDTO login(@RequestBody LoginBO bo){

        ResponseDTO dto = loginService.loginAdmin(bo.getUserName(), bo.getPassword());
        if (dto.getCode().equals(StatusEnum.SUCCESS.getCode())){
            LoginHandlerInterceptor.blackTokenList.remove(((UserInfoVO)dto.getData()).getUserId());
        }
        return dto;
    }


    @PostMapping("/register")
    @ApiOperation("用户登录接口，返回注册结果")
    ResponseDTO register(@RequestBody User user){
        if (StringUtils.isBlank(user.getUserName()) ||
            StringUtils.isBlank(user.getPwd()))
            return ResponseDTO.error(StatusEnum.PARAM_ERROR,"用户名或密码不可为空！请重拾");
        return loginService.registerAdmin(user);
    }

    @GetMapping("/logout")
    @ApiOperation("注销登录接口")
    ResponseDTO logout(HttpServletRequest request){
        LoginHandlerInterceptor.blackTokenList.put(this.getUserId(request),true);
        return loginService.logout();
    }


    @GetMapping("/info/basic/get")
    @ApiOperation("获取用户基本信息")
    ResponseDTO getUserInfo(HttpServletRequest request) {
        Integer userId = this.getUserId(request);
        return userInfoService.getUserInfoById(userId);
    }


    @PostMapping("info/basic/update")
    @ApiOperation("更新用户信息")
    ResponseDTO updateUserInfoById(@RequestBody User user,HttpServletRequest request) {
        if (null == user.getUserId()){
            user.setUserId(this.getUserId(request));
        }
        return userInfoService.updateUserInfoById(user);
    }

}
