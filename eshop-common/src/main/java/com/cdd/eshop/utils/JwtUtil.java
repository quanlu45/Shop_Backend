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

public final class JwtUtil {


    private static Algorithm  algorithm =  Algorithm.HMAC256("lnp^A0tT");

    private static String  issuer ="cddAuth";


    /***
     * 生成token
     * @param expDate  过期时间
     * @return
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


    public static String create(String privateKey, Duration timeout, Integer userId) {
        Algorithm ag =  Algorithm.HMAC256(privateKey);
        LocalDateTime localDateTime =  LocalDateTime.now().plusMinutes(timeout.toMinutes());
        Date date = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
        String token ="";
        try {
            token = JWT.create()
                    .withExpiresAt(date)
                    .withClaim("userId",userId)
                    .sign(ag);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return token;
    }

    /***
     * 校验token是否有效
     * @param token
     * @return
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


    public static Integer verifyAndGetId(String privateKey, String token) {
        try {
            Algorithm  privatealgorithm =  Algorithm.HMAC256(privateKey);
            JWTVerifier verifier = JWT.require(privatealgorithm)
                    .build();
            verifier.verify(token);
            DecodedJWT jwt = verifier.verify(token);
            return  jwt.getClaim("userId").asInt();
        } catch (JWTVerificationException e) {
            return null;
        }
    }

}
