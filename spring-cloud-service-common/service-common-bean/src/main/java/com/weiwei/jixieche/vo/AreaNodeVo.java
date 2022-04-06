package com.weiwei.jixieche.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
/**
 * @ClassName 类名
 * @Description TODO
 * @Author houji
 * @Date 2019/8/13 18:08
 * @Version 2.0
 **/
@Data
@EqualsAndHashCode
@ApiModel(value="省市区节点Vo")
public class AreaNodeVo implements Serializable {

    @ApiModelProperty("省市区id主键自增")
    private int id;

    @ApiModelProperty("地区名")
    private String name;

    @ApiModelProperty("父类id")
    private int pid;

    @ApiModelProperty("地区缩减名（末尾不带区，县）")
    private String shortName;

    @ApiModelProperty("级别")
    private int level;

    @ApiModelProperty("经度")
    private double lon;

    @ApiModelProperty("纬度")
    private double lat;

    @ApiModelProperty("城市拼音首字母(北京--B)")
    private String firstLetter;

    @ApiModelProperty("省市区子节点")
    private List<AreaNodeVo> children = new ArrayList<AreaNodeVo>();

}
