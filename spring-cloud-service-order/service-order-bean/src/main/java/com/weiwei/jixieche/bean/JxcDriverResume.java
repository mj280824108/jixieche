package com.weiwei.jixieche.bean;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.weiwei.jixieche.page.BasePage;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
* @Description: 司机求职信息
* @Author:      liuy
* @Date:  2019/8/21 11:34
*/
@Data
@EqualsAndHashCode
@ApiModel(value="司机求职信息表")
public class JxcDriverResume extends BasePage {
       private Integer resumeId;

       @ApiModelProperty("头像")
       private String headImg;

       @ApiModelProperty("名称")
       private String name;

       @ApiModelProperty("性别(0--男 1--女)")
       private Integer sex;

       @ApiModelProperty("出生日期")
       @JsonFormat(pattern="yyyy-MM-dd",timezone="GMT+8")
       private Date birthday;

       @ApiModelProperty("年龄")
       private Integer age;

       @ApiModelProperty("手机号")
       private String phone;

       private String log;

       @ApiModelProperty("求职地点")
       private String jobLocation;

       @ApiModelProperty("工作区域(0--本省 1--外省)")
       private String workArea;

       @ApiModelProperty("区域id")
       private Integer areaId;

       @ApiModelProperty("机械类型(0--泥头车 1--其他)")
       private Integer machineType;

       @ApiModelProperty("驾龄(0--一年以上 1--三年以上 2--五年以上 3--不限)")
       private String driverYear;

       @ApiModelProperty("薪资结算方式；0：按月结，1：面议")
       private Integer payment;

       @ApiModelProperty("薪资待遇(单位:分)")
       private Integer payMoney;

       @ApiModelProperty("福利要求(0-包吃 1-包住 2-报销路费 3-上保险 4-加班费)")
       private String welfare;

       @ApiModelProperty("其他福利")
       private String otherWelfare;

       @ApiModelProperty("个人介绍")
       private String personIntroduce;

       @ApiModelProperty("求职状态;(0:找工作中；1：考虑换份工作；2：在职-暂不考虑 3:无)")
       private String forJobType;

       @ApiModelProperty("简历状态;0:开启，1：失效")
       private Integer status;

       @ApiModelProperty("创建时间")
       private Date createTime;

       @ApiModelProperty("更新时间")
       private Date updateTime;

       @ApiModelProperty("司机id")
       private Integer driverId;

       @ApiModelProperty("0：未更新，1：已更新")
       private Integer updateStatus;

       /**
        * 司机简历查询条件
        */
       @ApiModelProperty("月薪起始金额")
       private Integer startMoney;

       @ApiModelProperty("月薪结束金额")
       private Integer endMoney;

       private static final long serialVersionUID = 1L;
}