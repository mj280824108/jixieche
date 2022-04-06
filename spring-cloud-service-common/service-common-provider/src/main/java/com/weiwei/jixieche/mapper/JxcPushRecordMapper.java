package com.weiwei.jixieche.mapper;

import com.weiwei.jixieche.bean.JxcPushRecord;
import com.weiwei.jixieche.vo.PushRecordTypeUnReadVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @ClassName ss
 * @Description TODO
 * @Author houji
 * @Date 2019/8/12 9:36
 * @Version 1.0.1
 **/
public interface JxcPushRecordMapper extends BaseMapper<JxcPushRecord> {

    /**
     * 查询用户未读消息类型列表
     * @param pushRecordTypeUnReadVo
     * @return
     */
    List<PushRecordTypeUnReadVo> queryUnReadPushRecordType(PushRecordTypeUnReadVo pushRecordTypeUnReadVo);

    /**
     * updateBatchPushRecordStatus
     * @param list
     */
    void updateBatchPushRecordStatus(@Param("list")List<Integer> list);

}