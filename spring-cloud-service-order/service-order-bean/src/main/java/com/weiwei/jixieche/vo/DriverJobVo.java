package com.weiwei.jixieche.vo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 兼职和全职司机招聘查询条件
 * @author liuy
 * @date 2019-08-16 10:03
 */
@Api("兼职和全职司机招聘查询条件")
@Data
@EqualsAndHashCode
public class DriverJobVo {

	@ApiModelProperty("机主用户ID")
	private Integer ownerUserId;

	@ApiModelProperty("司机用户ID")
	private Integer driverId;

	@ApiModelProperty("最后一条记录ID")
	private Integer lastPageLastId;

	@ApiModelProperty("每页显示条数")
	private Integer pageSize = 10;

	@ApiModelProperty("页码数")
	private Integer pageNo = 1;

	/**
	 * 短期司机查询条件
	 */
	@ApiModelProperty("要求司机评分 排序方式 1.从低到高 2.从高到底")
	private Integer socreSortType;

	@ApiModelProperty("开工时间:格式yyyy-MM-dd")
	private String startDate;

	@ApiModelProperty("结束时间:格式yyyy-MM-dd")
	private String endDate;


	/**
	 * 长期司机查询条件
	 */
	@ApiModelProperty("薪资待遇类型(薪资结算方式；0：按月结，1：面议)")
	private Integer payment;

	@ApiModelProperty("月薪起始金额")
	private Integer startMoney;

	@ApiModelProperty("月薪结束金额")
	private Integer endMoney;

	@ApiModelProperty("区域id")
	private Integer areaId;

}
