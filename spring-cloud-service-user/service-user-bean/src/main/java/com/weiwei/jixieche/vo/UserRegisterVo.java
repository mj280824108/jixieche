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
@ApiModel(value="用户注册Vo")
public class UserRegisterVo implements Serializable {

    @ApiModelProperty("注册类型(1--手机号密码注册 2--手机短信验证码注册)")
    private Integer registerType;

    @ApiModelProperty("客户端类型，1,机主版本 2,承租方版本")
    private Integer clientType;

    @ApiModelProperty("手机号码")
    private String phone;

    @ApiModelProperty("密码")
    private String password;

    @ApiModelProperty("短信验证码(必填)")
    private String verifyCode;

    @ApiModelProperty("设备id")
    private String deviceId;

    public interface registerType{
        //注册类型(1--手机号密码注册 2--手机短信验证码注册)
        Integer pwd = 1;
        Integer code = 2;
    }

}
