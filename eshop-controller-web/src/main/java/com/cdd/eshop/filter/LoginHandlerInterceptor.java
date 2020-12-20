package com.cdd.eshop.filter;

import com.alibaba.fastjson.JSON;
import com.cdd.eshop.bean.dto.ResponseDTO;
import com.cdd.eshop.common.StatusEnum;
import com.cdd.eshop.utils.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
public class LoginHandlerInterceptor implements HandlerInterceptor {

    @Value("#{'${login.interceptor.free-urls}'.split(',')}")
    private List<String> freeUrls;

    @Value("${login.interceptor.is-test}")
    private boolean test;

    @Value("${token.private-key}")
    private String tokenPrivateKey;

    //token黑名单列表
    public static Map<Integer,Boolean> blackTokenList = new ConcurrentHashMap<>();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {

        //测试放行
        if (test) return true;

        String url = request.getServletPath();

        //错误放行
        if("/error".equals(url)){
            return true;
        }

        //白名单放行
        if (null != freeUrls && !freeUrls.isEmpty()){
            for (String target : freeUrls){
                if (url.contains(target)){
                    return true;
                }
            }
        }

        String accesstoken = request.getHeader("accesstoken");
        log.info("accesstoken:{}",accesstoken);

        if (accesstoken == null) {
            setErrorResponse(response,StatusEnum.AUTH_FAILED);
            return false;
        }

        Integer userId = JwtUtil.verifyAndGetId(tokenPrivateKey,accesstoken);
        if (userId == null){
            setErrorResponse(response,StatusEnum.TOKEN_INVALID);
            return false;
        }

        //黑名单拦截
        if (blackTokenList.containsKey(userId)){
            setErrorResponse(response,StatusEnum.TOKEN_INVALID);
            return false;
        }

        return true;
    }

    private void setErrorResponse(HttpServletResponse response, StatusEnum error){
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        PrintWriter out = null;
        try {
            out = response.getWriter();
            out.append(JSON.toJSONString(ResponseDTO.error(error,"认证失败！未带token或token失效！")));
        } catch (Exception e) {
/*            response.sendError(500);*/
        }
    }
}
