package com.weiwei.jixieche.mapper;

import com.weiwei.jixieche.bean.JxcArea;
import com.weiwei.jixieche.vo.*;
import org.apache.ibatis.annotations.Param;

import java.util.List;
/**
 * @ClassName 类名
 * @Description TODO
 * @Author houji
 * @Date 2019/8/13 18:08
 * @Version 2.0
 **/
public interface JxcAreaMapper extends BaseMapper<JxcArea> {

    /**
     * houji
     * 根据100000查询省市区信息
     * @param id
     * @return
     */
    AreaVo getAreaById(@Param("id") Integer id);

    /**
     * houji
     * 根据pid父节点查询省市区信息集合
     * @param pid
     * @return
     */
    List<AreaVo> getAreaListByPid(@Param("pid")Integer pid);

    /**
     * houji
     * 根据大写拼音首字母查询城市(市)列表
     * @param firstLetter
     * @return
     */
    List<FirstLetterCityVo> getCityListByFirstLetter(@Param("firstLetter")String firstLetter);

    /**
     * houji
     * 查询开放的城市列表
     * @return
     */
    List<OpenedCityVo> getOpenedAreaList();

    /**
     * houji
     * 根据childId查询父类区域信息(区查市，市查省，省查国)
     * @param id
     * @return
     */
    OpenedProvinceVo getParentOpened(@Param("id")Integer id);

    /**
     * 根据id查询开放省的信息
     * @param id
     * @return
     */
    OpenedProvinceVo getOpenProvince(@Param("id")Integer id);

    /**
     * 查询开放城市的省集合
     * @return
     */
    List<OpenedProvinceVo> getOpenProvinceList();
}