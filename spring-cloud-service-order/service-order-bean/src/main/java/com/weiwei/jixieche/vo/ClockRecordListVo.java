package com.weiwei.jixieche.vo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.List;

@Api("打卡记录-查询条件")
@Data
@EqualsAndHashCode
public class ClockRecordListVo {

    @ApiModelProperty("最后一条记录ID")
    private Long lastPageLastId;

    @ApiModelProperty("司机用户ID")
    private Integer driverUserId;

    @ApiModelProperty("打卡日期")
    private String clockDate;

    @ApiModelProperty("职位ID")
    private Integer shortJobId;

    @ApiModelProperty("区域ID")
    private Integer areaId;

}