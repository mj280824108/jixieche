package com.weiwei.jixieche.constant;

/**
 * @ClassName RedisPrefixConstants
 * @Description redis的保存时间
 * @Author houji
 * @Date 2019/5/13 17:14
 * @Version 1.0.1
 **/
public enum RedisExpireTimeConstants {

    REDIS_PREFIX_LOGIN_TOKEN(60*60,"华为推送token保存时间"),
    LIMIT_INTERVAL(3 * 60,"短信发送验证码--180秒"),
    MOBILE_COUNT_EXPIRE(24 * 60 * 60,"短信当天count数的有效时间 24小时"),
    MOBILE_CODE_EXPIRE(60 * 3,"短信验证码有效时间 3分钟（测试）， 后期改为 30 分钟")

    ;

    RedisExpireTimeConstants(Integer expireTime, String descript) {
        this.expireTime = expireTime;
        this.descript = descript;
    }

    private Integer expireTime;
    //推送模板描述
    private String descript;

    public Integer getExpireTime() {
        return expireTime;
    }
    public String getDescript() {
        return descript;
    }

}
