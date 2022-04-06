package com.weiwei.jixieche.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.io.Serializable;
import java.util.Date;

@Data
@EqualsAndHashCode
@ApiModel(value="司机每一天的打卡记录与司机配对表关系")
public class JxcClockPairVo implements Serializable {

       private Integer id;

       @ApiModelProperty("申诉时用到的ID")
       private Long clockId;

       @ApiModelProperty("配对表主键Id")
       private Long clockInId;

       @ApiModelProperty("司机用户ID")
       private Integer driverId;

       @ApiModelProperty("机械ID")
       private Integer machineId;

       @ApiModelProperty("职位ID")
       private Integer shortJobId;

       @ApiModelProperty("创建时间")
       private Date createTime;

       @ApiModelProperty("台班支付状态 0：未支付 1：已支付")
       private Integer payState;

       @ApiModelProperty("实际支付台班金额（包含加班趟数费用，单位：分）")
       private Integer factAmount;

       @ApiModelProperty("申诉状态 0：申诉 1：申诉中 2：申诉完成")
       private Integer applyState;

       @ApiModelProperty("打卡次数")
       private Integer clockCount;

       @ApiModelProperty("总趟数")
       private Integer totalRoute;

       @ApiModelProperty("总工时")
       private double workHours;

       @ApiModelProperty("加班趟数")
       private Integer overTrainNum;

       @ApiModelProperty("打卡日期")
       private Date clockDate;

       private static final long serialVersionUID = 1L;
}