package com.weiwei.jixieche.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * @ClassName TenTradeRecordListVo
 * @Description TODO
 * @Author houji
 * @Date 2019/9/4 9:32
 * @Version 1.0.1
 **/
@Data
@EqualsAndHashCode
@ApiModel(value="承租方交易记录")
public class TenTradeRecordListVo implements Serializable {

    @ApiModelProperty("筛选条件月份(yyyy-MM)")
    private String dateTime;

    @ApiModelProperty("参数页码数")
    private Integer pageNo = 1;

    @ApiModelProperty("参数每页条数")
    private Integer pageSize = 10;

    @ApiModelProperty("总页码数")
    private Integer pageCount;

    @ApiModelProperty("总支出金额(单位:元)")
    private String payAmount;

    @ApiModelProperty("月支出金额(单位:元)")
    private String monthPayAmount;

    @ApiModelProperty("承租方userId")
    private Integer userId;

    @ApiModelProperty("机主交易记录list")
    private List<TenTradeRecordVo> tradeRecordList;
}
