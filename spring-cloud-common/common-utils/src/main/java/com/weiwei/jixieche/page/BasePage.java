package com.weiwei.jixieche.page;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Data
@EqualsAndHashCode
@ApiModel(value="基础分页父类")
public class BasePage implements Serializable {

    @ApiModelProperty("页码数")
    private Integer pageNo = 1;

    @ApiModelProperty("每页条数")
    private Integer pageSize = 10;

}
