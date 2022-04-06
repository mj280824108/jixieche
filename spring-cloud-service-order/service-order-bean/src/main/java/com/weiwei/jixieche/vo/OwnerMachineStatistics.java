package com.weiwei.jixieche.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * @author zhouw
 * @date 2019年5月23日 下午3:43:23
 * @description
 */
@Getter
@Setter
@ApiModel("机主机械日期收入统计")
public class OwnerMachineStatistics {

	@ApiModelProperty("日期")
	private String dayTime;

	@ApiModelProperty("车牌号")
	private String machineCardNo;

	@ApiModelProperty("总趟数")
	private Integer routeCount;

	@ApiModelProperty("总金额")
	private BigDecimal totalAmount;

	@ApiModelProperty("机械数量")
	private Integer machineCount;

	@ApiModelProperty("月份")
	private String monthTime;
}
