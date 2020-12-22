package com.xy.honest_record;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Calendar;

//@SpringBootTest
class HonestRecordApplicationTests {

    //盐
    private final static String salt = "mysign";
    //生成token，结构为 header.payload.signature
    @Test
    void contextLoads() {
//        Calendar calendar = Calendar.getInstance();
//        calendar.add(Calendar.SECOND,100);
//
//
//        String token = JWT.create()
//                .withClaim("userId",21) //payload
//                .withClaim("username","xiaoming")//payload
//                .withExpiresAt(calendar.getTime()) //过期时间
//                .sign(Algorithm.HMAC256(salt)); //签名算法和盐
//
//
//        System.out.println(token);

    }

    //解析token
    //@Test
    void contextLoads1() {
        //token验证器，必须指明生成该token签名的算法和盐
        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(salt)).build();

        //验证token，如果验证失败，则会抛出异常(算法异常，过期异常，数据匹配异常)。验证成功则返回token未加密前对象
        DecodedJWT decodedJWT = jwtVerifier.verify("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJleHAiOjE2MDc4NjU3ODQsInVzZXJJZCI6MjEsInVzZXJuYW1lIjoieGlhb21pbmcifQ.QMRClNG0v0GQvg5M_JupDKdcOba6uxwU5if7BPGIBF8");

        System.out.println(decodedJWT.getClaim("userId").asInt());
        System.out.println(decodedJWT.getClaim("username").asString());
    }


}
