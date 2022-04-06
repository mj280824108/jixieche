package com.weiwei.jixieche.constant;

/**
 * @ClassName RedisPrefixConstants
 * @Description redis前缀常量
 * @Author houji
 * @Date 2019/5/13 17:14
 * @Version 1.0.1
 **/
public enum RedisPrefixConstants {

    REDIS_LOGIN_TOKEN_PREFIX("redis_login_token_prefix_","用户登录token前缀常量"),
    REDIS_PUSH_KIT_TOKEN_PREFIX("push_kit_token_key","华为推送token"),

    REGISTER_MOBILE_CODE_PREFIX("register_msg_code_","短信注册用户发送验证码前缀"),
    FORGET_MOBILE_CODE_PREFIX("forget_msg_code_","用户忘记密码重新设置密码发送验证码前缀"),

    REGISTER_MOBILE_COUNT_PREFIX("register_msg_code_count.","短信注册用户发送验证码发送次数前缀"),

    PASSWORD_MOBILE_CODE_PREFIX("mobileMessageCode.password.","短信发送密码前缀"),
    PASSWORD_MOBILE_COUNT_PREFIX("mobileCount.password","短信发送密码次数前缀"),

    CONFIRM_MOBILE_CODE_PREFIX("mobileMessageCode.confirm.","认证短信验证码前缀"),
    CONFIRM_MOBILE_COUNT_PREFIX("mobileCount.confirm","认证短信验证码次数前缀"),

    OWNER_CONFIRM_MOBILE_CODE_PREFIX("OWNER_CONFIRM_MOBILE_CODE_PREFIX","机主认证发送短信验证码前缀"),
    OWNER_CONFIRM_MOBILE_COUNT_PREFIX("OWNER_CONFIRM_MOBILE_COUNT_PREFIX","机主认证发送短信验证码次数"),

    DRIVER_CONFIRM_MOBILE_CODE_PREFIX("DRIVER_CONFIRM_MOBILE_CODE_PREFIX","司机认证短信验证码前缀"),
    DRIVER_CONFIRM_MOBILE_COUNT_PREFIX("DRIVER_CONFIRM_MOBILE_COUNT_PREFIX","司机认证短信验证码发送次数前缀"),

    INFORMATION_POINT_COUNT("INFORMATION_POINT_COUNT_","资讯点赞量前缀"),
    INFORMATION_VIEW_COUNT("INFORMATION_VIEW_COUNT_","资讯浏览量前缀"),
    INFORMATION_SHARE_COUNT("INFORMATION_SHARE_COUNT_","资讯分享量前缀"),

    SHOPS_POINT_COUNT("SHOPS_POINT_COUNT","店铺点赞量前缀"),
    SHOPS_VIEW_COUNT("SHOPS_VIEW_COUNT","店铺浏览量前缀"),
    SHOPS_SHARE_COUNT("SHOPS_SHARE_COUNT","店铺分享量前缀"),
    SHOPS_COLLECTION_COUNT("SHOPS_COLLECTION_COUNT","店铺收藏量前缀"),

    BANKCARD_CONFIRM_MOBILE_CODE_PREFIX("BANKCARD_CONFIRM_MOBILE_CODE_PREFIX","银行卡短信验证码发送前缀"),
    BANKCARD_CONFIRM_MOBILE_COUNT_PREFIX("BANKCARD_CONFIRM_MOBILE_COUNT_PREFIX","银行卡短信验证码发送次数前缀")



    ;

    RedisPrefixConstants(String prefix, String descript) {
        this.prefix = prefix;
        this.descript = descript;
    }
    //对应jxc_msg_type模板的id
    private String prefix;
    //推送模板描述
    private String descript;

    public String getPrefix() {
        return prefix;
    }
    public String getDescript() {
        return descript;
    }

    // ----------------------------Redis 华为推送token的key-------------------------------------
    public static final String PUSH_KIT_TOKEN_PREFIX = "PUSH_KIT_TOKEN_KEY"; //华为推送token的保存redis前缀
    public final static int PUSH_KIT_TOKEN_EXPIRE = 60 * 60;     //华为推送token保存时间
}
