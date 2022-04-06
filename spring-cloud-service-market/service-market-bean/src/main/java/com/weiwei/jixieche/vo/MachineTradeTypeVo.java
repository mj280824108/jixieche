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
@ApiModel(value="机械交易类型Vo")
public class MachineTradeTypeVo implements Serializable {

    @ApiModelProperty("机械交易类型id")
    private Integer id;

    @ApiModelProperty("机械交易类型名称")
    private String name;

}
