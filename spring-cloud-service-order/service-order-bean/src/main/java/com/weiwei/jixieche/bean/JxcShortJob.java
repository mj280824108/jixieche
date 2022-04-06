package com.weiwei.jixieche.bean;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.weiwei.jixieche.vo.JxcDriverTeamCostVo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.io.Serializable;
import java.util.Date;

/**
 * 短期职位招聘表
 * jxc_short_job
 * @author 
 */
@Data
@EqualsAndHashCode
public class JxcShortJob implements Serializable {
    /**
     * 短期职位id,主键自增
     */
    private Integer id;

    /**
     * 0--泥头车 1--挖机 2--其他
     */
    private Byte machineType;

    /**
     * 驾龄要求(0--不限 1--一年以上 2--三年以上 3--五年以上 4--其他)
     */
    private String driveYear;

    /**
     * 招聘人数
     */
    private Integer needNum;
    /**
     * 已招聘人数
     */
    private Integer shortJobNum;

    /**
     * 年龄限制(0--不限 1--18-30岁- 2--30-45 3--45-60 4--其他 )
     */
    private String ageLimit;

    /**
     * 工作时间（开始）
     */
    @JsonFormat(pattern="yyyy-MM-dd", timezone="GMT+8")
    private Date workTimeStart;

    /**
     * 工作时间（结束）
     */
    @JsonFormat(pattern="yyyy-MM-dd", timezone="GMT+8")
    private Date workTimeEnd;

    /**
     * 工作时段（开始）
     */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    private Date timeFrameStart;

    /**
     * 工作时段（结束）
     */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    private Date timeFrameEnd;

	/**
	 * 工作时段(开始-结束)
	 */
	private String timeFrame;

    /**
     * 有效开始时间
     */
    @JsonFormat(pattern="yyyy-MM-dd", timezone="GMT+8")
    private Date effectiveDate;

    /**
     * 有效结束时间
     */
    @JsonFormat(pattern="yyyy-MM-dd", timezone="GMT+8")
    private Date effectiveDateEnd;

    /**
     * 联系人姓名
     */
    private String contactName;

    /**
     * 联系电话
     */
    private String contactPhone;

    /**
     * 0--开启，1--失效，2--已招满 3--机主关闭职位 4:进行中; 5:待支付
     */
    private Integer status;

    /**
     * 数据库查出来的失效状态
     */
    private Integer flag;

    /**
     * 发布时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 机主id
     */
    private Integer ownerId;

    /**
     * 工作区域id
     */
    private Integer areaId;

    /**
     * 工作区域
     */
    private String workArea;

    /**
     * 0：正常   1：删除
     */
    private Integer jobIsDelete;

    /**
     * 0：展示 1：不展示
     */
    private Integer show;

    /**
     * 要求的司机分数
     */
    private Integer score;

    /**
     * 领车地址
     */
    private String carAddress;

    /**
     * 经度
     */
    private String shortLng;

    /**
     * 纬度
     */
    private String shortLat;

    /**
     * 计费规则
     */
    private String accountRuler;

    /**
     * 临时司机台班计费规则
     */
    private JxcDriverTeamCostVo jxcDriverTeamCostVo;

    @ApiModelProperty("司机台班费用")
    private Integer driverTeamPrice;

    @ApiModelProperty("机主头像")
    private String ownerHeadImg;

    @ApiModelProperty("机主评分")
    private double ownerScore;

    @ApiModelProperty("机主认证状态; (0--未认证 1--已认证，2--未通过，3--审核中)")
    private Integer ownerConfirmStatus;

    private static final long serialVersionUID = 1L;


    //0：开启，1：失效，2：已招满 3 : 关闭(机主主动关闭) 4:进行中; 5:待支付
    public interface status {
        Integer START = 0;
        Integer DISABLED = 1;
        Integer FULL = 2;
        Integer CLOSED = 3;
        Integer PROCESSING = 4;
        Integer WAITPAY = 5;
    }

    //是否删除(0--不删除 1--删除)
    public interface jobIsDelete{
        Integer deleted = 1;
        Integer normal = 0;
    }

}