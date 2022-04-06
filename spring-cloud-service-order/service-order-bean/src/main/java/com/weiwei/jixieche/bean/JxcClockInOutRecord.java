package com.weiwei.jixieche.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

@Data
@EqualsAndHashCode
@ApiModel(value="司机打卡记录表")
public class JxcClockInOutRecord implements Serializable {
       @ApiModelProperty("上下班打卡记录ID")
       private Long recordId;

       @ApiModelProperty("记录类型 1：上班卡 2：下班卡")
       private Integer recordType;

       private Integer machineId;

       @ApiModelProperty("相关联子账号ID")
       private Integer childId;

       @ApiModelProperty("司机ID")
       private Integer driverId;

       @ApiModelProperty("职位ID")
       private Integer shortJobId;

       @ApiModelProperty("打卡时间")
       private Date clockTime;

       @ApiModelProperty("打卡地点")
       private String clockAddress;

       @ApiModelProperty("是否为后台强制打下班卡 0：不是 1：是")
       private Integer isForce;

       @ApiModelProperty("交接班的司机ID")
       private Integer upDriverId;

       @ApiModelProperty("交接班的行程ID")
       private Long routeId;

       @ApiModelProperty("机主用户ID")
       private Integer ownerUserId;

       private static final long serialVersionUID = 1L;

       /**
        * 记录类型 1：上班卡 2：下班卡
        */
       public interface  recordType{
              Integer CLOCKIN = 1;
              Integer CLOCKOUT = 2;
       }
}