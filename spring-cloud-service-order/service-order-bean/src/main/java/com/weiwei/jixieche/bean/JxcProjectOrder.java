package com.weiwei.jixieche.bean;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.weiwei.jixieche.page.BasePage;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;
import lombok.EqualsAndHashCode;
/**
 * @Author 钟焕 
 * @Description
 * @Date 21:40 2019-08-14
 **/
@Data
@EqualsAndHashCode
@ApiModel(value="承租方订单表")
public class JxcProjectOrder extends BasePage {
       @ApiModelProperty("承租方订单ID")
       private Long id;

       @ApiModelProperty("订单状态（0：待接单 1：取消订单 2：进行中 3：已完结）")
       private Integer orderState;

       @ApiModelProperty("项目ID")
       private Integer projectId;

       @ApiModelProperty("城市编码")
       private Integer cityCode;

       @ApiModelProperty("项目名称")
       private String projectName;

       @ApiModelProperty("承租方用户ID")
       private Integer userId;

       @ApiModelProperty("土方类型（0：坏土 1：好土）")
       private Integer earthType;

       @ApiModelProperty("每车方量")
       private Integer capacity;

       @ApiModelProperty("预计工程量（趟数）")
       private Integer estimateTransportTimes;

       @ApiModelProperty("预计要车数量")
       private Integer estimateMachineCount;

       @ApiModelProperty("进场日期")
       @JsonFormat(pattern = "yyyy-MM-dd" , timezone = "GMT+8")
       private Date startDate;

       @ApiModelProperty("出场日期")
       @JsonFormat(pattern = "yyyy-MM-dd" , timezone = "GMT+8")
       private Date endDate;

       @ApiModelProperty("上班钟点")
       private String workStartClock;

       @ApiModelProperty("下班钟点")
       private String workEndClock;

       @ApiModelProperty("结算方式（1：日结 2：周结）")
       private Integer accountMethod;

       @ApiModelProperty("停工标记（0：正常 1：停工）")
       private Integer stopWorkState;

       @ApiModelProperty("停工开始日期")
       private Date stopWorkStart;

       @ApiModelProperty("停工截止日期")
       private Date stopWorkEnd;

       @ApiModelProperty("申请停工或者提前完工的原因")
       private String endExplain;

       @ApiModelProperty("继续要车按钮标记（1：显示 0：不显示）")
       private Integer continueCarFlag;

       @ApiModelProperty("创建时间")
       private Date createTime;

       @ApiModelProperty("修改时间")
       private Date updateTime;

       private static final long serialVersionUID = 1L;
}