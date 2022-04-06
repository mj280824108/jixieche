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
 * @Date 2019/8/13 18:08
 * @Version 2.0
 **/
@Data
@EqualsAndHashCode
@ApiModel(value="消息推送记录表")
public class JxcPushRecord extends BasePage implements Serializable {

       @ApiModelProperty("短信记录id，主键自增")
       private Integer id;

       @ApiModelProperty("用户userId,短信接收人")
       private Integer userId;

       @ApiModelProperty("用户的手机号")
       private String phone;

       @ApiModelProperty("推送父类id 1--系统消息 2--注册登录 3--认证审核 4--订单消息 5--账户消息 6--平台消息")
       private Integer templatePid;

       @ApiModelProperty("推送模板id")
       private Integer templateId;

       @ApiModelProperty("推送内容")
       private String content;

       @ApiModelProperty("发送是否成功(0--未成功 1--成功)")
       private Integer successed;

       @ApiModelProperty("是否删除(0--删除 1--未删除)")
       private Integer deleted;

       @ApiModelProperty("是否读取(0--未读取  1--已读取)")
       private Integer readed;

       @ApiModelProperty("创建时间")
       @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
       private Date createTime;

       @ApiModelProperty("更新时间")
       private Date updateTime;

       private static final long serialVersionUID = 1L;
}