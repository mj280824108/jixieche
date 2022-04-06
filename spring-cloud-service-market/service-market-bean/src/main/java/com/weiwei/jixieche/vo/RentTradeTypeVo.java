package com.weiwei.jixieche.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * @ClassName RentTradeTypeVo
 * @Description TODO
 * @Author houji
 * @Date 2019/9/5 10:18
 * @Version 1.0.1
 **/
@Data
@EqualsAndHashCode
@ApiModel(value="租赁交易类型Vo")
public class RentTradeTypeVo  implements Serializable {

    @ApiModelProperty("租赁类型id")
    private Integer id;

    @ApiModelProperty("租赁类型名称")
    private String name;
}
