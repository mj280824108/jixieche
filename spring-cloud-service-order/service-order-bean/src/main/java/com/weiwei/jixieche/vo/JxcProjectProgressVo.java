package com.weiwei.jixieche.vo;

import com.weiwei.jixieche.page.BasePage;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @Author zhong huan
 * @Date 2019-09-06 11:05
 * @Description
 */
@Data
@EqualsAndHashCode
@ApiModel(value="项目进度Vo类")
public class JxcProjectProgressVo extends BasePage {
    @ApiModelProperty("日期")
    private String dateStr;

    @ApiModelProperty("完成趟数")
    private Integer totalCount;

    @ApiModelProperty("待支付总金额")
    private String totalNoPay = "0.00";

    @ApiModelProperty("已支付总金额")
    private String totalPay = "0.00";
}
