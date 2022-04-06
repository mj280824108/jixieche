package com.weiwei.jixieche.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.List;

/**
 * @ClassName OpenCityVo
 * @Description TODO
 * @Author houji
 * @Date 2019/8/28 14:21
 * @Version 1.0.1
 **/
@Data
@EqualsAndHashCode
@ApiModel(value="开放城市vo")
public class OpenCityVo implements Serializable {

    @ApiModelProperty("开放的省id")
    private List<OpenedProvinceVo> openedProvinceVo;

}
