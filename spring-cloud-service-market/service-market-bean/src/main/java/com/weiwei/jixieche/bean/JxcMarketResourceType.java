package com.weiwei.jixieche.bean;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

@Data
@EqualsAndHashCode
@ApiModel(value="市场资源类型表")
public class JxcMarketResourceType implements Serializable {

       @ApiModelProperty("页码数")
       private Integer pageNo = 1;

       @ApiModelProperty("每页条数")
       private Integer pageSize = 10;

       @ApiModelProperty("主键自增")
       private Integer id;

       @ApiModelProperty("资源名称(类型)")
       private String resourceName;

       @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
       @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
       @ApiModelProperty("创建时间")
       private Date createTime;

       @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
       @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
       @ApiModelProperty("更新时间")
       private Date updateTime;

       @ApiModelProperty("上级资源类型")
       private String code;

       @ApiModelProperty("品牌类型id")
       private Integer brandId;

       @ApiModelProperty("机械类型id")
       private Integer machineTypeId;

       @ApiModelProperty("资源图片url")
       private String imgUrl;

       @ApiModelProperty("描述")
       private String remark;

       private static final long serialVersionUID = 1L;
}