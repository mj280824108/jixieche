package com.weiwei.jixieche.constant;

public enum JxcMachineRemindConstants {

    REMIND_INSPECTION(1,"车辆年检提醒"),
    REMIND_SAFE(2,"车辆保险提醒"),
    REMIND_CARE(3,"车辆保养提醒"),
    REMIND_MACHINE_DRIVER(4,"行驶证提醒"),
    REMIND_DRIVER(5,"驾驶证提醒"),
    REMIND_MACHINE_OPERATION(6,"车辆运营证提醒"),
    ;

    JxcMachineRemindConstants(Integer id, String descript) {
        this.id = id;
        this.descript = descript;
    }
    //对应jxc_machine_remind模板的id
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
