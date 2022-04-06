package com.weiwei.jixieche.vo;

import com.weiwei.jixieche.page.BasePage;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @Author zhong huan
 * @Date 2019-10-09 17:13
 * @Description
 */
@ApiModel("手动发券用的Vo")
@Data
@EqualsAndHashCode
public class JxcSendSiteCouponVo extends BasePage{
    @ApiModelProperty("1:待发卡列表 2：发放记录")
    private Integer tabFlag;

    @ApiModelProperty("承租方ID")
    private Integer tenantryId;

    @ApiModelProperty("项目ID")
    private Integer projectId;

    @ApiModelProperty("承租方订单ID")
    private Long tenantryOrderId;

    @ApiModelProperty("机主订单ID")
    private Long ownerOrderId;

    @ApiModelProperty("机械ID")
    private Integer machineId;

    @ApiModelProperty("车牌号")
    private String machineCardNo;

    @ApiModelProperty("消纳场名称")
    private String siteName;

    @ApiModelProperty("机主姓名")
    private String ownerName;

    @ApiModelProperty("发卡时间")
    private String pairTime;

    @ApiModelProperty("卡券类型 0：坏土 1：好土")
    private Integer couponType;

    @ApiModelProperty("卡券状态 1：待核销 2：已核销")
    private Integer couponFlag;

}
