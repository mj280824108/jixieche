package com.weiwei.jixieche.bean;

import com.weiwei.jixieche.page.BasePage;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
@ApiModel(value="承租方项目表")
public class JxcProject extends BasePage {
       @ApiModelProperty("项目ID")
       private Integer id;

       @ApiModelProperty("承租方用户ID")
       private Integer userId;

       @ApiModelProperty("项目默认图片的ID")
       private Integer imgId;

       @ApiModelProperty("项目名称")
       private String projectName;

       @ApiModelProperty("项目负责人")
       private String projectLeader;

       @ApiModelProperty("项目负责人电话")
       private String leaderPhone;

       @ApiModelProperty("项目所在地经度")
       private String longitude;

       @ApiModelProperty("项目所在纬度")
       private String latitude;

       @ApiModelProperty("城市编码")
       private Integer cityCode;

       @ApiModelProperty("区编码")
       private Integer districtCode;

       @ApiModelProperty("项目详细地址")
       private String projectAddress;

       @ApiModelProperty("项目开工日期 yyyy-MM-dd")
       private String startDate;

       @ApiModelProperty("项目竣工日期 yyyy-MM-dd")
       private String endDate;

       @ApiModelProperty("出图证照片URL")
       private String landLicenseImg;

       @ApiModelProperty("出图证有效期开始日期")
       private String licenseStartDate;

       @ApiModelProperty("出图证有效期截止日期")
       private String licenseEndDate;

       @ApiModelProperty("创建时间")
       private Date createTime;

       @ApiModelProperty("修改时间")
       private Date updateTime;

       @ApiModelProperty("0：未竣工 1：已竣工")
       private Integer flag;

       @ApiModelProperty("机主订单id")
       private Long ownerOrderId;

       private static final long serialVersionUID = 1L;
}