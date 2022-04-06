package com.weiwei.jixieche.bean;

import com.weiwei.jixieche.page.BasePage;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
@ApiModel(value="咨询信息表")
public class JxcInforInformation extends BasePage implements Serializable {
       @ApiModelProperty("主键自增")
       private Integer id;

       @ApiModelProperty("资讯标题")
       private String title;

       @ApiModelProperty("资讯类型(1--行业资讯  2--机械百科 3--最新政策 4--热门推荐)")
       private Integer inforType;

       @ApiModelProperty("文章类型(0--单张大图,1--多图,2--右侧,3--无图,4--视频 5--上图)")
       private Integer articleType;

       @ApiModelProperty("资讯大图url,多图用分好隔开")
       private String inforBigImg;

       @ApiModelProperty("资讯小图url,多图用分好隔开")
       private String inforSmallImg;

       @ApiModelProperty("文章来源")
       private String inforSources;

       @ApiModelProperty("是否推荐(0--不推荐  1--推荐)")
       private Integer recommend;

       @ApiModelProperty("资讯浏览量")
       private Integer viewCount;

       @ApiModelProperty("点赞量")
       private Integer pointCount;

       @ApiModelProperty("收藏量")
       private Integer collectionCount;

       @ApiModelProperty("分享量")
       private Integer shareCount;

       @ApiModelProperty("创建时间")
       private Date createTime;

       @ApiModelProperty("创建时间")
       private Date updateTime;

       @ApiModelProperty("咨询状态(0--待发布,1--已发布,2--已下线,3--已刪除)")
       private Integer inforFlag;

       @ApiModelProperty("操作人id")
       private Integer updateId;

       @ApiModelProperty("有效时间开始")
       private Date startDate;

       @ApiModelProperty("有效时间结束")
       private Date endDate;

       @ApiModelProperty("是否置顶(0--不置顶,1--置顶)")
       private Integer roofFlag;

       private static final long serialVersionUID = 1L;
}