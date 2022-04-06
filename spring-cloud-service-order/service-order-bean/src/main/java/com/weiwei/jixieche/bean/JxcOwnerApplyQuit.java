package com.weiwei.jixieche.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
* @Description: 机主申请退出
* @Author:      liuy
* @Date:  2019/9/4 10:12
*/
@Data
@EqualsAndHashCode
@ApiModel(value="机主申请退出")
public class JxcOwnerApplyQuit implements Serializable {
       @ApiModelProperty("主键，自增")
       private Integer id;

       @ApiModelProperty("机主订单ID")
       private Long ownerOrderId;

       @ApiModelProperty("申请状态：0:申请中; 1:同意; 2:驳回")
       private Integer applyState;

       @ApiModelProperty("申请退出原因")
       private String applyReason;

       @ApiModelProperty("创建时间")
       private Date createTime;

       @ApiModelProperty("更新时间")
       private Date updateTime;

       private static final long serialVersionUID = 1L;
}