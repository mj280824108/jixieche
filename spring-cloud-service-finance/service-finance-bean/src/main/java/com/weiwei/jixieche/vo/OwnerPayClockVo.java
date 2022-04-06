package com.weiwei.jixieche.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * @ClassName OwnerTradePayVo
 * @Description TODO
 * @Author houji
 * @Date 2019/8/31 14:00
 * @Version 1.0.1
 **/
@Data
@EqualsAndHashCode
@ApiModel(value="机主支付台班金额Vo")
public class OwnerPayClockVo implements Serializable {

    @ApiModelProperty("打卡id")
    private Long clockId;

    @ApiModelProperty("打卡id")
    private Integer id;

    @ApiModelProperty("打卡的实际金额")
    private Integer factAmount;

    @ApiModelProperty("打卡的司机id")
    private Integer driverId;
}
