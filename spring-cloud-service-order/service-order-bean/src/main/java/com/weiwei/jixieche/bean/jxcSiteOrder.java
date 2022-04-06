package com.weiwei.jixieche.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * @Author zhong huan
 * @Date 2019-08-19 19:30
 * @Description
 */
@Data
@EqualsAndHashCode
@ApiModel(value="消纳券订单表")
public class jxcSiteOrder {
    @ApiModelProperty("订单编号")
    private Long id;

    @ApiModelProperty("所属消纳场id")
    private Integer siteId;

    @ApiModelProperty("承租方购卡人id")
    private Integer tenantryId;

    @ApiModelProperty("订单状态 (0:未处理订单 1:已处理订单)")
    private Integer orderFlag;

    @ApiModelProperty("订单创建时间")
    private Date createTime;

    @ApiModelProperty("是否删除、逻辑删除标识。0：正常 1：已删除")
    private Integer delFlag;
}
