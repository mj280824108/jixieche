package com.weiwei.jixieche.constant;

/**
 * @ClassName ClientTypeConstants
 * @Description 客户端类型
 * @Author houji
 * @Date 2019/7/29 15:52
 * @Version 1.0.1
 **/

public enum PayTypeConstants {

    UNION_PAY(1,"银联支付"),
    WX_PAY(2,"微信支付"),
    ALI_PAY(3,"支付宝支付")

    ;

    PayTypeConstants(Integer id, String descript) {
        this.id = id;
        this.descript = descript;
    }
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
