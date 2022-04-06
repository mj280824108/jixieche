package com.weiwei.jixieche.bean;

import com.weiwei.jixieche.page.BasePage;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
@ApiModel(value="机械表")
public class JxcMachine extends BasePage {
       @ApiModelProperty("机械id,主键自增")
       private Integer id;

       @ApiModelProperty("车牌号")
       private String machineCardNo;

       @ApiModelProperty("车辆归属地(市级)")
       private Integer areaCode;

       @ApiModelProperty("车辆生产出厂时间")
       private String machineProductTime;

       @ApiModelProperty("营运证图片正面")
       private String machineOperationCertificateFront;

       @ApiModelProperty("营运证图片反面")
       private String machineOperationCertificateBehind;

       @ApiModelProperty("营运证有效时间")
       private String operationCertificateValidTime;

       @ApiModelProperty("营运证状态 0可用 1过期 2即将过期")
       private Integer operationCertificateState;

       @ApiModelProperty("行驶证图片正面")
       private String machineDrivingLicenseFront;

       @ApiModelProperty("行驶证图片反面")
       private String machineDrivingLicenseBehind;

       @ApiModelProperty("行驶证有效时间")
       private String drivingLicenseValidTime;

       @ApiModelProperty("行驶证状态 0--可用 1--过期 2--即将过期")
       private Integer drivingLicenseState;

       @ApiModelProperty("审核状态( 0：审核中 1：已通过 2：未通过  )，配合宽进严出政策，去掉审核环节")
       private Integer confirmState;

       @ApiModelProperty("机械绑定机主的userId")
       private Integer userId;

       @ApiModelProperty("状态（0：无账号 1：空闲中 2：进行中）")
       private Integer status;

       @ApiModelProperty("机械方量")
       private Integer machineCapacity;

       @ApiModelProperty("评分")
       private Double score;

       @ApiModelProperty("列表展示 0：待审核 1：已审核")
       private Integer listType;

       @ApiModelProperty("车辆未审核通过原因")
       private String refuseReason;

       @ApiModelProperty("模式 0:关闭 1:听单 2:派单")
       private Integer mode;

       @ApiModelProperty("模式开始时间")
       private Date modeStartTime;

       @ApiModelProperty("创建时间")
       private Date createTime;

       @ApiModelProperty("修改时间")
       private Date updateTime;

       @ApiModelProperty("删除状态: 0:未删除; 1:已删除")
       private Integer deleteStatus;

       @ApiModelProperty("司机ID")
       private Integer driverId;

       private static final long serialVersionUID = 1L;

       //状态（0：无账号 1：空闲中 2：进行中
       public interface carStatus{
              Integer NO_ACCOUNT = 0;
              Integer IN_IDLE = 1;
              Integer PROCESSING = 2;
       }
}