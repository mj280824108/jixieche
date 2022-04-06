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
@ApiModel(value="机主个人信息Vo")
public class OwnerUserInfoVo implements Serializable {

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

    @ApiModelProperty("是否实名认证(0--未实名 1--已实名)")
    private Integer confirm;

    @ApiModelProperty("认证的id")
    private Integer confirmId;

    public interface confirm{
        //是否实名认证(0--未实名 1--已实名)
        Integer CONFIRM = 1;
        Integer UNCONFIRM = 0;
    }

}
