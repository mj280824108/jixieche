package com.weiwei.jixieche.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName OpenedProvinceVo
 * @Description TODO
 * @Author houji
 * @Date 2019/8/12 19:43
 * @Version 1.0.1
 **/
@Data
@EqualsAndHashCode
@ApiModel(value="开放省Vo")
public class OpenedProvinceVo implements Serializable {

    @ApiModelProperty("省id")
    private int provinceId;

    @ApiModelProperty("省名称")
    private String provinceName;

    @ApiModelProperty("省经度")
    private String lon;

    @ApiModelProperty("省纬度")
    private String lat;

    @ApiModelProperty("开放城市集合列表")
    private List<OpenedCityVo> openedCityList = new ArrayList<>();
}
