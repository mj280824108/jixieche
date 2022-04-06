package com.weiwei.jixieche.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 承租方管理员扫一扫
 * @author liuy
 * @date 2019-08-26 11:17
 */
@ApiModel("承租方管理员扫一扫")
@Data
@EqualsAndHashCode
public class JxcProjectUserVo {

	@ApiModelProperty("行程ID")
	private Integer routeId;

	@ApiModelProperty("消纳场地址")
	private String siteAddress;

	@ApiModelProperty("消纳场名称")
	private String siteName;

	@ApiModelProperty("施工地地址")
	private String projectAddress;

	@ApiModelProperty("施工场地打卡时间")
	private String startTime;

	@ApiModelProperty("消纳场打卡时间")
	private String endTime;

	@ApiModelProperty("车牌号码")
	private String machineCarNo;

	@ApiModelProperty("承租方订单ID")
	private Long tenantryOrderId;

	@ApiModelProperty("机主订单ID")
	private Long ownerOrderId;

	@ApiModelProperty("机械ID")
	private Integer machineId;

	@ApiModelProperty("司机用户ID")
	private Integer driverId;

	@ApiModelProperty("消纳场ID")
	private Integer siteId;

	@ApiModelProperty("是否入驻平台（1：是 0：否）")
	private Integer intoFlag;

	@ApiModelProperty("规格")
	private Integer capacity;

	@ApiModelProperty("消纳券类型 (0:坏土 1:好土)")
	private Integer couponType;

	@ApiModelProperty("是否停业（1：营业 0：停业）")
	private Integer openFlag;

	@ApiModelProperty("停业公告")
	private String closingNotice;

	@ApiModelProperty("承租方用户ID")
	private Integer projectUserId;

	@ApiModelProperty("项目ID")
	private Integer projectId;

	@ApiModelProperty("机主用户ID")
	private Integer ownerUserId;

}
