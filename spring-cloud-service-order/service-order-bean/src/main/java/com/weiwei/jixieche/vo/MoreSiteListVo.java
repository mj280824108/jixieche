package com.weiwei.jixieche.vo;

import com.weiwei.jixieche.bean.JxcSiteCouponType;
import com.weiwei.jixieche.page.BasePage;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Author zhong huan
 * @Date 2019-08-15 15:46
 * @Description
 */
@Data
@EqualsAndHashCode
@ApiModel(value="更多消纳场列表Vo类")
public class MoreSiteListVo extends BasePage {

    @ApiModelProperty("消纳场id")
    private Integer siteId;

    @ApiModelProperty("消纳场名")
    private String siteName;

    @ApiModelProperty("消纳场详细地址")
    private String siteAddress;

    @ApiModelProperty("经度")
    private String longitude;

    @ApiModelProperty("纬度")
    private String latitude;

    @ApiModelProperty("是否入驻平台（1：是 0：否）")
    private Integer intoFlag;

    @ApiModelProperty("是否停业（1：营业 0：停业）")
    private Integer openFlag;

    @ApiModelProperty("使用状态（1：正在使用 0：停用（订单更换了消纳场））")
    private Integer state;

    @ApiModelProperty("消纳券起价")
    private String priceStart;

    @ApiModelProperty("距离")
    private String distance;

    @ApiModelProperty("购买说明")
    private String buyDescription;

    @ApiModelProperty("停业公告")
    private String closingNotice;

    @ApiModelProperty("停业开始时间")
    private String closingStartTime;

    @ApiModelProperty("停业结束时间")
    private String closingEndTime;

    @ApiModelProperty("好土券总张数")
    private Integer goodSoilNum;

    @ApiModelProperty("坏土券总张数")
    private Integer badSoilNum;

    @ApiModelProperty("订单总数量")
    private Integer siteOrderNum;

    @ApiModelProperty("消纳券类型")
    private Map<String,Object> siteCouponTypeList;

    @ApiModelProperty("消纳场负责人")
    private String shoulder;

    @ApiModelProperty("负责人电话")
    private String phone;

    @ApiModelProperty("银行账户名称")
    private String bankAccountName = "";

    @ApiModelProperty("银行账号")
    private String bankAccountCode = "";

}
