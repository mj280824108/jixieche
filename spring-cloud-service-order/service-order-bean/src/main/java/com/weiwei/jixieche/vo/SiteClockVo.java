package com.weiwei.jixieche.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
/**
 * @Author zhong huan
 * @Date 2019-08-31 11:45
 * @Description
 */
@Data
@EqualsAndHashCode
@ApiModel(value="消纳场打卡用的Vo类")
public class SiteClockVo {
    @ApiModelProperty("消纳场ID")
    private Integer siteId;

    @ApiModelProperty("消纳场管理员ID")
    private Integer siteAdminId;

    @ApiModelProperty("车牌号")
    private String machineCardNo;

    @ApiModelProperty("消纳券号")
    private Long siteCouponId;

    @ApiModelProperty("消纳券异常原因选择 0:正常 1:卡卷类型不一致 2:车辆方量不一致")
    private Integer couponAbnormalType;

    @ApiModelProperty("异常详细说明")
    private String abnormalCauses;

    @ApiModelProperty("异常时的现场图片(多图用逗号分隔)")
    private String abnormalImg;

    @ApiModelProperty("经度")
    private String longitude;

    @ApiModelProperty("纬度")
    private String latitude;
}
