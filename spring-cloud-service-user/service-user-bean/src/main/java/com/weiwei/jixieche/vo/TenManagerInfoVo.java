package com.weiwei.jixieche.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * @ClassName TenManagerInfoVo
 * @Description TODO
 * @Author houji
 * @Date 2019/9/3 10:02
 * @Version 1.0.1
 **/
@Data
@EqualsAndHashCode
@ApiModel(value="查询承租方管理员信息详情Vo")
public class TenManagerInfoVo implements Serializable {

    @ApiModelProperty("承租方管理员id")
    private Integer tenManagerId;

    @ApiModelProperty("承租方管理员的名称")
    private String tenManagerName;

    @ApiModelProperty("承租方项目id")
    private Integer projectId;

    @ApiModelProperty("承租方项目名称")
    private String projectName;

    @ApiModelProperty("承租方管理员的第三方id")
    private String thirdId;

    @ApiModelProperty("管理员头像")
    private String headImg;

    @ApiModelProperty("管理员手机号")
    private String phone;

}
