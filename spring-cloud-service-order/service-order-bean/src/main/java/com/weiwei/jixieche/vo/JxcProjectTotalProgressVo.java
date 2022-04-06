package com.weiwei.jixieche.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @Author zhong huan
 * @Date 2019-09-06 11:13
 * @Description
 */
@Data
@EqualsAndHashCode
@ApiModel(value="项目进度Vo类")
public class JxcProjectTotalProgressVo {
    @ApiModelProperty("项目名称")
    private String projectName;

    @ApiModelProperty("总预计工程量")
    private Integer totalTransportTimes;

    @ApiModelProperty("已完成工程量")
    private Integer totalCompleteTimes;

    @ApiModelProperty("总支出")
    private String totalPayAmount = "0.00";

}
