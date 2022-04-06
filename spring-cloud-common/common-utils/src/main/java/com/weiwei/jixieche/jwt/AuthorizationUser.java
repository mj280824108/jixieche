package com.weiwei.jixieche.jwt;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @ClassName AuthorizationUser
 * @Description TODO
 * @Author houji
 * @Date 2019/8/14 13:40
 * @Version 1.0.1
 **/
@Data
@EqualsAndHashCode
@ApiModel(value="后台认证用户信息")
public class AuthorizationUser {

    @ApiModelProperty("用户的Id")
    private Integer userId;

    @ApiModelProperty("用户角色id")
    private Integer roleId;

    @ApiModelProperty("用户第三方id")
    private String thirdId;
}
