package com.weiwei.jixieche.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.omg.CORBA.INTERNAL;

import java.util.List;

/**
 * @Author 钟焕
 * @Description
 * @Date 20:13 2019-08-14
 **/
@Data
@EqualsAndHashCode
@ApiModel(value="批量更新机械行程状态Vo")
public class UpdateBatchRoutePayStatusVo{

    @ApiModelProperty("行程ID")
    private List<Long> routeIdList;

    @ApiModelProperty("支付状态（0：异常趟或正在跑趟中 1：待支付（平台已垫付） 2：已支付）")
    private Integer payState;

    /**
     * 支付状态
     */
    public interface payState{
        Integer PLAT_PAY = 1;
        Integer PAY = 2;
    }

    private static final long serialVersionUID = 1L;
}