package com.weiwei.jixieche.bean;

import com.weiwei.jixieche.page.BasePage;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;
import lombok.EqualsAndHashCode;
/**
 * @ClassName UserRegisterVo
 * @Description TODO
 * @Author houji
 * @Date 2019/8/13 18:08
 * @Version 2.0
 **/
@Data
@EqualsAndHashCode
@ApiModel(value="开放城市车载容量表")
public class JxcOpenedCity extends BasePage implements Serializable {
       @ApiModelProperty("主键自增")
       private Integer id;

       @ApiModelProperty("省id")
       private Integer provinceId;

       @ApiModelProperty("城市id")
       private Integer cityId;

       @ApiModelProperty("省市名称")
       private String cityName;

       @ApiModelProperty("区id")
       private Integer districtId;

       @ApiModelProperty("区名称")
       private String districtName;

       @ApiModelProperty("是否启用(0--未启用 1--启用)")
       private Integer disabled;

       @ApiModelProperty("创建时间")
       private Date createTime;

       @ApiModelProperty("修改时间")
       private Date updateTime;

       private static final long serialVersionUID = 1L;
}