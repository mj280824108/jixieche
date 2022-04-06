package com.weiwei.jixieche.bean;

import com.weiwei.jixieche.page.BasePage;
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
 * @Date 18:29 2019-08-26
 **/
@Data
@EqualsAndHashCode
@ApiModel(value="机械行程表")
public class JxcMachineRoute extends BasePage implements Serializable {
       @ApiModelProperty("行程ID")
       private Long id;

       @ApiModelProperty("项目ID")
       private Integer projectId;

       @ApiModelProperty("项目名称")
       private String projectName;

       @ApiModelProperty("承租方订单ID")
       private Long tenantryOrderId;

       @ApiModelProperty("承租方ID")
       private Integer tenantryId;

       @ApiModelProperty("机主ID")
       private Integer ownerId;

       @ApiModelProperty("承租方姓名")
       private String tenantryName;

       @ApiModelProperty("机主订单ID")
       private Long ownerOrderId;

       @ApiModelProperty("机械ID")
       private Integer machineId;

       @ApiModelProperty("司机用户ID")
       private Integer driverId;

       @ApiModelProperty("机械账号ID")
       private Integer childId;

       @ApiModelProperty("施工场地打卡时间")
       private Date startTime;

       @ApiModelProperty("消纳场打卡时间")
       private Date endTime;

       @ApiModelProperty("消纳场ID")
       private Integer siteId;

       @ApiModelProperty("异常类型（0：正常 1：施工场地漏打卡 2：消纳场地漏打卡 3：里程异常(实际里程超出或小于预计里程25%）")
       private Integer abnormalType;

       @ApiModelProperty("支付状态（0：异常趟或正在跑趟中 1：待支付（平台已垫付） 2：已支付）")
       private Integer payState;

       @ApiModelProperty("实际里程（单位：公里）")
       private BigDecimal factMileage;

       @ApiModelProperty("承租方应付金额（单位：分）")
       private Integer amount;

       @ApiModelProperty("机主实收金额（单位：分）")
       private Integer toOwnerAmount;

       @ApiModelProperty("发卡类型：1:实体卡; 2:消纳券")
       private Integer cardType;

       public interface AbnormalType{
              Integer NORMAL = 0;
              Integer ABNORMAL1 = 1;
              Integer ABNORMAL2 = 2;
              Integer ABNORMAL3 = 3;

       }

       private static final long serialVersionUID = 1L;
}