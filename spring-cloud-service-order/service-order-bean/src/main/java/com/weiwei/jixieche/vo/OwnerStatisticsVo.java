package com.weiwei.jixieche.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zhouw
 * @date 2019年5月23日 下午3:19:06
 * @description
 */
@ApiModel("机主收入统计参数结果数据Vo")
@Getter
@Setter
public class OwnerStatisticsVo implements Serializable {

	private static final long serialVersionUID = 2503371411392275885L;

	@ApiModelProperty("(参数必填,两者必有其一)统计类型。0：按时间，1：按车辆")
	private int statisticsType;

	@ApiModelProperty("(参数必填)用户userId")
	private Long userId;

	@ApiModelProperty("(参数必填)说明:1.按车辆搜索当天日期(yyyy-MM-dd),2.按天数搜索区间起始时间")
	private String startDate;

	@ApiModelProperty("(参数必填)说明:1.按车辆搜索当天日期(yyyy-MM-dd),2.按天数搜索区间截止时间")
	private String endDate;

	@ApiModelProperty("(参数必填)说明:1.按车辆搜索当天日期(yyyy-MM),2.按月统计")
	private String monthDate;

	@ApiModelProperty("(返回值)机主按(时间统计)报表数据")
	private List<DateItemDataVo> dateDataList = new ArrayList<>();

	@ApiModelProperty("(返回值)机主按(时间统计)折线图数据")
	private List<LineItemDataVo> lineDataList = new ArrayList<>();

	@ApiModelProperty("(返回值)机主按(机械统计)数据")
	private MachineDataVo machineData;

	@ApiModelProperty("折线图平均车辆数最大值")
	private Double maxMachineNum;

	@ApiModelProperty("折线图总金额(元)最大值")
	private Integer maxTotalCount;

	@ApiModelProperty("折线图异常趟数最大值")
	private BigDecimal maxTotalAmount;

	//统计类型选择
	public interface statisticsType{
		Integer DATE_TYPE = 0;
		Integer MACHINE_TYPE = 1;
	}
}
