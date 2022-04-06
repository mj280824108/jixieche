package com.weiwei.jixieche.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Data
@EqualsAndHashCode
@ApiModel(value="机械提醒设置Vo")
public class MachineNotExisitRemindVo implements Serializable {

       @ApiModelProperty("(参数必填)机主id")
       private Long ownerId;

       @ApiModelProperty("(参数必填)机械id")
       private Long machineId;

       @ApiModelProperty("(返回值)提醒id(1.车辆年检提醒 2.车辆保险提醒 3.车辆保养提醒 4.行驶证提醒 5.驾驶证提醒 6.车辆运营证提醒)")
       private Integer machineRemindId;

       @ApiModelProperty("(返回值)提醒名称")
       private String remindName;

}