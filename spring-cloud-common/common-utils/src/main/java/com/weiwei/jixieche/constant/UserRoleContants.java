package com.weiwei.jixieche.constant;

public enum UserRoleContants {

    NO_ROLE(1,"缺省角色"),
    OWNER(2,"机主"),
    TEN(3,"承租方"),
    DRIVER(4,"司机"),
    CHILD(5,"机械账号"),
    TEN_MAN(6,"承租方管理员"),
    SITE_MAN(7,"消纳场管理员")
    ;

    UserRoleContants(Integer roleId, String descript) {
        this.roleId = roleId;
        this.descript = descript;
    }
    //角色id
    private Integer roleId;
    //角色描述
    private String descript;

    public Integer getRoleId() {
        return roleId;
    }
    public String getDescript() {
        return descript;
    }
}
