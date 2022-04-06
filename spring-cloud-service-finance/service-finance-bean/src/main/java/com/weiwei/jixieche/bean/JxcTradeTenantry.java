package com.weiwei.jixieche.bean;

import com.weiwei.jixieche.page.BasePage;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.nio.channels.InterruptedByTimeoutException;
import java.util.Date;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
@ApiModel(value="承租方交易流水表")
public class JxcTradeTenantry extends BasePage implements Serializable {
       @ApiModelProperty("商户订单id")
       private Long id;

       @ApiModelProperty("付款人的userId")
       private Integer payerUserId;

       @ApiModelProperty("收款人的userId(0--平台系统)")
       private Integer payeeUserId;

       @ApiModelProperty("交易类型(1--支付机主订单)")
       private Integer tradeType;

       @ApiModelProperty("承租方支付金额(单位：分)")
       private Integer tradeAmount;

       @ApiModelProperty("项目id")
       private Integer projectId;

       @ApiModelProperty("承租方订单id")
       private Long tenantryOrderId;

       @ApiModelProperty("第三方支付方式(1--银联 2--微信 3--支付宝)")
       private Integer paymentMethod;

       @ApiModelProperty("第三方支付流水号(银联，支付宝，微信)")
       private String thirdTradeNo;

       @ApiModelProperty("交易状态(0--未成功  1--交易成功)")
       private Integer tradeStatus;

       @ApiModelProperty("创建时间")
       private Date createTime;

       @ApiModelProperty("修改时间")
       private Date updateTime;

       /**
        * 第三方支付方式(1--银联 2--微信 3--支付宝)
        */
       public interface PaymentMethod{
              Integer UNION = 1;
              Integer WXPAY = 2;
              Integer ALIPAY = 3;
       }

       /**
        * 交易状态(0--未成功  1--交易成功)
        */
       public interface tradeState {
              Integer UN_SUCCESS = 0;
              Integer SUCCESS = 1;
       }



       private static final long serialVersionUID = 1L;
}