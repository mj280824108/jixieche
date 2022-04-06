package com.weiwei.jixieche.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;

/**
 * @ClassName PushRecordTypeUnReadVo
 * @Description TODO
 * @Author houji
 * @Date 2019/8/26 14:39
 * @Version 1.0.1
 **/
@Data
@EqualsAndHashCode
@ApiModel(value="消息中心")
public class PushRecordTypeUnReadVo implements Serializable {

    @ApiModelProperty("(参数必填)用户userId")
    private Integer userId;

    @ApiModelProperty("推送的类型id(1--系统消息 2--注册登录 3--认证审核 4--订单消息 5--账户消息 6--平台消息)")
    private int typeId;

    @ApiModelProperty("类型名称")
    private String typeName;

    @ApiModelProperty("最后一条记录id")
    private Integer pushRecordId;

    @ApiModelProperty("最后一条未读消息时间")
    private String createTime;

    /**
     * 1--系统消息 2--注册登录 3--认证审核 4--订单消息 5--账户消息 6--平台消息
     */
    public interface TypeId{
        Integer SYSTEM = 1;
        Integer LOGIN = 2;
        Integer CONFIRM = 3;
        Integer ORDER = 4;
        Integer ACOUNT = 5;
        Integer PLAT = 6;
    }

}
