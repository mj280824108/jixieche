package com.weiwei.jixieche.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Map;

/**
 * @ClassName ShortMsgTemplateVo
 * @Description TODO
 * @Author houji
 * @Date 2019/8/12 15:44
 * @Version 1.0.1
 **/
@Data
@EqualsAndHashCode
@ApiModel(value="短信模板参数Vo")
public class ShortMsgTemplateVo implements Serializable {

    @ApiModelProperty("手机号(必填)")
    private String phone;

    @ApiModelProperty("短信类型id(必填)")
    private Integer msgTypeId;

    @ApiModelProperty("机主或承租方(1--机主版 2--承租方版)(必填)")
    private Integer client;

    @ApiModelProperty("短信的参数(JSON字符串)")
    private String params;
}
