package com.weiwei.jixieche.vo;

import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * @ClassName ShortMsgVo
 * @Description TODO
 * @Author houji
 * @Date 2019/8/23 16:38
 * @Version 1.0.1
 **/
@Data
@EqualsAndHashCode
@ApiModel(value="阿里发送短信参数Vo")
public class ShortMsgVo implements Serializable {

    @ApiModelProperty("阿里短信模版CODE")
    private String templateCode;

    @ApiModelProperty("短信参数(JSON字符串)")
    private String templateParams;

}
