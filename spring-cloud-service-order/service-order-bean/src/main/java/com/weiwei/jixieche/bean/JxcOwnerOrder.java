package com.weiwei.jixieche.bean;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
* @Description: 机主订单管理
* @Author:      liuy
* @Date:  2019/8/20 15:16
*/
@Data
@EqualsAndHashCode
@ApiModel(value="机主订单表")
public class JxcOwnerOrder implements Serializable {
       @ApiModelProperty("机主订单ID")
       private Long id;

       @ApiModelProperty("机主订单状态（0：已接单 1：取消订单（未到进行中前被取消） 2：进行中 3：已完结（退出） 4：进行中被承租方解雇）")
       private Integer orderState;

       @ApiModelProperty("承租方订单ID")
       private Long tenantryOrderId;

       @ApiModelProperty("机主ID")
       private Integer userId;

       @ApiModelProperty("接单机械ID")
       private Integer machineId;

       @ApiModelProperty("接单司机ID")
       private Integer driverId;

       @ApiModelProperty("接单类型（0：接单 1：派单）")
       private Integer acceptType;

       @ApiModelProperty("实际开工时间")
       private Date factStartTime;

       @ApiModelProperty("实际完工时间")
       private Date factEndTime;

       @ApiModelProperty("解雇原因")
       private String fireReason;

       @ApiModelProperty("请假标记（0：正常 1：请假 2：申请退出待承租方审核）")
       private Integer leaveState;

       @ApiModelProperty("请假原因（或申请退出原因）")
       private String leaveReason;

       @ApiModelProperty("请假开始时间")
       @JsonFormat(pattern="yyyy-MM-dd",timezone="GMT+8")
       private Date leaveStart;

       @ApiModelProperty("请假截止时间")
       @JsonFormat(pattern="yyyy-MM-dd",timezone="GMT+8")
       private Date leaveEnd;

       @ApiModelProperty("取消原因ID")
       private Integer cancelId;

       private Date createTime;

       private Date updateTime;

       private static final long serialVersionUID = 1L;
}