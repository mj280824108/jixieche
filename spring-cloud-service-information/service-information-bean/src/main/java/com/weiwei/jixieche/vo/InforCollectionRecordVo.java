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
 * @Version 1.0.1
 **/
@Data
@EqualsAndHashCode
@ApiModel(value="咨询信息Vo")
public class InforCollectionRecordVo implements Serializable {

    @ApiModelProperty("用户userId")
    private Integer userId;

    @ApiModelProperty("资讯inforId")
    private Integer inforId;

    @ApiModelProperty("收藏状态(0--取消收藏  1--收藏)")
    private Integer state;
}
