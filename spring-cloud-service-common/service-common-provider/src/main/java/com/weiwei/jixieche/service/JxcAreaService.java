package com.weiwei.jixieche.service;

import com.weiwei.jixieche.bean.JxcArea;
import com.weiwei.jixieche.response.ResponseMessage;
import com.weiwei.jixieche.vo.*;

import java.util.List;
/**
 * @ClassName 类名
 * @Description TODO
 * @Author houji
 * @Date 2019/8/13 18:08
 * @Version 2.0
 **/
public interface JxcAreaService extends BaseService<JxcArea> {

       /**
        * 程序启动加载省市区数据
        */
       void initAreaDatas();

       /**
        * 根据城市id查询城市id下的所有节点
        * @param id
        * @return
        */
       AreaNodeVo getAreaTreeByRootId(Integer id);

       /**
        * 根据Pid查询子节点记录
        * @param pid
        * @return
        */
       List<AreaVo> getAreaChildrenByPid(Integer pid);

       /**
        * 根据城市拼音首字母查询城市列表
        * @return
        */
       List<FirstLetterCityListVo> getFirstLetterCityList();

       /**
        * 查询开放城市列表
        * @return
        */
       ResponseMessage<OpenedProvinceVo> getOpenedCityList();
}