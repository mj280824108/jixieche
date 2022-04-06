package com.weiwei.jixieche.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @ClassName OwnerWallet
 * @Description TODO
 * @Author houji
 * @Date 2019/8/30 9:42
 * @Version 1.0.1
 **/
@Data
@EqualsAndHashCode
@ApiModel(value="机主交易明细Vo")
public class OwnerTradeRecordVo implements Serializable {

    @ApiModelProperty("筛选条件月份(yyyy-MM)")
    private String dateTime;

    @ApiModelProperty("筛选条件(1--全部 2--微信/支付宝/银联 3--余额)")
    private Integer searchType;

    @ApiModelProperty("支付方式(支付方式(0--平台垫付 1--银联 2--微信 3--支付宝))")
    private Integer paymentMethod;

    @ApiModelProperty("机主/司机的userId")
    private Integer userId;

    @ApiModelProperty("商户唯一订单id")
    private Long id;

    @ApiModelProperty("交易类型(1--平台垫付 2--机主提现 3--机主支付工资  4--司机提现)")
    private Integer tradeType;

    @ApiModelProperty("交易状态(0--失败  1--成功)")
    private Integer tradeStatus;

    @ApiModelProperty("承租方订单id")
    private Long tenantryOrderId;

    @ApiModelProperty("机主收入的项目名称")
    private String projectName;

    @ApiModelProperty("付款人userId")
    private Integer payerUserId;

    @ApiModelProperty("付款人用户名")
    private String payerUserName;

    @ApiModelProperty("收款人userId")
    private Integer payeeUserId;

    @ApiModelProperty("收款人用户名")
    private String payeeUserName;

    @ApiModelProperty("付款人头像")
    private String payerHeadImg;

    @ApiModelProperty("收款人头像")
    private String payeeHeadImg;

    @ApiModelProperty("交易金额(单位：元)")
    private String tradeAmount;

    @ApiModelProperty("提现实际到账金额(单位：分)")
    private String actualAmount;

    @ApiModelProperty("提现银行卡id")
    private Integer bankCardId;

    @ApiModelProperty("银行卡信息")
    private String bankCardInfo;

    @ApiModelProperty("银行卡logo")
    private String logo;

    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty("交易时间")
    private String createTime;

    /**
     * 1--行程收入 2--提现 3--支付工资
     */
    public interface OwnerTradeType{
        //1--行程收入 2--提现 3--支付工资
        Integer GET_MONEY  = 1;
        Integer TAKE_MONEY  = 2;
        Integer OWNER_PAY  = 3;
        Integer DRIVER_TAKE =4;
    }

}
