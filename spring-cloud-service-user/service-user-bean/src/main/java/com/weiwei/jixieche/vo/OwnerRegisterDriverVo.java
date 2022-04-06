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
@ApiModel(value="机主添加临时司机Vo")
public class OwnerRegisterDriverVo implements Serializable {

    @ApiModelProperty("(必填)临时司机密码")
    private String password;

    @ApiModelProperty("(必填)用户昵称")
    private String realName;

    @ApiModelProperty("(必填)手机号")
    private String phone;

}
