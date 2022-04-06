package com.weiwei.jixieche.vo;

import com.weiwei.jixieche.page.BasePage;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * @Author zhong huan
 * @Date 2019-08-23 14:08
 * @Description
 */
@Data
@EqualsAndHashCode
@ApiModel(value="承租方订单装车记录中的车辆行程记录")
public class MachineRouteRecord extends BasePage {
    @ApiModelProperty("当前所在订单的ID")
    private Long orderId;

    @ApiModelProperty("待支付趟数行程ID")
    private List<Long> routeIdList;

    @ApiModelProperty("统计的行程当天的时间(趟数记录详情查询开始时间)")
    private String startDate;

    @ApiModelProperty("趟数记录详情查询结束时间")
    private String endDate;

    @ApiModelProperty("机械ID")
    private Integer machineId;

    @ApiModelProperty("机械头像")
    private String machineImg;

    @ApiModelProperty("机主ID")
    private Integer ownerId;

    @ApiModelProperty("车牌号")
    private String machineCardNo="";

    @ApiModelProperty("趟数")
    private Integer routeCount;

    @ApiModelProperty("金额")
    private String amount;

    @ApiModelProperty("0：正常 1：施工场地漏打卡 2：消纳场地漏打卡 3：里程异常(实际里程超出或小于预计里程25%）")
    private Integer abnormalType;

    @ApiModelProperty("行程支付状态 0：行程进行中或者行程异常 1：待支付（平台已垫付）2：已支付 ")
    private Integer payState;

    @ApiModelProperty("状态标签 0：进行中 1：待支付 2：已支付 3：去支付 4：待处理 5：已申报")
    private Integer state;

}
