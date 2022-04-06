package com.weiwei.jixieche.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @ClassName AliTradePayReturnVo
 * @Description TODO
 * @Author houji
 * @Date 2019/8/16 19:08
 * @Version 1.0.1
 **/
@Data
@EqualsAndHashCode
@ApiModel(value="支付宝发起支付返回Vo")
public class AliTradePayReturnVo {

    @ApiModelProperty("商户唯一订单号")
    private String outTradeNo;

    @ApiModelProperty("交易金额(单位:分)")
    private String tradeAmount;

    @ApiModelProperty("对一笔交易的具体描述信息")
    private String orderString;

    @ApiModelProperty("第三方支付类型(1--银联支付  2--微信支付 3--支付宝支付)")
    private Integer thirdPayType = 3;

    @ApiModelProperty("交易备注")
    private String remark;

}
