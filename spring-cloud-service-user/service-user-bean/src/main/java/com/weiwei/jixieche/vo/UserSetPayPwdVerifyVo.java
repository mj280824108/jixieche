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
@ApiModel(value="用户设置支付密码验证参数Vo")
public class UserSetPayPwdVerifyVo implements Serializable {

    @ApiModelProperty("支付密码设置类型(1--设置密码  2--忘记支付密码 3--修改支付密码)")
    private Integer payPwdType;

    @ApiModelProperty("用户真实姓名")
    private String realName;

    @ApiModelProperty("手机号码(登录账号)")
    private String phone;

    @ApiModelProperty("身份证号")
    private String cardCode;

    @ApiModelProperty("验证码")
    private String code;

    @ApiModelProperty("旧支付密码")
    private String oldPayPwd;

    @ApiModelProperty("旧支付密码")
    private String newPayPwd;

}
