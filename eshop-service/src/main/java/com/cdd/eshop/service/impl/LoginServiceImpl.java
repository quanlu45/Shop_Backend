package com.cdd.eshop.service.impl;

import com.alibaba.fastjson.JSON;
import com.cdd.eshop.bean.dto.ResponseDTO;
import com.cdd.eshop.bean.po.User;
import com.cdd.eshop.bean.vo.UserInfoVO;
import com.cdd.eshop.common.StatusEnum;
import com.cdd.eshop.mapper.UserRepository;
import com.cdd.eshop.service.LoginService;
import com.cdd.eshop.utils.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.HashMap;
import java.util.Optional;

@Slf4j
@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    UserRepository userRepository;

    @Value("${token.private-key}")
    String tokenPrivateKey;

    @Override
    public ResponseDTO login(String userName, String password) {

        User user = new User();
        user.setUserName(userName);
        user.setPwd(password);

        Example<User> example = Example.of(user);
        Optional<User> userOptional = userRepository.findOne(example);

        if (!userOptional.isPresent()){
            log.info("login===> userName: {} , pwd: {} ,auth failed !",userName,password);
            return ResponseDTO.error(StatusEnum.AUTH_FAILED,"用户名或密码错误！请重新登录！");
        }

        log.info("login===> userName: {} , pwd: {} ,auth success !",userName,password);

        user = userOptional.get();
        if (user.getStatus() == 1){
            return ResponseDTO.error().msg("该管理员账号已经被停用！请联系管理员解禁！");
        }


        //设置token的有效期为三天
        String token = JwtUtil.createWithIntegerClaim(tokenPrivateKey,Duration.ofDays(3),"userId",user.getUserId());

        UserInfoVO vo = new UserInfoVO();
        vo.setUserName(user.getUserName());
        vo.setRegTime(user.getRegTime());
        vo.setToken(token);
        vo.setUserId(user.getUserId());
        vo.setAvatarUrl(user.getAvatarUrl());

        return ResponseDTO.success().data(vo);
    }

    @Override
    public ResponseDTO logout() {
        return ResponseDTO.success("注销成功!");
    }

    @Override
    public ResponseDTO register(User user) {

        try {
            userRepository.saveAndFlush(user);
            HashMap<String,Object> data = new HashMap<>();
            data.put("userId",user.getUserId());
            return ResponseDTO.success().data(data);

        }catch (DataIntegrityViolationException e){
            log.info("register===> {} ,user ={}","用户名重复", JSON.toJSONString(user));
            return ResponseDTO.error(StatusEnum.PARAM_ERROR,"用户名重复！请修改后再试！");

        }catch (Exception e){
            log.error("register===> {} ,user ={}",e.getMessage(), JSON.toJSONString(user));
            return ResponseDTO.error(StatusEnum.SERVER_ERROR,e.getCause().getMessage());
        }
    }

    @Override
    public ResponseDTO loginAdmin(String userName, String password) {
        User user = new User();
        user.setUserName(userName);
        user.setPwd(password);
        user.setIsAdmin(true);

        Example<User> example = Example.of(user);
        Optional<User> userOptional = userRepository.findOne(example);

        if (!userOptional.isPresent()){
            log.info("login===> userName: {} , pwd: {} ,auth failed !",userName,password);
            return ResponseDTO.error(StatusEnum.AUTH_FAILED,"用户名或密码错误！请重新登录！");
        }

        log.info("login===> userName: {} , pwd: {} ,auth success !",userName,password);

        user = userOptional.get();
        if (user.getStatus() == 1){
            return ResponseDTO.error().msg("该管理员账号已经被停用！请联系超级管理员解禁！");
        }

        //设置token的有效期为三天
        String token = JwtUtil.createWithIntegerClaim(tokenPrivateKey,Duration.ofDays(3),"userId",user.getUserId());

        UserInfoVO vo = new UserInfoVO();
        vo.setUserName(user.getUserName());
        vo.setRegTime(user.getRegTime());
        vo.setToken(token);
        vo.setUserId(user.getUserId());
        vo.setAvatarUrl(user.getAvatarUrl());

        return ResponseDTO.success().data(vo);
    }

    @Override
    public ResponseDTO registerAdmin(User user) {
        if(user!=null)
            user.setIsAdmin(true);
        try {
            userRepository.saveAndFlush(user);
            HashMap<String,Object> data = new HashMap<>();
            data.put("userId",user.getUserId());
            return ResponseDTO.success().data(data);

        }catch (DataIntegrityViolationException e){
            log.info("register===> {} ,user ={}","用户名重复", JSON.toJSONString(user));
            return ResponseDTO.error(StatusEnum.PARAM_ERROR,"用户名重复！请修改后再试！");

        }catch (Exception e){
            log.error("register===> {} ,user ={}",e.getMessage(), JSON.toJSONString(user));
            return ResponseDTO.error(StatusEnum.SERVER_ERROR,e.getCause().getMessage());
        }
    }
}
