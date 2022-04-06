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
@ApiModel(value="安卓客户端版本")
public class JxcAndroidAppVersion extends BasePage implements Serializable {
       @ApiModelProperty("主键")
       private Integer id;

       @ApiModelProperty("版本号")
       private Integer version;

       @ApiModelProperty("客户端编号 1:机主板 2:承租方版")
       private Integer appClient;

       @ApiModelProperty("是否测试版 0:否 1:是")
       private Boolean isTest;

       @ApiModelProperty("是否强制 0:否 1:是")
       private Boolean isForce;

       @ApiModelProperty("更新描述")
       private String description;

       @ApiModelProperty("apk包大小")
       private String apkSize;

       @ApiModelProperty("创建时间")
       private Date createTime;

       private static final long serialVersionUID = 1L;
}