package com.weiwei.jixieche.mapper;

import com.weiwei.jixieche.bean.JxcClockInOutPair;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface JxcClockInOutPairMapper extends BaseMapper<JxcClockInOutPair> {

    /**
     * 日历打标记
     * @author  liuy
     * @return
     * @date    2019/9/3 19:24
     */
    List<Map<String,Object>> dateListWhichHasRouteFinishedOrError(@Param("params") Map<String, Object> params);

    /**
     * 计算总工时
     * @author  liuy
     * @param params
     * @return
     * @date    2019/9/28 20:19
     */
    double calculatingTotalWorkHour(@Param("params") Map<String,Object> params);

    /**
     * 查询当前正在上班的司机所绑定的机主用户ID
     * @author  liuy
     * @param driverId
     * @return
     * @date    2019/10/8 15:55
     */
    Integer getOwnerUserIdByDriverId(@Param("driverId") Integer driverId);

    /**
     * 机械正在上班中
     * @author  liuy
     * @param machineId
     * @return
     * @date    2019/10/17 19:42
     */
    JxcClockInOutPair getMachineWorkById(@Param("machineId") Integer machineId);


}