package com.weiwei.jixieche.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
@ApiModel(value="兼职职位与司机的关联表")
public class JxcShortJobRefDriver implements Serializable {
       @ApiModelProperty("主键")
       private Long id;

       @ApiModelProperty("状态（0：已接受 1：进行中 2：已完结 3：辞职（司机主动取消） 4：被机主取消 5：被机主解雇）")
       private Integer state;

       @ApiModelProperty("兼职职位ID")
       private Integer shortJobId;

       @ApiModelProperty("机主用户ID")
       private Integer ownerId;

       @ApiModelProperty("司机用户ID")
       private Integer driverId;

       @ApiModelProperty("创建时间")
       private Date createTime;

       @ApiModelProperty("修改时间（辞职或者被取消或者被解雇的时间）")
       private Date updateTime;

       private static final long serialVersionUID = 1L;
}