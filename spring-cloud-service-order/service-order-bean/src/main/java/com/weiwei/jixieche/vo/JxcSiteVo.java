package com.weiwei.jixieche.vo;

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
@ApiModel(value="消纳场Vo类")
public class JxcSiteVo extends BasePage {
    @ApiModelProperty("消纳场id")
    private Integer id;

    @ApiModelProperty("消纳场名")
    private String siteName;

    @ApiModelProperty("省份编码")
    private Integer provinceCode;

    @ApiModelProperty("城市编码")
    private Integer cityCode;

    @ApiModelProperty("土方类型 0：坏土 1：好土")
    private Integer earthType;

    @ApiModelProperty("区编码")
    private Integer districtCode;

    @ApiModelProperty("消纳场详细地址")
    private String siteAddress;

    @ApiModelProperty("是否入驻平台（1：是 0：否）")
    private Integer intoFlag;

    @ApiModelProperty("删除标记（1：未删除 0：已删除）")
    private Integer delState;

    @ApiModelProperty("是否停业（1：营业 0：停业）")
    private Integer openFlag;

    @ApiModelProperty("停业公告")
    private String closingNotice;

    @ApiModelProperty("承租方订单ID")
    private Long tenantryOrderId;

    @ApiModelProperty("预计公里数（单位：公里）")
    private String estimateMiles;

    @ApiModelProperty("预计金额(单位：分)")
    private String estimatePrice;

    @ApiModelProperty("固定价格（一口价 单位：分）")
    private String fixedPrice;

    @ApiModelProperty("计价方式（0：按预计价格计算单价 1：按照固定价格计算单价）")
    private Integer pricingMode;


    private static final long serialVersionUID = 1L;
}