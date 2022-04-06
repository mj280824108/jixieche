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
@ApiModel(value="查询店铺详情Vo")
public class ShopDetailsVo implements Serializable {

    @ApiModelProperty("店铺id(必填)")
    private Integer id;

    @ApiModelProperty("用户userId")
    private Integer userId;

}
