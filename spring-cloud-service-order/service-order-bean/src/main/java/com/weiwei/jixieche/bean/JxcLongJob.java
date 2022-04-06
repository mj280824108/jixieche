package com.weiwei.jixieche.bean;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
@ApiModel(value="长期司机招聘表")
public class JxcLongJob implements Serializable {
       private Integer id;

       @ApiModelProperty("发布长期职位的机主userId")
       private Integer userId;

       @ApiModelProperty("工程类型(1--土方石 2--绿化 3--场地平整 4--道路 5--建筑 6--工厂 7--农田改造 8--山坡 9--矿山 10--拆迁 11--市政工程 12--隧道 13--其他)")
       private String projectType;

       @ApiModelProperty("工程类型")
       private List<String> projectTypeList;

       @ApiModelProperty("区域id")
       private Integer areaId;

       @ApiModelProperty("工作地点")
       private String workArea;

       @ApiModelProperty("0--泥头车 1--挖掘机 2--其他")
       private Integer machineType;

       @ApiModelProperty("驾龄(0--一年以上 1--三年以上 2--五年以上 3--不限)")
       private String driveYear;

       @ApiModelProperty("招聘人数")
       private Integer needNum;

       @ApiModelProperty("工作类型(0-白班 1--夜班 2--两班倒 3--三班倒 4--其他)")
       private Integer workType;

       @ApiModelProperty("白天时间(09:00 --- 18:00)")
       private String workTypeTime;

       @ApiModelProperty("福利(0--包吃 1--包住 2--报销路费 3--上保险 4--加班费 5--其他)")
       private String welfare;

       @ApiModelProperty("其他福利")
       private String otherWelfare;

       @ApiModelProperty("招聘类型(0--长期有效 1--日期范围)")
       private Integer jobType;

       @ApiModelProperty("招聘起始时间")
       @JsonFormat(pattern="yyyy-MM-dd",timezone="GMT+8")
       private Date startTime;

       @ApiModelProperty("招聘结束时间")
       @JsonFormat(pattern="yyyy-MM-dd",timezone="GMT+8")
       private Date endTime;

       @ApiModelProperty("薪资待遇类型(薪资结算方式；0：按月结，1：面议)")
       private Integer payment;

       @ApiModelProperty("月薪资(单位:分)")
       private Integer payMoney;

       @ApiModelProperty("经验要求")
       private String experience;

       @ApiModelProperty("联系人名称")
       private String contactName;

       @ApiModelProperty("联系电话")
       private String contactPhone;

       @ApiModelProperty("发布状态,0:开启，1：失效")
       private Byte status;

       @ApiModelProperty("是否删除(0--不删除 1--删除)")
       private Integer deleted;

       @ApiModelProperty("发布时间")
       @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
       private Date createTime;

       @ApiModelProperty("更新时间")
       @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
       private Date updateTime;

       @ApiModelProperty("机主头像")
       private String ownerHeadImg;

       @ApiModelProperty("机主评分")
       private double score;

       @ApiModelProperty("机主认证状态; (0--未认证 1--已认证，2--未通过，3--审核中)")
       private Integer ownerConfirmStatus;

       private static final long serialVersionUID = 1L;

       //是否删除(0--不删除 1--删除)
       public interface deleted{
              Integer deleted = 1;
              Integer normal = 0;
       }
}