package com.weiwei.jixieche.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 司机交接班
 * @author liuy
 * @date 2019-08-24 15:08
 */
@ApiModel("司机交接班")
@Data
@EqualsAndHashCode
public class JxcDriverChangeWorkVo {

	@ApiModelProperty("司机ID")
	private Integer driverId;

	@ApiModelProperty("需要交接班的司机Id")
	private Integer upDriverId;

	@ApiModelProperty("行程ID")
	private Long routeId;

	@ApiModelProperty("打卡地点")
	private String clockAddress;
}
