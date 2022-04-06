package com.weiwei.jixieche.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * @ClassName FirstLetterCityVo
 * @Description TODO
 * @Author houji
 * @Date 2019/8/12 19:19
 * @Version 1.0.1
 **/
@Data
@EqualsAndHashCode
@ApiModel(value="根据拼音首字母查询城市列表")
public class FirstLetterCityVo implements Serializable {

    @ApiModelProperty("城市id")
    private Integer id;

    @ApiModelProperty("城市名称")
    private String name;

    @ApiModelProperty("城市的Pid")
    private Integer pid;

}
