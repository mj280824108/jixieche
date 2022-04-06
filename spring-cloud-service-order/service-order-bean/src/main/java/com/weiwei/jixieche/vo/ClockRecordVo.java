package com.weiwei.jixieche.vo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Api("每天打卡记录")
@Data
@EqualsAndHashCode
public class ClockRecordVo {

    @ApiModelProperty("总趟数")
    private Integer totalTrainNum = 0;

    @ApiModelProperty("加班趟数")
    private Integer overTrainNum = 0;

    @ApiModelProperty("车牌号码")
    private String machineCarNo;

    @ApiModelProperty("//台班支付状态 0-未支付 1-已支付")
    private Integer payState;

    @ApiModelProperty("应收工资")
    private BigDecimal factAmount;

    @ApiModelProperty("打卡次数")
    private Integer clockCount;

    @ApiModelProperty("工时")
    private double workHours;

    @ApiModelProperty("打卡记录")
    private List<ClockRecord> recordList;

    @ApiModelProperty("打卡日期")
    private String clockDate;

    @ApiModelProperty("申诉时用到的ID")
    private Long clockId;

    @ApiModelProperty("上班卡记录ID")
    private Long clockInId;

    @ApiModelProperty("申诉状态 0:申诉 1：申诉中 2：申诉完成(隐藏按钮)")
    private String applyState = "0";

    @ApiModelProperty("机械区域ID")
    private Integer areaId;

}