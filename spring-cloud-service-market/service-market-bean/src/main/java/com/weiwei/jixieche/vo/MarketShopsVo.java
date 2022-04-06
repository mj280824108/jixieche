package com.weiwei.jixieche.vo;

import com.weiwei.jixieche.page.BasePage;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

@Data
@EqualsAndHashCode
@ApiModel(value="市场店铺表Vo")
public class MarketShopsVo extends BasePage implements Serializable {
       @ApiModelProperty("主键自增")
       private Integer id;

       @ApiModelProperty("店主id")
       private Integer shopKeeperId;

       @ApiModelProperty("店铺名称")
       private String shopName;

       @ApiModelProperty("店铺图片")
       private String shopBigImg;

       @ApiModelProperty("店铺小图url,用逗号隔开")
       private String shopSmallImgs;

       @ApiModelProperty("店铺详细地址")
       private String shopAddress;

       @ApiModelProperty("店铺星级")
       private Integer shopStar;

       @ApiModelProperty("店铺介绍")
       private String shopProduct;

       @ApiModelProperty("店铺服务")
       private String shopService;

       @ApiModelProperty("店铺所在省市区,区的id")
       private Integer shopAreaId;

       @ApiModelProperty("开店时间(yyyy-mm-dd)")
       private String shopOpenTime;

       @ApiModelProperty("销售商品名称")
       private String saleTypeName;

       @ApiModelProperty("证照信息")
       private String licenceImgUrl;

       @ApiModelProperty("门店负责人名称")
       private String personName;

       @ApiModelProperty("商家电话")
       private String shopPhone;

       @ApiModelProperty("店铺状态(0--关闭  1--正常)")
       private Integer shopStatus;

       @ApiModelProperty("是否认证(0--未认证  1--已认证)")
       private Integer confirmStatus;

       @ApiModelProperty("经度")
       private String shopLongitude;

       @ApiModelProperty("纬度")
       private String shopLatitude;

       @ApiModelProperty("店铺描述")
       private String described;

       @ApiModelProperty("创建时间")
       private Date createTime;

       @ApiModelProperty("修改时间")
       private Date updateTime;

       @ApiModelProperty("用户是否收藏(0--未收藏  1--已经收藏)")
       private Integer collectionStatus;

       @ApiModelProperty("店铺收藏量")
       private Integer collectionNumber;

       private static final long serialVersionUID = 1L;
}