package com.weiwei.jixieche.bean;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.weiwei.jixieche.page.BasePage;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

@Data
@EqualsAndHashCode
@ApiModel(value="用户认证信息表")
public class JxcUserConfirm extends BasePage implements Serializable {

       @ApiModelProperty("认证id,主键自增")
       private Integer id;

       @ApiModelProperty("认证用户userId")
       private Integer userId;

       @ApiModelProperty("真实姓名")
       private String realName;

       @ApiModelProperty("身份证号")
       private String cardCode;

       @ApiModelProperty("手机号")
       private String phone;

       @ApiModelProperty("身份证正面照")
       private String cardImgFront;

       @ApiModelProperty("身份证背面照片")
       private String cardImgBack;

       @ApiModelProperty("人脸照片")
       private String faceImg;

       @ApiModelProperty("认证方式(1：承租方个人认证，2：承租方企业认证,3:机主认证，4：司机认证 5--泥头车司机认证)")
       private Integer confirmType;

       @ApiModelProperty("承租方个人认证状态; (0--未认证 1--已认证，2--未通过，3--审核中)")
       private Integer personConfirmStatus;

       @ApiModelProperty("承租方企业认证状态; (0--未认证 1--已认证，2--未通过，3--审核中)")
       private Integer companyConfirmStatus;

       @ApiModelProperty("司机认证状态; (0--未认证 1--已认证，2--未通过，3--审核中)")
       private Integer driverConfirmStatus;

       @ApiModelProperty("泥头车认证状态; (0--未认证 1--已认证，2--未通过，3--审核中)")
       private Integer mudDriverConfirmStatus;

       @ApiModelProperty("机主认证状态; (0--未认证 1--已认证，2--未通过，3--审核中)")
       private Integer ownerConfirmStatus;

       @ApiModelProperty("法人姓名")
       private String legalName;

       @ApiModelProperty("工商注册号")
       private String businessNum;

       @ApiModelProperty("企业名称")
       private String companyName;

       @ApiModelProperty("工地负责人")
       private String siteManager;

       @ApiModelProperty("企业执照正页")
       private String businessLicense;

       @ApiModelProperty("司机从业资格证照片反面")
       private String certificationImgBack;

       @ApiModelProperty("司机从业资格证照片正面")
       private String certificationImgFront;

       @DateTimeFormat(pattern="yyyy-MM-dd")
       @JsonFormat(pattern="yyyy-MM-dd")
       @ApiModelProperty("司机从业资格证有效期(yyyy-MM-dd)")
       private Date certificateValidityTime;

       @ApiModelProperty("司机驾照反面")
       private String driveLicenseBack;

       @ApiModelProperty("司机驾照正面")
       private String driveLicenseFront;

       @JsonFormat(pattern="yyyy-MM-dd")
       @ApiModelProperty("驾驶证有效期(yyyy-MM-dd)")
       private Date licenceValidityTime;

       @ApiModelProperty("人脸识别，0:未认证，1：已认证")
       private Integer faceConfirmType;

       @ApiModelProperty("拒绝原因")
       private String refuseReason;

       @ApiModelProperty("创建时间")
       private Date createTime;

       @ApiModelProperty("更新时间")
       private Date updateTime;

       @ApiModelProperty("审核人id")
       private Integer confirmUserId;

       @ApiModelProperty("审核人用户名")
       private String confirmUserName;

       private static final long serialVersionUID = 1L;

       /**
        * 认证方式(1：承租方个人认证，2：承租方企业认证,3:机主认证，4：司机认证)
        */
       public interface confirmType{
              Integer personConfirm = 1;
              Integer companyConfirm = 2;
       }

       //认证状态; (0:认证中，1： 已认证，2：认证失败)

       public interface confirmStatus{
              Integer CONFIRM = 1;
       }

}