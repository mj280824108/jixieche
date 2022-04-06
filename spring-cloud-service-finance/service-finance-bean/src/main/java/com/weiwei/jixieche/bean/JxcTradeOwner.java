package com.weiwei.jixieche.bean;

import com.weiwei.jixieche.page.BasePage;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
@ApiModel(value="机主交易流水表")
public class JxcTradeOwner extends BasePage implements Serializable {
       @ApiModelProperty("商户唯一订单id")
       private Long id;

       @ApiModelProperty("交易类型(1--平台垫付 2--机主提现 3--机主支付工资  4--司机提现)")
       private Integer tradeType;

       @ApiModelProperty("付款人的用户userId,其中(0--系统平台)")
       private Integer payerUserId;

       @ApiModelProperty("收款人的userId,其中 0代表系统平台")
       private Integer payeeUserId;

       @ApiModelProperty("承租方的订单id")
       private Long tenantryOrderId;

       @ApiModelProperty("机械行程id")
       private Long routeId;

       @ApiModelProperty("交易金额(单位：分)，备注(1-每趟行程扣除佣金后的金额 2-司机台班金额)")
       private Integer tradeAmount;

       @ApiModelProperty("提现实际到账金额(单位：分)")
       private Integer actualAmount;

       @ApiModelProperty("交易状态(0--失败  1--成功)")
       private Integer tradeStatus;

       @ApiModelProperty("支付台班打卡id")
       private Long clockId;

       @ApiModelProperty("支付方式(0--平台垫付 1--银联 2--微信 3--支付宝)")
       private Integer paymentMethod;

       @ApiModelProperty("第三方支付流水号(银联，支付宝，微信)")
       private String thirdTradeNo;

       @ApiModelProperty("冻结状态(0--冻结 1--解冻)")
       private Integer lockStatus;

       @ApiModelProperty("提现的银行卡id")
       private Integer bankCardId;

       @ApiModelProperty("提现处理状态(0--未处理 1--已处理)")
       private Integer cashStatus;

       @ApiModelProperty("创建时间")
       private Date createTime;

       @ApiModelProperty("修改时间")
       private Date updateTime;

       /**
        * 交易状态(0--失败  1--成功)
        */
       public interface TradeStatus{
              Integer UN_SUCCESS  = 0;
              Integer SUCCESS = 1;
       }

       /**
        * 提现处理状态(0--未处理 1--已处理)
        */
       public  interface CashStatus{
              Integer NO_DONE = 0;
              Integer DONE = 1;
       }

       /**
        * 冻结状态(0--冻结 1--解冻)
        */
       public interface LockStatus{
              Integer LOCK = 0;
              Integer UNLOCK = 1;
       }

       /**
        * 交易类型(1--平台垫付 2--机主提现 3--机主支付工资  4--司机提现)
        */
       public interface UserTradeType{
              Integer PLAT_PAY = 1;
              Integer OWNER_TAKE = 2;
              Integer OWNER_PAY = 3;
              Integer DRIVER_TAKE = 4;
       }
}