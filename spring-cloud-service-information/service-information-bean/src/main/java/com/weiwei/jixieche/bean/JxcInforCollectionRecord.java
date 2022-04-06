package com.weiwei.jixieche.bean;

import com.weiwei.jixieche.page.BasePage;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;
import lombok.EqualsAndHashCode;
/**
 * @ClassName s
 * @Description TODO
 * @Author houji
 * @Date 2019/8/20 16:32
 * @Version 2.0
 **/
@Data
@EqualsAndHashCode
@ApiModel(value="资讯信息收藏记录表")
public class JxcInforCollectionRecord extends BasePage implements Serializable {
       @ApiModelProperty("用户userId")
       private Integer userId;

       @ApiModelProperty("资讯inforId")
       private Integer inforId;

       @ApiModelProperty("收藏状态(0--取消收藏  1--收藏)")
       private Integer state;

       @ApiModelProperty("创建时间")
       private Date createTime;

       @ApiModelProperty("更新时间")
       private Date updateTime;

       private static final long serialVersionUID = 1L;
}