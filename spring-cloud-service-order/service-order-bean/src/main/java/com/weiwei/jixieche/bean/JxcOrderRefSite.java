package com.weiwei.jixieche.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

/**
 * @Author zhong huan
 * @Date 2019-08-15 10:25
 * @Description
 */
@Data
@EqualsAndHashCode
@ApiModel(value="承租方订单与消纳场关联表")
public class JxcOrderRefSite {

    @ApiModelProperty("主键ID")
    private Integer id;

    @ApiModelProperty("承租方用户ID")
    private Integer userId;

    @ApiModelProperty("消纳场名")
    private String siteName;

    @ApiModelProperty("承租方订单ID")
    private Long tenantryOrderId;

    @ApiModelProperty("消纳场ID")
    private Integer siteId;

    @ApiModelProperty("预计公里数（单位：公里）")
    private BigDecimal estimateMiles;

    @ApiModelProperty("预计金额(单位：分)")
    private Integer estimatePrice;

    @ApiModelProperty("固定价格（一口价 单位：分）")
    private Integer fixedPrice;

    @ApiModelProperty("计价方式（0：按预计价格计算单价 1：按照固定价格计算单价）")
    private Integer pricingMode;

    @ApiModelProperty("使用状态（1：正在使用 0：停用（订单更换了消纳场））")
    private Integer state;
}
