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
@ApiModel(value="消息推送模板")
public class JxcPushTemplate extends BasePage implements Serializable {
       @ApiModelProperty("主键")
       private Integer id;

       @ApiModelProperty("类型名称")
       private String typeName;

       @ApiModelProperty("父级类型名称")
       private Integer pid;

       @ApiModelProperty("消息模版(pid不为0时候才有)")
       private String template;

       private static final long serialVersionUID = 1L;
}