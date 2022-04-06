package com.weiwei.jixieche.constant;

/**
 * @ClassName ShortMsgConstants
 * @Description 短信模板常量
 * @Author houji
 * @Date 2019/5/13 17:14
 * @Version 1.0.1
 **/
public enum ShortMsgConstants {

    SHORTMSG_REGISTER(1,"注册"),
    SHORTMSG_PWD(2,"密码"),
    SHORTMSG_AUTH(3,"认证"),
    SHORTMSG_OWNER_AUTH(4,"机主认证"),
    SHORTMSG_DRIVER_AUTH(5,"司机认证"),
    SHORTMSG_ADD_BANKCARD(6,"添加银行卡"),
    SHORTMSG_TEN_AUTH_NOT_PASS(7,"承租方认证审核拒绝"),
    SHORTMSG_TEN_AUTH_PASS(8,"承租方认证审核通过"),
    SHORTMSG_TEN_ORDER_TIME_END(9,"承租方订单到期提醒"),
    SHORTMSG_TEN_ORDER_TELL_START(10,"承租方订单开始提醒"),
    SHORTMSG_TEN_ORDER_TELL_DRIVER_LEAV(11,"承租方订单司机提前离场提醒"),
    SHORTMSG_TEN_DAY_ORDER_NOT_PAY(12,"承租方日订单未支付提醒"),
    SHORTMSG_TEN_WEEK_ORDER_NOT_PAY(13,"承租方周订单未支付提醒"),
    SHORTMSG_TEN_ORDER_MACHINE_LEAV(14,"承租方订单车辆请假提醒"),
    SHORTMSG_OWNER_AUTH_NOT_PASS(15,"机主认证审核拒绝"),
    SHORTMSG_OWNER_AUTH_PASS(16,"机主认证审核通过"),
    SHORTMSG_OWNER_MACHINE_NOT_PASS(17,"机主机械审核拒绝"),
    SHORTMSG_OWNER_MACHINE_PASS(18,"机主机械审核通过"),
    SHORTMSG_OWNER_GET_MONEY(19,"机主提现"),
    SHORTMSG_TEN_FIRE_OWNER_CHILD(20,"机主和子账号被承租方解雇提醒"),
    SHORTMSG_DRIVER_AUTH_NOT_PASS(21,"司机认证审核拒绝"),
    SHORTMSG_DRIVER_AUTH_PASS(22,"司机认证审核通过"),
    SHORTMSG_DRIVER_GET_MONEY(23,"司机提现提醒"),
    SHORTMSG_OWNER_FIRE_DRIVER(24,"司机被机主解雇"),
    SHORTMSG_DRIVER_RECEIPT(25,"司机接单成功"),
    SHORTMSG_CHILD_RECEIPT(26,"子账号接单成功"),
    SHORTMSG_CHILD_SOTR_WORK(27,"子账号停工提醒"),
    SHORTMSG_CHILD_WORK_BEFORE_DONE(28,"子账号提前完工提醒"),
    SHORTMSG_TEN_CANNEL_ORDER_TELL_CHILD(29,"承租方订单取消子账号提醒"),
    SHORTMSG_CHILD_ORDER_TIME_START(30,"子账号订单开始提醒"),
    SHORTMSG_CHILD_DIS_ORDER_SUCCESS(31,"子账号派单成功"),
    SHORTMSG_OWNER_ORDER_TIME_START(32,"机主订单开始提醒"),
    SHORTMSG_OWNER_CHILD_MACHINE_RECEIPT_TELL_TEN(33,"机主以及机械账号接单提醒承租方"),
    SHORTMSG_OWNER_CHILD_CANNEL_ORDER_TELL_TEN(34,"机主或子账号取消订单提醒承租方"),
    SHORTMSG_OWNER_FIRE_DIRVER(35,"司机被机主拒绝"),
    HORTMSG_QUiCK_LOGIN(36, "快捷登录"),
    SHORTMSG_CHILD_ORDER_NOTICE_TEN(37, "子账号接单通知承租方"),
    SHORTMSG_TEN_COMLATE_PROJECT_ChILD(38, "承租方申请完工通知子账号"),

    ALISMS_REGINSTER(41,"ap验证码"),
    ALISMS_STOP_DIRVER(45,"停工提醒司机"),
    ALISMS_TAKE_MONEY(46,"提现提醒"),
    ALISMS_TEN_DAY_ORDER_PAY(47,"承租方日结订单提醒"),
    ALISMS_TEN_WEEK_ORDER_PAY(48,"承租方周结订单提醒"),
    ALISMS_MACHINE_LEAVE(49,"承租方订单车辆请假通知"),
    ALISMS_TEN_STOP_OWNER(50,"承租方申请停工提醒机主"),
    ALISMS_TEN_FIRE_OWNER(51,"承租方解雇车辆通知机主"),

    xxx(000,"最后隔离")
    ;




    ShortMsgConstants(Integer id, String descript) {
        this.id = id;
        this.descript = descript;
    }
    //对应jxc_msg_type模板的id
    private Integer id;
    //推送模板描述
    private String descript;

    public Integer getId() {
        return id;
    }
    public String getDescript() {
        return descript;
    }

}
