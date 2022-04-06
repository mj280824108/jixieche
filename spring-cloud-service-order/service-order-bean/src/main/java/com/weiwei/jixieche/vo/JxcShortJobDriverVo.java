package com.weiwei.jixieche.vo;

import com.weiwei.jixieche.page.BasePage;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 兼职司机列表
 * @author liuy
 * @date 2019-08-30 14:05
 */
@ApiModel("兼职司机列表")
@Data
@EqualsAndHashCode
public class JxcShortJobDriverVo extends BasePage {

	@ApiModelProperty("司机用户ID")
	private Integer driverId;

	@ApiModelProperty("司机用户名称")
	private String driverName;

	@ApiModelProperty("司机手机号码")
	private String driverPhone;

	@ApiModelProperty("工期开始日期")
	private String workTimeStart;

	@ApiModelProperty("工期结束日期")
	private String workTimeEnd;

	@ApiModelProperty("职位ID")
	private Integer shortJobId;

	@ApiModelProperty("驾龄要求(0--不限 1--一年以上 2--三年以上 3--五年以上 4--其他)")
	private Integer driveYear;

	@ApiModelProperty("司机头像")
	private String driverImg;

	@ApiModelProperty("状态（0：已接受 1：进行中 2：已完结 3：辞职（司机主动取消） 4：被机主取消 5：被机主解雇）")
	private Integer state;

	@ApiModelProperty("支付状态: 0：未支付; 1:已支付")
	private Integer payState;

}
