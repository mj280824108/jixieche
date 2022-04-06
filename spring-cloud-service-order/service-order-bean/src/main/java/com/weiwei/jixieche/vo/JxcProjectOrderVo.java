package com.weiwei.jixieche.vo;

import com.weiwei.jixieche.page.BasePage;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;
import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;
/**
 * @Author 钟焕
 * @Description
 * @Date 20:06 2019-08-14
 **/
@Data
@EqualsAndHashCode
@ApiModel(value="承租方订单Vo类")
public class JxcProjectOrderVo extends BasePage {
    @ApiModelProperty("承租方订单ID")
    private Long id;

    @ApiModelProperty("订单状态（0：待接单 1：取消订单 2：进行中 3：已完结）")
    private Integer orderState;

    @ApiModelProperty("项目ID")
    private Integer projectId;

    @ApiModelProperty("项目名称")
    private String projectName;

    @ApiModelProperty("市编码")
    private Integer cityCode;

    @ApiModelProperty("项目详细地址")
    private String projectAddress;

    @ApiModelProperty("项目负责人")
    private String projectLeader;

    @ApiModelProperty("项目负责人电话")
    private String leaderPhone;

    @ApiModelProperty("已接单车辆数量")
    private Integer acceptedCarCount;

    @ApiModelProperty("承租方用户ID")
    private Integer userId;

    @ApiModelProperty("土方类型（0：坏土 1：好土）")
    private Integer earthType;

    @ApiModelProperty("每车方量")
    private Integer capacity;

    @ApiModelProperty("预计工程量（趟数）")
    private Integer estimateTransportTimes;

    @ApiModelProperty("预计要车数量")
    private Integer estimateMachineCount;

    @ApiModelProperty("进场日期")
    private String startDate;

    @ApiModelProperty("出场日期")
    private String endDate;

    @ApiModelProperty("上班钟点")
    private String workStartClock;

    @ApiModelProperty("下班钟点")
    private String workEndClock;

    @ApiModelProperty("结算方式（1：日结 2：周结）")
    private Integer accountMethod;

    @ApiModelProperty("停工标记（0：正常 1：停工）")
    private Integer stopWorkState;

    @ApiModelProperty("评价按钮标记（0：隐藏 1：显示）")
    private Integer scoreFlag = 0;

    @ApiModelProperty("停工开始日期")
    private String stopWorkStart;

    @ApiModelProperty("停工截止日期")
    private String stopWorkEnd;

    @ApiModelProperty("申请停工或者提前完工的原因")
    private String endExplain;

    @ApiModelProperty("继续要车按钮标记（1：显示 0：不显示）")
    private Integer continueCarFlag;

    @ApiModelProperty("创建时间")
    private String createTime;

    @ApiModelProperty("修改时间")
    private Date updateTime;

    @ApiModelProperty("消纳场列表")
    private List<JxcSiteVo> siteList;

    @ApiModelProperty("列表查询开始时间")
    private String searchStartDate;

    @ApiModelProperty("列表查询开始时间")
    private String searchEndDate;

    @ApiModelProperty("待支付趟数")
    private Integer noPayRouteCount;

    @ApiModelProperty("正常趟数")
    private Integer normalRouteCount;

    @ApiModelProperty("异常趟数")
    private Integer abnormalRouteCount;

    @ApiModelProperty("加入列表Vo类")
    private ProjectOrderListVo projectOrderListVo;

    private static final long serialVersionUID = 1L;
}