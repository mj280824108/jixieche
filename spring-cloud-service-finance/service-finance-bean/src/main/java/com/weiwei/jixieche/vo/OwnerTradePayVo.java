package com.weiwei.jixieche.vo;

import com.weiwei.jixieche.page.PageViewDatatable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.models.auth.In;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @ClassName OwnerTradePayVo
 * @Description TODO
 * @Author houji
 * @Date 2019/8/31 14:00
 * @Version 1.0.1
 **/
@Data
@EqualsAndHashCode
@ApiModel(value="机主月份收入支出金额Vo")
public class OwnerTradePayVo implements Serializable {

    @ApiModelProperty("(必填)机主的userId")
    private Integer userId;

    @ApiModelProperty("付款人的用户userId,其中(0--系统平台)")
    private Integer payerUserId;

    @ApiModelProperty("收款人的userId,其中 0代表系统平台")
    private Integer payeeUserId;

    @ApiModelProperty("(必填)查询日期(yyyy-MM)")
    private String tradeMonth;

    @ApiModelProperty("支出(收入)金额(单位:元)")
    private String totalAmount;

    public interface TypeTrade{
        Integer OWNER_TOKEN = 1;
        Integer OWNER_PAY = 3;
    }

}
