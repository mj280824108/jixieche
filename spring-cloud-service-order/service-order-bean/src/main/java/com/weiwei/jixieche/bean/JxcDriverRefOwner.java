package com.weiwei.jixieche.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;

import io.swagger.annotations.ApiOperation;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
@ApiModel(value="子账号与机主关联关系表")
public class JxcDriverRefOwner implements Serializable {
       private Long id;

       @ApiModelProperty("机主id")
       private Integer ownerId;

       @ApiModelProperty("司机用户ID")
       private Integer driverId;

       @ApiModelProperty("机主给全职司机的备注名称")
       private String remarkName;

       @ApiModelProperty("机械账号电话")
       private String phone;

       @ApiModelProperty("删除状态（1：未删除 0：已删除 2:解雇）")
       private Integer delState;

       @ApiModelProperty("司机类型: 1:我自己; 2:我的司机; 3:兼职司机")
       private Integer driverType;

       @ApiModelProperty("创建时间")
       private Date createTime;

       @ApiModelProperty("修改时间")
       private Date updateTime;

       @ApiModelProperty("兼职职位ID")
       private Integer shortJobId;

       /**
        * 司机类型: 1:我自己; 2:我的司机; 3:兼职司机
        */
       public interface DriverType{
              Integer OWN = 1;
              Integer MY_DRIVER = 2;
              Integer SHORT_DRIVER =3;
       }

       /**
        * 删除状态（1：未删除 0：已删除 2:解雇）
        */
       public interface DriverDelState{
              Integer IS_DELETE = 0;
              Integer NO_DELETE = 1;
              Integer FIRE_DELETE = 2;
       }
       private static final long serialVersionUID = 1L;
}