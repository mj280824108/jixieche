package com.weiwei.jixieche.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.List;
/**
 * @ClassName 类名
 * @Description TODO
 * @Author houji
 * @Date 2019/8/13 18:08
 * @Version 2.0
 **/
@Data
@EqualsAndHashCode
@ApiModel(value="小米推送参数Vo")
public class PushXmVo implements Serializable {

       @ApiModelProperty("(必填)用户类型(0--承租方 1--机主)")
       private Integer userType;

       @ApiModelProperty("(必填)第三方id集合")
       private List<String> thirdIdList;

       @ApiModelProperty("(必填)设置在通知栏展示的通知的标题")
       private String title;

       @ApiModelProperty("(必填)设置在通知栏展示的通知描述, 不允许全是空白字符")
       private String description;

       public interface Type{
              Integer ten = 0;
              Integer owner = 1;
       }

       private static final long serialVersionUID = 1L;
}