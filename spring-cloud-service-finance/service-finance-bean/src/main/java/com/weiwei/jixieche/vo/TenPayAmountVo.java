package com.weiwei.jixieche.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @ClassName OwnerWallet
 * @Description TODO
 * @Author houji
 * @Date 2019/8/30 9:42
 * @Version 1.0.1
 **/
@Data
@EqualsAndHashCode
@ApiModel(value="承租方支付金额Vo")
public class TenPayAmountVo implements Serializable {

    @ApiModelProperty("筛选条件月份(yyyy-MM)")
    private String dateTime;

    @ApiModelProperty("参数机主的userId")
    private Integer userId;

    @ApiModelProperty("支出金额(元)")
    private String totalPayAmount;

}
