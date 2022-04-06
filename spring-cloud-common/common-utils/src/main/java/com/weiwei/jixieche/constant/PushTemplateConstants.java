package com.weiwei.jixieche.constant;

/**
 * @ClassName PushTemplateConstants
 * @Description 推送消息模板常量
 * @Author houji
 * @Date 2019/5/13 17:14
 * @Version 1.0.1
 **/
public enum PushTemplateConstants {

    JPUSH_REGISTER_SUCCESS(101,"注册成功消息"),
    JPUSH_RECEIPT(102,"接单推送"),
    JPUSH_OWNER_CANCEL(103,"机主取消通知"),
    JPUSH_ORDER_START(104,"订单开始提醒"),
    JPUSH_CHILD_RECEIPT_SUCCESS(105,"子账号接单成功"),
    JPUSH_PERFECT_INFO(106,"注册成功后完善资料通知"),
    JPUSH_REAL_NAME_AUTH(107,"实名认证提醒"),
    JPUSH_OWNER_LEAVE_BEFORE(108,"机主申请退出"),
    JPUSH_OWNER_APPLY_LEAVE(109,"机主申请请假"),
    JPUSH_OWNER_RECEIPT_SUCCESS(110,"机主接单成功"),
    JPUSH_TEN_STOP_TELL_OWNER(111,"承租方停工提醒机主"),
    JPUSH_TEN_STOP_TELL_DRIVER(112,"承租方停工提醒司机"),
    JPUSH_TEN_DONE_BEFORE_TELL_OWNER(113,"承租方提前完工提醒机主"),
    JPUSH_TEN_DONE_BEFORE_TELL_DRIVER(114,"承租方提前完工提醒司机"),
    JPUSH_TEN_FIRE_MACHINE_TELL_OWNER(115,"承租方解雇机械提醒机主"),
    JPUSH_TEN_FIRE_MACHINE_TELL_DRIVER(116,"承租方解雇机械提醒司机"),
    JPUSH_TEN_CANCEL_ORDER_TELL_OWNER(117,"承租方取消订单提醒机主"),
    JPUSH_TEN_CANCEL_ORDER_TELL_DRIVER(118,"承租方取消订单提醒司机"),
    JPUSH_OWNER_FIRE_DRIVER(119,"机主解雇司机"),
    JPUSH_SHORT_DRIVER_RECEIPT_SUCCESS(120,"临时司机接单成功"),
    //JPUSH_121(101,"承租方审核拒绝通知"),
    //JPUSH_122(101,"承租方审核通过通知"),
    //JPUSH_123(101,"机主审核拒绝通知"),
    //JPUSH_124(101,"机主审核通过通知"),
    //JPUSH_125(101,"机主机械审核拒绝通知"),
    //JPUSH_126(101,"机主机械审核通过通知"),
    //JPUSH_127(101,"派单成功"),
    //JPUSH_128(101,"司机审核拒绝通知"),
    //JPUSH_129(101,"司机审核通过通知"),
    JPUSH_TELL_TEN_CONTINUE_GET_MACHINE(130,"提醒承租方是否继续要车"),
    JPUSH_ORDER_START_TWELVE_BEFORE_TEN(131,"订单开始前12小时通知承租方"),
    JPUSH_ORDER_START_TWELVE_BEFORE_OWNER(132,"订单开始前12小时通知机主"),
    JPUSH_ORDER_START_TWELVE_BEFORE_CHILD(133,"订单开始前12小时通知子账号"),
    //JPUSH_134(134,"平台发布公告"),
    JPUSH_DAY_ORDER_PRO_PAY(135,"日结订单催付款通知"),
    JPUSH_WEEK_ORDER_PRO_PAY(136,"周结订单催付款通知"),
    JPUSH_LISTEN_ORDER(137,"听单消息"),
    JPUSH_CASH_MONEY(138,"提现消息"),
    JPUSH_OWNER_REFUSE_DRIVER(139,"机主拒绝司机"),
    JPUSH_OWNER_FIRE_LONG_DRIVER(140,"机主解雇（删除）长期司机"),
    JPUSH_OWNER_PAY_NOTICE_ONCE(141,"机主第一次催款通知"),
    JPUSH_OWNER_PAY_NOTICE_TWICE(142,"机主第二次催款通知"),
    JPUSH_TEN_ORDER_START(143,"承租方订单开始"),
    JPUSH_REFRESH(144,"司机工作台刷新"),
    JPUSH_RETURN_COUPON_NOTICE(145,"消纳场退卡以后通知承租方"),
    JPUSH_CHANGE_SITE_NOTICE(146,"消纳场退卡以后通知承租方"),
    ;

    PushTemplateConstants(Integer id, String descript) {
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
