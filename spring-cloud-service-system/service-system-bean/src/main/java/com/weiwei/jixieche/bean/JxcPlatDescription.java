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
@ApiModel(value="平台说明")
public class JxcPlatDescription extends BasePage implements Serializable {
       @ApiModelProperty("主键自增")
       private Integer id;

       @ApiModelProperty("用户类型(0--承租方 1--机主 2--司机 3--未识别的角色)")
       private Integer userType;

       @ApiModelProperty("说明类型(0--用户协议 1--计费规则 2--名词说明 3--服务条款 4--信用分规则 5--隐私条款 6--发票须知 7--增票资质确认书)")
       private Integer descriptType;

       @ApiModelProperty("内容")
       private String content;

       @ApiModelProperty("是否启用 (0--启用 1--不启用)")
       private Byte disable;

       @ApiModelProperty("创建人userId")
       private Integer createUserId;

       @ApiModelProperty("创建人姓名")
       private String createUserName;

       @ApiModelProperty("创建时间")
       private Date createTime;

       @ApiModelProperty("更新时间")
       private Date updateTime;

       private static final long serialVersionUID = 1L;
}