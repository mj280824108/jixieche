package com.weiwei.jixieche.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.weiwei.jixieche.bean.JxcMarketResourceType;
import com.weiwei.jixieche.constant.ErrorCodeConstants;
import com.weiwei.jixieche.mapper.JxcMarketResourceTypeMapper;
import com.weiwei.jixieche.page.PageUtils;
import com.weiwei.jixieche.response.ResponseMessage;
import com.weiwei.jixieche.response.ResponseMessageFactory;
import com.weiwei.jixieche.service.JxcMarketResourceTypeService;
import com.weiwei.jixieche.verify.AssertUtil;
import java.util.List;
import javax.annotation.Resource;

import com.weiwei.jixieche.verify.CollectionUtils;
import com.weiwei.jixieche.vo.MarketResourceTypeListVo;
import org.springframework.stereotype.Service;
/**
 * @ClassName dd
 * @Description TODO
 * @Author houji
 * @Date 2019/8/21 14:00
 * @Version 2.0
 **/
@Service("jxcMarketResourceTypeService")
public class JxcMarketResourceTypeServiceImpl implements JxcMarketResourceTypeService {
       @Resource
       private JxcMarketResourceTypeMapper jxcMarketResourceTypeMapper;

       /**
        * 前端分页查询市场资源类型表
        * @param pageNo  分页索引
        * @param pageSize  每页显示数量
        * @param jxcMarketResourceType 查询条件
        * @return
        */
       @Override
       public ResponseMessage<JxcMarketResourceType> findByPageForFront(Integer pageNo, Integer pageSize, JxcMarketResourceType jxcMarketResourceType) {
               ResponseMessage result = ResponseMessageFactory.newInstance();
              PageHelper.startPage(pageNo,pageSize);
              List<JxcMarketResourceType> list = this.jxcMarketResourceTypeMapper.selectListByConditions(jxcMarketResourceType);
              PageInfo<JxcMarketResourceType> page = new PageInfo<>(list);
              result.setData(new PageUtils<>(page).getPageViewDatatable());
              return result;
       }

       /**
        * 查询市场资源类型表(不分页)
        * @param
        * @return
        */
       @Override
       public ResponseMessage<JxcMarketResourceType> query() {
              ResponseMessage result = ResponseMessageFactory.newInstance();
              JxcMarketResourceType jxcMarketResourceType = new JxcMarketResourceType();
              List<JxcMarketResourceType> list = this.jxcMarketResourceTypeMapper.selectListByConditions(jxcMarketResourceType);
              if(CollectionUtils.isEmpty(list)){
                     result.setCode(ErrorCodeConstants.QUERY_EMPTY_DATA.getId());
                     result.setMessage(ErrorCodeConstants.QUERY_EMPTY_DATA.getDescript());
                     return result;
              }else{
                     result.setData(list);
              }
              return result;
       }

       /**
        * 添加市场资源类型表
        * @param  jxcMarketResourceType
        * @return
        */
       @Override
       public ResponseMessage<JxcMarketResourceType> addObj(JxcMarketResourceType jxcMarketResourceType) {
              ResponseMessage result = ResponseMessageFactory.newInstance();
              int res=this.jxcMarketResourceTypeMapper.insertSelective(jxcMarketResourceType);
              AssertUtil.numberGtZero(res,ErrorCodeConstants.ADD_ERORR.getDescript(),ErrorCodeConstants.ADD_ERORR.getId());
              return result;
       }

       /**
        * 修改市场资源类型表
        * @param jxcMarketResourceType
        * @return
        */
       @Override
       public ResponseMessage<JxcMarketResourceType> modifyObj(JxcMarketResourceType jxcMarketResourceType) {
              ResponseMessage result = ResponseMessageFactory.newInstance();
              int res=this.jxcMarketResourceTypeMapper.updateByPrimaryKeySelective(jxcMarketResourceType);
              AssertUtil.numberGtZero(res,ErrorCodeConstants.EDIT_ERORR.getDescript(),ErrorCodeConstants.EDIT_ERORR.getId());
              return result;
       }

       /**
        * 根据ID查询市场资源类型表
        * @param id
        * @return
        */
       @Override
       public ResponseMessage<JxcMarketResourceType> queryObjById(int id) {
              ResponseMessage result = ResponseMessageFactory.newInstance();
              JxcMarketResourceType model=this.jxcMarketResourceTypeMapper.selectByPrimaryKey(id);
              AssertUtil.notNull(model,ErrorCodeConstants.QUERY_EMPTY_DATA.getDescript(),ErrorCodeConstants.QUERY_EMPTY_DATA.getId());
              result.setData(model);
              return result;
       }

       /**
        * 查询市场资源类型列表
        * @return
        */
       @Override
       public ResponseMessage queryMarketResourceTypeList() {
              ResponseMessage result = ResponseMessageFactory.newInstance();
              //查询资源大类
              MarketResourceTypeListVo marketResourceTypeListVo = new MarketResourceTypeListVo();
              marketResourceTypeListVo.setCode("0");
              List<MarketResourceTypeListVo> pResourceList = this.jxcMarketResourceTypeMapper.queryMarketResourceTypeList(marketResourceTypeListVo);
              pResourceList.forEach(pResource -> {
                     MarketResourceTypeListVo cResource = new MarketResourceTypeListVo();
                     cResource.setCode(pResource.getId().toString());
                     pResource.setChildResourceList(this.jxcMarketResourceTypeMapper.queryMarketResourceTypeList(cResource));
              });
              result.setData(pResourceList);
              return result;
       }
}