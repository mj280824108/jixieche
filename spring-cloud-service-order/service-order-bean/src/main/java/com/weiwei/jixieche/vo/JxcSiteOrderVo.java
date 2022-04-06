package com.weiwei.jixieche.vo;

import com.weiwei.jixieche.bean.JxcSiteCouponOrder;
import com.weiwei.jixieche.page.BasePage;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.List;

/**
 * @Author zhong huan
 * @Date 2019-08-19 19:30
 * @Description
 */
@Data
@EqualsAndHashCode
@ApiModel(value="消纳券订单Vo类")
public class JxcSiteOrderVo extends BasePage {
    @ApiModelProperty("订单编号")
    private Long id;

    @ApiModelProperty("所属消纳场id")
    private Integer siteId;

    @ApiModelProperty("承租方购卡人id")
    private Integer tenantryId;

    @ApiModelProperty("订单状态 (0:未处理订单 1:已处理订单)")
    private Integer orderFlag;

    @ApiModelProperty("订单创建时间")
    private String createTime;

    @ApiModelProperty("是否删除、逻辑删除标识。0：正常 1：已删除")
    private Integer delFlag;

    @ApiModelProperty("订单总价")
    private String totalAmount;

    @ApiModelProperty("订单实付金额")
    private BigDecimal realAmount;

    @ApiModelProperty("消纳券订单详情")
    private List<JxcSiteCouponOrderVo> siteCouponOrderList;

    @ApiModelProperty("消纳券类型 (0:坏土 1:好土)")
    private Integer couponType;

}
