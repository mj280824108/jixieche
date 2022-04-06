package com.weiwei.jixieche.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * @ClassName AreaNameVo
 * @Description TODO
 * @Author houji
 * @Date 2019/9/6 11:05
 * @Version 1.0.1
 **/
@Data
@EqualsAndHashCode
@ApiModel(value="机械子品牌Vo")
public class MachineTypeChildVo implements Serializable {

    @ApiModelProperty("主键自增")
    private Integer id;

    @ApiModelProperty("机械类型名称")
    private String machineName;

    @ApiModelProperty("机械图片")
    private String imgUrl;

    @ApiModelProperty("上级机械id")
    private Integer pId;

    @ApiModelProperty("上级机械类型名称")
    private String pName;

}
