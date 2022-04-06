package com.weiwei.jixieche.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
@ApiModel(value="取消原因")
public class JxcCancelReason implements Serializable {
       @ApiModelProperty("主键自增")
       private Integer id;

       @ApiModelProperty("内容")
       private String content;

       @ApiModelProperty("创建人userId")
       private Integer createUserId;

       @ApiModelProperty("创建时间")
       private Date createTime;

       @ApiModelProperty("更新时间")
       private Date updateTime;

       private static final long serialVersionUID = 1L;
}