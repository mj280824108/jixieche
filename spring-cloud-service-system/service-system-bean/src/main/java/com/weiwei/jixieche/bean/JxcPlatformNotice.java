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
@ApiModel(value="平台公告信息表")
public class JxcPlatformNotice extends BasePage implements Serializable {
       @ApiModelProperty("主键自增")
       private Integer id;

       @ApiModelProperty("发布针对类型(0--平台所有用户 2--承租方 1--机主方)")
       private Integer aimedType;

       @ApiModelProperty("内容类型(0--平台公告 1--帮助说明)")
       private Integer contentType;

       @ApiModelProperty("标题")
       private String title;

       @ApiModelProperty("标题图片")
       private String titleImg;

       @ApiModelProperty("有效开始时间")
       private Date effectiveStartTime;

       @ApiModelProperty("有效结束时间")
       private Date effectiveEndTime;

       @ApiModelProperty("公告内容")
       private String content;

       @ApiModelProperty("公告发布状态(0--编辑未发布 1--发布 2--下架 3--删除)")
       private Integer state;

       @ApiModelProperty("创建userId(后台用户userId)")
       private Integer createId;

       @ApiModelProperty("创建用户名")
       private String creator;

       @ApiModelProperty("创建时间")
       private Date createTime;

       @ApiModelProperty("修改时间")
       private Date updateTime;

       private static final long serialVersionUID = 1L;
}