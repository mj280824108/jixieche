package com.weiwei.jixieche.mapper;

import com.weiwei.jixieche.bean.JxcProject;
import com.weiwei.jixieche.vo.JxcMachineListVo;
import com.weiwei.jixieche.vo.JxcProjectProgressVo;
import com.weiwei.jixieche.vo.JxcProjectTotalProgressVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @Author 钟焕
 * @Description
 * @Date 11:50 2019-08-14
 **/
public interface JxcProjectMapper extends BaseMapper<JxcProject> {
    /**
     * 检查开放城市
     * @param districtId
     * @return
     */
    Map<String,Object> checkDistrictCode(@Param("districtId") Integer districtId);

    /**
     * 如果没有区ID时，那就检查一下市ID是否属于开放城市
     * @param cityId
     * @return
     */
    Integer checkCityCode(@Param("cityId") Integer cityId);

    /**
     * 查看项目列表
     * @param userId
     * @param flag
     * @return
     */
    List<JxcProject> selectJxcProjectList(@Param("userId") Integer userId,@Param("flag") Integer flag);

    /**
     * 项目的车辆管理列表
     * @param projectId
     * @param flag
     * @return
     */
    List<JxcMachineListVo> tenantryViewMachineList(@Param("projectId") Integer projectId,@Param("flag") Integer flag);

    /**
     * 根据目标ID查询是否有评分记录
     * @param targetId
     * @param orderId
     * @return
     */
    Long getScoreIdByTargetId(@Param("targetId") Integer targetId,@Param("orderId") String orderId);

    /**
     * 按天查询项目进度
     * @param projectId
     * @return
     */
    List<JxcProjectProgressVo> selectProjectProgressByDay(@Param("projectId") Integer projectId);

    /**
     * 查询项目总体进度
     * @param projectId
     * @return
     */
    JxcProjectTotalProgressVo selectProjectTotalProgress(@Param("projectId") Integer projectId);

    /**
     * 统计项目中有多少个订单
     * @param projectId
     * @return
     */
    int countOrderByProjectId(@Param("projectId") Integer projectId);

    /**
     * 删除空项目
     * @param projectId
     */
    void delProject(@Param("projectId") Integer projectId);

    /**
     * 检查是否被限制发单的标记
     * @param userId
     * @return
     */
    Integer getSendOrderFlag(@Param("userId") Integer userId);

}