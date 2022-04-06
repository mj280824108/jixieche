package com.weiwei.jixieche.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.weiwei.jixieche.page.BasePage;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

@Data
@EqualsAndHashCode
@ApiModel(value="市场发布(出售)出租信息表")
public class JxcMarketReleaseVo extends BasePage implements Serializable {
       @ApiModelProperty("主键自增")
       private Integer id;

       @ApiModelProperty("发布类型(1--机械求购 2--机械出售 3--机械求租 4--机械出租 5--资源求购 6--资源出售 7--其他)")
       private Integer realeseType;

       @ApiModelProperty("资源类型id")
       private Integer sourceId;

       @ApiModelProperty("资源类型名称")
       private String sourceName;

       @ApiModelProperty("机械类型id")
       private Integer machineTypeId;

       @ApiModelProperty("机械类型名称")
       private String machineTypeName;

       @ApiModelProperty("品牌类型id")
       private Integer brandTypeId;

       @ApiModelProperty("品牌类型名称")
       private String brandTypeName;

       @ApiModelProperty("发布标题")
       private String title;

       @ApiModelProperty("求购类型(1--面议 2--价格范围)")
       private Integer buyType;

       @ApiModelProperty("求购范围最低价(单文：分)")
       private Integer buyPriceDown;

       @ApiModelProperty("求购范围最高价(单位：分)")
       private Integer buyPriceTop;

       @ApiModelProperty("求购(求租)出售数量")
       private Integer productNumber;

       @ApiModelProperty("详细需求,机械简介,资源简介")
       private String requestDetails;

       @ApiModelProperty("新旧类型(1--新机 2--二手)")
       private Integer newDegreeType;

       @ApiModelProperty("新旧程度(9--九成新  8--九成新 7--七成新)，数字为几就是几成新")
       private Integer newDegreeLevel;

       @ApiModelProperty("上架下架状态(0--下架 1--上架)")
       private Integer upperLowerStatus;

       @ApiModelProperty("是否新上架(1--新上架 2--以前上架)")
       private Integer newUpperLower;

       @ApiModelProperty("商品出厂时间(yyyy-MM-dd)")
       private String productTime;

       @ApiModelProperty("地址的市id")
       private Integer cityId;

       @ApiModelProperty("地址的区id")
       private Integer districtId;

       @ApiModelProperty("省市区(如:湖北省武汉市洪山区)")
       private String areaName;

       @ApiModelProperty("停放地址详情")
       private String parkeAddress;

       @ApiModelProperty("出售价格(单位:分)")
       private Integer salePrice;

       @ApiModelProperty("出售数量")
       private Integer saleNumber;

       @ApiModelProperty("出售(出租)图片,多张分号;分割")
       private String salePictures;

       @ApiModelProperty("预计工期天数")
       private Integer estimateProjectTime;

       @ApiModelProperty("进场时间(yyyy-mm-dd)")
       private String projectStartTime;

       @ApiModelProperty("出场日期(yyyy-mm-dd)")
       private String projectEndTime;

       @ApiModelProperty("施工地址")
       private String projectAddress;

       @ApiModelProperty("出租(求租)方式(1--包月  2--包天 3--小时)")
       private Integer rentType;

       @ApiModelProperty("出租(求租)小时价格(单位：分)")
       private Integer leaseHourPrice;

       @ApiModelProperty("出租(求租)月结价格(单位：分)")
       private Integer leaseMonthPrice;

       @ApiModelProperty("出租(求租)台班价格(单位：分)")
       private Integer leaseTeamPrice;

       @ApiModelProperty("资源出售(求购)方式(1--自定义 2--面议)")
       private Integer sourceType;

       @ApiModelProperty("资源出售价格(单位：分)")
       private Integer sourceSalePrice;

       @ApiModelProperty("资源出售(求购)数量")
       private Integer sourceNumber;

       @ApiModelProperty("资源出售(求购)数量的单位(15顿/车)")
       private String sourceUnit;

       @ApiModelProperty("资源求购最低价(单位：分)")
       private Integer sourceBuyDownPrice;

       @ApiModelProperty("资源求购最高价(单位：分)")
       private Integer sourceBuyTopPrice;

       @ApiModelProperty("资源地址")
       private String sourceAddress;

       @ApiModelProperty("资源出售图片,多张分号;分割")
       private String sourcePictures;

       @ApiModelProperty("求购联系人")
       private String personName;

       @ApiModelProperty("联系人电话")
       private String personPhone;

       @ApiModelProperty("浏览量")
       private Integer viewNumber;

       @ApiModelProperty("发布信息的商铺id")
       private Integer shopsId;

       @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
       @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
       @ApiModelProperty("创建时间")
       private Date createTime;

       @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
       @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
       @ApiModelProperty("修改时间")
       private Date updateTime;

       @ApiModelProperty("店主id")
       private Integer shopKeeperId;

       @ApiModelProperty("显示状态(0--不显示,1--显示)")
       private Integer displayFlag;

       private static final long serialVersionUID = 1L;
}