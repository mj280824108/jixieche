package com.weiwei.jixieche.vo;

import com.weiwei.jixieche.page.BasePage;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @Author zhong huan
 * @Date 2019-08-26 20:04
 * @Description
 */
@Data
@EqualsAndHashCode
@ApiModel(value="承租方项目管理中的机械列表")
public class JxcMachineListVo extends BasePage{

    @ApiModelProperty("机械头像")
    private String machineImg;

    @ApiModelProperty("项目ID")
    private Integer projectId;

    @ApiModelProperty("列表类型 1：待接单 2：进行中 3：已完结")
    private Integer tabFlag;

    @ApiModelProperty("机械ID")
    private Integer machineId;

    @ApiModelProperty("订单ID")
    private Long orderId;

    @ApiModelProperty("结算方式")
    private Integer accountMethod;

    @ApiModelProperty("开始时间")
    private String startDate;

    @ApiModelProperty("结束时间")
    private String endDate;

    @ApiModelProperty("承租方订单状态")
    private Integer orderState;

    @ApiModelProperty("机主订单状态")
    private Integer ownerOrderState;

    @ApiModelProperty("评分ID")
    private Long scoreId;

    @ApiModelProperty("机主订单ID")
    private Long ownerOrderId;

    @ApiModelProperty("机械方量")
    private String machineCapacity;

    @ApiModelProperty("车牌号")
    private String machineCardNo;

    @ApiModelProperty("机主姓名")
    private String ownerName;

    @ApiModelProperty("机主电话")
    private String phone;

    @ApiModelProperty("运输趟数")
    private Integer totalRouteCount=0;
}

