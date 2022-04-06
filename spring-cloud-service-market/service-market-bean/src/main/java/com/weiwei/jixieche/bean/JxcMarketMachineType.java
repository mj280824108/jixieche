package com.weiwei.jixieche.bean;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.weiwei.jixieche.page.BasePage;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

@Data
@EqualsAndHashCode
@ApiModel(value="市场机械类型字典表")
public class JxcMarketMachineType extends BasePage implements Serializable {

       @ApiModelProperty("主键自增")
       private Integer id;

       @ApiModelProperty("上级机械类型(0--本身是上级机械 1--土方机械 2--路面机械 3--起重机械 4--混凝土机械 5--矿山机械 6--特种设备)")
       private String code;

       @ApiModelProperty("机械类型名称")
       private String machineName;

       @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
       @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
       @ApiModelProperty("创建时间")
       private Date createTime;

       @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
       @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
       @ApiModelProperty("修改时间")
       private Date updateTime;

       @ApiModelProperty("机械图片")
       private String imgUrl;

       @ApiModelProperty("描述")
       private String remark;

       private static final long serialVersionUID = 1L;
}