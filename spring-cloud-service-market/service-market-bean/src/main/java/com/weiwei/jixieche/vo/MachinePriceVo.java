package com.weiwei.jixieche.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * @ClassName MachineTradeTypeVo
 * @Description TODO
 * @Author houji
 * @Date 2019/9/5 10:14
 * @Version 1.0.1
 **/
@Data
@EqualsAndHashCode
@ApiModel(value="价格搜索Vo")
public class MachinePriceVo implements Serializable {

    @ApiModelProperty("价格搜索类型id")
    private Integer id;

    @ApiModelProperty("价格搜索类型名称")
    private String name;

}
