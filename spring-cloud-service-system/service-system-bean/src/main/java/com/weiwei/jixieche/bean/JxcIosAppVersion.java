package com.weiwei.jixieche.bean;

import com.weiwei.jixieche.page.BasePage;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;
import lombok.EqualsAndHashCode;
/**
 * @ClassName UserRegisterVo
 * @Description TODO
 * @Author houji
 * @Date 2019/8/13 18:08
 * @Version 2.0
 **/
@Data
@EqualsAndHashCode
@ApiModel(value="ios版app控制表")
public class JxcIosAppVersion extends BasePage implements Serializable {
       @ApiModelProperty("id")
       private Integer id;

       @ApiModelProperty("版本号")
       private String version;

       @ApiModelProperty("版本号第一位")
       private Integer v1;

       @ApiModelProperty("版本号第二位")
       private Integer v2;

       @ApiModelProperty("版本号第三位")
       private Integer v3;

       @ApiModelProperty("1:机主板,2:承租方版")
       private Integer appClient;

       @ApiModelProperty("0:正式版,1:测试版")
       private Boolean isTest;

       @ApiModelProperty("是否强制更新")
       private Boolean isForce;

       @ApiModelProperty("向前推能够兼容的最早版本(不强制更新时应该为最近一个强制更新版本及其之后的某一个版本)")
       private String compatibleVersion;

       private String description;

       @ApiModelProperty("创建时间")
       private Date createDate;

       private static final long serialVersionUID = 1L;
}