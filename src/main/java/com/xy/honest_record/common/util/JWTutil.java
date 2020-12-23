package com.xy.honest_record.common.util;

import cn.hutool.json.JSONUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import java.util.Calendar;

/*
* 通用 JWT 工具类，可以传入任意对象作为 数据体payload
* */
public class JWTutil<T> {

    //盐
    private final static String salt = "mysalt123456";
    //加密、解密算法
    private final static Algorithm algorithm = Algorithm.HMAC256(salt);
    //过期时间，秒
    private final static int defaultExpireSecond = 10 *60;

    //生成token，结构为 header.payload.signature
    //参数 t ,表示数据体，可为任意类型对象
    public static<T> String getToken(T t, int expireSecond) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.SECOND,expireSecond);
        //把tokenPayload转换为json字符串保存，方便后面解析为对象
        String tokenPayloadJson = JSONUtil.toJsonStr(t);
        String token = JWT.create()
                .withClaim("tokenPayloadJson",tokenPayloadJson) //payload
                .withExpiresAt(calendar.getTime()) //过期时间
                .sign(algorithm); //签名算法和盐
        return token;
    }

    public static<T> String getToken(T t) {
        return getToken(t,defaultExpireSecond);
    }

    //验证token是否被篡改和过期，验证出错会抛异常
    public static DecodedJWT verifyToken(String token) {
        //token验证器
        JWTVerifier jwtVerifier = JWT.require(algorithm).build();
        //验证token，如果验证失败，则会抛出异常(算法异常，过期异常，数据匹配异常)。验证成功则返回token未加密前对象
        DecodedJWT decodedJWT = jwtVerifier.verify(token);
        return decodedJWT;
    }

    //从token中获取payLoad
    public static<T> T getPayLoadFromToken(String token,Class<T> clazz){
        DecodedJWT decodedJWT = verifyToken(token);
        String tokenPayloadJson = decodedJWT.getClaim("tokenPayloadJson").asString();
        T t = (T) JSONUtil.toBean(tokenPayloadJson,clazz);
        return t;
    }

}
