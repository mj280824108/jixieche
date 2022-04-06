package com.weiwei.jixieche.vo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

/**
 * 异常趟数处理进度
 * @author liuy
 * @date 2019-08-27 13:51
 */
@ApiModel("异常趟数处理进度")
@Data
@EqualsAndHashCode
public class JxcOwnerAbnormalVo {

	@ApiModelProperty("行程ID")
	private Long routeId;

	@ApiModelProperty("项目名称")
	private String projectName;

	@ApiModelProperty("车牌号码")
	private String machineCardNo;

	@ApiModelProperty("0：正常 1：施工场地漏打卡 2：消纳场地漏打卡 3：里程异常(实际里程超出或小于预计里程25%）")
	private Integer abnormalType;

	@ApiModelProperty("异常状态(0--异常申请待处理 1--处理完毕)")
	private Integer abnormalStatus;

	@ApiModelProperty("异常处理完成，协商价格(单位分)")
	private BigDecimal consultPrice;

	@ApiModelProperty("协商处理人userid")
	private Integer consultUserId;

	@ApiModelProperty("异常申请用户userid")
	private Integer appealUserId;

	@ApiModelProperty("承租方自己确认异常标记（0：否 1：是）")
	private Integer selfHandleFlag;

	@ApiModelProperty("支付状态（0：异常趟或正在跑趟中 1：待支付（平台已垫付） 2：已支付）")
	private Integer payState;

	@ApiModelProperty("施工场地打卡时间")
	private String startTime;

	@ApiModelProperty("消纳场打卡时间")
	private String endTime;

	@ApiModelProperty("处理状态：0:待处理 1:待核实 2:已处理 3:已支付")
	private Integer state;

	@ApiModelProperty("施工场地打卡地址")
	private String projectAddress;

	@ApiModelProperty("消纳场打卡地址")
	private String siteAddress;

	@ApiModelProperty("司机ID")
	private Integer driverId;

	@ApiModelProperty("司机手机号码")
	private String driverPhone;

	@ApiModelProperty("机主订单ID")
	private Long ownerOrderId;

	@ApiModelProperty("提交申诉时间")
	private String time1;

	@ApiModelProperty("客服已处理时间")
	private String time2;

	public interface state{
		Integer NO_PROCESSED = 0;
		Integer VERIFIED = 1;
		Integer PROCESSED = 2;
		Integer PAID = 3;

	}

}
