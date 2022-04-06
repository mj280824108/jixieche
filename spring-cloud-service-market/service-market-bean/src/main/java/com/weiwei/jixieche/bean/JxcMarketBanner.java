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
@ApiModel(value="市场banner表")
public class JxcMarketBanner extends BasePage implements Serializable {
       @ApiModelProperty("主键自增")
       private Integer id;

       @ApiModelProperty("标题")
       private String title;

       @ApiModelProperty("类型(1--市场banner图 2--消纳场app首页 3--消纳场管理员登录首页 4--交易市场首页)")
       private Integer bannerTypeId;

       @ApiModelProperty("起始时间(yyyy-MM-dd)")
       private Date startTime;

       @ApiModelProperty("结束时间(yyyy-MM-dd)")
       private Date endTime;

       @ApiModelProperty("banner图片url地址,用英文逗号隔开")
       private String imgUrl;

       @ApiModelProperty("跳转url地址链接")
       private String linkUrl;

       @ApiModelProperty("是否启用(0--不启用 1--启用)")
       private Integer disabled;

       @ApiModelProperty("创建时间")
       private Date createTime;

       @ApiModelProperty("修改时间")
       private Date updateTime;

       private static final long serialVersionUID = 1L;
}