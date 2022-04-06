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
@ApiModel(value="用户店铺资源信息Vo")
public class UserShopsInfoVo implements Serializable {

    @ApiModelProperty("页码数")
    private Integer pageNo = 1;

    @ApiModelProperty("每页条数")
    private Integer pageSize = 10;

    @ApiModelProperty("当前页码数")
    private Integer pageCount;

    @ApiModelProperty("用户userId")
    private Integer userId;

    @ApiModelProperty("个人店铺信息")
    private UserShopsVo userShopsVo = new UserShopsVo();

    @ApiModelProperty("发布类型(1--机械求购 2--机械出售 3--机械求租 4--机械出租,前端默认 机械求购)")
    private Integer realeseType;

    @ApiModelProperty("搜索的机械信息列表数据")
    private List<JxcMarketReleaseVo> marketReleaseVoList = new ArrayList<>();
}
