package com.weiwei.jixieche.utils;

import com.weiwei.jixieche.mapper.JxcAreaMapper;
import com.weiwei.jixieche.vo.*;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName JxcAreaLoadUtils
 * @Description 地区加载数据
 * @Author houji
 * @Date 2019/5/9 13:42
 * @Version 1.0.1
 **/
@Component
public class AreaUtils {

    @Resource
    private JxcAreaMapper jxcAreaMapper;

    //树形结构k-v
    public static Map<Integer, AreaNodeVo> areaNodeMap = new HashMap<Integer, AreaNodeVo>();

    //无children的地点k-v
    public static Map<Integer, AreaVo> areaMap = new HashMap<Integer, AreaVo>();

    //children的pid-k-v
    public static Map<Integer, List<AreaVo>> childrenMap = new HashMap<Integer, List<AreaVo >>();

    //拼音首字母大写的城市列表
    public static List<FirstLetterCityListVo> firstLetterCityListVoList = new ArrayList<FirstLetterCityListVo>();

    //初始化数据
    public AreaNodeVo initAreaDatas(AreaVo areaVo) {
        AreaNodeVo node = null;
        if (areaVo != null) {
            //国节点
            areaMap.put(areaVo.getId(), areaVo);
            node = transAreaToNode(areaVo);
            //根据国节点id查询省的数据信息
            List<AreaVo> children = this.jxcAreaMapper.getAreaListByPid(areaVo.getId());
            if (children != null && children.size() > 0) {
                //childrenMap储存国节点(100000)下面的所有省的数据
                childrenMap.put(areaVo.getId(), children);
                List<AreaNodeVo> nodeChildren = new ArrayList<AreaNodeVo>();
                for (AreaVo a : children) {
                    //递归
                    nodeChildren.add(initAreaDatas(a));
                }
                node.setChildren(nodeChildren);
            }
            areaNodeMap.put(node.getId(), node);
        }
        return node;
    }

    //根据100000的节点查询省节点
    private AreaNodeVo transAreaToNode(AreaVo areaVo) {
        return new AreaNodeVo() {{
            setId(areaVo.getId());
            setName(areaVo.getName());
            setPid(areaVo.getPid());
            setLevel(areaVo.getLevel());
            setShortName(areaVo.getShortName());
            setFirstLetter(areaVo.getFirstLetter());
            setLon(areaVo.getLon());
            setLat(areaVo.getLat());
        }};
    }

    /**
     * char[65]--char[90]对应的值是拼音大写(A---Z)
     * 根据拼音首字母大写加载城市列表
     */
    public void initFirstLetterCityList() {
        for (char i = 65; i <= 90; i++) {
            String firstLetter = String.valueOf(i);
            List<FirstLetterCityVo> cityVoList = this.jxcAreaMapper.getCityListByFirstLetter(firstLetter);
            firstLetterCityListVoList.add(new FirstLetterCityListVo(){{
                setFirstLetter(firstLetter);
                setFirstLetterCityVoList(cityVoList);
            }});
        }
    }

    /**
     * 加载开放城市列表信息
     * @param openedProvincesWithCities
     */
    public void initOpenedCityList(List<OpenedProvinceVo> openedProvincesWithCities) {
        /*if (openedProvincesWithCities == null) {
            openedProvincesWithCities = new ArrayList<OpenedProvinceVo>();
        }
        //查询开放的城市
        List<OpenedAreaVo> openedCityListFromDb = this.jxcAreaMapper.getOpenedAreaList();
        OpenedAreaVo province = null;
        OpenedProvinceVo opp = null;
        OpenedCityVo opc = null;
        Map<Integer, OpenedProvinceVo> openedProvinceMap = new HashMap<>();
        for (final OpenedAreaVo opCity : openedCityListFromDb) {
            province = jxcAreaMapper.getParentOpened(opCity.getAreaId());
            if (!openedProvinceMap.keySet().contains(province.getAreaId())) {
                opp = new OpenedProvinceVo();
                opp.setProvinceId(province.getAreaId());
                opp.setProvinceName(province.getAreaName());
                opp.setLon(province.getLon());
                opp.setLat(province.getLat());
                openedProvinceMap.put(opp.getProvinceId(), opp);
            } else {
                opp = openedProvinceMap.get(province.getAreaId());
            }
            opc = new OpenedCityVo() {{
                setCityId(opCity.getAreaId());
                setCityName(opCity.getAreaName());
                setLon(opCity.getLon());
                setLat(opCity.getLat());
            }};
            opp.getOpenedCityList().add(opc);
        }
        for (Map.Entry<Integer, OpenedProvinceVo> entry : openedProvinceMap.entrySet()) {
            openedProvincesWithCities.add(entry.getValue());
        }*/
    }
}
