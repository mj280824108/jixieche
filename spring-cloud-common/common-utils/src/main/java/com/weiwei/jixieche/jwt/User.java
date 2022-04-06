package com.weiwei.jixieche.jwt;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * @ClassName LoginUser
 * @Description TODO
 * @Author houji
 * @Date 2019/7/29 18:18
 * @Version 1.0.1
 **/

@Data
@EqualsAndHashCode
@ApiModel(value="用户登录token储存信息")
public class User implements Serializable {

    @ApiModelProperty("用户的Id")
    private Integer userId;

    @ApiModelProperty("用户的姓名")
    private String realName;

    @ApiModelProperty("用户手机号")
    private String phone;

    @ApiModelProperty("用户角色id")
    private Integer roleId;

    @ApiModelProperty("用户第三方id")
    private String thirdId;

}
