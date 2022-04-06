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
@ApiModel(value="子账号与机主关联关系表")
public class JxcChildRefOwner extends BasePage implements Serializable {
       private Long id;

       @ApiModelProperty("子账号id")
       private Integer childId;

       @ApiModelProperty("机主id")
       private Integer ownerId;

       @ApiModelProperty("司机用户ID")
       private Integer driverId;

       @ApiModelProperty("机主给全职司机的备注名称")
       private String remarkName;

       @ApiModelProperty("机械账号电话")
       private String phone;

       @ApiModelProperty("删除状态（1：未删除 0：已删除）")
       private Integer delState;

       @ApiModelProperty("创建时间")
       private Date createTime;

       @ApiModelProperty("修改时间")
       private Date updateTime;

       private static final long serialVersionUID = 1L;
}