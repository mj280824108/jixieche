package com.weiwei.jixieche.vo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;
import java.util.List;

/**
 * 车辆详情
 * @author liuy
 * @date 2019-08-13 18:03
 */
@Data
@EqualsAndHashCode
@ApiModel(value="机械表")
public class JxcMachineVo {

	@ApiModelProperty("机械id,主键自增")
	private Integer id;

	@ApiModelProperty("车牌号")
	private String machineCardNo;

	@ApiModelProperty("车辆归属地(市级)")
	private Integer areaCode;

	@ApiModelProperty("工作区域名称")
	private String workArea;

	@ApiModelProperty("车辆生产出厂时间")
	private String machineProductTime;

	@ApiModelProperty("营运证图片正面")
	private String machineOperationCertificateFront;

	@ApiModelProperty("营运证图片反面")
	private String machineOperationCertificateBehind;

	@ApiModelProperty("营运证有效时间")
	private String operationCertificateValidTime;

	@ApiModelProperty("营运证状态 0可用 1过期 2即将过期")
	private Integer operationCertificateState;

	@ApiModelProperty("行驶证图片正面")
	private String machineDrivingLicenseFront;

	@ApiModelProperty("行驶证图片反面")
	private String machineDrivingLicenseBehind;

	@ApiModelProperty("行驶证有效时间")
	private String drivingLicenseValidTime;

	@ApiModelProperty("行驶证状态 0--可用 1--过期 2--即将过期")
	private Integer drivingLicenseState;

	@ApiModelProperty("审核状态( 0：审核中 1：已通过 2：未通过  )，配合宽进严出政策，去掉审核环节")
	private Integer confirmState;

	@ApiModelProperty("机械绑定机主的userId")
	private Integer userId;

	@ApiModelProperty("状态（0：无账号 1：不接单 2：空闲中：3：已接单 4：进行中）")
	private Integer status;

	@ApiModelProperty("机械方量")
	private Integer machineCapacity;

	@ApiModelProperty("绑定司机列表")
	List<DriverVo> driverList;

	@ApiModelProperty("项目名称")
	private String projectName;

	@ApiModelProperty("项目开工日期 yyyy-MM-dd")
	private String startDate;

	@ApiModelProperty("项目竣工日期 yyyy-MM-dd")
	private String endDate;

	@ApiModelProperty("项目地点")
	private String projectAddress;

	@ApiModelProperty("消纳场地点")
	private String siteAddress;

	@ApiModelProperty("总趟数")
	private Integer machineTotalCount;

	@ApiModelProperty("评分")
	private Double score;

	@ApiModelProperty("机主用户名称")
	private String ownerUserName;

	@ApiModelProperty("机主手机号码")
	private String ownerPhone;

	@ApiModelProperty("评价状态(1:已评价; 0:未评价)")
	private Integer evaluationStatus;

	@ApiModelProperty("机主订单id")
	private Long ownerOrderId;

	@ApiModelProperty("承租方订单状态：0：待接单 1：取消订单 2：进行中 3：已完结")
	private Integer projectOrderState;

	@ApiModelProperty("机主订单状态：0：已接单 1：取消订单（未到进行中前被取消） 2：进行中 3：已完结（退出） 4：进行中被承租方解雇）")
	private Integer ownerOrderState;

}
