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
@ApiModel(value="市场收藏记录")
public class JxcMarketCollectionRecord extends BasePage implements Serializable {
       @ApiModelProperty("主键自增")
       private Integer id;

       @ApiModelProperty("用户userId")
       private Integer userId;

       @ApiModelProperty("收藏类型(1--机械 2--店铺 3--资讯)")
       private Integer collectionType;

       @ApiModelProperty("发布类型(1--机械求购 2--机械出售 3--机械求租 4--机械出租 5--资源求购 6--资源出售 7--其他)")
       private Integer realseType;

       @ApiModelProperty("发布商品的id")
       private Integer markeRealseId;

       @ApiModelProperty("收藏的资讯id")
       private Integer informationId;

       @ApiModelProperty("收藏店铺id")
       private Integer shopId;

       @ApiModelProperty("收藏状态(0--取消收藏 1--收藏)")
       private Integer collectionDisabled;

       @ApiModelProperty("创建时间")
       private Date createTime;

       @ApiModelProperty("修改时间")
       private Date updateTime;

       /**
        * 收藏类型
        */
       public interface collectionType{
              Integer MACHINE = 1;
              Integer SHOPS = 2;
              Integer INFOR = 3;
       }

       /**
        * 收藏状态(0--取消收藏 1--收藏)
        */
       public interface collectionDisabled{
              Integer CANNEL = 0;
              Integer COLLECTION = 1;
       }

       private static final long serialVersionUID = 1L;
}