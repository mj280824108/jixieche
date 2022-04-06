package com.weiwei.jixieche.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * @ClassName UserChooseRoleVo
 * @Description TODO
 * @Author houji
 * @Date 2019/8/14 18:37
 * @Version 1.0.1
 **/
@Data
@EqualsAndHashCode
@ApiModel(value="机主版app选择角色Vo")
public class UserChooseRoleVo implements Serializable {

    @ApiModelProperty("添加的用户userId")
    private Integer userId;

    @ApiModelProperty("角色id")
    private Integer roleId;

}
