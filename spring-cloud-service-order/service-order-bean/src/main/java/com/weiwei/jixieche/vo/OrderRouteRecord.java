package com.weiwei.jixieche.vo;

import com.weiwei.jixieche.page.PageViewDatatable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * @Author zhong huan
 * @Date 2019-08-23 13:55
 * @Description
 */
@Data
@EqualsAndHashCode
@ApiModel(value="承租方订单装车记录")
public class OrderRouteRecord {
    @ApiModelProperty("订单ID")
    private Long orderId;

    @ApiModelProperty("0：待接单 1：已取消 2：进行中 3：已完工")
    private Integer orderState;

    @ApiModelProperty("待支付标签 0：隐藏 1：显示")
    private Integer payFlag = 0;

    @ApiModelProperty("停工标记 0：正常 1：停工")
    private Integer stopWorkState;

    @ApiModelProperty("结算方式（1：日结 2：周结）")
    private Integer accountMethod;

    @ApiModelProperty("项目名称")
    private String projectName = "";

    @ApiModelProperty("项目Id")
    private Integer projectId;

    @ApiModelProperty("总趟数")
    private Integer totalRouteCount;

    @ApiModelProperty("已支付")
    private String totalPay ="0.00";

    @ApiModelProperty("待支付")
    private String totalNoPay ="0.00";

    @ApiModelProperty("待支付趟数")
    private Integer noPayRouteCount;

    @ApiModelProperty("正常趟数")
    private Integer normalRouteCount;

    @ApiModelProperty("异常趟数")
    private Integer abnormalRouteCount;

    @ApiModelProperty("列表查询开始时间")
    private String searchStartDate;

    @ApiModelProperty("列表查询开始时间")
    private String searchEndDate;

    @ApiModelProperty("待支付趟数记录")
    private List<MachineRouteRecord> noPayList;

    @ApiModelProperty("待支付趟数行程ID")
    private List<Long> routeIdList;

    @ApiModelProperty("正常趟数记录的分页结果")
    private PageViewDatatable<MachineRouteRecord> normalList = new PageViewDatatable<>();

    @ApiModelProperty("异常趟数记录的分页结果")
    private PageViewDatatable<MachineRouteRecord> abnormalList = new PageViewDatatable<>();
}
