package com.weiwei.jixieche.bean;

import com.weiwei.jixieche.page.BasePage;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;

import io.swagger.annotations.ApiOperation;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
@ApiModel(value="机械与司机或子账号绑定关系表")
public class JxcMachineRefDriver extends BasePage {
       @ApiModelProperty("主键")
       private Long id;

       @ApiModelProperty("机械ID")
       private Integer machineId;

       @ApiModelProperty("车牌号码")
       private String machineCarNo;

       @ApiModelProperty("机主用户ID和司机ID")
       private Integer userId;

       @ApiModelProperty("机主用户名称")
       private String ownerUserName;

       @ApiModelProperty("机主手机号码")
       private String ownerPhone;

       @ApiModelProperty("绑定时间")
       private Date bindTime;

       @ApiModelProperty("解绑时间")
       private Date unbindTime;

       @ApiModelProperty("绑定状态（1：绑定 0：解绑）")
       private Integer bindState;

       @ApiModelProperty("机主用户ID")
       private Integer ownerUserId;

       @ApiModelProperty("机主绑定状态 （1：绑定 0：解绑）")
       private Integer ownerBindState = 1;

       private static final long serialVersionUID = 1L;

       //绑定状态（1：绑定 0：解绑）
       public interface bindState{
              Integer IS_BIND = 1;
              Integer NOT_BIND = 0;
       }
}