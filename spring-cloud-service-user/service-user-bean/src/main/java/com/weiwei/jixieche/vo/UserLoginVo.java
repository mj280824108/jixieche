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
@ApiModel(value="用户登录Vo")
public class UserLoginVo implements Serializable {

    @ApiModelProperty("(必填)登录类型(1--手机号密码登录 2--手机短信验证码登录)")
    private Integer loginType;

    @ApiModelProperty("(必填)户端类型，1,机主版本 2,承租方版本")
    private Integer clientType;

    @ApiModelProperty("(必填)手机号码")
    private String phone;

    @ApiModelProperty("密码")
    private String password;

    @ApiModelProperty("短信验证码")
    private String verifyCode;

    @ApiModelProperty("设备id")
    private String deviceId;

    public interface loginType{
        //登录类型(1--手机号密码登录 2--手机短信验证码登录)
        Integer pwd = 1;
        Integer code = 2;
    }

}
