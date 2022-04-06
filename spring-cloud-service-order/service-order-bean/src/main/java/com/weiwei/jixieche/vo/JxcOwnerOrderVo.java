package com.weiwei.jixieche.vo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 机主订单
 * @author liuy
 * @date 2019-08-20 15:24
 */
@Api("机主订单")
@Data
@EqualsAndHashCode
public class JxcOwnerOrderVo {

	@ApiModelProperty("机主订单ID")
	private Long ownerOrderId;

	@ApiModelProperty("车牌号码")
	private String machineCarNo;

	@ApiModelProperty("项目名称")
	private String projectName;

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

	@ApiModelProperty("订单状态 ：1：已接单; 2:进行中; 3: 已完结; 4:已取消,5:待付款;6:申请中")
	private Integer orderStatus;

	@ApiModelProperty("施工地址")
	private String projectAddress;

	@ApiModelProperty("承租方用户Id")
	private Integer projectUserId;

	@ApiModelProperty("司机用户Id")
	private Integer driverId;

	@ApiModelProperty("评价状态(1:已评价; 0:未评价)")
	private Integer evaluationStatus;

	/**
	 * 订单状态 ：1：已接单; 2:进行中; 3: 已完结; 4:已取消;5:待付款;6:申请中
	 */
	public interface  orderStatus{
		Integer ORDER_RECEIVED = 1;
		Integer ORDER_PROCESSING = 2;
		Integer ORDER_FINISHED = 3;
		Integer ORDER_CANCELLED = 4;
		Integer WAIT_PAY = 5;
		Integer APPLY_ING = 6;
	}
}
