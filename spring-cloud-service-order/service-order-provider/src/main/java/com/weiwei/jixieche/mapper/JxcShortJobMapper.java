package com.weiwei.jixieche.mapper;

import com.weiwei.jixieche.bean.JxcLongJob;
import com.weiwei.jixieche.bean.JxcShortJob;
import com.weiwei.jixieche.response.ResponseMessage;
import com.weiwei.jixieche.vo.DriverJobVo;
import com.weiwei.jixieche.vo.JxcDriverTeamCostVo;
import com.weiwei.jixieche.vo.JxcShortJobDriverDetailVo;
import com.weiwei.jixieche.vo.JxcShortJobDriverVo;
import io.swagger.models.auth.In;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
* @Description: 短期职位招聘
* @Author:      liuy
* @Date:  2019/8/15 13:45
*/
public interface JxcShortJobMapper{

    /**
     * 短期职位招聘列表
     * @author  liuy
     * @param driverJobVo 查询条件
     * @return
     * @date    2019/8/15 11:37
     */
    List<JxcShortJob> getShortJobList(DriverJobVo driverJobVo);
    Integer hasNextPage(DriverJobVo driverJobVo);

    /**
     * 发布短期职位信息
     * @author  liuy
     * @param record
     * @return
     * @date    2019/8/15 13:50
     */
    int insertSelective(JxcShortJob record);

    /**
     * 短期职位详情
     * @author  liuy
     * @param id
     * @return
     * @date    2019/8/15 13:54
     */
    JxcShortJob selectByPrimaryKey(Integer id);

    /**
     * 更新短期职位信息
     * @author  liuy
     * @param record
     * @return
     * @date    2019/8/15 13:50
     */
    int updateByPrimaryKeySelective(JxcShortJob record);

    /**
     * 查询司机台班费用
     * @author  liuy
     * @param cityId
     * @return
     * @date    2019/8/15 14:10
     */
    JxcDriverTeamCostVo queryDriverTeamCost(@Param("cityId") Integer cityId);

    /**
     * 查询职位已招聘人数
     * @author  liuy
    * @param shortJobId
     * @return
     * @date    2019/8/15 14:25
     */
    int getShortJobCount(@Param("shortJobId") Integer shortJobId);

    /**
     * 查找最后一条打卡记录的打卡类型
     * @param driverUserId
     * @param shortJobId
     * @return
     */
    Integer getClockRecordType(@Param("driverUserId") Integer driverUserId,@Param("shortJobId") Integer shortJobId);

    /**
     * 接单职位司机列表
     * @author  liuy
     * @param shortJobId
     * @return
     * @date    2019/8/30 14:01
     */
    List<JxcShortJobDriverVo> getShortDriverList(Integer shortJobId);

    /**
     * 接单职位-司机详情
     * @author  liuy
     * @param driverId
     * @return
     * @date    2019/8/30 16:13
     */
    JxcShortJobDriverDetailVo getShortDriverDetail(@Param("driverId") Integer driverId, @Param("shortJobId") Integer shortJobId);

    /**
     * 查询司机所接的职位
     * @author  liuy
     * @param driverId
     * @return
     * @date    2019/9/17 15:37
     */
    List<Map<String,Object>> getShortJobListByDriverId(@Param("driverId") Integer driverId);

    /**
     * 接单职位的司机是否有未支付的
     * @author  liuy
     * @param shortJobId
     * @return
     * @date    2019/9/25 11:19
     */
    Integer getShortJobDriverNoPayById(@Param("shortJobId")Integer shortJobId);
}