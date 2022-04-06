package com.weiwei.jixieche.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
@ApiModel(value="司机行程中途交接存储表")
public class JxcDriverHandover implements Serializable {

       @ApiModelProperty("行程ID")
       private Long routeId;

       @ApiModelProperty("上班司机第三方ID")
       private String thirdIdUp;

       @ApiModelProperty("下班司机第三方ID")
       private String thirdIdDown;

       @ApiModelProperty("交接时间")
       private Date createTime;

       private static final long serialVersionUID = 1L;
}