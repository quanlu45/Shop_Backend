package com.cdd.eshop.common;

import com.cdd.eshop.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Value;

import javax.servlet.http.HttpServletRequest;

/**
 * 基控制器
 *
 * @author quan
 * @date 2020/12/21
 */
public class BaseController {

    @Value("${token.private-key}")
    private String tokenPrivateKey;


    /**
     * 默认的页面大小
     */
    protected Integer defaultPageSize = 10;

    /**
     * 默认的页面数量
     */
    protected Integer defaultPageNumber = 0;

    /**
     * 获得用户的Id
     *
     * @param request 请求
     * @return {@link Integer}
     */
    protected Integer getUserId(HttpServletRequest request){
        return JwtUtil.verifyAndGetIntegerClaim(
                tokenPrivateKey,
                request.getHeader("accesstoken"),
                "userId");
    }

}
