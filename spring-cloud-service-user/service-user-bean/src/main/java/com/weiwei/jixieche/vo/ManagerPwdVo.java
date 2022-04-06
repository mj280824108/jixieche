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
@ApiModel(value="承租方管理员密码Vo")
public class ManagerPwdVo implements Serializable {

    @ApiModelProperty("承租方管理员userId")
    private Integer userId;

    @ApiModelProperty("承租方管理员新的密码")
    private String password;

}
