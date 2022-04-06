package com.weiwei.jixieche.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.weiwei.jixieche.page.BasePage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 兼职司机工作管理
 * @author liuy
 * @date 2019-08-16 14:50
 */
@Api("兼职司机工作管理")
@Data
@EqualsAndHashCode
public class ShortJobVo extends BasePage {

	@ApiModelProperty("兼职工作ID")
	private Integer shortJobId;

	@ApiModelProperty("开始日期")
	private String startDate;

	@ApiModelProperty("结束日期")
	private String endDate;

	@ApiModelProperty("领车地址")
	private String carAddress;

	@ApiModelProperty("手机号码")
	private String phone;

	@ApiModelProperty("状态（0：已接受 1：进行中 2：已完结 3：辞职（司机主动取消） 4：被机主取消 5：被机主解雇） 6:待收款")
	private Integer state;

	@ApiModelProperty("是否显示待收款 : 0:不显示; 1:显示")
	private Integer payFlag;

	@ApiModelProperty("工作时段（开始）")
	@JsonFormat(pattern = "HH:mm",timezone = "GMT+8")
	private Date timeFrameStart;

	@ApiModelProperty("工作时段（结束）")
	@JsonFormat(pattern = "HH:mm",timezone = "GMT+8")
	private Date timeFrameEnd;

	@ApiModelProperty("工作时段")
	private String timeFrame;

	@ApiModelProperty("预计工资")
	private BigDecimal expectedPay;

	@ApiModelProperty("机主用户名称")
	private String ownerUserName;

	@ApiModelProperty("机主手机号码")
	private String ownerPhone;

	@ApiModelProperty("机主头像")
	private String ownerHeadImg;

	@ApiModelProperty("机主信用分")
	private double ownerScore;

	@ApiModelProperty("职位发布的工作天数")
	private String jobWorkTime;

	@ApiModelProperty("工作区域ID")
	private Integer areaId;

	@ApiModelProperty("工作区域名称")
	private String workArea;

	@ApiModelProperty("机主用户ID")
	private Integer ownerUserId;

	@ApiModelProperty("机主认证状态; (0--未认证 1--已认证，2--未通过，3--审核中)")
	private Integer confirmStatus;

	@ApiModelProperty("评价状态(1:已评价; 0:未评价)")
	private Integer evaluationStatus;

	/**
	 * 查询条件
	 */
	@ApiModelProperty("司机用户ID")
	private Integer driverUserId;

	@ApiModelProperty("工作状态标识：0:已接单; 1:进行中; 2:已完结")
	private Integer flag;

	/**
	 * 是否显示待收款 : 0:不显示; 1:显示
	 */
	public interface payFlag{
		Integer IS_PAY = 1;
		Integer NOT_PAY = 0;
	}
}
