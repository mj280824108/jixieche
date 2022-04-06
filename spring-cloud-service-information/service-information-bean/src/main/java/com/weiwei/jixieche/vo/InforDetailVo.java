package com.weiwei.jixieche.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

@Data
@EqualsAndHashCode
@ApiModel(value="咨询信息表")
public class InforDetailVo implements Serializable {
       @ApiModelProperty("主键自增")
       private Integer id;

       @ApiModelProperty("资讯浏览量")
       private Integer viewCount;

       @ApiModelProperty("点赞量")
       private Integer pointCount;

       @ApiModelProperty("收藏量")
       private Integer collectionCount;

       @ApiModelProperty("分享量")
       private Integer shareCount;

       @ApiModelProperty("是否点赞(空值--未点赞,其他--点赞id)")
       private Integer pointId;

       @ApiModelProperty("是否收藏(0--未收藏  1--已收藏)")
       private Integer collectionId;

       @ApiModelProperty("app内部资讯详情")
       private String inforUrl;

       @ApiModelProperty("分享资讯详情")
       private String shareInforUrl;

       @ApiModelProperty("资讯标题")
       private String title;

       @ApiModelProperty("logo图片")
       private String logoImgUrl;

       private static final long serialVersionUID = 1L;
}