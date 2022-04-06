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
@ApiModel(value="发起支付交易Vo")
public class TradePayVo implements Serializable {

    @ApiModelProperty("(必填)支付发起端app(1--机主端  2--承租方端)")
    private Integer clientType;

    @ApiModelProperty("(必填)交易类型(1--承租方支付订单 2--机主提现 3--机主支付工资 4--司机提现)")
    private Integer tradeType;

    @ApiModelProperty("(必填)第三方支付类型(0--平台垫付 1--银联 2--微信 3--支付宝)")
    private Integer paymentMethod;

    @ApiModelProperty("(必填)交易金额,保留2位小数 示例--51.32")
    private String tradeAmount;

    @ApiModelProperty("承租方项目id")
    private Integer projectId;

    @ApiModelProperty("承租方订单id")
    private Long tenantryOrderId;

    @ApiModelProperty("趟数集合id")
    private ArrayList<Long> routeIdList;

    @ApiModelProperty("打卡id")
    private Long clockId;

    @ApiModelProperty("支付宝收款账户")
    private String payeeAccount;

    @ApiModelProperty("交易备注")
    private String remark;

    @ApiModelProperty("商户网站唯一订单号")
    private String outTradeNo;

    @ApiModelProperty("付款人的userId")
    private Integer payerUserId;

    @ApiModelProperty("收款人的userId")
    private Integer payeeUserId;

    /**
     * 交易类型(1--承租方支付订单 2--机主提现 3--机主支付工资 4--司机提现)
     */
    public interface tradeType{
        Integer TEN_PAY = 1;
        Integer OWNER_TOKE = 2;
        Integer OWNER_PAY = 3;
        Integer DRIVER_TOKEN = 4;
    }

    /**
     * 第三方支付类型(1--银联支付  2--微信支付 3--支付宝支付)
     */
    public interface PaymentMethod{
        Integer PLATPAY = 0;
        Integer UNIONPAY = 1;
        Integer WXPAY = 2;
        Integer ALIPAY =3;
    }

}
