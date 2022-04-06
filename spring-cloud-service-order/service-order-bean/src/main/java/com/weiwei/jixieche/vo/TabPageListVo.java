package com.weiwei.jixieche.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @Author 钟焕
 * @Description Tab列表分页专用Vo类
 * @Date 17:33 2019-08-14
 **/
@Data
@EqualsAndHashCode
@ApiModel(value="Tab列表分页专用Vo类")
public class TabPageListVo {

    @ApiModelProperty("每页数量")
    private Integer pageSize;

    @ApiModelProperty("当前页码")
    private Integer pageNo;

    @ApiModelProperty("标记具体哪列Tab")
    private Integer flag;
}
