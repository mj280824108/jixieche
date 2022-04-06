package com.weiwei.jixieche.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Data
@EqualsAndHashCode
@ApiModel(value="机械提醒设置集合列表Vo")
public class JxcMachineRemindListVo implements Serializable {

       @ApiModelProperty("(参数)每页条数")
       private Integer pageSize = 20;

       @ApiModelProperty("(参数)页码数")
       private Integer pageNo = 1;

       @ApiModelProperty("(参数必填)机主id")
       private Long ownerId;

       @ApiModelProperty("机械id")
       private Long machineId;

       @ApiModelProperty("车牌号")
       private String machineCardNo;

       @ApiModelProperty("车辆年检状态(0:未设置 1:已设置 2:过期)")
       private int  inspectionStatus;

       @ApiModelProperty("车辆保险状态(0:未设置 1:已设置 2:过期)")
       private int  safeStatus;

       @ApiModelProperty("车辆保养状态状态(0:未设置 1:已设置 2:过期)")
       private int  careStatus;

       //0:未设置 1:已设置 2:过期
       public interface Status{
              Integer NOT_SETTING = 0;
              Integer SETTING = 1;
              Integer OVER_TIME =2;
       }

       private static final long serialVersionUID = 1L;
}