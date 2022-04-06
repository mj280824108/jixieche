package com.weiwei.jixieche.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * @ClassName OwnerWallet
 * @Description TODO
 * @Author houji
 * @Date 2019/8/30 9:42
 * @Version 1.0.1
 **/
@Data
@EqualsAndHashCode
@ApiModel(value="机主月交易明细Vo")
public class OwnerTradeRecordListVo implements Serializable {

    @ApiModelProperty("筛选条件月份(yyyy-MM)")
    private String dateTime;

    @ApiModelProperty("筛选条件(1--全部 2--微信/支付宝/银联 3--余额)")
    private Integer searchType;

    @ApiModelProperty("参数页码数")
    private Integer pageNo = 1;

    @ApiModelProperty("参数每页条数")
    private Integer pageSize = 10;

    @ApiModelProperty("总页码数")
    private Integer pageCount;

    @ApiModelProperty("机主/司机的userId")
    private Integer userId;

    @ApiModelProperty("总支出金额(单位:元)")
    private String payAmount;

    @ApiModelProperty("总收入金额(单位:元)")
    private String incomeAmount;

    @ApiModelProperty("机主交易记录list")
    private List<OwnerTradeRecordVo> tradeRecordList;

}
