package com.weiwei.jixieche.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 检查进行中订单是否跑趟中或是正在上班中
 * @author liuy
 * @date 2019-09-04 15:07
 */
@ApiModel("检查进行中订单是否跑趟中或是正在上班中")
@Data
@EqualsAndHashCode
public class JxcMachineWorkInVo {

	@ApiModelProperty("司机ID")
	private Integer driverId;

	@ApiModelProperty("司机名称")
	private String driverName;

	@ApiModelProperty("上班中的司机ID")
	private Integer upDriverId;

	@ApiModelProperty("上班中的司机名称")
	private String upDriverName;

	@ApiModelProperty("上班中的司机电话")
	private String upDriverPhone;

	@ApiModelProperty("跑趟中状态(status: 0:未跑趟中 1:第一次交接班; 2:第二次交接班)")
	private Integer status = 0;

	@ApiModelProperty("上班中状态(0:未上班 1:正在上班中)")
	private Integer isWork = 0;

	@ApiModelProperty("行程ID")
	private Long routeId;

	@ApiModelProperty("机主电话")
	private String ownerPhone;


}
