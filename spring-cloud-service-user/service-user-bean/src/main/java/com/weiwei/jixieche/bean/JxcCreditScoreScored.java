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
 * @ClassName 类名
 * @Description TODO
 * @Author houji
 * @Date 2019/8/13 21:32
 * @Version 2.0
 **/
@Data
@EqualsAndHashCode
@ApiModel(value="用户信用分记录表")
public class JxcCreditScoreScored extends BasePage implements Serializable {
       private Integer id;

       @ApiModelProperty("获得积分分userId")
       private Integer userId;

       @ApiModelProperty("积分规则模板的id")
       private Integer templateId;

       @ApiModelProperty("规则标题")
       private String templateTitle;

       @ApiModelProperty("所得积分(如果扣分用负数表示，如果是得分用正数表示)")
       private Integer score;

       @ApiModelProperty("积分时间")
       @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
       private Date createTime;

       @ApiModelProperty("积分修改时间")
       @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
       private Date updateTime;

       private static final long serialVersionUID = 1L;
}