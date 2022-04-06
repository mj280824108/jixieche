package com.weiwei.jixieche.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Data
@EqualsAndHashCode
@ApiModel(value="司机台班计费")
public class JxcDriverTeamCostVo implements Serializable {

       @ApiModelProperty("主键自增")
       private Integer id;

       @ApiModelProperty("市")
       private Integer cityId;

       @ApiModelProperty("司机台班费用")
       private Integer driverTeamPrice;

       @ApiModelProperty("超时费用(加班趟数费用)")
       private Integer driverOutPrice;

       private static final long serialVersionUID = 1L;
}