package com.weiwei.jixieche.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;
import lombok.EqualsAndHashCode;
/**
 * @Author 钟焕 
 * @Description
 * @Date 18:31 2019-08-26
 **/
@Data
@EqualsAndHashCode
@ApiModel(value="异常订单申诉表")
public class JxcAbnormalOrderAppeal implements Serializable {
       @ApiModelProperty("主键自增")
       private Long id;

       @ApiModelProperty("项目id")
       private Integer projectId;

       @ApiModelProperty("项目订单id(承租方订单id)")
       private Long tenantryOrderId;

       @ApiModelProperty("机主订单id")
       private Long ownerOrderId;

       @ApiModelProperty("行程id")
       private Long routeId;

       @ApiModelProperty("打卡id(打卡比对表clock_in_id)")
       private Long clockId;

       @ApiModelProperty("异常类型(0--里程异常 1--打卡异常 2--台班异常)")
       private Integer abnormalType;

       @ApiModelProperty("申诉原因(异常描述)")
       private String abnormalDescription;

       @ApiModelProperty("异常状态(0--异常申请待处理 1--处理完毕)")
       private Integer abnormalStatus;

       @ApiModelProperty("异常处理完成，协商价格(单位分)")
       private Integer consultPrice;

       @ApiModelProperty("异常申请用户userid")
       private Integer appealUserId;

       @ApiModelProperty("异常申请用户类型(2：机主 3：承租方 4：司机)")
       private Integer appealUserType;

       @ApiModelProperty("异常申请用户真实姓名")
       private String appealUserName;

       @ApiModelProperty("申诉人回访预留电话")
       private String appealUserPhone;

       @ApiModelProperty("协商处理人userid")
       private Integer consultUserId;

       @ApiModelProperty("协商处理人姓名")
       private String consultUserName;

       @ApiModelProperty("创建时间")
       private Date createTime;

       @ApiModelProperty("修改时间(处理异常时间)")
       private Date updateTime;

       private static final long serialVersionUID = 1L;
}