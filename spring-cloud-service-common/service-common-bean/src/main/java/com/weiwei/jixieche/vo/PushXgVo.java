package com.weiwei.jixieche.vo;

import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
/**
 * @ClassName 类名
 * @Description TODO
 * @Author houji
 * @Date 2019/8/13 18:08
 * @Version 2.0
 **/
@Data
@EqualsAndHashCode
@ApiModel(value="信鸽推送参数Vo")
public class PushXgVo implements Serializable {

       @ApiModelProperty("第三方id")
       private String thirdId;

       @ApiModelProperty("通知标题")
       private String title;

       @ApiModelProperty("通知内容")
       private String content;

       @ApiModelProperty("通知或消息自定义参数")
       private JSONObject customContent;

       private static final long serialVersionUID = 1L;
}