package com.weiwei.jixieche.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;


/**
 * @Author zhong huan
 * @Date 2019-08-20 20:16
 * @Description
 */
@Data
@EqualsAndHashCode
@ApiModel(value="消纳场券订单详情Vo")
public class SiteOrderDetailVo {
    @ApiModelProperty("订单编号")
    private Long orderId;

    @ApiModelProperty("消纳场ID")
    private Integer siteId;

    @ApiModelProperty("合计金额")
    private BigDecimal totalAmount = BigDecimal.ZERO;

    @ApiModelProperty("实付金额")
    private BigDecimal realAmount = BigDecimal.ZERO;

    @ApiModelProperty("发券时间")
    private String giveOutTime = "";

    @ApiModelProperty("负责人姓名")
    private String shoulder;

    @ApiModelProperty("联系方式")
    private String phone;

    @ApiModelProperty("订单状态 (0:未处理订单 1:已处理订单 2:取消订单)")
    private Integer orderFlag;

    @ApiModelProperty("订单创建时间")
    private String createTime;

    @ApiModelProperty("银行账户名称")
    private String bankAccountName;

    @ApiModelProperty("银行账号")
    private String bankAccountCode;

    @ApiModelProperty("消纳券订单详情")
    private Map<String,Object> siteCouponOrderList;


    public interface orderFlag{
        Integer NO_DEAL = 0;
        Integer YES_DEAL = 1;
        Integer CANCEL = 2;
    }





}
