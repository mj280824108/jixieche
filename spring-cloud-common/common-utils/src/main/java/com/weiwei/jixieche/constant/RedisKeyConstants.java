package com.weiwei.jixieche.constant;

/**
 * @ClassName RedisPrefixConstants
 * @Description redis存储key的值
 * @Author houji
 * @Date 2019/5/13 17:14
 * @Version 1.0.1
 **/
public enum RedisKeyConstants {


    REDIS_PUSH_KIT_TOKEN_KEY("PUSH_KIT_TOKEN_KEY","华为推送token的key"),

    SHORT_CODE_REGISTER("code_register_","注册验证码key"),
    SHORT_CODE_LOGIN("code_login_","快捷登录验证码key"),
    SHORT_CODE_FORGET_PWD("code_forget_pwd_","忘记密码验证码key"),
    SHORT_CODE_BIND_CARD("code_bind_card_","绑定银行卡验证码key"),
    SHORT_CODE_ADD_MANAGER("code_add_manager_","承租方添加管理员验证码key"),
    SHORT_CODE_ADD_LONG_DRIVER("code_add_long_driver_","机主添加长期司机验证码key"),
    SHORT_CODE_USER_SET_PAY_PWD("code_user_set_pay_pwd_","用户设置支付密码验证码key"),
    REDIS_HOT_WORD_SCORE("redis_hot_word_score","用户评价热词前缀"),


    redis_login_verifyCode("PUSH_KIT_TOKEN_KEY","华为推送token的key")

    ;
    //app验证码类(1--短信注册 2--快捷登录  3--忘记密码 4--绑定银行卡 5--承租方添加管理员 6--机主添加长期司机)

    RedisKeyConstants(String prefix, String descript) {
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
}
