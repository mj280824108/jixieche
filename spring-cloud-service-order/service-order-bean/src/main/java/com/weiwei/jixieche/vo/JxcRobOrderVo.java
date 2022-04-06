package com.weiwei.jixieche.vo;

import com.weiwei.jixieche.page.BasePage;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

/**
 * 接单列表VO
 * @author liuy
 * @date 2019-08-22 16:30
 */
@ApiModel("接单列表VO")
@Data
@EqualsAndHashCode
public class JxcRobOrderVo extends BasePage {

    @ApiModelProperty("施工地址")
	private String projectAddress;

	@ApiModelProperty("工作天数")
	private Integer workDay;

	@ApiModelProperty("消纳场地址")
	private String siteAddress;

	@ApiModelProperty("消纳场名称")
	private String siteName;

	@ApiModelProperty("一口价")
	private BigDecimal fixedPrice;

	@ApiModelProperty("预计金额")
	private BigDecimal estimatePrice;

	@ApiModelProperty("计价方式（0：按预计价格计算单价 1：按照固定价格计算单价）")
	private Integer pricingMode;

	@ApiModelProperty("预计趟数")
	private Integer routeCount;

	@ApiModelProperty("预计公里数")
	private BigDecimal estimateMiles;

	@ApiModelProperty("开始日期")
	private String startDate;

	@ApiModelProperty("结束日期")
	private String endDate;

	@ApiModelProperty("区域ID")
	private Integer areaId;

	@ApiModelProperty("机械ID")
	private Integer machineId;

	@ApiModelProperty("承租方订单ID")
	private Long projectOrderId;
}
