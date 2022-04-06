package com.weiwei.jixieche.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.List;

/**
 * 趟数记录
 * @author liuy
 * @date 2019-08-20 11:41
 */
@ApiModel("趟数记录")
@Data
@EqualsAndHashCode
public class JxcMachineRouteVo {

	@ApiModelProperty("行程ID")
	private Long routeId;

	@ApiModelProperty("消纳场地址")
	private String siteAddress;

	@ApiModelProperty("施工地地址")
	private String projectAddress;

	@ApiModelProperty("施工场地打卡时间")
	private String startTime;

	@ApiModelProperty("消纳场打卡时间")
	private String endTime;

	@ApiModelProperty("车辆ID")
	private Integer machineId;

	@ApiModelProperty("车牌号码")
	private String machineCarNo;

	@ApiModelProperty("异常类型（0：正常 1：施工场地漏打卡 2：消纳场地漏打卡 3：实际里程超出或小于预计里程25%）")
	private Integer abnormalType;

	@ApiModelProperty("支付状态（0：异常趟或正在跑趟中 1：待支付（平台已垫付） 2：已支付）")
	private Integer payState;

	@ApiModelProperty("第三方id(用户第三方鹰眼，极光推送唯一识别码)")
	private String thirdId;

	@ApiModelProperty("承租方应付金额（单位：分）")
	private BigDecimal amount;

	@ApiModelProperty("异常状态(0--异常申请待处理 1--处理完毕")
	private Integer abnormalStatus;

	@ApiModelProperty("趟数状态: -1：未打消纳场卡, 0:正常 1：未处理； 2：已处理")
	private Integer status;

	/**
	 * 查询条件
	 */
	@ApiModelProperty("司机用户ID")
	private Integer driverId;

	@ApiModelProperty("查询条件：最后一条记录ID")
	private Long lastPageLastId;

	@ApiModelProperty("机主订单ID")
	private Long ownerOrderId;

	@ApiModelProperty("车辆ID集合")
	private List<Integer> machineIds;

	@ApiModelProperty("兼职职位ID")
	private Integer shortJobId;

	public interface RouteState{
		Integer NOCLOCK = -1;
		Integer NORMAL = 0;
		Integer UNPROCESSED = 1;
		Integer PROCESSED= 2;
	}

}
