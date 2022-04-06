package com.weiwei.jixieche.mapper;

import com.weiwei.jixieche.bean.JxcInforCollectionRecord;
import com.weiwei.jixieche.vo.InforCollectionRecordVo;

/**
 * @ClassName s
 * @Description TODO
 * @Author houji
 * @Date 2019/8/20 16:32
 * @Version 2.0
 **/
public interface JxcInforCollectionRecordMapper extends BaseMapper<JxcInforCollectionRecord> {

    /**
     * 查询用户收藏资讯信息
     * @param inforCollectionRecordVo
     * @return
     */
    InforCollectionRecordVo queryUserCollection(InforCollectionRecordVo inforCollectionRecordVo);
}