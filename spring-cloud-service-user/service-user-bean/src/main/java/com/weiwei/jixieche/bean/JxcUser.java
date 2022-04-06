package com.weiwei.jixieche.bean;

import com.weiwei.jixieche.page.BasePage;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;
import lombok.EqualsAndHashCode;
/**
 * @ClassName 类名
 * @Description TODO
 * @Author houji
 * @Date 2019/8/13 21:32
 * @Version 2.0
 **/
@Data
@EqualsAndHashCode
@ApiModel(value="用户表")
public class JxcUser extends BasePage implements Serializable {
       @ApiModelProperty("id主键自增")
       private Integer id;

       @ApiModelProperty("手机号码(登录账号)")
       private String phone;

       @ApiModelProperty("密码")
       private String password;

       @ApiModelProperty("第三方id(用户第三方鹰眼，极光推送唯一识别码)")
       private String thirdId;

       @ApiModelProperty("用户状态(0--禁用 1--启用)")
       private Integer state;

       @ApiModelProperty("用户来源(0--手机注册 1--后台注册 2--其他)")
       private Integer userSource;

       @ApiModelProperty("用户真实姓名")
       private String realName;

       @ApiModelProperty("性别(0--女 1--男)")
       private Integer sex;

       @ApiModelProperty("用户头像")
       private String headImg;

       @ApiModelProperty("用户支付密码")
       private String payPwd;

       @ApiModelProperty("创建时间")
       private Date createTime;

       @ApiModelProperty("修改时间")
       private Date updateTime;

       @ApiModelProperty("最后登录时间")
       private Date lastLoginTime;

       @ApiModelProperty("评分")
       private Double score;

       private static final long serialVersionUID = 1L;
}