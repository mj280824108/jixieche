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
public class ManagerRegisterVo implements Serializable {

    @ApiModelProperty("(必填)承租方用户userId")
    private Integer tenId;

    @ApiModelProperty("(必填)管理员密码")
    private String password;

    @ApiModelProperty("(必填)用户名称")
    private String realName;

    @ApiModelProperty("(必填)手机号")
    private String phone;

    @ApiModelProperty("(必填)验证码")
    private String code;

    @ApiModelProperty("(必填)项目id")
    private Integer projectId;

    @ApiModelProperty("(必填)项目名称")
    private String projectName;

}
