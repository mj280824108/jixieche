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
@ApiModel(value="方量字典表")
public class JxcCapacity extends BasePage implements Serializable {
       @ApiModelProperty("主键自增")
       private Integer id;

       @ApiModelProperty("省id")
       private Integer provinceId;

       @ApiModelProperty("省名称")
       private String provinceName;

       @ApiModelProperty("市id")
       private Integer cityId;

       @ApiModelProperty("市名称")
       private String cityName;

       @ApiModelProperty("容量")
       private Integer capacity;

       @ApiModelProperty("是否启用(0--启用 1--关闭)")
       private Integer disabled;

       @ApiModelProperty("创建时间")
       private Date createTime;

       @ApiModelProperty("更新时间")
       private Date updateTime;

       private static final long serialVersionUID = 1L;
}