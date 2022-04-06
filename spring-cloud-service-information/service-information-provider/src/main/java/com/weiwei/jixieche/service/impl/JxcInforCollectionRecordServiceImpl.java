package com.weiwei.jixieche.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.weiwei.jixieche.bean.JxcInforCollectionRecord;
import com.weiwei.jixieche.constant.ErrorCodeConstants;
import com.weiwei.jixieche.mapper.JxcInforCollectionRecordMapper;
import com.weiwei.jixieche.page.PageUtils;
import com.weiwei.jixieche.response.ResponseMessage;
import com.weiwei.jixieche.response.ResponseMessageFactory;
import com.weiwei.jixieche.service.JxcInforCollectionRecordService;
import com.weiwei.jixieche.verify.AssertUtil;
import java.util.List;
import javax.annotation.Resource;

import com.weiwei.jixieche.vo.InforCollectionRecordVo;
import org.springframework.stereotype.Service;
/**
 * @ClassName s
 * @Description TODO
 * @Author houji
 * @Date 2019/8/20 16:32
 * @Version 2.0
 **/
@Service("jxcInforCollectionRecordService")
public class JxcInforCollectionRecordServiceImpl implements JxcInforCollectionRecordService {
       @Resource
       private JxcInforCollectionRecordMapper jxcInforCollectionRecordMapper;

       /**
        * 添加资讯信息收藏记录表
        * @param  jxcInforCollectionRecord
        * @return
        */
       @Override
       public ResponseMessage<JxcInforCollectionRecord> addObj(JxcInforCollectionRecord jxcInforCollectionRecord) {
              ResponseMessage result = ResponseMessageFactory.newInstance();
              int res=this.jxcInforCollectionRecordMapper.insertSelective(jxcInforCollectionRecord);
              AssertUtil.numberGtZero(res,ErrorCodeConstants.ADD_ERORR.getDescript(),ErrorCodeConstants.ADD_ERORR.getId());
              return result;
       }

       /**
        * 修改资讯信息收藏记录表
        * @param jxcInforCollectionRecord
        * @return
        */
       @Override
       public ResponseMessage<JxcInforCollectionRecord> modifyObj(JxcInforCollectionRecord jxcInforCollectionRecord) {
              ResponseMessage result = ResponseMessageFactory.newInstance();
              int res=this.jxcInforCollectionRecordMapper.updateByPrimaryKeySelective(jxcInforCollectionRecord);
              AssertUtil.numberGtZero(res,ErrorCodeConstants.EDIT_ERORR.getDescript(),ErrorCodeConstants.EDIT_ERORR.getId());
              return result;
       }

       /**
        * 根据ID查询资讯信息收藏记录表
        * @param id
        * @return
        */
       @Override
       public ResponseMessage<JxcInforCollectionRecord> queryObjById(int id) {
              ResponseMessage result = ResponseMessageFactory.newInstance();
              JxcInforCollectionRecord model=this.jxcInforCollectionRecordMapper.selectByPrimaryKey(id);
              AssertUtil.notNull(model,ErrorCodeConstants.QUERY_EMPTY_DATA.getDescript(),ErrorCodeConstants.QUERY_EMPTY_DATA.getId());
              result.setData(model);
              return result;
       }

       /**
        * 查询用户收藏资讯信息
        * @param inforCollectionRecordVo
        * @return
        */
       @Override
       public ResponseMessage<InforCollectionRecordVo> queryUserCollection(InforCollectionRecordVo inforCollectionRecordVo) {
              ResponseMessage result = ResponseMessageFactory.newInstance();
              InforCollectionRecordVo model=this.jxcInforCollectionRecordMapper.queryUserCollection(inforCollectionRecordVo);
              AssertUtil.notNull(model,ErrorCodeConstants.QUERY_EMPTY_DATA.getDescript(),ErrorCodeConstants.QUERY_EMPTY_DATA.getId());
              result.setData(model);
              return result;
       }
}