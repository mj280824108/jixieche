package com.weiwei.jixieche.vo;

import com.weiwei.jixieche.page.BasePage;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * 兼职司机详情
 * @author liuy
 * @date 2019-08-30 14:05
 */
@ApiModel("兼职司机详情")
@Data
@EqualsAndHashCode
public class JxcShortJobDriverDetailVo {

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

	@ApiModelProperty("状态（0：已接受 1：进行中 2：已完结 3：辞职（司机主动取消） 4：被机主取消 5：被机主解雇）")
	private Integer state;

	@ApiModelProperty("0--泥头车 1--挖机 2--其他")
	private Integer machineType;

	@ApiModelProperty("性别(0--男 1--女)")
	private Integer sex;

	@ApiModelProperty("出生日期")
	private Date birthday;

	@ApiModelProperty("年龄")
	private Integer age;

	@ApiModelProperty("身份证号")
	private String cardCode;

	@ApiModelProperty("司机认证状态; (0--未认证 1--已认证，2--未通过，3--审核中)")
	private Integer driverConfirmStatus;

	@ApiModelProperty("驾龄(0--一年以上 1--三年以上 2--五年以上 3--不限)")
	private Integer driverYear;

	@ApiModelProperty("工作时段(开始-结束)")
	private String timeFrame;

	@ApiModelProperty("工作区域")
	private String workArea;

	@ApiModelProperty("领车地址")
	private String carAddress;

	@ApiModelProperty("从业资格证 0:未提供 1:已提供")
	private Integer certification;

	@ApiModelProperty("驾驶证 0:未提供 1:已提供")
	private  Integer driveLicense;

	@ApiModelProperty("评价状态(1:已评价; 0:未评价)")
	private Integer evaluationStatus;

	@ApiModelProperty("评分")
	private double score;

}
