package com.weiwei.jixieche.bean;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;
/**
 * @Author 钟焕
 * @Description
 * @Date 17:56 2019-10-16
 **/
@Data
@EqualsAndHashCode
@ApiModel(value="消纳场收款银行账户")
public class JxcSiteBankAccount {
       @ApiModelProperty("主键自增")
       private Integer id;

       @ApiModelProperty("消纳场id")
       private Integer siteId;

       @ApiModelProperty("消纳场名字")
       private String siteName;

       @ApiModelProperty("银行卡开卡银行名称")
       private String bankCardName;

       @ApiModelProperty("银行卡卡号")
       private String bankCardNumber;

       @ApiModelProperty("开卡用户真实姓名")
       private String cardRealName;

       @ApiModelProperty("开卡人手机号")
       private String cardPhone;

       @ApiModelProperty("是否启用(0--不启用  1--启用)")
       private Integer disabled;

       @ApiModelProperty("创建时间")
	   @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
       private Date createTime;

       @ApiModelProperty("更新时间")
	   @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
       private Date updateTime;

       private static final long serialVersionUID = 1L;
}