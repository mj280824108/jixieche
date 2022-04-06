package com.weiwei.jixieche.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.HashSet;
import java.util.Set;

/**
 * @ClassName ShortMsgTemplate
 * @Description TODO
 * @Author houji
 * @Date 2019/8/12 16:08
 * @Version 1.0.1
 **/
@Data
@EqualsAndHashCode
@ApiModel(value = "发送短信参数模板")
public class ShortMsgTemplate {

    @ApiModelProperty("模板id")
    private int templateId;

    @ApiModelProperty("短信类型")
    private String msgType;

    @ApiModelProperty("模板内容")
    private String templateBody;

    @ApiModelProperty("操作类型")
    private Integer operationType;

    @ApiModelProperty("模板参数")
    private Set<String> templateFields = new HashSet<>();

}
