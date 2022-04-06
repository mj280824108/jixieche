package com.weiwei.jixieche.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * @ClassName UserCreditScoreVo
 * @Description TODO
 * @Author houji
 * @Date 2019/8/14 14:22
 * @Version 1.0.1
 **/
@Data
@EqualsAndHashCode
@ApiModel(value="用户注册返回Vo")
public class UserRegisterReturnVo implements Serializable {

    @ApiModelProperty("用户userId")
    private Integer userId;

    @ApiModelProperty("用户角色id")
    private Integer roleId;

    @ApiModelProperty("第三方id")
    private String thirdId;

    @ApiModelProperty("密码是否设置(0--未设置  1--已设置)")
    private Integer isEmptyPwd;

    @ApiModelProperty("手机号")
    private String phone;

    @ApiModelProperty("jwt生成token")
    private String token;

    public interface isEmptyPwd{
        Integer EMPTY = 0;
        Integer UNEMPTY = 1;
    }
}
