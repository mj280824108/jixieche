package com.weiwei.jixieche.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@EqualsAndHashCode
@ApiModel(value="市场资源类型列表Vo")
public class MarketResourceTypeListVo implements Serializable {

       @ApiModelProperty("主键自增")
       private Integer id;

       @ApiModelProperty("资源名称(类型)")
       private String resourceName;

       @ApiModelProperty("上级资源类型")
       private String code;

       @ApiModelProperty("资源图片url")
       private String imgUrl;

       @ApiModelProperty("资源子类列表")
       private List<MarketResourceTypeListVo> childResourceList = new ArrayList<>();

}