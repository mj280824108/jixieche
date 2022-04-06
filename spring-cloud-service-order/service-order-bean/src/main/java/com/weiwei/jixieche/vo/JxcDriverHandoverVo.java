package com.weiwei.jixieche.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * @Author zhong huan
 * @Date 2019-08-27 19:33
 * @Description
 */
@Data
@EqualsAndHashCode
@ApiModel(value="子账号与机主关联关系表")
public class JxcDriverHandoverVo {
    @ApiModelProperty("行程ID")
    private Long routeId;

    @ApiModelProperty("下班司机第三方id(用户第三方鹰眼，极光推送唯一识别码)")
    private String thirdIdDown;

    @ApiModelProperty("上班司机第三方id(用户第三方鹰眼，极光推送唯一识别码)")
    private String thirdIdUp;

    @ApiModelProperty("交接时间字符串")
    private String createTimeStr;

    @ApiModelProperty("交接时间字符串")
    private Date createTime;

}
