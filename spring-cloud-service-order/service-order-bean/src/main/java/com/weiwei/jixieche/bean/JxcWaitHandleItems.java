package com.weiwei.jixieche.bean;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
@ApiModel(value="待处理事项")
public class JxcWaitHandleItems implements Serializable {
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
       private String machineCarNo;

       @ApiModelProperty("司机ID")
       private Integer driverId;

       @ApiModelProperty("异常行程的ID")
       private Long routeId;

       @ApiModelProperty("项目名称")
       private String projectName;

       @ApiModelProperty("应收金额")
       private Integer payAmount;

       @ApiModelProperty("机主用户或承租方用户")
       private Integer userId;

       @ApiModelProperty("待支付账单开始时间")
       @JsonFormat(pattern = "yyyy-MM-dd" , timezone = "GMT+8")
       private Date billStartDate;

       @ApiModelProperty("待支付账单结束时间")
       @JsonFormat(pattern = "yyyy-MM-dd" , timezone = "GMT+8")
       private Date billEndDate;

       @ApiModelProperty("0:未删除; 1:已删除")
       private Integer isDeleted;

       @ApiModelProperty("创建时间")
       private Date createTime;

       @ApiModelProperty("更新时间")
       private Date updateTime;

       private static final long serialVersionUID = 1L;

       //1:退场申请; 2:异常处理; 3:待支付; 4:异常待支付; 11:上班司机设置; 12:未添加司机;
       public interface itemType{
              Integer ITEMTYPE1 = 1;
              Integer ITEMTYPE2 = 2;
              Integer ITEMTYPE3 = 3;
              Integer ITEMTYPE4 = 4;
              /**
               * 上班司机设置
               */
              Integer ITEMTYPE11 = 11;
              Integer ITEMTYPE12 = 12;
       }
}