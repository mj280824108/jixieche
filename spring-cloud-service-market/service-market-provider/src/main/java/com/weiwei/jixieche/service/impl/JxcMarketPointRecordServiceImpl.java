package com.weiwei.jixieche.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.weiwei.jixieche.bean.JxcMarketPointRecord;
import com.weiwei.jixieche.constant.ErrorCodeConstants;
import com.weiwei.jixieche.jwt.AuthorizationUser;
import com.weiwei.jixieche.mapper.JxcMarketPointRecordMapper;
import com.weiwei.jixieche.page.PageUtils;
import com.weiwei.jixieche.response.ResponseMessage;
import com.weiwei.jixieche.response.ResponseMessageFactory;
import com.weiwei.jixieche.service.JxcMarketPointRecordService;
import com.weiwei.jixieche.verify.AssertUtil;
import java.util.List;
import javax.annotation.Resource;

import com.weiwei.jixieche.verify.CollectionUtils;
import org.springframework.stereotype.Service;

@Service("jxcMarketPointRecordService")
public class JxcMarketPointRecordServiceImpl implements JxcMarketPointRecordService {
       @Resource
       private JxcMarketPointRecordMapper jxcMarketPointRecordMapper;

       /**
        * 前端分页查询用户点赞记录表
        * @param pageNo  分页索引
        * @param pageSize  每页显示数量
        * @param jxcMarketPointRecord 查询条件
        * @return
        */
       @Override
       public ResponseMessage<JxcMarketPointRecord> findByPageForFront(Integer pageNo, Integer pageSize, JxcMarketPointRecord jxcMarketPointRecord) {
               ResponseMessage result = ResponseMessageFactory.newInstance();
              PageHelper.startPage(pageNo,pageSize);
              List<JxcMarketPointRecord> list = this.jxcMarketPointRecordMapper.selectListByConditions(jxcMarketPointRecord);
              PageInfo<JxcMarketPointRecord> page = new PageInfo<>(list);
              result.setData(new PageUtils<>(page).getPageViewDatatable());
              return result;
       }

       /**
        * 添加用户点赞记录表
        * @param user
        * @param jxcMarketPointRecord
        * @return
        */
       @Override
       public ResponseMessage<JxcMarketPointRecord> addObj(AuthorizationUser user, JxcMarketPointRecord jxcMarketPointRecord) {
              ResponseMessage result = ResponseMessageFactory.newInstance();
              if(user != null && user.getUserId() != null){

                     JxcMarketPointRecord record = new JxcMarketPointRecord();
                     record.setUserId(user.getUserId());
                     if(jxcMarketPointRecord.getInforId() != null){
                            record.setInforId(jxcMarketPointRecord.getInforId());
                     }else if(jxcMarketPointRecord.getMarketReleaseId() != null){
                            record.setMarketReleaseId(jxcMarketPointRecord.getMarketReleaseId());
                     }else if(jxcMarketPointRecord.getShopId() != null){
                            record.setShopId(jxcMarketPointRecord.getShopId());
                     }else{
                            result.setCode(ErrorCodeConstants.PARAM_EMPTY_ERROR.getId());
                            result.setMessage("点赞的资讯/机械/店铺id不能都为空");
                            return result;
                     }
                     List<JxcMarketPointRecord> list =  this.jxcMarketPointRecordMapper.selectListByConditions(record);
                     if(CollectionUtils.isEmpty(list)){
                            //为空添加数据
                            int res=this.jxcMarketPointRecordMapper.insertSelective(jxcMarketPointRecord);
                            AssertUtil.numberGtZero(res,ErrorCodeConstants.ADD_ERORR.getDescript(),ErrorCodeConstants.ADD_ERORR.getId());
                            return result;
                     }else{
                            record =  list.get(0);
                            jxcMarketPointRecord.setId(record.getId());
                            int res=this.jxcMarketPointRecordMapper.updateByPrimaryKeySelective(jxcMarketPointRecord);
                            AssertUtil.numberGtZero(res,ErrorCodeConstants.EDIT_ERORR.getDescript(),ErrorCodeConstants.EDIT_ERORR.getId());
                            return result;
                     }
              }else{
                     result.setCode(ErrorCodeConstants.PARAM_EMPTY_ERROR.getId());
                     result.setMessage("token不存在或解析错误");
                     return result;
              }
       }

       /**
        * 添加用户点赞记录表
        * @param  jxcMarketPointRecord
        * @return
        */
       @Override
       public ResponseMessage<JxcMarketPointRecord> addObj(JxcMarketPointRecord jxcMarketPointRecord) {
              return null;
       }

       /**
        * 修改用户点赞记录表
        * @param jxcMarketPointRecord
        * @return
        */
       @Override
       public ResponseMessage<JxcMarketPointRecord> modifyObj(JxcMarketPointRecord jxcMarketPointRecord) {
              ResponseMessage result = ResponseMessageFactory.newInstance();
              int res=this.jxcMarketPointRecordMapper.updateByPrimaryKeySelective(jxcMarketPointRecord);
              AssertUtil.numberGtZero(res,ErrorCodeConstants.EDIT_ERORR.getDescript(),ErrorCodeConstants.EDIT_ERORR.getId());
              return result;
       }

       /**
        * 根据ID查询用户点赞记录表
        * @param id
        * @return
        */
       @Override
       public ResponseMessage<JxcMarketPointRecord> queryObjById(int id) {
              ResponseMessage result = ResponseMessageFactory.newInstance();
              JxcMarketPointRecord model=this.jxcMarketPointRecordMapper.selectByPrimaryKey(id);
              AssertUtil.notNull(model,ErrorCodeConstants.QUERY_EMPTY_DATA.getDescript(),ErrorCodeConstants.QUERY_EMPTY_DATA.getId());
              result.setData(model);
              return result;
       }
}