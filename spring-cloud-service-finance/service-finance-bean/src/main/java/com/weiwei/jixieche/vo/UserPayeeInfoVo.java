package com.weiwei.jixieche.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * @ClassName TenProjectInfoVo
 * @Description TODO
 * @Author houji
 * @Date 2019/9/3 11:25
 * @Version 1.0.1
 **/
@Data
@EqualsAndHashCode
@ApiModel(value="收款人信息Vo")
public class UserPayeeInfoVo implements Serializable {

    @ApiModelProperty("收款人userId")
    private Integer userId;

    @ApiModelProperty("收款人姓名")
    private String userName;

    @ApiModelProperty("头像")
    private String headImg;


}
