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
@ApiModel(value="华为推送Vo")
public class PushKitVo implements Serializable {

       @ApiModelProperty("(必填)华为设备token集合")
       private List<String> deviceTokensList;

       @ApiModelProperty("(必填)推送标题")
       private String title;

       @ApiModelProperty("(必填)推送内容")
       private String content;

       private static final long serialVersionUID = 1L;
}