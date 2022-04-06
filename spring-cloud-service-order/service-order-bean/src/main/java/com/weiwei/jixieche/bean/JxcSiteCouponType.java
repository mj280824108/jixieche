package com.weiwei.jixieche.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

/**
 * @Author zhong huan
 * @Date 2019-08-19 17:12
 * @Description
 */
@Data
@EqualsAndHashCode
@ApiModel(value="消纳券类型表")
public class JxcSiteCouponType {
    @ApiModelProperty("消纳券类型id")
    private Long id;

    @ApiModelProperty("方/车")
    private Integer capacity;

    @ApiModelProperty("所属消纳场id")
    private Integer siteId;

    @ApiModelProperty("消纳券类型 (0:坏土 1:好土)")
    private Integer couponType;

    @ApiModelProperty("消纳券价格")
    private BigDecimal price;

    @ApiModelProperty("券库存(-1表示不限库存)")
    private BigDecimal stockNum;

    @ApiModelProperty("上架状态 (0:下架 1:上架)")
    private Integer shelfFlag;

    public interface CouponTypeSite{
        Integer GOOD_SOIL = 1;
        Integer BAD_SOIL = 0;
    }

    public interface ShelfFlag{
        Integer UP = 1;
        Integer DOWN = 0;
    }

}
