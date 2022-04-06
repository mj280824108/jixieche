package com.weiwei.jixieche.bean;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.weiwei.jixieche.page.BasePage;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
@ApiModel(value="司机台班打卡配对表")
public class JxcClockInOutPair extends BasePage implements Serializable {
       @ApiModelProperty("上班卡记录ID")
       private Long clockInId;

       @ApiModelProperty("下班卡记录ID")
       private Long clockOutId;

       @ApiModelProperty("兼职职位ID")
       private Integer shortJobId;

       @ApiModelProperty("机械ID")
       private Integer machineId;

       @ApiModelProperty("司机用户ID")
       private Integer driverId;

       @ApiModelProperty("子账号ID")
       private Integer childId;

       @ApiModelProperty("上班时间")
       @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
       private Date clockInTime;

       @ApiModelProperty("下班时间")
       @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
       private Date clockOutTime;

       @ApiModelProperty("台班支付状态 0：未支付 1：已支付")
       private Integer payState;

       @ApiModelProperty("实际支付台班金额（包含加班趟数费用，单位：分）")
       private Integer factAmount;

       @ApiModelProperty("申诉状态 0：申诉 1：申诉中 2：申诉完成")
       private Integer applyState;

       private static final long serialVersionUID = 1L;

       @ApiModelProperty("区域Id")
       private Integer areaId;

       @ApiModelProperty("司机账单ID")
       private Long clockId;
}