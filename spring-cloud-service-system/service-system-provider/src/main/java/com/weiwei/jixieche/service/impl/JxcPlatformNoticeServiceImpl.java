package com.weiwei.jixieche.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.weiwei.jixieche.bean.JxcPlatformNotice;
import com.weiwei.jixieche.constant.ErrorCodeConstants;
import com.weiwei.jixieche.mapper.JxcPlatformNoticeMapper;
import com.weiwei.jixieche.page.PageUtils;
import com.weiwei.jixieche.response.ResponseMessage;
import com.weiwei.jixieche.response.ResponseMessageFactory;
import com.weiwei.jixieche.service.JxcPlatformNoticeService;
import com.weiwei.jixieche.verify.AssertUtil;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
/**
 * @ClassName UserRegisterVo
 * @Description TODO
 * @Author houji
 * @Date 2019/8/13 18:08
 * @Version 2.0
 **/
@Service("jxcPlatformNoticeService")
public class JxcPlatformNoticeServiceImpl implements JxcPlatformNoticeService {
       @Resource
       private JxcPlatformNoticeMapper jxcPlatformNoticeMapper;

       /**
        * 前端分页查询平台公告信息表
        * @param pageNo  分页索引
        * @param pageSize  每页显示数量
        * @param jxcPlatformNotice 查询条件
        * @return
        */
       @Override
       public ResponseMessage<JxcPlatformNotice> findByPageForFront(Integer pageNo, Integer pageSize, JxcPlatformNotice jxcPlatformNotice) {
               ResponseMessage result = ResponseMessageFactory.newInstance();
              PageHelper.startPage(pageNo,pageSize);
              List<JxcPlatformNotice> list = this.jxcPlatformNoticeMapper.selectListByConditions(jxcPlatformNotice);
              PageInfo<JxcPlatformNotice> page = new PageInfo<>(list);
              result.setData(new PageUtils<>(page).getPageViewDatatable());
              return result;
       }

       /**
        * 添加平台公告信息表
        * @param  jxcPlatformNotice
        * @return
        */
       @Override
       public ResponseMessage<JxcPlatformNotice> addObj(JxcPlatformNotice jxcPlatformNotice) {
              ResponseMessage result = ResponseMessageFactory.newInstance();
              int res=this.jxcPlatformNoticeMapper.insertSelective(jxcPlatformNotice);
              AssertUtil.numberGtZero(res,ErrorCodeConstants.ADD_ERORR.getDescript(),ErrorCodeConstants.ADD_ERORR.getId());
              return result;
       }

       /**
        * 修改平台公告信息表
        * @param jxcPlatformNotice
        * @return
        */
       @Override
       public ResponseMessage<JxcPlatformNotice> modifyObj(JxcPlatformNotice jxcPlatformNotice) {
              ResponseMessage result = ResponseMessageFactory.newInstance();
              int res=this.jxcPlatformNoticeMapper.updateByPrimaryKeySelective(jxcPlatformNotice);
              AssertUtil.numberGtZero(res,ErrorCodeConstants.EDIT_ERORR.getDescript(),ErrorCodeConstants.EDIT_ERORR.getId());
              return result;
       }

       /**
        * 根据ID查询平台公告信息表
        * @param id
        * @return
        */
       @Override
       public ResponseMessage<JxcPlatformNotice> queryObjById(int id) {
              ResponseMessage result = ResponseMessageFactory.newInstance();
              JxcPlatformNotice model=this.jxcPlatformNoticeMapper.selectByPrimaryKey(id);
              AssertUtil.notNull(model,ErrorCodeConstants.QUERY_EMPTY_DATA.getDescript(),ErrorCodeConstants.QUERY_EMPTY_DATA.getId());
              result.setData(model);
              return result;
       }
}