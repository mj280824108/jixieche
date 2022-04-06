package com.weiwei.jixieche.vo;

import com.weiwei.jixieche.page.BasePage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 考勤记录
 * @author liuy
 * @date 2019-08-19 16:28
 */
@ApiModel("考勤记录")
@Data
@EqualsAndHashCode
public class AttendanceRecordVo extends BasePage {

	@ApiModelProperty("打卡日期")
	private String clockDate;

	@ApiModelProperty("车牌号码")
	private String machineCarNo;

	@ApiModelProperty("上班时间")
	private String clockInTime;

	@ApiModelProperty("下班时间")
	private String clockOutTime;

	@ApiModelProperty("工时")
	private double workHours;

	@ApiModelProperty("查询条件：月份")
	private String clockMonth;

	@ApiModelProperty("查询条件：开始日期")
	private String startDate;

	@ApiModelProperty("查询条件：结束日期")
	private String endDate;

	@ApiModelProperty("司机用户ID")
	private Integer driverId;

	@ApiModelProperty("参数标识: 1:按月份查询; 2:按日期查询")
	private Integer flag;

}
