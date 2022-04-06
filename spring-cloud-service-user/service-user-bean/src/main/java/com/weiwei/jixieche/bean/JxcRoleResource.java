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
@ApiModel(value="角色资源表")
public class JxcRoleResource extends BasePage implements Serializable {
       @ApiModelProperty("角色资源id，主键自增")
       private Integer id;

       @ApiModelProperty("角色id")
       private Integer roleId;

       @ApiModelProperty("资源id")
       private Integer resourceId;

       @ApiModelProperty("创建时间")
       private Integer createTime;

       @ApiModelProperty("修改时间")
       private Integer updateTime;

       private static final long serialVersionUID = 1L;
}