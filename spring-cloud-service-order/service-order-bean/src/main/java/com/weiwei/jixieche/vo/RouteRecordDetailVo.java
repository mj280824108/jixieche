package com.weiwei.jixieche.vo;

import com.weiwei.jixieche.page.BasePage;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;
import java.util.Map;

/**
 * @Author zhong huan
 * @Date 2019-08-26 14:37
 * @Description
 */
@Data
@EqualsAndHashCode
@ApiModel(value="机械行程记录详情")
public class RouteRecordDetailVo extends BasePage {
    @ApiModelProperty("行程ID")
    private Long routeId;

    @ApiModelProperty("机械ID")
    private Integer machineId;

    @ApiModelProperty("机械头像")
    private String machineImg;

    @ApiModelProperty("车牌号")
    private String machineCardNo;

    @ApiModelProperty("项目名称")
    private String projectName;

    @ApiModelProperty("司机的第三方ID（用于显示轨迹）")
    private String thirdId;

    @ApiModelProperty("施工场地打卡时间")
    private String startTime;

    @ApiModelProperty("消纳场打卡时间")
    private String endTime;

    @ApiModelProperty("施工场地详细地址")
    private String projectAddress;

    @ApiModelProperty("消纳场详细地址")
    private String siteAddress;

    @ApiModelProperty("0：正常 1：施工场地漏打卡 2：消纳场地漏打卡 3：里程异常(实际里程超出或小于预计里程25%）")
    private Integer abnormalType;

    @ApiModelProperty("状态标签 0：进行中 1：待支付 2：已支付 3：去支付 4：申报异常 5：已申报")
    private Integer state;

    @ApiModelProperty("异常处理状态 0：未处理 1：已处理")
    private Integer abnormalStatus;

    @ApiModelProperty("支付状态 0：进行中或者异常 1：待支付（平台已垫付）2：已支付")
    private Integer payState;

    @ApiModelProperty("待支付金额(协商金额)")
    private String factAmount;

    @ApiModelProperty("实际里程")
    private String factMileage;

    @ApiModelProperty("预计里程")
    private String estimateMiles;

    @ApiModelProperty("预计金额")
    private String estimatePrice;

    @ApiModelProperty("机主电话")
    private String ownerName;

    @ApiModelProperty("机主电话")
    private String ownerPhone;

    @ApiModelProperty("此次行程的司机的电话")
    private String driverPhone;

    @ApiModelProperty("是否有交接班的标记 0：没有 1：有")
    private Integer handoverFlag = 0;

    @ApiModelProperty("两个司机查询轨迹的消息")
    private List<Map<String,Object>> handoverList;

}
