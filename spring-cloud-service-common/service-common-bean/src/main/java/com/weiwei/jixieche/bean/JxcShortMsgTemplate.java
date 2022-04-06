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
 * @Date 2019/8/13 18:08
 * @Version 2.0
 **/
@Data
@EqualsAndHashCode
@ApiModel(value="短信模板")
public class JxcShortMsgTemplate extends BasePage implements Serializable {
       @ApiModelProperty("主键")
       private Integer id;

       @ApiModelProperty("类型")
       private String msgType;

       @ApiModelProperty("模版体")
       private String templateBody;

       private Integer operationType;

       @ApiModelProperty("阿里短信模板Code")
       private String templateCode;

       @ApiModelProperty("是否是阿里短信模板(0--不是  1--是 )")
       private Integer aliSms;

       private static final long serialVersionUID = 1L;
}