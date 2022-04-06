package com.weiwei.jixieche.vo;

import com.weiwei.jixieche.page.BasePage;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @Author zhong huan
 * @Date 2019-09-05 9:41
 * @Description
 */
@Data
@EqualsAndHashCode
@ApiModel(value="承租方查看机主退场申请记录")
public class JxcOwnerApplyQuitVo extends BasePage {
    @ApiModelProperty("申请记录ID")
    private Integer id;

    @ApiModelProperty("机械ID")
    private Integer machineId;

    @ApiModelProperty("机主订单ID")
    private Long ownerOrderId;

    @ApiModelProperty("承租方订单ID")
    private Long orderId;

    @ApiModelProperty("机械头像")
    private String machineImg;

    @ApiModelProperty("车牌号")
    private String machineCardNo;

    @ApiModelProperty("机主姓名")
    private String ownerName;

    @ApiModelProperty("申请理由")
    private String applyReason;

    @ApiModelProperty("项目名称")
    private String projectName;

    @ApiModelProperty("申请状态：0:申请中; 1:同意; 2:驳回 3:过期（定时器强制同意）")
    private Integer applyState;

    @ApiModelProperty("申请时间")
    private String createTime;
}
