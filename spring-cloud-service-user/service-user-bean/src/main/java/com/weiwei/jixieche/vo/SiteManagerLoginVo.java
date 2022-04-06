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
@ApiModel(value="消纳场管理员登录Vo")
public class SiteManagerLoginVo implements Serializable {

    @ApiModelProperty("手机号码")
    private String phone;

    @ApiModelProperty("密码")
    private String password;

    @ApiModelProperty("设备id")
    private String deviceId;

}
