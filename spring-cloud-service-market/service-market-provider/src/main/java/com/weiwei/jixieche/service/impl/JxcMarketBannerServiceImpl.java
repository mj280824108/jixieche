package com.weiwei.jixieche.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.weiwei.jixieche.bean.JxcMarketBanner;
import com.weiwei.jixieche.constant.ErrorCodeConstants;
import com.weiwei.jixieche.mapper.JxcMarketBannerMapper;
import com.weiwei.jixieche.page.PageUtils;
import com.weiwei.jixieche.response.ResponseMessage;
import com.weiwei.jixieche.response.ResponseMessageFactory;
import com.weiwei.jixieche.service.JxcMarketBannerService;
import com.weiwei.jixieche.verify.AssertUtil;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

@Service("jxcMarketBannerService")
public class JxcMarketBannerServiceImpl implements JxcMarketBannerService {
       @Resource
       private JxcMarketBannerMapper jxcMarketBannerMapper;

       /**
        * 前端分页查询市场banner表
        * @param pageNo  分页索引
        * @param pageSize  每页显示数量
        * @param jxcMarketBanner 查询条件
        * @return
        */
       @Override
       public ResponseMessage<JxcMarketBanner> findByPageForFront(Integer pageNo, Integer pageSize, JxcMarketBanner jxcMarketBanner) {
               ResponseMessage result = ResponseMessageFactory.newInstance();
              PageHelper.startPage(pageNo,pageSize);
              List<JxcMarketBanner> list = this.jxcMarketBannerMapper.selectListByConditions(jxcMarketBanner);
              PageInfo<JxcMarketBanner> page = new PageInfo<>(list);
              result.setData(new PageUtils<>(page).getPageViewDatatable());
              return result;
       }

       /**
        * 添加市场banner表
        * @param  jxcMarketBanner
        * @return
        */
       @Override
       public ResponseMessage<JxcMarketBanner> addObj(JxcMarketBanner jxcMarketBanner) {
              ResponseMessage result = ResponseMessageFactory.newInstance();
              int res=this.jxcMarketBannerMapper.insertSelective(jxcMarketBanner);
              AssertUtil.numberGtZero(res,ErrorCodeConstants.ADD_ERORR.getDescript(),ErrorCodeConstants.ADD_ERORR.getId());
              return result;
       }

       /**
        * 修改市场banner表
        * @param jxcMarketBanner
        * @return
        */
       @Override
       public ResponseMessage<JxcMarketBanner> modifyObj(JxcMarketBanner jxcMarketBanner) {
              ResponseMessage result = ResponseMessageFactory.newInstance();
              int res=this.jxcMarketBannerMapper.updateByPrimaryKeySelective(jxcMarketBanner);
              AssertUtil.numberGtZero(res,ErrorCodeConstants.EDIT_ERORR.getDescript(),ErrorCodeConstants.EDIT_ERORR.getId());
              return result;
       }

       /**
        * 根据ID查询市场banner表
        * @param id
        * @return
        */
       @Override
       public ResponseMessage<JxcMarketBanner> queryObjById(int id) {
              ResponseMessage result = ResponseMessageFactory.newInstance();
              JxcMarketBanner model=this.jxcMarketBannerMapper.selectByPrimaryKey(id);
              AssertUtil.notNull(model,ErrorCodeConstants.QUERY_EMPTY_DATA.getDescript(),ErrorCodeConstants.QUERY_EMPTY_DATA.getId());
              result.setData(model);
              return result;
       }
}