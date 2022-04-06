package com.weiwei.jixieche.constant;

public enum RedisCountConstants {

    MOBILE_CODE_COUNT_LIMIT(10,"次数限制 10 次/接口")
    ;

    RedisCountConstants(Integer count, String descript) {
        this.count = count;
        this.descript = descript;
    }

    private Integer count;
    //推送模板描述
    private String descript;

    public Integer getCount() {
        return count;
    }
    public String getDescript() {
        return descript;
    }
}
