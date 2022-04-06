package com.weiwei.jixieche.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 承租方管理员扫一扫返回数据
 * @author liuy
 * @date 2019-08-26 11:41
 */
@ApiModel("承租方管理员扫一扫返回数据")
@Data
@EqualsAndHashCode
public class JxcProjectUserScanVo {

	@ApiModelProperty("规格")
	private Integer capacity;

	@ApiModelProperty("机主订单ID")
	private Long orderId;

	@ApiModelProperty("承租方订单ID")
	private Long tenantryOrderId;

	@ApiModelProperty("是否停业（1：营业 0：停业）")
	private Integer openFlag;

	@ApiModelProperty("停业公告")
	private String closingNotice;

	@ApiModelProperty("消纳券类型 (0:坏土 1:好土)")
	private Integer couponType;

	@ApiModelProperty("电子消纳劵号码")
	private Long couponId;

	@ApiModelProperty("消纳场地址")
	private String siteAddress;

	@ApiModelProperty("消纳场名称")
	private String siteName;

	@ApiModelProperty("车牌号码")
	private String machineCarNo;

	@ApiModelProperty("打卡状态：1:打卡成功(已入驻); 2:打卡成功(已入驻),消纳券余额不足10张; 3:打卡成功(未入驻) 4:消纳券余额不足 5：消纳场停业中")
	private Integer clockStatus;

	@ApiModelProperty("剩余张数")
	private Integer count;

	@ApiModelProperty("上班状态(1:上班中; 0:未上班)")
	private Integer workStatus;

	//打卡状态：1:打卡成功(已入驻); 2:打卡成功(已入驻),消纳券余额不足10张;
	// 3:打卡成功(未入驻) 4:消纳券余额不足 5：消纳场停业中; 6:打卡失败; 7:上一趟车辆有未核销消纳劵
	public interface clockStatus{
		Integer CLOCKSTATUS1= 1;
		Integer CLOCKSTATUS2= 2;
		Integer CLOCKSTATUS3= 3;
		Integer CLOCKSTATUS4= 4;
		Integer CLOCKSTATUS5= 5;
		Integer CLOCKSTATUS6= 6;
		Integer CLOCKSTATUS7= 7;
	}

	//上班状态(1:上班中; 0:未上班)
	public interface workStatus{
		Integer WORKINSTATUS = 1;
		Integer WORKOUTSTATUS = 0;
	}


}
