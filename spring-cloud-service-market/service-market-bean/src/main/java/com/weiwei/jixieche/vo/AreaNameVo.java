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
@ApiModel(value="省市区名称Vo")
public class AreaNameVo  implements Serializable {

    @ApiModelProperty("省id")
    private Integer pId;

    @ApiModelProperty("省名称")
    private String pName;

    @ApiModelProperty("市id")
    private Integer cId;

    @ApiModelProperty("市名称")
    private String cName;

    @ApiModelProperty("区id")
    private Integer dId;

    @ApiModelProperty("区名称")
    private String dName;

}
