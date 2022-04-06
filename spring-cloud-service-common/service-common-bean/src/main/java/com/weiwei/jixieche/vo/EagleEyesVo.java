package com.weiwei.jixieche.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * @ClassName EagleEyesVo
 * @Description TODO
 * @Author houji
 * @Date 2019/5/27 10:23
 * @Version 1.0.1
 **/
@Data
@EqualsAndHashCode
@ApiModel(value="鹰眼轨迹服务终端管理请求参数Vo")
public class EagleEyesVo implements Serializable {

    @ApiModelProperty("客户端请求编码")
    private String client;

    @ApiModelProperty("鹰眼第三方id，如果是多个第三方id用逗号分隔开来(eg:张三,李四)")
    private String entityName;

    @ApiModelProperty("UNIX 时间戳")
    private long startTime;

    @ApiModelProperty("UNIX 时间戳(起始时间不能大于结束时间,并且区间不能大于24小时)")
    private long endTime;

    @ApiModelProperty("客户端请求编码")
    private String actionCode;

    public interface actionCode{
        String ADDENTITY  = "1";
        String CHECKENTITYEXISTS = "2";
        String GETDISTANCE = "3";
    }

}
