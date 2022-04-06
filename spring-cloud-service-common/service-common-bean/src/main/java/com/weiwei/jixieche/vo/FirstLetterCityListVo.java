package com.weiwei.jixieche.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.List;

/**
 * @ClassName FirstLetterCityListVo
 * @Description TODO
 * @Author houji
 * @Date 2019/8/12 19:18
 * @Version 1.0.1
 **/
@Data
@EqualsAndHashCode
@ApiModel(value="大写拼音首字母城市列表")
public class FirstLetterCityListVo implements Serializable {

    @ApiModelProperty("拼音大写首字母")
    private String firstLetter;

    @ApiModelProperty("拼音大写首字母城市(市)列表")
    private List<FirstLetterCityVo> firstLetterCityVoList;
}
