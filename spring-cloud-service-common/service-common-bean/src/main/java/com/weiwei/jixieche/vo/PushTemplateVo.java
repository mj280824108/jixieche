package com.weiwei.jixieche.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName PushTemplateVo
 * @Description TODO
 * @Author houji
 * @Date 2019/8/12 14:27
 * @Version 1.0.1
 **/
@Data
@EqualsAndHashCode
@ApiModel(value="推送模板Vo")
public class PushTemplateVo implements Serializable {

    @ApiModelProperty("推送的id")
    private int typeId;

    @ApiModelProperty("推送的父类id")
    private int pid;

    @ApiModelProperty("推送类型名称")
    private String typeName;

    @ApiModelProperty("消息模板内容")
    private String template;

    @ApiModelProperty("推送模板父类集合")
    private List<String> fields = new ArrayList<>();


}
