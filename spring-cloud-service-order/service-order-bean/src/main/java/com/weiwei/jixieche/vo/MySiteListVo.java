package com.weiwei.jixieche.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @Author zhong huan
 * @Date 2019-08-15 14:49
 * @Description
 */
@Data
@EqualsAndHashCode
@ApiModel(value="我的消纳场列表")
public class MySiteListVo {

    @ApiModelProperty("消纳场id")
    private Integer siteId;

    @ApiModelProperty("消纳场名")
    private String siteName;

    @ApiModelProperty("消纳场详细地址")
    private String siteAddress;

    @ApiModelProperty("是否入驻平台（1：是 0：否）")
    private Integer intoFlag;

    @ApiModelProperty("是否停业（1：营业 0：停业）")
    private Integer openFlag;

    @ApiModelProperty("使用状态（1：正在使用 0：停用（订单更换了消纳场））")
    private Integer state;

    @ApiModelProperty("好土券")
    private Integer goodSoilTicket = 0;

    @ApiModelProperty("坏土券")
    private Integer badSoilTicket = 0;

}
