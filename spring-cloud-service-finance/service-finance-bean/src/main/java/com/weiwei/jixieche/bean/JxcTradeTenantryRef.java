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
@ApiModel(value="承租方支付趟数关联表")
public class JxcTradeTenantryRef extends BasePage implements Serializable {
       @ApiModelProperty("支付的唯一订单号(jxc_trade_tenantry表外键)")
       private Long id;

       @ApiModelProperty("承租方支付车辆行程id")
       private Long routeId;

       @ApiModelProperty("创建时间")
       private Date createTime;

       @ApiModelProperty("修改时间")
       private Date updateTime;

       private static final long serialVersionUID = 1L;
}