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
 * @Date 2019/8/13 18:08
 * @Version 2.0
 **/
@Data
@EqualsAndHashCode
@ApiModel(value="短信记录表")
public class JxcShortMsgRecord extends BasePage implements Serializable {

    @ApiModelProperty("主键自增")
    private Integer id;

    @ApiModelProperty("手机号")
    private String phone;

    @ApiModelProperty("动作类型（1登录2忘记密码）")
    private Integer msgTypeId;

    @ApiModelProperty("阿里短信模板code")
    private String templateCode;

    @ApiModelProperty("创建时间")
    private Date createTime;

    @ApiModelProperty("版本（1机主板2承租方板）")
    private Integer client;

    @ApiModelProperty("短信内容")
    private String msg;

    private static final long serialVersionUID = 1L;
}