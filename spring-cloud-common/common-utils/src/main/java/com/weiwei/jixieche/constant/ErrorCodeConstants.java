package com.weiwei.jixieche.constant;

/**
 * @ClassName ErrorCodeConstants
 * @Description 关于后台返回前端错误码和错误码描述
 * @Author houji
 * @Date 2019/7/29 15:52
 * @Version 1.0.1
 **/
public enum ErrorCodeConstants {

    PARAM_EMPTY(10001,"参数为空"),
    PARAM_MUST_EMPTY(10002,"必填参数不完整"),
    PARAM_UNKNOW(10003,"参数不识别"),
    PARAM_TYPE_UNKNOW(10004,"参数类型不识别"),
    PARAM_USER_PWD_EMPTY(10005,"用户名或密码错误"),
    TOKEN_EMPTY(10005,"token为空"),
    TOKEN_EXPIRED_TIME(10006,"token过期,请重新登录"),
    CODE_REGEX_ERROR(10007,"验证码错误"),
    RIGISTER_FAIL(10008,"注册失败"),
    RIGISTER_SUCCESS(10009,"注册成功"),
    QUERY_ERORR(10010,"查询错误"),
    QUERY_EMPTY_DATA(10011,"查询数据为空"),
    ADD_ERORR(10012,"添加错误"),
    EDIT_ERORR(10013,"修改错误"),
    DELETE_ERORR(10014,"删除错误"),
    UPLOAD_FILE_EXISIT(10015,"上传文件已经存在"),
    UPLOAD_FILE_ERORR(10016,"上传文件错误"),
    UPLOAD_FILE_NULL(10017,"上传文件为空"),
    USER_LOGIN_OTHER(10018,"用户其他地方登陆"),
    USER_ROLE_NULL(10019,"用户角色为空"),
    USER_DISABLED(10020,"用户被禁用"),
    USER_MONEY_NULL(10021,"用户金额为空"),
    USER_MONEY_LESS(10022,"用户金额不足"),
    START_TIME_LATER_END_TIME(10023,"起始时间大于结束时间"),
    QUERY_DATA_EXISIT(10024,"查询数据已经存在"),
    TOKEN_CHANGE(10025,"token签名不合法"),
    QUERY_PARAM_ERROR(10026,"查询参数错误"),
    JPUSH_CONFIG_ERROR(10027,"站内信发送失败"),
    SERVICE_EXCEPTION_ERROR(10028,"服务异常"),
    QUERY_PARAM_EMPTY(10029,"查询参数为空"),
    PUSH_EXCEPTION(10030,"推送异常"),
    QUERY_PARAM_UNKNOW(10031,"查询参数不识别"),
    PUSH_CUSTOM_ERROR(10032,"推送自定义消息失败"),
    TOO_OFTEN_MOBILE_MSG(10033, "手机号发送短信过于频繁"),
    TOO_MUCH_MOBILE_MSG(10034, "手机号发送短信过多"),
    SHORT_MSG_CODE_ERROR(10035,"短信验证码错误"),
    PARAM_EMPTY_ERROR(10036,"必填参数缺失或错误"),
    PHONE_FORMAT_ERROR(10037,"电话号码格式错误"),
    PARAM_FORMAT_ERROR(10038,"参数格式错误"),
    NO_SUCH_DATA(10039,"数据不存在"),
    TOKEN_GEN_FAIL(10039,"token生成失败"),
    PHONE_NOT_EXIST(10040,"手机号不存在"),
    LOGIN_USER_CLIENT_SAME(10041,"用户登录客户端APP不一致"),
    USER_PHONE_EXIST(10042,"手机号已注册"),
    ALIPAY_PAY_ERROR(10043,"支付宝支付异常"),
    TRADE_AMOUNT_ERROR(10044,"支付金额错误"),
    FEIGN_RESULT_ERROR(10045,"feign接口调用失败"),
    WX_TRADE_PRE_PAY(10046,"微信预支付异常"),
    CANCEL_FAIL(10047,"订单取消失败"),
    ORDER_DRIVER_RUN(10048,"司机正在跑趟中"),
    ORDER_DRIVER_WORK_IN(10049,"司机正在上班中"),
    REDIS_KEY_NULL(10050,"redis的key为空"),
    RESOURCE_OVER_COST(10051,"该订单车辆已满"),
    NO_RES_FIND(10052,"当前岗位工期与您已接单岗位存在工期冲突！"),
    DRIVER_CHANGER_WORK(10053,"同一趟不可两次交接班，请跑完当前趟再交接上下班"),
    PERSON_CONFIRM_STATUS(10054,"个人认证状态未通过"),
    SHORT_MSG_SEND_FAIL(10055,"短信发送失败"),
    CLOCK_TOO_FARTHER(10056,"打卡距离太远"),
    BANK_CARD_TOKEN(10057,"获取银行卡token错误"),
    BANK_CARD_INFO(10058,"获取银行卡信息错误"),
    USER_SHOP_EMPTY(10059,"用户店铺不存在"),
    CHANGE_SITE_NOTICE(10060,"更换消纳场提示"),
    CASH_APPLY_ERROR(10061,"提现失败"),
    TEN_CANCEL_1(10062,"确定取消吗？"),
    TEN_CANCEL_2(10063,"2小时之内，扣分"),
    TEN_CANCEL_3(10064,"有车接单，2小时之外"),
    FORBID_SEND_ORDER(10065,"承租方账号被禁止发单"),
    ;


    //错误码id
    private int id;
    //错误码描述
    private String descript;

    ErrorCodeConstants(int id, String descript) {
        this.id = id;
        this.descript = descript;
    }

    public int getId() {
        return id;
    }
    public String getDescript() {
        return descript;
    }
}
