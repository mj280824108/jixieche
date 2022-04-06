package com.weiwei.jixieche.vo;

import com.weiwei.jixieche.page.BasePage;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @Author zhong huan
 * @Date 2019-08-21 15:05
 * @Description
 */
@Data
@EqualsAndHashCode
@ApiModel(value="消纳券Vo类")
public class JxcSiteCouponVo extends BasePage {
    @ApiModelProperty("电子券号")
    private Long id;

    @ApiModelProperty("方量")
    private Integer capacity;

    @ApiModelProperty("消纳券状态 (0:未使用 1:待核销 2:已核销 3:已退卡)")
    private Integer couponFlag;

    @ApiModelProperty("卡券类型 0：坏土 1：好土")
    private Integer couponType;

    @ApiModelProperty("消纳场名称")
    private String siteName;

    @ApiModelProperty("使用该券的机械ID")
    private Integer machineId;

    @ApiModelProperty("车牌号")
    private String machineCardNo;

    @ApiModelProperty("异常原因 (0:正常 1:卡卷类型不一致 2:车辆方量不一致)")
    private Integer abnormalType;

    @ApiModelProperty("详细原因")
    private String abnormalCauses;

    @ApiModelProperty("异常时的现场图片(多图用逗号分隔)")
    private String abnormalImg;
}
