package com.weiwei.jixieche.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName AreaNameVo
 * @Description TODO
 * @Author houji
 * @Date 2019/9/6 11:05
 * @Version 1.0.1
 **/
@Data
@EqualsAndHashCode
@ApiModel(value="机械父品牌Vo")
public class MachineTypeVo implements Serializable {

    @ApiModelProperty("主键自增")
    private Integer pId;

    @ApiModelProperty("机械类型名称")
    private String pName;

    @ApiModelProperty("机械子品牌")
    List<MachineTypeChildVo> childVoList = new ArrayList<>();

}
