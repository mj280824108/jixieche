package com.weiwei.jixieche.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

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
@ApiModel(value="机主我的钱包")
public class OwnerWalletVo {

    @ApiModelProperty("(参数必填)用户userId")
    private Integer userId;

    @ApiModelProperty("总资产(单位:元)")
    private String totalAmount;

    @ApiModelProperty("冻结金额(单位:元)")
    private String lockAmount;

    @ApiModelProperty("可用余额(单位:元)")
    private String unLockAmount;


}
