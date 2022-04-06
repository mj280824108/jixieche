package com.weiwei.jixieche.rabbit;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Map;

/**
 * @ClassName JxcShortMsgVo
 * @Description TODO
 * @Author houji
 * @Date 2019/5/14 14:43
 * @Version 1.0.1
 **/
@Data
@EqualsAndHashCode
@ApiModel(value="短信参数Vo")
public class JxcShortMsgVo implements Serializable {

    @ApiModelProperty("手机号(必填)")
    private String phone;

    @ApiModelProperty("短信类型id(必填)")
    private Integer msgTypeId;

    @ApiModelProperty("机主或承租方(1--机主版 2--承租方版)(必填)")
    private Integer client;

    @ApiModelProperty("短信的参数")
    private Map<String,String > params;

}
