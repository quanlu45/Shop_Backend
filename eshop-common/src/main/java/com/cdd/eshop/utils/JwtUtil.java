package com.cdd.eshop.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

/**
 * jwt工具类
 *
 * @author quan
 * @date 2020/12/21
 */
public final class JwtUtil {


    /**
     * 算法
     */
    private static final Algorithm  algorithm =  Algorithm.HMAC256("lnp^A0tT");

    /**
     * 发行人
     */
    private static final String  issuer ="cddAuth";


    /**
     * 创建token
     *
     * @param expDate 过期日期
     * @return {@link String}
     */
    public static String create(Date expDate) {
        String token ="";
        try {
            token = JWT.create()
                    .withIssuer(issuer)
                    .withExpiresAt(expDate)
                    .sign(algorithm);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return token;
    }


    /**
     * 创建
     *
     * @param privateKey 私钥
     * @param timeout    超时
     * @return {@link String}
     */
    public static String create(String privateKey,Duration timeout) {
        Algorithm ag =  Algorithm.HMAC256(privateKey);
        LocalDateTime localDateTime =  LocalDateTime.now().plusMinutes(timeout.toMinutes());
        Date date = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
        String token ="";
        try {
            token = JWT.create()
                    .withExpiresAt(date)
                    .sign(ag);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return token;
    }


    /**
     * 创建token 带整数声明
     *
     * @param privateKey 私钥
     * @param timeout    超时
     * @param claimKey   声明key
     * @param claimVal   声明值
     * @return {@link String}
     */
    public static String createWithIntegerClaim(String privateKey, Duration timeout,String claimKey, Integer claimVal) {
        Algorithm ag =  Algorithm.HMAC256(privateKey);
        LocalDateTime localDateTime =  LocalDateTime.now().plusMinutes(timeout.toMinutes());
        Date date = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
        String token ="";
        try {
            token = JWT.create()
                    .withExpiresAt(date)
                    .withClaim(claimKey,claimVal)
                    .sign(ag);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return token;
    }

    /**
     * 验证*
     * 校验token是否有效
     *
     * @param token 令牌
     * @return boolean
     */
    public static boolean verify(String token) {
        try {
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer(issuer)
                    .build(); //Reusable verifier instance
             DecodedJWT jwt = verifier.verify(token);
             return  true;
        } catch (JWTVerificationException e) {
            return  false;
        }
    }


    /**
     * 验证
     *
     * @param privateKey 私钥
     * @param token      令牌
     * @return boolean
     */
    public static boolean verify(String privateKey,String token) {
        try {
           Algorithm  privatealgorithm =  Algorithm.HMAC256(privateKey);
           JWTVerifier verifier = JWT.require(privatealgorithm)
                    .build();
            verifier.verify(token);

            return  true;
        } catch (JWTVerificationException e) {
            return  false;
        }
    }


    /**
     * 验证和得到Id
     *
     * @param privateKey 私钥
     * @param token      令牌
     * @return {@link Integer}
     */
    public static Integer verifyAndGetIntegerClaim(String privateKey, String token,String claimKey) {
        try {
            Algorithm  privatealgorithm =  Algorithm.HMAC256(privateKey);
            JWTVerifier verifier = JWT.require(privatealgorithm)
                    .build();
            DecodedJWT jwt = verifier.verify(token);
            return  jwt.getClaim(claimKey).asInt();
        } catch (JWTVerificationException e) {
            return null;
        }
    }



}
