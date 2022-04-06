package com.weiwei.jixieche.rabbit;

import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * @ClassName 极光推送消息模板vo
 * @Description TODO
 * @Author houji
 * @Date 2019/5/13 16:30
 * @Version 1.0.1
 **/
@Data
@EqualsAndHashCode
@ApiModel(value="极光推送消息参数模板Vo")
public class JpushTemplateVo  implements Serializable {

    @ApiModelProperty("app编号 0:全体 1:机主 2:承租方")
    private int appClient;

    @ApiModelProperty("是否全体发送")
    private boolean sendToAll = false;

    @ApiModelProperty("接受推送的别名数组(逗号分割)")
    private String aliases;

    @ApiModelProperty("模版编号")
    private int templateCode;

    @ApiModelProperty("额外参数")
    private JSONObject params;
}
