package com.weiwei.jixieche.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.List;

@Data
@EqualsAndHashCode
@ApiModel(value="机械提醒设置Vo")
public class MachineRemindRecodVo implements Serializable {

       @ApiModelProperty("(参数必填)机械id")
       private Long machineId;

       @ApiModelProperty("(参数必填)机主id")
       private Long ownerId;

       @ApiModelProperty("必填的机械提醒集合")
       private List<MachineMustRemindListVo> machineMustRemindList;

       @ApiModelProperty("其他机械提醒集合")
       private List<MachineMustRemindListVo> machineOtherRemindList;

}