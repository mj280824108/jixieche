package com.weiwei.jixieche.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * @ClassName RentLeaseTypeVo
 * @Description TODO
 * @Author houji
 * @Date 2019/9/5 10:19
 * @Version 1.0.1
 **/
@Data
@EqualsAndHashCode
@ApiModel(value="租赁类型Vo")
public class RentLeaseTypeVo implements Serializable {

    @ApiModelProperty("租赁结算方式id")
    private Integer id;

    @ApiModelProperty("租赁类型结算名称")
    private String name;

}
