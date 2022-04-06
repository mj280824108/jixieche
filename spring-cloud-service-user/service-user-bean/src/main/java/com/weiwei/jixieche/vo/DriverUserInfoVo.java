package com.weiwei.jixieche.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

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
public class DriverUserInfoVo implements Serializable {

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

    @DateTimeFormat(pattern="yyyy-MM-dd")
    @JsonFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty("从业资格证日期")
    private Date certificateValidityTime;

    @DateTimeFormat(pattern="yyyy-MM-dd")
    @JsonFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty("驾驶证过期时间")
    private Date licenceValidityTime;

    @ApiModelProperty("从业资格证状态(1--已认证  2--已过期)")
    private Integer certificateValidityStatus;

    @ApiModelProperty("驾驶证状态(1--已认证  2--已过期)")
    private Integer licenceValidityStatus;

    @ApiModelProperty("用户评分")
    private Double score = 5.0;

    @ApiModelProperty("认证方式；1：承租方个人认证，2：承租方企业认证,3:机主认证，4：司机认证 5:泥头车司机认证")
    private Integer confirmType;

    @ApiModelProperty("认证的id")
    private Integer confirmId;

    @ApiModelProperty("司机认证状态; (0--未认证 1--已认证，2--未通过，3--审核中)")
    private Integer driverConfirmStatus;

    @ApiModelProperty("泥头车认证状态; (0--未认证 1--已认证，2--未通过，3--审核中)")
    private Integer mudDriverConfirmStatus;

    @ApiModelProperty("车牌号码")
    private String machineCardNo;

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

    public interface ValidityStatus{
        Integer CONFIRM = 1;
        Integer OVERTIME = 2;
    }

}
