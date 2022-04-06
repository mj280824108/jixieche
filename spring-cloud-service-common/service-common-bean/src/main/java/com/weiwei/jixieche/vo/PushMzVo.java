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
@ApiModel(value="魅族推送参数Vo")
public class PushMzVo implements Serializable {

       @ApiModelProperty("推送类型(0--承租方推送  1--机主推送)")
       private Integer pushType;

       @ApiModelProperty("推送第三方id集合")
       private List<String> thirdIdList;

       @ApiModelProperty("通知标题")
       private String title;

       @ApiModelProperty("通知内容")
       private String content;

       public interface  pushType{
              Integer ten = 0;
              Integer owner =1;
       }

       private static final long serialVersionUID = 1L;
}