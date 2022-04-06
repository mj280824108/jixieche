package com.weiwei.jixieche.bean;

import com.weiwei.jixieche.page.BasePage;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;
import lombok.EqualsAndHashCode;
/**
 * @ClassName UserRegisterVo
 * @Description TODO
 * @Author houji
 * @Date 2019/8/13 18:08
 * @Version 2.0
 **/
@Data
@EqualsAndHashCode
@ApiModel(value="平台反馈信息表")
public class JxcPlatformFeedback extends BasePage implements Serializable {
       @ApiModelProperty("主键自增")
       private Integer id;

       @ApiModelProperty("反馈userid")
       private Integer userId;

       @ApiModelProperty("用户头像")
       private String userHeadImg;

       @ApiModelProperty("反馈用户真实姓名")
       private String userRealName;

       @ApiModelProperty("用户手机号")
       private String userPhone;

       @ApiModelProperty("反馈内容")
       private String feedbackContent;

       @ApiModelProperty("反馈针对订单id")
       private Long orderId;

       @ApiModelProperty("平台回复内容")
       private String platformReplyContent;

       @ApiModelProperty("反馈时间")
       private Date createTime;

       @ApiModelProperty("修改时间")
       private Date updateTime;

       private static final long serialVersionUID = 1L;
}