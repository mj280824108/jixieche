package com.weiwei.jixieche.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.weiwei.jixieche.bean.JxcInforInformation;
import com.weiwei.jixieche.bean.JxcMarketCollectionRecord;
import com.weiwei.jixieche.bean.JxcMarketRelease;
import com.weiwei.jixieche.bean.JxcMarketShops;
import com.weiwei.jixieche.constant.ErrorCodeConstants;
import com.weiwei.jixieche.mapper.JxcMarketCollectionRecordMapper;
import com.weiwei.jixieche.mapper.JxcMarketReleaseMapper;
import com.weiwei.jixieche.mapper.JxcMarketShopsMapper;
import com.weiwei.jixieche.page.PageUtils;
import com.weiwei.jixieche.response.ResponseMessage;
import com.weiwei.jixieche.response.ResponseMessageFactory;
import com.weiwei.jixieche.service.JxcMarketCollectionRecordService;
import com.weiwei.jixieche.verify.AssertUtil;
import java.util.List;
import javax.annotation.Resource;

import com.weiwei.jixieche.verify.CollectionUtils;
import com.weiwei.jixieche.vo.AreaNameVo;
import com.weiwei.jixieche.vo.UserCollectionInfoVo;
import org.springframework.stereotype.Service;
/**
 * @ClassName dd
 * @Description TODO
 * @Author houji
 * @Date 2019/8/21 14:00
 * @Version 2.0
 **/
@Service("jxcMarketCollectionRecordService")
public class JxcMarketCollectionRecordServiceImpl implements JxcMarketCollectionRecordService {

       @Resource
       private JxcMarketCollectionRecordMapper jxcMarketCollectionRecordMapper;

       @Resource
       private JxcMarketReleaseMapper jxcMarketReleaseMapper;

       @Resource
       private JxcMarketShopsMapper jxcMarketShopsMapper;

       /**
        * 前端分页查询市场收藏记录
        * @param pageNo  分页索引
        * @param pageSize  每页显示数量
        * @param jxcMarketCollectionRecord 查询条件
        * @return
        */
       @Override
       public ResponseMessage<JxcMarketCollectionRecord> findByPageForFront(Integer pageNo, Integer pageSize, JxcMarketCollectionRecord jxcMarketCollectionRecord) {
               ResponseMessage result = ResponseMessageFactory.newInstance();
              PageHelper.startPage(pageNo,pageSize);
              List<JxcMarketCollectionRecord> list = this.jxcMarketCollectionRecordMapper.selectListByConditions(jxcMarketCollectionRecord);
              PageInfo<JxcMarketCollectionRecord> page = new PageInfo<>(list);
              result.setData(new PageUtils<>(page).getPageViewDatatable());
              return result;
       }


       /**
        * 添加市场收藏记录
        * @param  jxcMarketCollectionRecord
        * @return
        */
       @Override
       public ResponseMessage<JxcMarketCollectionRecord> addObj(JxcMarketCollectionRecord jxcMarketCollectionRecord) {
              ResponseMessage result = ResponseMessageFactory.newInstance();
              int res=this.jxcMarketCollectionRecordMapper.insertSelective(jxcMarketCollectionRecord);
              AssertUtil.numberGtZero(res,ErrorCodeConstants.ADD_ERORR.getDescript(),ErrorCodeConstants.ADD_ERORR.getId());
              return result;
       }

       /**
        * 修改市场收藏记录
        * @param jxcMarketCollectionRecord
        * @return
        */
       @Override
       public ResponseMessage<JxcMarketCollectionRecord> modifyObj(JxcMarketCollectionRecord jxcMarketCollectionRecord) {
              ResponseMessage result = ResponseMessageFactory.newInstance();
              int res=this.jxcMarketCollectionRecordMapper.updateByPrimaryKeySelective(jxcMarketCollectionRecord);
              AssertUtil.numberGtZero(res,ErrorCodeConstants.EDIT_ERORR.getDescript(),ErrorCodeConstants.EDIT_ERORR.getId());
              return result;
       }

       /**
        * 根据ID查询市场收藏记录
        * @param id
        * @return
        */
       @Override
       public ResponseMessage<JxcMarketCollectionRecord> queryObjById(int id) {
              ResponseMessage result = ResponseMessageFactory.newInstance();
              JxcMarketCollectionRecord model=this.jxcMarketCollectionRecordMapper.selectByPrimaryKey(id);
              AssertUtil.notNull(model,ErrorCodeConstants.QUERY_EMPTY_DATA.getDescript(),ErrorCodeConstants.QUERY_EMPTY_DATA.getId());
              result.setData(model);
              return result;
       }

       /**
        * 分页查询用户收藏资讯/机械/店铺信息
        * @param userCollectionInfoVo
        * @return
        */
       @Override
       public ResponseMessage<JxcMarketCollectionRecord> queryCollectionInfo(UserCollectionInfoVo userCollectionInfoVo) {
              ResponseMessage result = ResponseMessageFactory.newInstance();
              PageHelper.startPage(userCollectionInfoVo.getPageNo(),userCollectionInfoVo.getPageSize());
              List<UserCollectionInfoVo> list = this.jxcMarketCollectionRecordMapper.queryUserCollectionRecordList(userCollectionInfoVo);
              if(CollectionUtils.isEmpty(list)){
                     PageInfo<UserCollectionInfoVo> page = new PageInfo<>(list);
                     result.setData(new PageUtils<>(page).getPageViewDatatable());
                     return result;
              }else{
                     list.forEach(record -> {
                            if(userCollectionInfoVo.getCollectionType() == UserCollectionInfoVo.collectionType.INFOR){
                                   record.setInforInformation(this.jxcMarketCollectionRecordMapper.selectInforById(record.getInformationId()));
                            }else if(userCollectionInfoVo.getCollectionType() == UserCollectionInfoVo.collectionType.MACHINE){
                                   JxcMarketRelease jxcMarketRelease = this.jxcMarketReleaseMapper.selectByPrimaryKey(record.getMarkeRealseId());
                                   if(jxcMarketRelease.getDistrictId() != null){
                                          AreaNameVo areaNameVo = this.jxcMarketReleaseMapper.queryAreaName(jxcMarketRelease.getDistrictId());
                                          jxcMarketRelease.setAreaName(areaNameVo.getPName()+areaNameVo.getCName()+areaNameVo.getDName());
                                   }
                                   record.setMarketRelease(jxcMarketRelease);
                            }else if(userCollectionInfoVo.getCollectionType() == UserCollectionInfoVo.collectionType.SHOP){
                                   JxcMarketShops jxcMarketShops = this.jxcMarketShopsMapper.selectByPrimaryKey(record.getShopId());
                                   jxcMarketShops.setCollectionNumber(this.jxcMarketCollectionRecordMapper.queryShopCollectionCount(record.getShopId()));
                                   record.setMarketShops(jxcMarketShops);
                            }
                     });
                     PageInfo<UserCollectionInfoVo> page = new PageInfo<>(list);
                     result.setData(new PageUtils<>(page).getPageViewDatatable());
              }
              return result;
       }

       /**
        * 用户收藏/取消收藏资讯/机械/店铺
        * @param jxcMarketCollectionRecord
        * @return
        */
       @Override
       public ResponseMessage<JxcMarketCollectionRecord> editUserCollectionRecord(JxcMarketCollectionRecord jxcMarketCollectionRecord) {
              ResponseMessage result = ResponseMessageFactory.newInstance();
              if(jxcMarketCollectionRecord.getUserId() != null){
                     if(jxcMarketCollectionRecord.getInformationId() != null
                             || jxcMarketCollectionRecord.getShopId() != null
                             || jxcMarketCollectionRecord.getMarkeRealseId() != null){
                            List<JxcMarketCollectionRecord> list = this.jxcMarketCollectionRecordMapper.selectListByConditions(jxcMarketCollectionRecord);
                            //数据不存在，添加数据
                            if(CollectionUtils.isEmpty(list)){
                                   int res = this.jxcMarketCollectionRecordMapper.insertSelective(jxcMarketCollectionRecord);
                                   AssertUtil.numberGtZero(res,ErrorCodeConstants.ADD_ERORR.getDescript(),ErrorCodeConstants.ADD_ERORR.getId());
                                   return result;
                            }else{
                                   //数据存在，则修改
                                   jxcMarketCollectionRecord = list.get(0);
                                   JxcMarketCollectionRecord updateRecord = new JxcMarketCollectionRecord();
                                   if(jxcMarketCollectionRecord.getCollectionDisabled() == JxcMarketCollectionRecord.collectionDisabled.CANNEL){
                                          updateRecord.setId(jxcMarketCollectionRecord.getId());
                                          updateRecord.setCollectionDisabled(JxcMarketCollectionRecord.collectionDisabled.COLLECTION);
                                   }else{
                                          updateRecord.setId(jxcMarketCollectionRecord.getId());
                                          updateRecord.setCollectionDisabled(JxcMarketCollectionRecord.collectionDisabled.CANNEL);
                                   }
                                   int res=this.jxcMarketCollectionRecordMapper.updateByPrimaryKeySelective(updateRecord);
                                   AssertUtil.numberGtZero(res,ErrorCodeConstants.EDIT_ERORR.getDescript(),ErrorCodeConstants.EDIT_ERORR.getId());
                                   return result;
                            }
                     }else{
                            result.setCode(ErrorCodeConstants.PARAM_EMPTY_ERROR.getId());
                            result.setMessage("收藏的资讯/店铺/机械id不能为空");
                            return result;
                     }
              }else{
                     result.setCode(ErrorCodeConstants.PARAM_EMPTY_ERROR.getId());
                     result.setMessage("收藏用户不能为空");
                     return result;
              }
       }
}