package com.weiwei.jixieche.jwt;

import java.util.UUID;

/**
 * @ClassName JwtConstant
 * @Description jwt的常量值
 * @Author houji
 * @Date 2019/7/29 18:07
 * @Version 1.0.1
 **/
public class JwtConstant {
    public static final String JWT_ID = UUID.randomUUID().toString().replaceAll("-", "").substring(11);


    public static final String ISSUER = "wuhangongyouzhijia_2019";

    /**
     * 加密密文
     */
    public static final String JWT_SECRET = "weiweijixie_2019";
    public static final long JWT_TTL = 365*24*60*60*1000;  //毫秒

    /**
     * 注册的token存在的时间期限
     */
    public static final long REGISTER_TOKEN_TIME = 60*1000;  //millisecond
}
