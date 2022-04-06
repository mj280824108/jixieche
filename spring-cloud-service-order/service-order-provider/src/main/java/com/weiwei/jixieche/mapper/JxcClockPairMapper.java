package com.weiwei.jixieche.mapper;

import com.weiwei.jixieche.bean.JxcClockPair;
import com.weiwei.jixieche.vo.ClockRecordVo;
import com.weiwei.jixieche.vo.UpdateBatchClockPayStatusVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface JxcClockPairMapper extends BaseMapper<JxcClockPair> {

    /**
     * 批量更新台班支付状态
     * @param updateBatchClockPayStatusVo
     */
    void updateBatchClockPayStatus(UpdateBatchClockPayStatusVo updateBatchClockPayStatusVo);

    /**
     * 查询是否有未支付
     * @author  liuy
     * @param driverId
     * @return
     * @date    2019/8/21 16:18
     */
    Integer getCountPayByDriverId(@Param("driverId") Integer driverId, @Param("shortJobId") Integer shortJobId);

    /**
     * 司机考勤工资
     * @author  liuy
     * @param params
     * @return
     * @date    2019/9/24 14:02
     */
    List<ClockRecordVo> getDriverClockDateById(@Param("params") Map<String,Object> params);
    Long hasNextPage(@Param("params") Map<String,Object> params);

    /**
     * 司机打卡记录
     * @author  liuy
     * @param params
     * @return
     * @date    2019/9/24 14:02
     */
    List<ClockRecordVo> getDriverClockDate(@Param("params") Map<String,Object> params);
    Long hasNextPageDriverClockDate(@Param("params") Map<String,Object> params);

    /**
     * 更新台班支付状态
     * @author  liuy
     * @param clockId
     * @return
     * @date    2019/10/7 15:56
     */
    Integer updateClockPayStatus(@Param("clockId") Long clockId);

    /**
     * 查询台班信息
     * @param clockId
     * @return
     */
    JxcClockPair getByClockId(@Param("clockId") Long clockId);

    /**
     * 更新台班申请状态为申诉中
     * @author  liuy
     * @param clockId
     * @return
     * @date    2019/10/9 9:07
     */
    Integer updateClockApplyState(@Param("clockId") Long clockId);

}