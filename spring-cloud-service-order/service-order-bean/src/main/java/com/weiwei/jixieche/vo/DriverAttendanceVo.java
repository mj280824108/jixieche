package com.weiwei.jixieche.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 司机考勤记录
 * @author liuy
 * @date 2019-08-15 20:14
 */
@Data
@EqualsAndHashCode
@ApiModel(value="司机考勤记录")
public class DriverAttendanceVo {

	@ApiModelProperty("车牌号码")
	private String machindeCarNo;

	@ApiModelProperty("打卡日期")
	private String attendanceDate;

	@ApiModelProperty("上班时间")
	private String startTime;

	@ApiModelProperty("下班时间")
	private String endTime;

	@ApiModelProperty("上班工时")
	private double attendanceHour;

}
