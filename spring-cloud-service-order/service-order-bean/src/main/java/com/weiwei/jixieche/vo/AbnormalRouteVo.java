package com.weiwei.jixieche.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;
import java.util.Map;

/**
 * @Author zhong huan
 * @Date 2019-08-24 12:35
 * @Description
 */
@Data
@EqualsAndHashCode
@ApiModel(value="异常行程Vo类")
public class AbnormalRouteVo {
    @ApiModelProperty("行程ID")
    private Long routeId;

    @ApiModelProperty("承租方订单ID")
    private Long tenantryOrderId;

    @ApiModelProperty("行程实际金额")
    private String amount;

    @ApiModelProperty("给机主的金额")
    private String toOwnerAmount;

    @ApiModelProperty("行程实际公里数")
    private String factMileage;

    @ApiModelProperty("协商处理状态")
    private Integer abnormalStatus;

    @ApiModelProperty("协商金额")
    private String consultPrice;

    @ApiModelProperty("预计金额")
    private String estimatePrice;

    @ApiModelProperty("预计公里数")
    private String estimateMiles;

    @ApiModelProperty("项目名称")
    private String projectName;

    @ApiModelProperty("消纳场地址")
    private String siteAddress;

    @ApiModelProperty("施工地地址")
    private String projectAddress;

    @ApiModelProperty("施工场地打卡时间")
    private String startTime;

    @ApiModelProperty("消纳场打卡时间")
    private String endTime;

    @ApiModelProperty("车辆ID")
    private Integer machineId;

    @ApiModelProperty("车牌号码")
    private String machineCardNo;

    @ApiModelProperty("异常类型（0：正常 1：施工场地漏打卡 2：消纳场地漏打卡 3：实际里程超出或小于预计里程25%）")
    private Integer abnormalType;

    @ApiModelProperty("支付状态（0：异常趟或正在跑趟中 1：待支付（平台已垫付） 2：已支付）")
    private Integer payState;

    @ApiModelProperty("第三方id(用户第三方鹰眼，极光推送唯一识别码)")
    private String thirdId;

    @ApiModelProperty("车主电话")
    private String ownerPhone;

    @ApiModelProperty("车主电话")
    private String ownerName;

    @ApiModelProperty("随车电话")
    private String driverPhone;

    @ApiModelProperty("是否有交接班的标记 0：没有 1：有")
    private Integer handoverFlag = 0;

    @ApiModelProperty("两个司机查询轨迹的消息")
    private List<Map<String,Object>> handoverList;

}
