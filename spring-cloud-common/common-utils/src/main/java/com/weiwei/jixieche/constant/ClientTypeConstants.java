package com.weiwei.jixieche.constant;

/**
 * @ClassName ClientTypeConstants
 * @Description 客户端类型
 * @Author houji
 * @Date 2019/7/29 15:52
 * @Version 1.0.1
 **/

public enum ClientTypeConstants {

    CLIENT_OWNER(1,"机主"),

    CLIENT_TENANTRY(2,"承租方"),

    ;

    ClientTypeConstants(Integer id, String descript) {
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
