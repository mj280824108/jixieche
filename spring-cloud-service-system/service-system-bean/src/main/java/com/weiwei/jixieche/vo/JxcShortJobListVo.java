package com.weiwei.jixieche.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 兼职职位列表
 * @author liuy
 * @date 2019-09-17 20:10
 */
@Data
@EqualsAndHashCode
public class JxcShortJobListVo {

	@ApiModelProperty("开始日期")
	private String startDate;

	@ApiModelProperty("结束日期")
	private String endDate;

	@ApiModelProperty("机主ID")
	private Integer ownerId;

	@ApiModelProperty("司机ID")
	private Integer driverId;

	@ApiModelProperty("司机手机号码")
	private String driverPhone;

	@ApiModelProperty("司机名称")
	private String driverName;

	@ApiModelProperty("主键ID")
	private Integer id;

	@ApiModelProperty("兼职职位ID")
	private Integer shortJobId;

}
