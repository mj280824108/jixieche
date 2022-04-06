package com.weiwei.jixieche.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
* @Description: 司机信息
* @Author:      liuy
* @Date:  2019/8/14 10:32
*/
@Data
@EqualsAndHashCode
@ApiModel(value="司机信息")
public class DriverVo {

	@ApiModelProperty("主键ID")
	private Long id;

	@ApiModelProperty("司机ID")
	private Integer driverId;

	@ApiModelProperty("司机名称")
	private String driverName;

	@ApiModelProperty("司机手机号码")
	private String driverPhone;

	@ApiModelProperty("司机类型: 1:我自己; 2:我的司机; 3:兼职司机")
	private Integer driverType;

	@ApiModelProperty("绑定车牌号码")
	private String machineCardNo;

	@ApiModelProperty("机械ID")
	private Integer machineId;

	@ApiModelProperty("开始工作时间")
	@JsonFormat(pattern="yyyy-MM-dd",timezone="GMT+8")
	private Date startDate;

	@ApiModelProperty("结束工作时间")
	@JsonFormat(pattern="yyyy-MM-dd",timezone="GMT+8")
	private Date endDate;

	@ApiModelProperty("司机绑定状态 1:已绑定; 0:未绑定")
	private Integer bindState;

	@ApiModelProperty("司机上班状态标识：1:上班中; 0:未上班")
	private Integer workState;

	@ApiModelProperty("司机生日")
	private Date birthday;

	@ApiModelProperty("年龄")
	private Integer age;

	@ApiModelProperty("驾龄:0--一年以上 1--三年以上 2--五年以上 3--不限")
	private Integer driverYear;

	@ApiModelProperty("司机头像")
	private String driverHeadImg;

	@ApiModelProperty("支付状态：0:未支付; 1:已支付")
	private Integer payState;

	@ApiModelProperty("从业资格证 0:未提供 1:已提供")
	private Integer certification;

	@ApiModelProperty("驾驶证 0:未提供 1:已提供")
	private  Integer driveLicense;

	@ApiModelProperty("评分")
	private double score;

	@ApiModelProperty("职位ID")
	private Integer shortJobId;

	@ApiModelProperty("1：司机列表; 2:我的司机")
	private Integer flag;

	@ApiModelProperty("验证码")
	private String code;

	@ApiModelProperty("第三方ID")
	private String thirdId;

	@ApiModelProperty("性别(0--男 1--女)")
	private Integer driverSex;

	public interface driverType{
		Integer DRIVER = 1;
		Integer OWNERDRIVER = 2;
		Integer SHORTDRIVER = 3;
	}
}
