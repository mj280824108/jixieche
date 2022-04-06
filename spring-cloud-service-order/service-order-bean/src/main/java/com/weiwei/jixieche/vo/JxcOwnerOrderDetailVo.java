package com.weiwei.jixieche.vo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

/**
 * 机主订单
 * @author liuy
 * @date 2019-08-20 15:24
 */
@Api("机主订单详情")
@Data
@EqualsAndHashCode
public class JxcOwnerOrderDetailVo {

	@ApiModelProperty("机主订单ID")
	private Long ownerOrderId;

	@ApiModelProperty("承租方订单ID")
	private Long projectOrderId;

	@ApiModelProperty("车牌号码")
	private String machineCarNo;

	@ApiModelProperty("车辆ID")
	private Integer machineId;

	@ApiModelProperty("项目名称")
	private String projectName;

	@ApiModelProperty("项目负责人")
	private String projectLeader;

	@ApiModelProperty("承租方负责人电话")
	private String projectPhone;

	@ApiModelProperty("工期开始日期")
	private String startDate;

	@ApiModelProperty("工期结束日期")
	private String endDate;

	@ApiModelProperty("机主订单状态（0：已接单 1：取消订单（未到进行中前被取消） 2：进行中 3：已完结（退出） 4：进行中被承租方解雇")
	private Integer ownerOrderState;

	@ApiModelProperty("承租方订单状态（0：待接单 1：取消订单 2：进行中 3：已完结）")
	private Integer projectOrderState;

	@ApiModelProperty("承租方图片")
	private String projectImgUrl;

	@ApiModelProperty("是否显示待收款(1:是; 0:否)")
	private Integer waitPayFlag;

	@ApiModelProperty("订单状态 ：1：已接单; 2:进行中; 3: 已完结; 4:已取消")
	private Integer orderStatus;

	@ApiModelProperty("施工地址")
	private String projectAddress;

	@ApiModelProperty("消纳场地址")
	private String siteAddress;

	@ApiModelProperty("工作时段-开始时间")
	private String workStartTime;

	@ApiModelProperty("工作时段-结束时间")
	private String workEndTime;

	@ApiModelProperty("土方类型（0：坏土 1：好土")
	private Integer earthType;

	@ApiModelProperty("结算方式（1：日结 2：周结）")
	private Integer accountMethod;

	@ApiModelProperty("需求车辆数")
	private Integer estimateMachineCount;

	@ApiModelProperty("预计工程量（趟数）")
	private Integer estimateTransportTimes;

	@ApiModelProperty("公里数")
	private double estimateMiles;

	@ApiModelProperty("单价")
	private BigDecimal orderPrice;

	@ApiModelProperty("计价方式（0：按预计价格计算单价 1：按照固定价格计算单价")
	private Integer pricingMode;

	@ApiModelProperty("司机手机号码")
	private String driverPhone;

	@ApiModelProperty("司机名称")
	private String driverName;

	@ApiModelProperty("承租方评分")
	private double projectScore;

	@ApiModelProperty("已完成趟数")
	private Integer completeCount;

	@ApiModelProperty("异常趟数")
	private Integer abnormalCount;

	@ApiModelProperty("已收款金额")
	private BigDecimal payAmount;

	@ApiModelProperty("接单用户ID")
	private Integer userId;

	@ApiModelProperty("承租方用户ID")
	private Integer projectUserId;

	@ApiModelProperty("司机用户ID")
	private Integer driverId;

	@ApiModelProperty("评价状态(1:已评价; 0:未评价)")
	private Integer evaluationStatus;

	@ApiModelProperty("承租方订单城市ID")
	private Integer cityCode;

	@ApiModelProperty("机主第三方ID")
	private String thirdId;

	@ApiModelProperty("机主手机号码")
	private String ownerPhone;

	@ApiModelProperty("施工地经度")
	private String projectLng;

	@ApiModelProperty("施工地纬度")
	private String projectLat;

	@ApiModelProperty("消纳场经度")
	private String siteLng;

	@ApiModelProperty("消纳场纬度")
	private String siteLat;

}
