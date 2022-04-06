package com.weiwei.jixieche.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

/**
 * @Author 钟焕
 * @Description
 * @Date 17:35 2019-08-22
 **/
@Data
@EqualsAndHashCode
@ApiModel(value="计费规则Vo类")
public class JxcTransCostsVo {
    @ApiModelProperty("主键")
    private Integer id;

    @ApiModelProperty("省份编码")
    private Integer provinceId;

    @ApiModelProperty("市编码")
    private Integer cityId;

    @ApiModelProperty("平台佣金率(%) 没有带百分号  实际应用该字段请自带百分号")
    private Integer platCommission;

    @ApiModelProperty("土方类型中文 1-好土 0-坏土 2-其他")
    private String earthType;

    @ApiModelProperty("起步价")
    private Integer startPrice;

    @ApiModelProperty("起步价距离")
    private BigDecimal startPriceMileage;

    @ApiModelProperty("后续费用")
    private Integer followPrice;

    @ApiModelProperty("超出距离后计价")
    private Integer outMileage;

    @ApiModelProperty("统一价格")
    private Integer unifiedPrice;

    @ApiModelProperty("等待费用")
    private Integer waitePrice;

    @ApiModelProperty("额外费用")
    private Integer additionalPrice;

}
