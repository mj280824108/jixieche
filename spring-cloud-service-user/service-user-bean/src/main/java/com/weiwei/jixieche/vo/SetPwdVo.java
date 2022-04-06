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
@ApiModel(value="用户设置密码Vo")
public class SetPwdVo implements Serializable {

    @ApiModelProperty("用户新的密码")
    private String pwd;

    @ApiModelProperty("用户的userId")
    private Integer userId;

    public interface resetType{
        //0--忘记密码  1--修改密码
        Integer FORGET = 0;
        Integer UPDATE = 1;
    }

}
