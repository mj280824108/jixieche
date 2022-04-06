package com.weiwei.jixieche.vo;

import com.weiwei.jixieche.page.BasePage;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
@EqualsAndHashCode
@ApiModel(value="承租方待办事项Vo")
public class JxcWaitHandleItemsVo extends BasePage {
       @ApiModelProperty("待办事项记录ID")
       private Integer id;

       @ApiModelProperty("1:退场申请; 2:异常处理; 3:待支付; 4:异常待支付; 11:上班司机设置; 12:未添加司机;")
       private Integer itemType;

       @ApiModelProperty("承租方订单ID")
       private Long projectOrderId;

       @ApiModelProperty("机主订单ID")
       private Long ownerOrderId;

       @ApiModelProperty("机械ID")
       private Integer machineId;

       @ApiModelProperty("车牌号码")
       private String machineCardNo;

       @ApiModelProperty("司机ID")
       private Integer driverId;

       @ApiModelProperty("异常行程的ID")
       private Long routeId;

       @ApiModelProperty("项目名称")
       private String projectName;

       @ApiModelProperty("应收金额")
       private Integer payAmount;

       @ApiModelProperty("应收金额")
       private String payAmountStr;

       @ApiModelProperty("机主用户或承租方用户")
       private Integer userId;

       @ApiModelProperty("账单开始时间")
       private String billStartDate;

       @ApiModelProperty("账单结束时间")
       private String billEndDate;

       @ApiModelProperty("创建时间")
       private String createTime;

       @ApiModelProperty("1：施工场地漏打卡 2：消纳场地漏打卡 3：里程异常")
       private Integer abnormalType;

       @ApiModelProperty("订单结算方式 1：日结 2：周结")
       private Integer accountMethod;

       @ApiModelProperty("进场日期")
       private String startDate;

       @ApiModelProperty("出场日期")
       private String endDate;

}