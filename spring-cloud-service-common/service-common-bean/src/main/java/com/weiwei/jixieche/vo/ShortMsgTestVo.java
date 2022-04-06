package com.weiwei.jixieche.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * @ClassName ShortMsgTestVo
 * @Description TODO
 * @Author houji
 * @Date 2019/8/27 14:47
 * @Version 1.0.1
 **/
@Data
@EqualsAndHashCode
@ApiModel(value="短信验证码参数Vo")
public class ShortMsgTestVo implements Serializable {

    @ApiModelProperty("手机号(必填)")
    private String phone;

    @ApiModelProperty("1--登录 2--注册 3--忘记密码 4-修改密码")
    private Integer codeType;

    /**
     * 1--登录 2--注册 3--忘记密码 4-修改密码
     */
    public interface CodeType{
        Integer LOGIN = 1;
        Integer REGISTER = 2;
        Integer FORGET_PWD = 3;
        Integer UPDATE_PWD = 4;
    }
}
