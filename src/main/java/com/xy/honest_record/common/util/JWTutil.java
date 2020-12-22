package com.xy.honest_record.common.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Calendar;

public class JWTutil {

    //盐
    private final static String salt = "mysign";
    //加密、解密算法
    private final static Algorithm algorithm = Algorithm.HMAC256(salt);

    //生成token，结构为 header.payload.signature

    public String getToken() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.SECOND,100);


        String token = JWT.create()
                .withClaim("userId",21) //payload
                .withClaim("username","xiaoming")//payload
                .withExpiresAt(calendar.getTime()) //过期时间
                .sign(algorithm); //签名算法和盐


        System.out.println(token);

        return token;

    }

    //验证和解析token，验证出错会抛异常
    public void verifyToken(String token) {

        //token验证器
        JWTVerifier jwtVerifier = JWT.require(algorithm).build();

        //验证token，如果验证失败，则会抛出异常(算法异常，过期异常，数据匹配异常)。验证成功则返回token未加密前对象
        DecodedJWT decodedJWT = jwtVerifier.verify(token);



        System.out.println(decodedJWT.getClaim("userId").asInt());
        System.out.println(decodedJWT.getClaim("username").asString());
    }
}
