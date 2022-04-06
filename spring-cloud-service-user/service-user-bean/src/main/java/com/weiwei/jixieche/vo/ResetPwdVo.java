package com.weiwei.jixieche.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * @ClassName ResetPwd
 * @Description TODO
 * @Author houji
 * @Date 2019/8/15 19:00
 * @Version 1.0.1
 **/
@Data
@EqualsAndHashCode
@ApiModel(value="重置(忘记密码)密码")
public class ResetPwdVo implements Serializable {

    @ApiModelProperty("用户手机号")
    private String phone;

    @ApiModelProperty("短信验证码")
    private String verifyCode;

    @ApiModelProperty("客户端类型，1,机主版本 2,承租方版本")
    private Integer clientType;

    @ApiModelProperty("用户新的密码")
    private String pwd;

    @ApiModelProperty("用户旧密码")
    private String oldPwd;

    @ApiModelProperty("忘记或者修改密码(0--忘记密码  1--修改密码)")
    private Integer resetType;

    public interface resetType{
        //0--忘记密码  1--修改密码
        Integer FORGET = 0;
        Integer UPDATE = 1;
    }

}
