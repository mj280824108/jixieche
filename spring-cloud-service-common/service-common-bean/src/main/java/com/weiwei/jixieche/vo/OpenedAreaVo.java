package com.weiwei.jixieche.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName OpenedAreaVo
 * @Description TODO
 * @Author houji
 * @Date 2019/8/12 19:49
 * @Version 1.0.1
 **/
@Data
@EqualsAndHashCode
@ApiModel(value="开放省市Vo")
public class OpenedAreaVo implements Serializable {

    @ApiModelProperty("省市id")
    private Integer areaId;

    @ApiModelProperty("省市名称")
    private String areaName;

    /*@ApiModelProperty("省id")
    private Integer provinceId;*/

    @ApiModelProperty("经度")
    private String lon;

    @ApiModelProperty("纬度")
    private String lat;

    private List<OpenedCityVo> OpenedCityVoList = new ArrayList<OpenedCityVo>();


}
