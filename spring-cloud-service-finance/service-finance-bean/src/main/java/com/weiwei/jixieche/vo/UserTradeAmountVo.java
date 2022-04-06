package com.weiwei.jixieche.vo;

/**
 * @ClassName UserTradeAmountVo
 * @Description TODO
 * @Author houji
 * @Date 2019/9/4 15:57
 * @Version 1.0.1
 **/

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Data
@EqualsAndHashCode
@ApiModel(value="用户收入金额Vo")
public class UserTradeAmountVo implements Serializable {

    @ApiModelProperty("用户userId")
    private Integer userId;

    @ApiModelProperty("交易类型(1--平台垫付 2--机主提现 3--机主支付工资  4--司机提现)")
    private Integer tradeType;

    @ApiModelProperty("收款人的userId,其中 0代表系统平台")
    private Integer payeeUserId;

    @ApiModelProperty("付款人的用户userId,其中(0--系统平台)")
    private Integer payerUserId;

    @ApiModelProperty("冻结状态(0--冻结 1--解冻)")
    private Integer lockStatus;

    @ApiModelProperty("查询结果金额")
    private Integer totalAmount;

    @ApiModelProperty("提现处理状态(0--未处理 1--已处理)")
    private Integer cashStatus;

    /**
     * 1--平台垫付 2--机主提现 3--机主支付工资  4--司机提现
     */
    public interface WalletType{
        Integer PLAT = 1;
        Integer OWNER_TAKE = 2;
        Integer OWNER_PAY =3;
        Integer DRIVER_TAKE = 4;
    }

    /**
     * 冻结状态(0--冻结 1--解冻)
     */
    public interface lockStatus{
        Integer LOCK = 0;
        Integer UNLOCK = 1;
    }
}
