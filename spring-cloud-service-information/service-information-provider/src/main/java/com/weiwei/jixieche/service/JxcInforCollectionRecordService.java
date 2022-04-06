package com.weiwei.jixieche.service;

import com.weiwei.jixieche.bean.JxcInforCollectionRecord;
import com.weiwei.jixieche.response.ResponseMessage;
import com.weiwei.jixieche.vo.InforCollectionRecordVo;

/**
 * @ClassName s
 * @Description TODO
 * @Author houji
 * @Date 2019/8/20 16:32
 * @Version 2.0
 **/
public interface JxcInforCollectionRecordService extends BaseService<JxcInforCollectionRecord> {

       /**
        * 查询用户收藏资讯信息
        * @param inforCollectionRecordVo
        * @return
        */
       ResponseMessage<InforCollectionRecordVo> queryUserCollection(InforCollectionRecordVo inforCollectionRecordVo);


}