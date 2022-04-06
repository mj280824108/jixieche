package com.weiwei.jixieche.bean;

import com.weiwei.jixieche.page.BasePage;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
/**
 * @ClassName 类名
 * @Description TODO
 * @Author houji
 * @Date 2019/8/13 18:08
 * @Version 2.0
 **/
@Data
@EqualsAndHashCode
@ApiModel(value="省市区域表")
public class JxcArea extends BasePage implements Serializable {
       @ApiModelProperty("主键id")
       private Integer id;

       @ApiModelProperty("地区名")
       private String name;

       @ApiModelProperty("pid")
       private Integer pid;

       @ApiModelProperty("地区缩减名（末尾不带区，县）")
       private String shortName;

       @ApiModelProperty("级别")
       private Integer level;

       @ApiModelProperty("城市编码")
       private String cityCode;

       @ApiModelProperty("邮政编码")
       private String zipCode;

       @ApiModelProperty("区域全名")
       private String mergerName;

       @ApiModelProperty("经度")
       private String lon;

       @ApiModelProperty("纬度")
       private String lat;

       @ApiModelProperty("城市名称拼音")
       private String pinyin;

       @ApiModelProperty("0:不是直辖市，1：直辖市")
       private Boolean isDu;

       private static final long serialVersionUID = 1L;
}