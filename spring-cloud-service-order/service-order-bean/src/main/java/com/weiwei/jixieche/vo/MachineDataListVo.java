package com.weiwei.jixieche.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author zhouw
 * @date 2019年5月23日 下午3:19:06
 * @description
 */
@Data
@EqualsAndHashCode
@ApiModel(value="机械列表数据Vo")
public class MachineDataListVo implements Serializable {

	private static final long serialVersionUID = 2503371411392275885L;

	@ApiModelProperty("车牌号")
	private String machineCardNo;

	@ApiModelProperty("完成趟数")
	private Integer finishRouteCount;

	@ApiModelProperty("总金额(元)")
	private BigDecimal totalAmount;

	@ApiModelProperty("异常趟数")
	private Integer abnormalCount;

}
