package com.weiwei.jixieche.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Data
@EqualsAndHashCode
@ApiModel(value="机械提醒必填设置集合Vo")
public class MachineMustRemindListVo implements Serializable {

       @ApiModelProperty("提醒id")
       private Integer id;

       @ApiModelProperty("提醒名称")
       private String remindName;

       @ApiModelProperty("提醒类型(参数)")
       private Integer remindType;

       @ApiModelProperty("提醒记录id")
       private Integer remindRecordId;

       @ApiModelProperty("机械id")
       private Long machineId;

       @ApiModelProperty("机主id")
       private Long ownerId;

       @ApiModelProperty("车牌号")
       private String machineCardNo;

       @ApiModelProperty("车辆提醒类型id(同一辆车只有一个提醒类型)")
       private Integer machineRemindId;

       @ApiModelProperty("设置的提醒时间")
       private String machineRemindTime;

}