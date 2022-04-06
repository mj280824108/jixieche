package com.weiwei.jixieche.vo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 泥头车首页
 * @author liuy
 * @date 2019-08-22 14:45
 */
@ApiModel("泥头车首页")
@Data
@EqualsAndHashCode
public class JxcIndexVo {

	@ApiModelProperty("机械ID")
	private Integer machineId;

	@ApiModelProperty("车牌号码")
	private String machineCarNo;

	@ApiModelProperty("车辆区域ID")
	private Integer areaId;

	@ApiModelProperty("施工地地址")
	private String projectAddress;

	@ApiModelProperty("施工地经度")
	private String projectLng;

	@ApiModelProperty("施工地纬度")
	private String projectLat;

	@ApiModelProperty("消纳场地址")
	private String siteAddress;

	@ApiModelProperty("消纳场经度")
	private String siteLng;

	@ApiModelProperty("消纳场纬度")
	private String siteLat;

	@ApiModelProperty("是否在上班中 1:是; 0:否")
	private Integer isWork;

	@ApiModelProperty("上班时间")
	private String workInTime;

	@ApiModelProperty("司机ID")
	private Integer driverId;

	@ApiModelProperty("正在上班的司机电话")
	private String driverWorkPhone;

	@ApiModelProperty("是否有正在上班的司机: 1:是；0:否")
	private Integer isWorkDriver;

	@ApiModelProperty("是否入驻平台（1：是 0：否）")
	private Integer intoFlag;

	@ApiModelProperty("首页显示状态标识：0:未认证; 1:未绑车; 2:进行中; 3:已接单; 4:可接单; 5:暂无可接订单")
	private Integer status;

	@ApiModelProperty("行程ID")
	private Long routeId;

	@ApiModelProperty("模式 0:关闭 1:听单 2:派单")
	private Integer mode;

	@ApiModelProperty("机主电话")
	private String ownerPhone;

	@ApiModelProperty("发卡类型：1:实体卡; 2:消纳券")
	private Integer cardType;

	@ApiModelProperty("1:全职; 2:兼职")
	private Integer jobType = 1;

	@ApiModelProperty("机主订单ID")
	private String ownerOrderId;

	//是否在上班中 1:是; 0:否
	public interface isWork{
		Integer ISWORK_YES = 1;
		Integer ISWORK_NO = 0;
	}

	//是否有正在上班的司机: 1:是；0:否
	public interface isWorkDriver{
		Integer ISWORKDRIVER_YES = 1;
		Integer ISWORKDRIVER_NO = 0;
	}

	//首页显示状态标识：1:未绑车; 2:进行中; 3:已接单; 4:可接单; 5:暂无可接订单
	public interface status{
		Integer NO_CONFIRM = 0;
		Integer NO_MACHINE = 1;
		Integer HAVE_IN_ORDER = 2;
		Integer ACCEPTED_ORDER = 3;
		Integer ACCEPTABLE_ORDER = 4;
		Integer NO_ORDER = 5;
	}

	/**
	 * 1:全职; 2:兼职
	 */
	public interface isJobType{
		Integer LONGJOB = 1;
		Integer SHORTJOB = 2;
	}
}
