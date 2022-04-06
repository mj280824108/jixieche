package com.weiwei.jixieche.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.omg.CORBA.INTERNAL;

import java.io.Serializable;

/**
 * @ClassName AliShortMsgVo
 * @Description TODO
 * @Author houji
 * @Date 2019/8/28 20:41
 * @Version 1.0.1
 **/
@Data
@EqualsAndHashCode
@ApiModel(value="阿里云平台发送短信Vo")
public class AliShortMsgVo implements Serializable {

    @ApiModelProperty("手机号(必填)")
    private String phone;

    @ApiModelProperty("客户端类型,必填(1--app  2--后台)")
    private Integer clientType;

    @ApiModelProperty("app验证码类(1--短信注册 2--快捷登录  3--忘记密码 4--绑定银行卡 5--承租方添加管理员 6--机主添加长期司机 7--设置支付密码)")
    private Integer codeType;

    @ApiModelProperty("后台必填模板id")
    private Integer templateId;

    @ApiModelProperty("后台调用短信模板变量对应的实际值，JSON格式")
    private String templateParam;

    /**
     * 客户端类型
     */
    public interface ClientType{
        Integer APP = 1;
        Integer BACK = 2;
    }

    /**
     * app端
     * 1--短信注册 2--快捷登录  3--忘记密码 4--绑定银行卡 5--承租方添加管理员 6--机主添加长期司机
     */
    public interface CodeType{
        Integer REGISTER = 1;
        Integer LOGIN = 2;
        Integer FORGET_PWD = 3;
        Integer BING_CARD = 4;
        Integer INSERT_MANAGER = 5;
        Integer INSERT_LONG_DRIVER = 6;
        Integer USER_SET_PAY_PWD = 7;
    }


}
