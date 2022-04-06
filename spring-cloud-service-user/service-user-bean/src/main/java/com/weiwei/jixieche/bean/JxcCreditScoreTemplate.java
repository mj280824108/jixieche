package com.weiwei.jixieche.bean;

import com.weiwei.jixieche.page.BasePage;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;
import lombok.EqualsAndHashCode;
/**
 * @ClassName 类名
 * @Description TODO
 * @Author houji
 * @Date 2019/8/13 21:32
 * @Version 2.0
 **/
@Data
@EqualsAndHashCode
@ApiModel(value="信用分模板表")
public class JxcCreditScoreTemplate extends BasePage implements Serializable {
       @ApiModelProperty("主键自增")
       private Integer id;

       @ApiModelProperty("标题")
       private String title;

       @ApiModelProperty("有效开始时间")
       private Date effectiveStartTime;

       @ApiModelProperty("有效结束时间")
       private Date effectiveEndTime;

       @ApiModelProperty("积分条件")
       private Integer condition;

       @ApiModelProperty("积分 加分为正  扣分为负")
       private Integer score;

       @ApiModelProperty("使用范围(1--机主 2--承租方 3--司机 4--子账号)")
       private Integer useScope;

       @ApiModelProperty("规则说明(JSON字符串，里面设置参数值(得分用正数扣分用负数表示))")
       private String ruleSetting;

       @ApiModelProperty("规则详细说明")
       private String ruleDescription;

       @ApiModelProperty("是否启用(0--启用 1--不启用)")
       private Integer enabled;

       @ApiModelProperty("创建用户userId")
       private Integer createUserId;

       @ApiModelProperty("创建规则用户名")
       private String createUserName;

       @ApiModelProperty("创建时间")
       private Date createTime;

       @ApiModelProperty("修改时间")
       private Date updateTime;

       private static final long serialVersionUID = 1L;
}