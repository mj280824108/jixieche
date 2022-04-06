package com.weiwei.jixieche.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Data
@EqualsAndHashCode
@ApiModel(value="轮播资讯列表Vo")
public class InforFireListVo implements Serializable {

       @ApiModelProperty("主键自增")
       private Integer id;

       @ApiModelProperty("资讯标题")
       private String title;

       @ApiModelProperty("资讯类型(1--行业资讯  2--机械百科 3--最新政策 4--热门推荐)")
       private Integer inforType;

       private static final long serialVersionUID = 1L;
}