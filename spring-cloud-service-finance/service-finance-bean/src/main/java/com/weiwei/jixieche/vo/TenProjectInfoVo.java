package com.weiwei.jixieche.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * @ClassName TenProjectInfoVo
 * @Description TODO
 * @Author houji
 * @Date 2019/9/3 11:25
 * @Version 1.0.1
 **/
@Data
@EqualsAndHashCode
@ApiModel(value="承租方项目信息Vo")
public class TenProjectInfoVo implements Serializable {

    @ApiModelProperty("承租方项目id")
    private Integer projectId;

    @ApiModelProperty("承租方项目名称")
    private String projectName;

    @ApiModelProperty("承租方项目订单id")
    private Long tenOrderId;

}
