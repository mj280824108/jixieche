package com.weiwei.jixieche.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * @ClassName TradePayVo
 * @Description TODO
 * @Author houji
 * @Date 2019/8/16 13:59
 * @Version 1.0.1
 **/
@Data
@EqualsAndHashCode
@ApiModel(value="支付宝发起支付Vo")
public class AliTradePayVo implements Serializable {

    @ApiModelProperty("支付发起端app(1--机主端  2--承租方端)")
    private Integer clientType;

    @ApiModelProperty("交易类型(1--承租方支付订单 2--机主提现 3--机主支付工资 4--司机提现)")
    private Integer tradeType;

    @ApiModelProperty("交易金额,保留2位小数(单位:分)")
    private String tradeAmount;

    @ApiModelProperty("承租方订单id")
    private Long tenantryOrderId;

    @ApiModelProperty("机主订单id")
    private Long ownerOrderId;

    @ApiModelProperty("短期招聘职位id")
    private Long shortJobId;

    @ApiModelProperty("趟数集合id")
    private ArrayList<Long> machineRouteIdList;

    @ApiModelProperty("打卡集合id")
    private ArrayList<Long> clockIdList;

    @ApiModelProperty("支付宝收款账户")
    private String payeeAccount;

    @ApiModelProperty("交易备注")
    private String remark;

    @ApiModelProperty("商户网站唯一订单号")
    private String outTradeNo;

    public interface tradeType{
        //交易类型(1--承租方支付订单 2--机主提现 3--机主支付工资 4--司机提现)
        Integer TEN_PAY = 1;
        Integer OWNER_IN = 2;
        Integer OWNER_OUT = 3;
        Integer DRIVER_IN = 4;
    }

}
