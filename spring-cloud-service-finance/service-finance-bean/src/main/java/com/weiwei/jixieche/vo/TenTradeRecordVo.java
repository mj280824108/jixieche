package com.weiwei.jixieche.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @ClassName OwnerWallet
 * @Description TODO
 * @Author houji
 * @Date 2019/8/30 9:42
 * @Version 1.0.1
 **/
@Data
@EqualsAndHashCode
@ApiModel(value="承租方交易明细Vo")
public class TenTradeRecordVo implements Serializable {

    @ApiModelProperty("筛选条件月份(yyyy-MM)")
    private String dateTime;

    @ApiModelProperty("交易类型(1--支付机主订单)")
    private Integer tradeType;

    @ApiModelProperty("支付方式(1--银联 2--微信 3--支付宝)")
    private Integer paymentMethod;

    @ApiModelProperty("承租方的userId")
    private Integer userId;

    @ApiModelProperty("商户唯一订单id")
    private Long id;

    @ApiModelProperty("承租方订单id")
    private Long tenantryOrderId;

    @ApiModelProperty("承租方项目名称")
    private String projectName;

    @ApiModelProperty("交易金额(单位：元)")
    private String tradeAmount;

    @ApiModelProperty("收款人的userId")
    private Integer payeeUserId;

    @ApiModelProperty("交易时间")
    private String createTime;

    @ApiModelProperty("交易状态(0--未成功  1--交易成功)")
    private Integer tradeStatus;

    /**
     * 1--承租方支付行程
     */
    public interface TenTradeType{
        Integer TEN_PAY  = 1;
    }

}
