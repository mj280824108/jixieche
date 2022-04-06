package com.weiwei.jixieche.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Data
@EqualsAndHashCode
@ApiModel(value="查询资讯详情参数Vo")
public class InforParamVo implements Serializable {

       @ApiModelProperty("资讯id")
       private Integer id;

       @ApiModelProperty("用户userId")
       private Integer userId;

       @ApiModelProperty("app端类型(0--承租方 1--机主)")
       private Integer appClientType;

       private static final long serialVersionUID = 1L;
}