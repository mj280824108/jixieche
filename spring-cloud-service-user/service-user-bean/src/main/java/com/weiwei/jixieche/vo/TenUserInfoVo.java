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
@ApiModel(value="承租方个人信息Vo")
public class TenUserInfoVo implements Serializable {

    @ApiModelProperty("用户userId")
    private Integer userId;

    @ApiModelProperty("用户真实姓名")
    private String realName;

    @ApiModelProperty("用户手机号码")
    private String phone;

    @ApiModelProperty("用户头像")
    private String headImg;

    @ApiModelProperty("身份证号")
    private String cardCode;

    @ApiModelProperty("用户信用分")
    private Integer creditScore;

    @ApiModelProperty("用户评分")
    private Double score = 5.0;

    @ApiModelProperty("认证方式；1：承租方个人认证，2：承租方企业认证")
    private Integer confirmType;

    @ApiModelProperty("认证的id")
    private Integer confirmId;

    @ApiModelProperty("承租方个人认证状态; (0--未认证 1--已认证，2--未通过，3--审核中)")
    private Integer personConfirmStatus;

    @ApiModelProperty("承租方企业认证状态; (0--未认证 1--已认证，2--未通过，3--审核中)")
    private Integer companyConfirmStatus;

    @ApiModelProperty("店铺id(0--为没有店铺，其他值为店铺id)")
    private Integer shopId;

    /**
     * 认证状态
     * 0--未认证 1--已认证，2--未通过，3--审核中
     */
    public interface ConfirmStatus{
        Integer UNCONFIRM = 0;
        Integer CONFIRM = 1;
        Integer UNPASS = 2;
        Integer PROGRESS = 3;
    }


}
