package com.weiwei.jixieche.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName OpenedCityVo
 * @Description TODO
 * @Author houji
 * @Date 2019/8/12 19:40
 * @Version 1.0.1
 **/
@Data
@EqualsAndHashCode
@ApiModel(value="开放城市Vo")
public class OpenedCityVo implements Serializable {

    @ApiModelProperty("市id")
    private Integer cityId;

    @ApiModelProperty("城市名称")
    private String cityName;

    @ApiModelProperty("城市经度")
    private String lon;

    @ApiModelProperty("城市纬度")
    private String lat;

    @ApiModelProperty("省id")
    private Integer provinceId;

}
