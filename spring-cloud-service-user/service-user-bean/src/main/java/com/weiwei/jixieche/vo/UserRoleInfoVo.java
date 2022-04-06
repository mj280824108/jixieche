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
@ApiModel(value="用户级别信息Vo")
public class UserRoleInfoVo implements Serializable {

    @ApiModelProperty("手机号(参数)")
    private String phone;

    @ApiModelProperty("用户角色(参数)")
    private Integer roleId;

    @ApiModelProperty("用户userId")
    private Integer userId;

}
