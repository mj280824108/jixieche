package com.weiwei.jixieche.constant;

/**
 * @ClassName CommonErrCodeConstants
 * @Description 信用分积分模板
 * @Author houji
 * @Date 2019/5/31 13:48
 * @Version 1.0.1
 **/
public enum CreditScoreTemplateConstants {

    SCORE_OWNER_FIRE_DRIVER(1,"机主拒绝/解雇司机"),
    SCORE_CHILD_CANCEL_TEN_ORDER(2,"机主/子账号取消承租方订单"),
    SCORE_DRIVER_CANCEL_SHORT_JOB(3,"司机取消已接受的短期职位"),
    SCORE_TEN_CANCEL_DRIVER_ORDER(4,"承租方取消已有司机接单的订单"),
    SCORE_CHILD_BAD_POST(5,"子账号被差评"),
    SCORE_DRIVER_BAD_POST(6,"司机被差评"),
    SCORE_TEN_BAD_POST(7,"承租方被差评"),
    SCORE_OWNER_PHONE_REGISTER(8,"机主手机注册"),
    SCORE_DRIVER_PHONE_REGISTER(9,"司机手机注册"),
    SCORE_TEN_PHONE_REGISTER(10,"承租方手机注册"),
    SCORE_OWNER_REAL_AUTH(11,"机主实名认证"),
    SCORE_DRIVER_REAL_AUTH(12,"司机实名认证"),
    SCORE_TEN_REAL_AUTH(13,"承租方个人实名认证"),
    SCORE_COMPANY_REAL_AUTH(14,"企业实名认证"),
    SCORE_OWNER_BIND_BANK_CARD(15,"机主绑定银行卡"),
    SCORE_DRIVER_BIND_BANK_CARD(16,"司机绑定银行卡"),
    SCORE_TEN_BIND_BANK_CARD(17,"承租方绑定银行卡"),
    SCORE_OWNER_FIRST_RELEASE_AUTH_MACHINE(18,"机主首次发布一辆认证通过的机械"),
    SCORE_OWNER_FIRST_RELEASE_EMPLOY(19,"机主首次发布招聘"),
    SCORE_OWNER_CHILD_LEAVE_TWO_ON_MONTH(20,"机主的子账号每个订单中一个月请假两次以上扣1分"),
    SCORE_CHILD_DOWN_SIXTY(21,"一个子账号信用分低于60分一次扣XX分"),
    SCORE_CHILD_DONE_ORDER(22,"子账号接受订单并完成"),
    SCORE_CHILD_BEFORE_LEAVE(23,"子账号在订单未完结之前提前离场"),
    SCORE_CHILD_INIT_SCORE(24,"子账号的初始分值"),
    SCORE_DRIVER_FIRST_RELEASE_RESUME(25,"司机首次发布简历"),
    SCORE_DRIVER_DONE_ORDER(26,"司机完成订单"),
    SCORE_TEN_FIRST_RELEASE_ORDER(27,"承租方首次发布订单"),
    SCORE_TEN_DONE_ORDER_PAY(28,"承租方完成订单并且支付"),
    SCORE_TEN_FIRE_MACHINE(29,"承租方无故解雇车辆（不包含请假中的车辆）"),
    ;

    CreditScoreTemplateConstants(Integer id, String descript) {
        this.id = id;
        this.descript = descript;
    }
    //对应jxc_credit_score_rule_template模板的id
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
