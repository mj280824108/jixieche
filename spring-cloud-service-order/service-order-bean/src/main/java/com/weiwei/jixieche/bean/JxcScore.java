package com.weiwei.jixieche.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
@ApiModel(value="用户评价评分表")
public class JxcScore implements Serializable {
       @ApiModelProperty("评分id")
       private Long scoreId;

       @ApiModelProperty("评分对象")
       private Integer sourceId;

       @ApiModelProperty("被评分对象")
       private Integer targetId;

       @ApiModelProperty("关联的订单号")
       private String orderId;

       @ApiModelProperty("评分类型(1--承租方对机械帐号评分 2--机械帐号对承租方评分 3--机主对司机评分 4--司机对机主评分)")
       private Byte scoreType;

       @ApiModelProperty("评价的分数")
       private Float score;

       @ApiModelProperty("评价内容")
       private String content;

       @ApiModelProperty("关键词")
       private String fireWords;

       @ApiModelProperty("创建时间")
       private Date createTime;

       @ApiModelProperty("更新时间")
       private Date updateTime;

       private static final long serialVersionUID = 1L;
}