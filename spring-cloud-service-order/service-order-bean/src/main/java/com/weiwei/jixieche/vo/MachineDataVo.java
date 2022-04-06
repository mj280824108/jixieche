package com.weiwei.jixieche.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author zhouw
 * @date 2019年5月23日 下午3:19:06
 * @description
 */

@Data
@EqualsAndHashCode
@ApiModel("机械统计数据Vo")
public class MachineDataVo implements Serializable {

	private static final long serialVersionUID = 2503371411392275885L;

	@ApiModelProperty("总趟数")
	private Integer totalRouteCount = 0;

	@ApiModelProperty("总收入(元)")
	private BigDecimal totalAmount;

	@ApiModelProperty("搜索时间天数(yyyy-mm-dd)显示")
	private String dateTime="";

	@ApiModelProperty("(参数必填)说明:1.按车辆搜索当天日期(yyyy-MM-dd),2.按天数搜索区间起始时间")
	private String startDate="";

	@ApiModelProperty("(参数必填)说明:1.按车辆搜索当天日期(yyyy-MM-dd),2.按天数搜索区间截止时间")
	private String endDate="";

	@ApiModelProperty("(参数必填)说明:1.按车辆搜索当天日期(yyyy-MM),2.按月统计")
	private String monthDate;

	@ApiModelProperty("列表数据")
	private List<MachineDataListVo> machineDataList;

}
