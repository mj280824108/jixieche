package com.weiwei.jixieche.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * @ClassName InforCollectionRecordVo
 * @Description TODO
 * @Author houji
 * @Date 2019/8/20 17:30
 * @Version 2.0
 **/
@Data
@EqualsAndHashCode
@ApiModel(value="资讯点赞(浏览)(分享)量增加Vo")
public class InforPointNumberVo implements Serializable {

    @ApiModelProperty("资讯inforId")
    private Integer inforId;

    @ApiModelProperty("操作类型状态(1--浏览量 2--点赞量 3--分享量)")
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
    }


}
