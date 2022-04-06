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
 * @Date 2019/8/13 21:32
 * @Version 2.0
 **/
@Data
@EqualsAndHashCode
@ApiModel(value="角色表")
public class JxcRole extends BasePage implements Serializable {
       @ApiModelProperty("角色id主键自增")
       private Integer id;

       @ApiModelProperty("角色名称")
       private String roleName;

       @ApiModelProperty("角色描述")
       private String description;

       private static final long serialVersionUID = 1L;
}