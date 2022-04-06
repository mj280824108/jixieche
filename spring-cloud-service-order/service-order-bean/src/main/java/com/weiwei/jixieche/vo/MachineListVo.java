package com.weiwei.jixieche.vo;

import com.weiwei.jixieche.page.BasePage;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @Author zhong huan
 * @Date 2019-08-22 13:37
 * @Description
 */
@Data
@EqualsAndHashCode
@ApiModel(value="接单机械列表Vo类")
public class MachineListVo extends BasePage{
    @ApiModelProperty("机械id")
    private Integer machineId;

    @ApiModelProperty("车牌号")
    private String machineCardNo;

    @ApiModelProperty("机械方量")
    private Integer machineCapacity;

    @ApiModelProperty("当前司机姓名")
    private String driverName;

    @ApiModelProperty("当前司机电话")
    private String driverPhone;

    @ApiModelProperty("是否正在上班 1：是 0：否")
    private Integer workState;

    @ApiModelProperty("所属机主姓名")
    private String ownerName;

    @ApiModelProperty("机主电话")
    private String ownerPhone;

    @ApiModelProperty("机械头像")
    private String machineImg;

    @ApiModelProperty("评分ID,-1则表示未被评分")
    private Long scoredId;



}
