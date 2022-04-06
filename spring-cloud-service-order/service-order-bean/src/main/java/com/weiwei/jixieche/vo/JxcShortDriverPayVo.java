package com.weiwei.jixieche.vo;

import com.weiwei.jixieche.page.BasePage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 兼职司机工资支付
 * @author liuy
 * @date 2019-08-24 11:37
 */
@ApiModel("兼职司机工资支付")
@Data
@EqualsAndHashCode
public class JxcShortDriverPayVo extends BasePage {

	@ApiModelProperty("司机ID")
	private Integer driverId;

	@ApiModelProperty("台班ID")
	private Long clockId;

	@ApiModelProperty("司机姓名")
	private String driverName;

	@ApiModelProperty("司机手机号码")
	private String driverPhone;

	@ApiModelProperty("应收工资")
	private BigDecimal payMoney;

	@ApiModelProperty("司机最后一次打下班卡时间")
	private Date createTime;

	@ApiModelProperty("台班支付状态 0：未支付 1：已支付")
	private Integer payState;

	@ApiModelProperty("兼职职位ID")
	private Integer shortJobId;

}
