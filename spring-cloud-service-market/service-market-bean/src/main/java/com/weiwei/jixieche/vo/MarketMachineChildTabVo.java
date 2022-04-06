package com.weiwei.jixieche.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * @ClassName MarketTabListVo
 * @Description TODO
 * @Author houji
 * @Date 2019/9/4 19:35
 * @Version 1.0.1
 **/
@Data
@EqualsAndHashCode
@ApiModel(value="机械交易筛选搜索Vo")
public class MarketMachineChildTabVo implements Serializable {

    @ApiModelProperty("1--机械求购 2--机械出售")
    private Integer machineTradeType;

    @ApiModelProperty("机械类型")
    private Integer machineType;

    @ApiModelProperty("新旧类型(1--新机 2--二手)")
    private Integer machineNewOld;

    @ApiModelProperty("筛选")
    private MarketMachineChildTabSwitchVo switchVo = new MarketMachineChildTabSwitchVo();

}
