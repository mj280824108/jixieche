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
@ApiModel(value="用户添加信用分记录Vo")
public class UserCreditScoreVo implements Serializable {

    @ApiModelProperty("添加的用户userId")
    private Integer userId;

    @ApiModelProperty("信用分信用模板")
    private Integer templateId;

}
