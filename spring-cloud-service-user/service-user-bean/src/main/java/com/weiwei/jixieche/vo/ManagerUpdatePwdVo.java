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
@ApiModel(value="管理员注册Vo")
public class ManagerUpdatePwdVo implements Serializable {

    @ApiModelProperty("(必填)管理员密码")
    private String password;

    @ApiModelProperty("(必填)承租方管理员的userId")
    private Integer userId;

}
