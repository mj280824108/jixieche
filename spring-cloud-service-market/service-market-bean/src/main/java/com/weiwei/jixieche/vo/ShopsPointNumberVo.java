package com.weiwei.jixieche.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * @ClassName ShopsPointNumber
 * @Description TODO
 * @Author houji
 * @Date 2019/8/22 10:43
 * @Version 1.0.1
 **/
@Data
@EqualsAndHashCode
@ApiModel(value="店铺浏览量点赞量Vo")
public class ShopsPointNumberVo implements Serializable {

    @ApiModelProperty("店铺id")
    private Integer shopsId;

    @ApiModelProperty("操作类型状态(1--浏览量 2--点赞量 3--分享量 4--收藏量)")
    private Integer operationType;

    @ApiModelProperty("操作状态(0--取消操作 1--增加操作)")
    private Integer operationStatus;

    /**
     * 操作状态(0--取消操作 1--增加操作)
     */
    public interface operationStatus{
        Integer CANNEL = 0;
        Integer ADD = 1;
    }

    /**
     * 操作类型状态(1--浏览量 2--点赞量 3--分享量)
     */
    public interface operationType{
        Integer VIEW = 1;
        Integer POINT = 2;
        Integer SHARE = 3;
        Integer COLLECTION = 4;
    }

}
