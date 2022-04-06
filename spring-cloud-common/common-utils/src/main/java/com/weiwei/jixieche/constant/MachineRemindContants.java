package com.weiwei.jixieche.constant;

/**
 * @ClassName MachineRemindContants
 * @Description 机械提醒常量
 * @Author houji
 * @Date 2019/5/13 17:14
 * @Version 1.0.1
 **/
public enum MachineRemindContants {
    REMIND_YEAR(101,"注册成功消息"),
    ;

    MachineRemindContants(Integer id, String descript) {
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
