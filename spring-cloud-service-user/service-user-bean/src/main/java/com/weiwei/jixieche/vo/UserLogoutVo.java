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
@ApiModel(value="用户退出登录Vo")
public class UserLogoutVo implements Serializable {

    @ApiModelProperty("用户userId")
    private Integer userId;

    @ApiModelProperty("设备id")
    private String deviceId;

}
