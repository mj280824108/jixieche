package com.weiwei.jixieche.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author zhouw
 * @date 2019年5月23日 下午3:19:06
 * @description
 */
@ApiModel("折线图数据Vo")
@Getter
@Setter
public class LineItemDataVo implements Serializable {

	private static final long serialVersionUID = 2503371411392275885L;

	@ApiModelProperty("月份(4)")
	private String monthStr;

	@ApiModelProperty("平均车辆数")
	private Double machineNum;

	@ApiModelProperty("总金额(元)")
	private Integer totalCount;

	@ApiModelProperty("异常趟数")
	private BigDecimal totalAmount;



}
