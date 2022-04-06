package com.weiwei.jixieche.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * @ClassName TradePayVo
 * @Description TODO
 * @Author houji
 * @Date 2019/8/16 13:59
 * @Version 1.0.1
 **/
@Data
@EqualsAndHashCode
@ApiModel(value="提现用户信息Vo")
public class CashApplyUserInfoVo implements Serializable {

    @ApiModelProperty("用户userId")
    private Integer userId;

    @ApiModelProperty("用户角色id")
    private Integer roleId;

    @ApiModelProperty("用户状态(0--禁用 1--启用)")
    private Integer state;

    @ApiModelProperty("用户支付密码")
    private String payPwd;

    @ApiModelProperty("用户真实姓名")
    private String realName;

}
