package com.weiwei.jixieche.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.weiwei.jixieche.bean.JxcMarketBrand;
import com.weiwei.jixieche.constant.ErrorCodeConstants;
import com.weiwei.jixieche.mapper.JxcMarketBrandMapper;
import com.weiwei.jixieche.page.PageUtils;
import com.weiwei.jixieche.response.ResponseMessage;
import com.weiwei.jixieche.response.ResponseMessageFactory;
import com.weiwei.jixieche.service.JxcMarketBrandService;
import com.weiwei.jixieche.verify.AssertUtil;
import java.util.List;
import javax.annotation.Resource;

import com.weiwei.jixieche.verify.CollectionUtils;
import org.springframework.stereotype.Service;
/**
 * @ClassName dd
 * @Description TODO
 * @Author houji
 * @Date 2019/8/21 14:00
 * @Version 2.0
 **/
@Service("jxcMarketBrandService")
public class JxcMarketBrandServiceImpl implements JxcMarketBrandService {
       @Resource
       private JxcMarketBrandMapper jxcMarketBrandMapper;

       /**
        * 前端分页查询市场品牌字典表
        * @param pageNo  分页索引
        * @param pageSize  每页显示数量
        * @param jxcMarketBrand 查询条件
        * @return
        */
       @Override
       public ResponseMessage<JxcMarketBrand> findByPageForFront(Integer pageNo, Integer pageSize, JxcMarketBrand jxcMarketBrand) {
               ResponseMessage result = ResponseMessageFactory.newInstance();
              PageHelper.startPage(pageNo,pageSize);
              List<JxcMarketBrand> list = this.jxcMarketBrandMapper.selectListByConditions(jxcMarketBrand);
              PageInfo<JxcMarketBrand> page = new PageInfo<>(list);
              result.setData(new PageUtils<>(page).getPageViewDatatable());
              return result;
       }

       @Override
       public ResponseMessage<JxcMarketBrand> query() {
              ResponseMessage result = ResponseMessageFactory.newInstance();
              List<JxcMarketBrand> list = this.jxcMarketBrandMapper.queryChildBrandList();
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
        * 添加市场品牌字典表
        * @param  jxcMarketBrand
        * @return
        */
       @Override
       public ResponseMessage<JxcMarketBrand> addObj(JxcMarketBrand jxcMarketBrand) {
              ResponseMessage result = ResponseMessageFactory.newInstance();
              int res=this.jxcMarketBrandMapper.insertSelective(jxcMarketBrand);
              AssertUtil.numberGtZero(res,ErrorCodeConstants.ADD_ERORR.getDescript(),ErrorCodeConstants.ADD_ERORR.getId());
              return result;
       }

       /**
        * 修改市场品牌字典表
        * @param jxcMarketBrand
        * @return
        */
       @Override
       public ResponseMessage<JxcMarketBrand> modifyObj(JxcMarketBrand jxcMarketBrand) {
              ResponseMessage result = ResponseMessageFactory.newInstance();
              int res=this.jxcMarketBrandMapper.updateByPrimaryKeySelective(jxcMarketBrand);
              AssertUtil.numberGtZero(res,ErrorCodeConstants.EDIT_ERORR.getDescript(),ErrorCodeConstants.EDIT_ERORR.getId());
              return result;
       }

       /**
        * 根据ID查询市场品牌字典表
        * @param id
        * @return
        */
       @Override
       public ResponseMessage<JxcMarketBrand> queryObjById(int id) {
              ResponseMessage result = ResponseMessageFactory.newInstance();
              JxcMarketBrand model=this.jxcMarketBrandMapper.selectByPrimaryKey(id);
              AssertUtil.notNull(model,ErrorCodeConstants.QUERY_EMPTY_DATA.getDescript(),ErrorCodeConstants.QUERY_EMPTY_DATA.getId());
              result.setData(model);
              return result;
       }
}