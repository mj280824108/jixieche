package com.weiwei.jixieche.service;

import com.weiwei.jixieche.bean.JxcClockInOutRecord;
import com.weiwei.jixieche.response.ResponseMessage;
import com.weiwei.jixieche.vo.ClockRecordListVo;
import com.weiwei.jixieche.vo.JxcDriverChangeWorkVo;
import org.apache.ibatis.annotations.Param;

public interface JxcClockInOutRecordService extends BaseService<JxcClockInOutRecord> {
       /**
     * 前端分页查询司机打卡记录表
     * @param pageNo  分页索引
     * @param pageSize  每页显示数量
     * @param jxcClockInOutRecord 查询条件
     * @return 
     */
       ResponseMessage findByPageForFront(Integer pageNo, Integer pageSize, JxcClockInOutRecord jxcClockInOutRecord);

       /**
        * 上班打卡
        * @author  liuy
        * @param jxcClockInOutRecord
        * @return
        * @date    2019/8/17 10:22
        */
       ResponseMessage clockIn(JxcClockInOutRecord jxcClockInOutRecord);

       /**
        * 下班打卡
        * @author  liuy
        * @param jxcClockInOutRecord
        * @return
        * @date    2019/8/17 11:03
        */
       ResponseMessage clockOut(JxcClockInOutRecord jxcClockInOutRecord);

       /**
        * 司机打卡记录
        * @author  liuy
        * @param clockRecordListVo
        * @return
        * @date    2019/8/17 13:49
        */
       ResponseMessage getClockListByDriver(ClockRecordListVo clockRecordListVo);

       /**
        * 司机跑趟中交接班
        * @author  liuy
        * @param jxcDriverChangeWorkVo
        * @return
        * @date    2019/8/24 14:43
        */
       ResponseMessage driverChangeWork(JxcDriverChangeWorkVo jxcDriverChangeWorkVo);

       /**
        * 机主设置司机上班
        * @author  liuy
        * @param jxcClockInOutRecord
        * @return
        * @date    2019/9/26 9:35
        */
       ResponseMessage setUpDriverWorkIn(JxcClockInOutRecord jxcClockInOutRecord);
}