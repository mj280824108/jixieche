package com.weiwei.jixieche.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Data
@EqualsAndHashCode
@ApiModel(value="机主批量添加机械提醒记录Vo")
public class BatchInsertListVo implements Serializable {

       @ApiModelProperty("(参数必填)提醒id")
       private Integer machineRemindId;

       @ApiModelProperty("(参数必填)提醒时间")
       private String machineRemindTime;

       @ApiModelProperty("车牌号")
       private String machineCardNo;

       @ApiModelProperty("车辆所属机主电话")
       private String ownePhone;



}