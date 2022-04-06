package com.weiwei.jixieche.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * @ClassName MarketTabListVo
 * @Description TODO
 * @Author houji
 * @Date 2019/9/4 19:35
 * @Version 1.0.1
 **/
@Data
@EqualsAndHashCode
@ApiModel(value="资源交易子tab搜索Vo")
public class MarketSourceChildTabVo implements Serializable {

    @ApiModelProperty("资源类型")
    private Integer sourceType;

    @ApiModelProperty("求购/出售 5--资源求购 6--资源出售")
    private Integer sourceTradeType;

    @ApiModelProperty("地区(市)")
    private Integer cityId;

    @ApiModelProperty("区id")
    private Integer districtId;

}
