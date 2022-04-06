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
@ApiModel(value="车辆提醒记录表")
public class JxcMachineRemindRecord extends BasePage implements Serializable {
       @ApiModelProperty("主键自增")
       private Integer id;

       @ApiModelProperty("车辆id")
       private Long machineId;

       @ApiModelProperty("车牌号")
       private String machineCardNo;

       @ApiModelProperty("车辆所属机主")
       private Long ownerId;

       @ApiModelProperty("车辆所属机主电话")
       private String ownePhone;

       @ApiModelProperty("车辆提醒类型id(同一辆车只有一个提醒类型)")
       private Integer machineRemindId;

       @ApiModelProperty("车辆提醒时间(格式:yyyy-MM-dd)")
       private String machineRemindTime;

       @ApiModelProperty("是否删除(0-未删除 1-已删除)")
       private Integer deleted;

       @ApiModelProperty("创建时间")
       private Date createTime;

       @ApiModelProperty("修改时间")
       private Date updateTime;

       public interface Delete{
              Integer NOT_DELETE =0;
              Integer DELETE =1;
       }

       private static final long serialVersionUID = 1L;
}