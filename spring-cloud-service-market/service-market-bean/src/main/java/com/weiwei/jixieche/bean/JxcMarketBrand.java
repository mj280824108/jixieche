package com.weiwei.jixieche.bean;

import com.weiwei.jixieche.page.BasePage;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
@ApiModel(value="市场品牌字典表")
public class JxcMarketBrand extends BasePage implements Serializable {
       @ApiModelProperty("主键自增")
       private Integer id;

       @ApiModelProperty("品牌名称(型号)")
       private String brandName;

       @ApiModelProperty("品牌所属公司")
       private String brandCompany;

       @ApiModelProperty("品牌公司电话")
       private String brandCompanyPhone;

       @ApiModelProperty("创建时间")
       private Date createTime;

       @ApiModelProperty("更新时间")
       private Date updateTime;

       @ApiModelProperty("上级品牌类型")
       private String code;

       @ApiModelProperty("所属机械类型id")
       private Integer machineTypeId;

       @ApiModelProperty("描述")
       private String remark;

       private static final long serialVersionUID = 1L;
}