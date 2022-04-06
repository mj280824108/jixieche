package com.weiwei.jixieche.bean;

import java.math.BigDecimal;

/**
 * 运输费规则
 */
public class TransCosts {
    /**
     * 主键 自增
     */
    private Integer id;
    /**
     * 省份编码
     */
    private Integer provinceId;
    /**
     * 市编码
     */
    private Integer cityId;
    /**
     * 平台佣金率(%) 没有带百分号  实际应用该字段请自带百分号
     */
    private Integer platCommission;
    /**
     * 土方类型中文 1-干土 2-湿土 3-黏土
     */
    private String earthType;
    /**
     * 起步价
     */
    private Integer startPrice;
    /**
     * 起步价距离
     */
    private BigDecimal startPriceMileage;
    /**
     * 后续费用
     */
    private Integer followPrice;
    /**
     * 超出距离后计价
     */
    private Integer outMileage;
    /**
     * 统一价格
     */
    private Integer unifiedPrice;
    /**
     * 等待费用
     */
    private Integer waitePrice;
    /**
     * 额外费用
     */
    private Integer additionalPrice;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(Integer provinceId) {
        this.provinceId = provinceId;
    }

    public Integer getCityId() {
        return cityId;
    }

    public void setCityId(Integer cityId) {
        this.cityId = cityId;
    }

    public Integer getPlatCommission() {
        return platCommission;
    }

    public void setPlatCommission(Integer platCommission) {
        this.platCommission = platCommission;
    }

    public String getEarthType() {
        return earthType;
    }

    public void setEarthType(String earthType) {
        this.earthType = earthType;
    }

    public Integer getStartPrice() {
        return startPrice;
    }

    public void setStartPrice(Integer startPrice) {
        this.startPrice = startPrice;
    }

    public BigDecimal getStartPriceMileage() {
        return startPriceMileage;
    }

    public void setStartPriceMileage(BigDecimal startPriceMileage) {
        this.startPriceMileage = startPriceMileage;
    }

    public Integer getFollowPrice() {
        return followPrice;
    }

    public void setFollowPrice(Integer followPrice) {
        this.followPrice = followPrice;
    }

    public Integer getOutMileage() {
        return outMileage;
    }

    public void setOutMileage(Integer outMileage) {
        this.outMileage = outMileage;
    }

    public Integer getUnifiedPrice() {
        return unifiedPrice;
    }

    public void setUnifiedPrice(Integer unifiedPrice) {
        this.unifiedPrice = unifiedPrice;
    }

    public Integer getWaitePrice() {
        return waitePrice;
    }

    public void setWaitePrice(Integer waitePrice) {
        this.waitePrice = waitePrice;
    }

    public Integer getAdditionalPrice() {
        return additionalPrice;
    }

    public void setAdditionalPrice(Integer additionalPrice) {
        this.additionalPrice = additionalPrice;
    }

    @Override
    public String toString() {
        return "TransCosts{" +
                "id=" + id +
                ", provinceId=" + provinceId +
                ", cityId=" + cityId +
                ", platCommission=" + platCommission +
                ", earthType='" + earthType + '\'' +
                ", startPrice=" + startPrice +
                ", startPriceMileage=" + startPriceMileage +
                ", followPrice=" + followPrice +
                ", outMileage=" + outMileage +
                ", unifiedPrice=" + unifiedPrice +
                ", waitePrice=" + waitePrice +
                '}';
    }
}
