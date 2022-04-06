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
@ApiModel(value="微信发起支付Vo")
public class WxTradePayVo implements Serializable {

    @ApiModelProperty("商品描述")
    private String body;

    @ApiModelProperty("商户网站唯一订单号")
    private String attach;

    @ApiModelProperty("商户网站唯一订单号")
    private String outTradeNo;

    @ApiModelProperty("标价金额")
    private Integer totalFee;

    @ApiModelProperty("终端IP")
    private String spBillCreateIp;

    @ApiModelProperty("商户网站唯一订单号")
    private Integer type;

    @ApiModelProperty("支付发起端app(1--机主端  2--承租方端)")
    private Integer clientType;

    public interface tradeType{
        //交易类型(1--承租方支付订单 2--机主提现 3--机主支付工资 4--司机提现)
        Integer TEN_PAY = 1;
        Integer OWNER_IN = 2;
        Integer OWNER_OUT = 3;
        Integer DRIVER_IN = 4;
    }

}
