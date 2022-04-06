package com.weiwei.jixieche.service;

import com.weiwei.jixieche.bean.JxcClockPair;
import com.weiwei.jixieche.response.ResponseMessage;
import com.weiwei.jixieche.vo.UpdateBatchClockPayStatusVo;
import org.springframework.web.bind.annotation.RequestParam;

public interface JxcClockPairService extends BaseService<JxcClockPair> {
       /**
     * 前端分页查询司机每一天的打卡记录与司机配对关联表
     * @param pageNo  分页索引
     * @param pageSize  每页显示数量
     * @param jxcClockPair 查询条件
     * @return 
     */
       ResponseMessage findByPageForFront(Integer pageNo, Integer pageSize, JxcClockPair jxcClockPair);

       /**
        * 批量更新台班支付状态
        * @param updateBatchClockPayStatusVo
        */
       void updateBatchClockPayStatus(UpdateBatchClockPayStatusVo updateBatchClockPayStatusVo);

       /**
        * 更新台班支付状态
        * @param clockId
        */
       void updateClockPayStatus(Long clockId);

       /**
        * 台班异常申诉
        * @param clockId
        * @param description 申诉原因
        * @return
        */
       ResponseMessage updateClockApplyStateById(Long clockId, String description);
}