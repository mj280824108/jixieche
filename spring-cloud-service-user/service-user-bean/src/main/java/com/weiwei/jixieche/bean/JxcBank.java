package com.weiwei.jixieche.bean;

import com.weiwei.jixieche.page.BasePage;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
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
@ApiModel(value="银行表")
public class JxcBank extends BasePage implements Serializable {

       @ApiModelProperty(" 发卡行机构代码(03080000-招商银行)")
       private String id;

       @ApiModelProperty("银行名称")
       private String bankName;

       @ApiModelProperty("背景图url")
       private String bgImg;

       @ApiModelProperty("logo")
       private String logo;

       private static final long serialVersionUID = 1L;
}