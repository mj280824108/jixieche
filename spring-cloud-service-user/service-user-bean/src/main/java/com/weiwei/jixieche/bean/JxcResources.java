package com.weiwei.jixieche.bean;

import com.weiwei.jixieche.page.BasePage;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;
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
@ApiModel(value="资源(菜单)表")
public class JxcResources extends BasePage implements Serializable {
       @ApiModelProperty("菜单id,主键自增")
       private Integer id;

       @ApiModelProperty("菜单名字")
       private String name;

       @ApiModelProperty("菜单url")
       private String url;

       @ApiModelProperty("排序")
       private Integer orderby;

       @ApiModelProperty("图标")
       private String iconUrl;

       @ApiModelProperty("父类id(0--一级菜单 1--二级菜单)")
       private Integer pid;

       @ApiModelProperty("父类名称")
       private String pname;

       @ApiModelProperty("是否启用(0--启用 1--不启用)")
       private Integer isEnabled;

       @ApiModelProperty("创建时间")
       private Date createTime;

       @ApiModelProperty("修改时间")
       private Date updateTime;

       private static final long serialVersionUID = 1L;
}