package com.weiwei.jixieche.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * @ClassName AreaNameVo
 * @Description TODO
 * @Author houji
 * @Date 2019/9/6 11:05
 * @Version 1.0.1
 **/
@Data
@EqualsAndHashCode
@ApiModel(value="用户店铺信息Vo")
public class UserShopsVo implements Serializable {

    @ApiModelProperty("主键自增")
    private Integer id;

    @ApiModelProperty("店主id")
    private Integer shopKeeperId;

    @ApiModelProperty("店铺名称")
    private String shopName;

    @ApiModelProperty("是否认证(0--未认证  1--已认证)")
    private Integer confirmStatus;

    @ApiModelProperty("店铺所在省市区,区的id")
    private Integer shopAreaId;

    @ApiModelProperty("店铺所在省市区地址")
    private Integer shopAreaName;

    @ApiModelProperty("店铺详细地址")
    private String shopAddress;


}
