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
@ApiModel(value="用户基本信息Vo")
public class UserInfoVo implements Serializable {

    @ApiModelProperty("用户userId")
    private Integer userId;

    @ApiModelProperty("用户真实姓名")
    private String realName;

    @ApiModelProperty("用户手机号码")
    private String phone;

    @ApiModelProperty("用户头像")
    private String headImg;

    @ApiModelProperty("用户信用分")
    private Integer creditScore;

    @ApiModelProperty("用户评分")
    private Double score = 5.0;

    @ApiModelProperty("认证的id")
    private Integer confirmId;

    @ApiModelProperty("第三方ID")
    private String thirdId;

    @ApiModelProperty("身份证号")
    private String cardCode;

    @ApiModelProperty("认证方式(1：承租方个人认证，2：承租方企业认证,3:机主认证，4：司机认证 5--泥头车司机认证)")
    private Integer confirmType;

    @ApiModelProperty("车牌号")
    private String machineCardNo;

    @ApiModelProperty("承租方个人认证状态; (0--未认证 1--已认证，2--未通过，3--审核中)")
    private Integer personConfirmStatus;

    @ApiModelProperty("承租方企业认证状态; (0--未认证 1--已认证，2--未通过，3--审核中)")
    private Integer companyConfirmStatus;

    @ApiModelProperty("机主认证状态; (0--未认证 1--已认证，2--未通过，3--审核中)")
    private Integer ownerConfirmStatus;

    @ApiModelProperty("司机认证状态; (0--未认证 1--已认证，2--未通过，3--审核中)")
    private Integer driverConfirmStatus;

    @ApiModelProperty("泥头车认证状态; (0--未认证 1--已认证，2--未通过，3--审核中)")
    private Integer mudDriverConfirmStatus;

    public interface confirm{
        //是否实名认证(0--未实名 1--已实名)
        Integer CONFIRM = 1;
        Integer UNCONFIRM = 0;
    }

}
