package com.weiwei.jixieche.service.impl;

import com.weiwei.jixieche.constant.ErrorCodeConstants;
import com.weiwei.jixieche.bean.JxcArea;
import com.weiwei.jixieche.mapper.JxcAreaMapper;
import com.weiwei.jixieche.response.ResponseMessage;
import com.weiwei.jixieche.response.ResponseMessageFactory;
import com.weiwei.jixieche.service.JxcAreaService;
import com.weiwei.jixieche.utils.AreaUtils;
import com.weiwei.jixieche.verify.AssertUtil;

import java.util.*;
import javax.annotation.Resource;

import com.weiwei.jixieche.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
/**
 * @ClassName 类名
 * @Description TODO
 * @Author houji
 * @Date 2019/8/13 18:08
 * @Version 2.0
 **/
@Service("jxcAreaService")
public class JxcAreaServiceImpl implements JxcAreaService {

       @Resource
       private JxcAreaMapper jxcAreaMapper;

       @Autowired
       private AreaUtils areaUtils;

       //添加省市区域表
       @Override
       public ResponseMessage<JxcArea> addObj(JxcArea t) {
              ResponseMessage result = ResponseMessageFactory.newInstance();
              int res=this.jxcAreaMapper.insertSelective(t);
              AssertUtil.numberGtZero(res,ErrorCodeConstants.ADD_ERORR.getDescript(),ErrorCodeConstants.ADD_ERORR.getId());
              return result;
       }

       //修改省市区域表
       @Override
       public ResponseMessage<JxcArea> modifyObj(JxcArea t) {
              ResponseMessage result = ResponseMessageFactory.newInstance();
              int res=this.jxcAreaMapper.updateByPrimaryKeySelective(t);
              AssertUtil.numberGtZero(res,ErrorCodeConstants.EDIT_ERORR.getDescript(),ErrorCodeConstants.EDIT_ERORR.getId());
              return result;
       }

       //根据ID查询省市区域表
       @Override
       public ResponseMessage<JxcArea> queryObjById(int id) {
              ResponseMessage result = ResponseMessageFactory.newInstance();
              JxcArea model=this.jxcAreaMapper.selectByPrimaryKey(id);
              AssertUtil.notNull(model,ErrorCodeConstants.QUERY_EMPTY_DATA.getDescript(),ErrorCodeConstants.QUERY_EMPTY_DATA.getId());
              result.setData(model);
              return result;
       }

       @Override
       public void initAreaDatas() {
              //查询10000根节点下的所有节点数据，100000为根节点,代表中国
              AreaVo areaVo = this.jxcAreaMapper.getAreaById(100000);
              areaUtils.initAreaDatas(areaVo);
              areaUtils.initFirstLetterCityList();
       }

       //根据城市id查询城市id下的所有节点
       @Override
       public AreaNodeVo getAreaTreeByRootId(Integer id) {
              return areaUtils.areaNodeMap.get(id);
       }

       //根据跟Pid查询子节点记录
       @Override
       public List<AreaVo> getAreaChildrenByPid(Integer pid) {
              return areaUtils.childrenMap.get(pid) == null ? new ArrayList<AreaVo>() : areaUtils.childrenMap.get(pid);
       }

       //根据城市拼音首字母查询城市列表
       @Override
       public List<FirstLetterCityListVo> getFirstLetterCityList() {
              return areaUtils.firstLetterCityListVoList;
       }

       //查询开放的城市列表
       @Override
       public ResponseMessage<OpenedProvinceVo> getOpenedCityList() {
              ResponseMessage result = ResponseMessageFactory.newInstance();
              List<OpenedProvinceVo> openedProvinceList = new ArrayList<OpenedProvinceVo>();
              //查询开放的城市city
              List<OpenedCityVo> openedCityList = this.jxcAreaMapper.getOpenedAreaList();
              Map<Integer,List<OpenedCityVo>> openedProvinceMap = new HashMap<>();
              openedCityList.forEach(cityVo -> {
                     if(openedProvinceMap.containsKey(cityVo.getProvinceId())){
                            List<OpenedCityVo> list = openedProvinceMap.get(cityVo.getProvinceId());
                            list.add(cityVo);
                            openedProvinceMap.put(cityVo.getProvinceId(),list);
                     }else{
                            List<OpenedCityVo> list = new ArrayList<>();
                            list.add(cityVo);
                            openedProvinceMap.put(cityVo.getProvinceId(),list);
                     }
              });
              for (Map.Entry<Integer, List<OpenedCityVo>> entry : openedProvinceMap.entrySet()) {
                     OpenedProvinceVo openedProvinceVo = this.jxcAreaMapper.getOpenProvince(entry.getKey());
                     openedProvinceVo.getOpenedCityList().addAll(entry.getValue());
                     openedProvinceList.add(openedProvinceVo);
              }
              result.setData(openedProvinceList);
              return result;
       }
}