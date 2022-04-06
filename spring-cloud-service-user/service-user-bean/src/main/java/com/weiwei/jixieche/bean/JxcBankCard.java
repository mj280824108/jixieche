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
@ApiModel(value="用户银行卡")
public class JxcBankCard implements Serializable {

       @ApiModelProperty("页码数")
       private Integer pageNo = 1;

       @ApiModelProperty("每页条数")
       private Integer pageSize = 10;

       @ApiModelProperty("银行卡id")
       private Integer id;

       @ApiModelProperty("持卡账号id（机主和临时司机对应的user的id）")
       private Integer userId;

       @ApiModelProperty("银行id")
       private String bankId;

       @ApiModelProperty("银行卡号")
       private String cardNumber;

       @ApiModelProperty("开户行名称")
       private String bankName;

       @ApiModelProperty("状态(0--不启用 1--启用)")
       private Integer status;

       @ApiModelProperty("姓名")
       private String name;

       @ApiModelProperty("身份证")
       private String personNumber;

       @ApiModelProperty("电话")
       private String phone;

       @ApiModelProperty("英文简写")
       private String bankCode;

       @ApiModelProperty("创建时间")
       private Date createTime;

       @ApiModelProperty("创建时间")
       private Date updateTime;

       @ApiModelProperty("验证码")
       private String code;

       /**
        * 银行卡状态
        * 状态(0--不启用 1--启用)
        */
       public interface BankCardStats{
              Integer ENABLE = 1;
              Integer DISABLED = 0;
       }
}