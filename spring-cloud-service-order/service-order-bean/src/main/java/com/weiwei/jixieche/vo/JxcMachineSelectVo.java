package com.weiwei.jixieche.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 筛选车辆列表
 * @author liuy
 * @date 2019-10-08 11:41
 */
@Data
@EqualsAndHashCode
@ApiModel(value="筛选车辆列表")
public class JxcMachineSelectVo {

	@ApiModelProperty("机械id,主键自增")
	private Integer id;

	@ApiModelProperty("车牌号")
	private String machineCardNo;

	@ApiModelProperty("车辆归属地(市级)")
	private Integer areaCode;
}
