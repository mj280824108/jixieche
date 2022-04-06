package com.weiwei.jixieche.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * @Author 钟焕
 * @Description
 * @Date 20:13 2019-08-14
 **/
@Data
@EqualsAndHashCode
@ApiModel(value="批量更新台班支付状态Vo")
public class UpdateBatchClockPayStatusVo {

    @ApiModelProperty("行程ID")
    private List<Long> clockIdList;

    @ApiModelProperty("台班支付状态 0：未支付 1：已支付")
    private Integer payState;

    public interface payState{
        Integer UN_PAY = 0;
        Integer PAY = 1;
    }

    private static final long serialVersionUID = 1L;
}