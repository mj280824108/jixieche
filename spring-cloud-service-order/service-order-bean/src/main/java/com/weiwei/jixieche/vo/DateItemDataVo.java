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
@ApiModel("时间项数据Vo")
@Getter
@Setter
public class DateItemDataVo implements Serializable {

	private static final long serialVersionUID = 2503371411392275885L;

	@ApiModelProperty("完结趟数集合")
	private List<Integer> finishCountList = new ArrayList<>();

	@ApiModelProperty("总金额(元)集合")
	private List<BigDecimal> finishAmountList = new ArrayList<>();

	@ApiModelProperty("异常趟数集合")
	private List<Integer> abnormalCountList = new ArrayList<>();

}
