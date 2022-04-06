package com.weiwei.jixieche.vo;

import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * @ClassName JpushCustomMsgVo
 * @Description 极光推送自定义消息参数
 * @Author houji
 * @Date 2019/8/12 14:13
 * @Version 1.0.1
 **/
@Data
@EqualsAndHashCode
@ApiModel(value="极光推送自定义消息")
public class JpushCustomMsgVo implements Serializable {

    @ApiModelProperty("推送Code")
    public Integer serviceCode;

    @ApiModelProperty("用户userId")
    public Integer userId;

    @ApiModelProperty("额外参数")
    public JSONObject param;
}
