package com.weiwei.jixieche.vo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.math.BigDecimal;

@Api("打卡记录")
@Data
@EqualsAndHashCode
public class ClockRecord {

    //上下班配对表即台班表主键
    @ApiModelProperty("上班记录Id")
    private Long clockInId;

    @ApiModelProperty("下班记录Id")
    private Long clockOutId;

    @ApiModelProperty("上班打卡时间")
    private String clockInTime = "";

    @ApiModelProperty("下班打卡时间")
    private String clockOutTime = "";

    //上班打卡地点
    @ApiModelProperty("上班打卡地址")
    private String clockInAddress = "";

    @ApiModelProperty("下班打卡地址")
    private String clockOutAddress = "";

    @ApiModelProperty("申诉状态 0:申诉 1：申诉中 2：申诉完成(隐藏按钮)")
    private String applyState = "0";

    @ApiModelProperty("已收工资")
    private BigDecimal factAmount = new BigDecimal(0);

    @ApiModelProperty("区域ID")
    private Integer areaId;

    @ApiModelProperty("工时")
    private double workHours;
}