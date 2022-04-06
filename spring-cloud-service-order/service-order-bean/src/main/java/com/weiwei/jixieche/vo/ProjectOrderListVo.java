package com.weiwei.jixieche.vo;

import com.weiwei.jixieche.page.BasePage;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @Author zhong huan
 * @Date 2019-08-21 17:11
 * @Description
 */
@Data
@EqualsAndHashCode
@ApiModel(value="承租方订单列表")
public class ProjectOrderListVo extends BasePage {
    @ApiModelProperty("列表标记 0：全部 1：待接单 2：进行中（包含已完工待支付的订单） 3：已完结 4：进行中（不包含已完工待支付的订单）")
    private Integer tabFlag;

    @ApiModelProperty("订单ID")
    private Long orderId;

    @ApiModelProperty("项目名称")
    private Integer projectId;

    @ApiModelProperty("项目名称")
    private String projectName;

    @ApiModelProperty("预计工程总量")
    private Integer estimateTransportTimes;

    @ApiModelProperty("已完成工程总量")
    private Integer completeTransportTimes = 0;

    @ApiModelProperty("今日已完成工程总量")
    private Integer todayCompleteTransportTimes = 0;

    @ApiModelProperty("结算方式 1：日结 2：周结")
    private Integer accountMethod;

    @ApiModelProperty("预计要车辆数量")
    private Integer estimateMachineCount;

    @ApiModelProperty("已接单车辆数量")
    private Integer receivedMachineCount;

    @ApiModelProperty("当天参与车辆数量")
    private Integer todayMachineCount;

    @ApiModelProperty("总共参与车辆数量")
    private Integer totalMachineCount;

    @ApiModelProperty("0：待接单 1：已取消 2：进行中 3：已完工")
    private Integer orderState;

    @ApiModelProperty("待支付标签 0：隐藏 1：显示")
    private Integer payFlag = 0;

    @ApiModelProperty("待支付金额")
    private String payAmount;

    @ApiModelProperty("待支付趟数")
    private Integer noPayRouteCount = 0;

    @ApiModelProperty("停工标记 0：正常 1：停工")
    private Integer stopWorkState;

    @ApiModelProperty("进场日期")
    private String startDate;

    @ApiModelProperty("出场日期")
    private String endDate;

    public interface OrderState{
        Integer WAIT = 0;
        Integer CANCEL = 1;
        Integer ONGING = 2;
        Integer END = 3;
    }

}
