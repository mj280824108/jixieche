package com.weiwei.jixieche.bean;

import com.weiwei.jixieche.page.BasePage;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;
import lombok.EqualsAndHashCode;
/**
 * @Author 钟焕 
 * @Description
 * @Date 20:13 2019-08-14
 **/
@Data
@EqualsAndHashCode
@ApiModel(value="消纳场表")
public class JxcSite extends BasePage {
       @ApiModelProperty("消纳场id")
       private Integer id;

       @ApiModelProperty("消纳场名")
       private String siteName;

       @ApiModelProperty("省份编码")
       private Integer provinceCode;

       @ApiModelProperty("城市编码")
       private Integer cityCode;

       @ApiModelProperty("区编码")
       private Integer districtCode;

       @ApiModelProperty("消纳场详细地址")
       private String siteAddress;

       @ApiModelProperty("经度")
       private String longitude;

       @ApiModelProperty("纬度")
       private String latitude;

       @ApiModelProperty("是否入驻平台（1：是 0：否）")
       private Integer intoFlag;

       @ApiModelProperty("删除标记（1：未删除 0：已删除）")
       private Integer delState;

       @ApiModelProperty("是否停业（1：营业 0：停业）")
       private Integer openFlag;

       @ApiModelProperty("负责人姓名")
       private String shoulder;

       @ApiModelProperty("联系方式")
       private String phone;

       @ApiModelProperty("购买说明")
       private String buyDescription;

       @ApiModelProperty("停业公告")
       private String closingNotice;

       @ApiModelProperty("停业开始时间")
       private Date closingStartTime;

       @ApiModelProperty("停业结束时间")
       private Date closingEndTime;

       @ApiModelProperty("创建时间")
       private Date createTime;

       @ApiModelProperty("修改时间")
       private Date updateTime;

       private static final long serialVersionUID = 1L;
}