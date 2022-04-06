package com.weiwei.jixieche.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName AreaNameVo
 * @Description TODO
 * @Author houji
 * @Date 2019/9/6 11:05
 * @Version 1.0.1
 **/
@Data
@EqualsAndHashCode
@ApiModel(value="查询机械详情Vo")
public class MachineDetailsVo implements Serializable {

    @ApiModelProperty("机械类型id(必填)")
    private Integer id;

    @ApiModelProperty("用户userId")
    private Integer userId;

}
