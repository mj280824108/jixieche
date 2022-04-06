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
@ApiModel(value="用户点赞记录表")
public class JxcMarketPointRecord extends BasePage implements Serializable {
       @ApiModelProperty("主键自增")
       private Integer id;

       @ApiModelProperty("用户的userId")
       private Integer userId;

       @ApiModelProperty("点赞类型(1--机械 2--店铺 3--资讯)")
       private Integer pointType;

       @ApiModelProperty("点赞状态(0--取消点赞 1--点赞)")
       private Integer pointStatus;

       @ApiModelProperty("市场发布机械/资源的id")
       private Integer marketReleaseId;

       @ApiModelProperty("点赞的店铺id")
       private Integer shopId;

       @ApiModelProperty("资讯的id")
       private Integer inforId;

       @ApiModelProperty("创建时间")
       private Date createTime;

       @ApiModelProperty("修改时间")
       private Date updateTime;

       private static final long serialVersionUID = 1L;
}