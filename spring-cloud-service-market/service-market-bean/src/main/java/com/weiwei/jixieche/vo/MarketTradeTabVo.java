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
@ApiModel(value="市场交易搜索tab的Vo")
public class MarketTradeTabVo implements Serializable {

    @ApiModelProperty("页码数")
    private Integer pageNo = 1;

    @ApiModelProperty("每页条数")
    private Integer pageSize = 10;

    @ApiModelProperty("机械交易")
    private MarketMachineChildTabVo machineTrade = new MarketMachineChildTabVo();

    @ApiModelProperty("租赁交易")
    private MarketRentChildTabVo rentTrade = new MarketRentChildTabVo();

    @ApiModelProperty("资源交易")
    private MarketSourceChildTabVo sourceTrade = new MarketSourceChildTabVo();

    @ApiModelProperty("搜索关键字")
    private String title;

    @ApiModelProperty("市的id")
    private Integer cityId;

    @ApiModelProperty("资源价格(1--低到高 2--高到低)")
    private Integer sourcePriceOrder;

    @ApiModelProperty("机械价格(1--低到高 2--高到低)")
    private Integer machinePriceOrder;
}
