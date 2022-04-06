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
@ApiModel(value="租赁交易子tab搜索Vo")
public class MarketRentChildTabVo implements Serializable {

    @ApiModelProperty("3--机械求租 4--机械出租")
    private Integer rentType;

    @ApiModelProperty("机械类型")
    private Integer machineType;

    @ApiModelProperty("日结/周结/月结(1--包月  2--包天 3--小时)")
    private Integer rentLeaseType;

}
