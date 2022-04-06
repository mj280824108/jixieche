package com.weiwei.jixieche.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.omg.CORBA.INTERNAL;

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
@ApiModel(value="机主个人信息Vo")
public class OwnerInfoVo implements Serializable {

    @ApiModelProperty("id主键自增")
    private Integer userId;

    @ApiModelProperty("用户头像")
    private String headImg;

    @ApiModelProperty("用户真实姓名")
    private String realName;

    @ApiModelProperty("手机号码(登录账号)")
    private String phone;

    @ApiModelProperty("身份证号")
    private String cardCode;

    @ApiModelProperty("当前信用分")
    private Integer creditScore;

    @ApiModelProperty("当前评分")
    private Double score = 5.0 ;

    @ApiModelProperty("机主认证id")
    private Integer confirmId;

    @ApiModelProperty("机主认证状态; (0--未认证 1--已认证，2--未通过，3--审核中)")
    private Integer ownerConfirmStatus;

    @ApiModelProperty("认证方式；3:机主认证")
    private Integer confirmType;

    @ApiModelProperty("车牌号")
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

}
