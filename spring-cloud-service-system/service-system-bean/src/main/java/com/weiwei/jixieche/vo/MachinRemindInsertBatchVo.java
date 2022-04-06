package com.weiwei.jixieche.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.List;

@Data
@EqualsAndHashCode
@ApiModel(value="机主批量添加机械提醒记录")
public class MachinRemindInsertBatchVo implements Serializable {

       @ApiModelProperty("(参数必填)机主id")
       private Long ownerId;

       @ApiModelProperty("(参数必填)机械id")
       private Long machineId;

       @ApiModelProperty("记录必填内容")
       List<BatchInsertListVo> batchDataList;

}