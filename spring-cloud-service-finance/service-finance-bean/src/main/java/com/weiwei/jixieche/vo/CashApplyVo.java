package com.weiwei.jixieche.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * @ClassName TradePayVo
 * @Description TODO
 * @Author houji
 * @Date 2019/8/16 13:59
 * @Version 1.0.1
 **/
@Data
@EqualsAndHashCode
@ApiModel(value="提现申请Vo")
public class CashApplyVo implements Serializable {

    @ApiModelProperty("提现的银行卡")
    private Integer cardId;

    @ApiModelProperty("提现的支付密码")
    private String payPassword;

    @ApiModelProperty("提现的金额(单位:元),必须保留2位小数")
    private String amount;

}
