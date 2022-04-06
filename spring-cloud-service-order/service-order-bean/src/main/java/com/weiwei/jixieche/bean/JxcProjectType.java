package com.weiwei.jixieche.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
@ApiModel(value="工程类型")
public class JxcProjectType implements Serializable {
       @ApiModelProperty("工程类型id")
       private Integer typeId;

       @ApiModelProperty("工程类型名")
       private String typeName;

       private static final long serialVersionUID = 1L;
}