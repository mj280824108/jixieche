package com.weiwei.jixieche.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * @ClassName UserRegisterVo
 * @Description TODO
 * @Author houji
 * @Date 2019/8/13 18:08
 * @Version 2.0
 **/
@Data
@EqualsAndHashCode
@ApiModel(value="用户设置支付密码Vo")
public class UserSetPayPwdVo implements Serializable {

    @ApiModelProperty("支付密码设置类型(1--设置支付密码  2--忘记支付密码)")
    private Integer payPwdType;

    @ApiModelProperty("旧支付密码")
    private String newPayPwd;

}
