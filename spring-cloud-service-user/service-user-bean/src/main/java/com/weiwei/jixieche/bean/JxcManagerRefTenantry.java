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
@ApiModel(value="承租方管理员与承租方关联表")
public class JxcManagerRefTenantry extends BasePage implements Serializable {

       @ApiModelProperty("承租方管理员id")
       private Integer tenManagerId;

       @ApiModelProperty("承租方id")
       private Integer tenId;

       @ApiModelProperty("承租方项目id")
       private Integer projectId;

       @ApiModelProperty("承租方项目名称")
       private String projectName;

       @ApiModelProperty("承租方管理员的名称")
       private String tenManagerName;

       @ApiModelProperty("是否删除(0--删除  1--不删除)")
       private String disabled;

       @ApiModelProperty("创建时间")
       private Date createTime;

       @ApiModelProperty("修改时间")
       private Date updateTime;


       private static final long serialVersionUID = 1L;
}