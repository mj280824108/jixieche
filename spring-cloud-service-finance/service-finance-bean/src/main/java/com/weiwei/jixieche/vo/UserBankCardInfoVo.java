package com.weiwei.jixieche.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * @ClassName UserCardInfoVo
 * @Description TODO
 * @Author houji
 * @Date 2019/9/4 14:34
 * @Version 1.0.1
 **/
@Data
@EqualsAndHashCode
@ApiModel(value="银行卡信息Vo")
public class UserBankCardInfoVo  implements Serializable {

    @ApiModelProperty("银行卡id")
    private Integer id;

    @ApiModelProperty("银行卡人姓名")
    private String name;

    @ApiModelProperty("银行卡号")
    private String cardNumber;

    @ApiModelProperty("开户行名称")
    private String bankName;

    @ApiModelProperty("银行logo图片")
    private String logo;

}
