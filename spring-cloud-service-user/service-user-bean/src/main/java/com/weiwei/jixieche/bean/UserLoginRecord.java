package com.weiwei.jixieche.bean;

import com.weiwei.jixieche.page.BasePage;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;
@Data
@EqualsAndHashCode
@ApiModel(value="用户登录行为记录表")
public class UserLoginRecord extends BasePage {
    @ApiModelProperty("主键id")
    private Integer id;
    @ApiModelProperty("用户手机号")
    private String phone;
    @ApiModelProperty("创建时间")
    private Date createTime;
    @ApiModelProperty("状态1登录成功,2登录失败,3退出登录")
    private Integer state;
    @ApiModelProperty("情况描述")
    private String describe;

}