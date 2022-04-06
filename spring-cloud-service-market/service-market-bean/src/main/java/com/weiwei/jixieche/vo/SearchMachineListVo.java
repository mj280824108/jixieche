package com.weiwei.jixieche.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * @ClassName ShopsPointNumber
 * @Description TODO
 * @Author houji
 * @Date 2019/8/22 10:43
 * @Version 1.0.1
 **/
@Data
@EqualsAndHashCode
@ApiModel(value="机械(交易)搜索Vo")
public class SearchMachineListVo implements Serializable {

    @ApiModelProperty("(搜索条件)页码数")
    private Integer pageNo = 1;

    @ApiModelProperty("(搜索条件)每页条数")
    private Integer pageSize = 10;

    @ApiModelProperty("机械标题")
    private String title;

    @ApiModelProperty("机械类型id")
    private Integer machineTypeId;

    @ApiModelProperty("发布类型(1--求购 2--出售 3--求租 4--出租 5--其他)")
    private Integer realeseType;

    @ApiModelProperty("品牌类型id")
    private Integer brandTypeId;

    @ApiModelProperty("新旧类型(1--新机 2--二手)")
    private Integer newDegreeType;

    @ApiModelProperty("新旧程度(9--九成新  8--九成新 7--七成新)，数字为几就是几成新")
    private Integer newDegreeLevel;



}
