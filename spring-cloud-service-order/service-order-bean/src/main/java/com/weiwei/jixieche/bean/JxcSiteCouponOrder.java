package com.weiwei.jixieche.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

/**
 * @Author zhong huan
 * @Date 2019-08-19 19:42
 * @Description
 */
@Data
@EqualsAndHashCode
@ApiModel(value="消纳券和订单关联表")
public class JxcSiteCouponOrder {
    @ApiModelProperty("订单编号")
    private Long orderId;

    @ApiModelProperty("消纳券类型id")
    private Long couponTypeId;

    @ApiModelProperty("订单数量")
    private Integer num;

    @ApiModelProperty("实发数量")
    private Integer realNum;

    @ApiModelProperty("下单时的单价")
    private BigDecimal price;
}
