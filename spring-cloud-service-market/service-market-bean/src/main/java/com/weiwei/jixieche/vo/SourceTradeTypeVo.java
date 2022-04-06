package com.weiwei.jixieche.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * @ClassName SourceTradeTypeVo
 * @Description TODO
 * @Author houji
 * @Date 2019/9/5 10:21
 * @Version 1.0.1
 **/
@Data
@EqualsAndHashCode
@ApiModel(value="资源交易类型")
public class SourceTradeTypeVo implements Serializable {

    @ApiModelProperty("资源交易类型id")
    private Integer id;

    @ApiModelProperty("资源交易类型名称")
    private String name;

}
