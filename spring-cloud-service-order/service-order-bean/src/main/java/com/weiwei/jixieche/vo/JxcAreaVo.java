package com.weiwei.jixieche.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Data
@EqualsAndHashCode
@ApiModel(value="jxc_area")
public class JxcAreaVo implements Serializable {

       @ApiModelProperty("省id")
       private Integer provinceId;

       @ApiModelProperty("省名称")
       private String provinceName;

       @ApiModelProperty("省Pid")
       private Integer provincePid;

       @ApiModelProperty("市id")
       private Integer cityId;

       @ApiModelProperty("市名称")
       private String cityName;

       @ApiModelProperty("省Pid")
       private Integer cityPid;

       private static final long serialVersionUID = 1L;
}