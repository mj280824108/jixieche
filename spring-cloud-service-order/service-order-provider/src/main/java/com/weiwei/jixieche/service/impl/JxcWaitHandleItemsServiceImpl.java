package com.weiwei.jixieche.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.weiwei.jixieche.bean.JxcMachine;
import com.weiwei.jixieche.bean.JxcWaitHandleItems;
import com.weiwei.jixieche.constant.ErrorCodeConstants;
import com.weiwei.jixieche.jwt.AuthorizationUser;
import com.weiwei.jixieche.mapper.DriverMapper;
import com.weiwei.jixieche.mapper.JxcClockInOutRecordMapper;
import com.weiwei.jixieche.mapper.JxcMachineRefDriverMapper;
import com.weiwei.jixieche.mapper.JxcWaitHandleItemsMapper;
import com.weiwei.jixieche.page.PageUtils;
import com.weiwei.jixieche.response.ResponseMessage;
import com.weiwei.jixieche.response.ResponseMessageFactory;
import com.weiwei.jixieche.service.JxcWaitHandleItemsService;
import com.weiwei.jixieche.util.DataTypeEmptyUtils;
import com.weiwei.jixieche.util.DateUtils;
import com.weiwei.jixieche.utils.JxcClockUtils;
import com.weiwei.jixieche.verify.AssertUtil;
import com.weiwei.jixieche.verify.CollectionUtils;
import com.weiwei.jixieche.verify.VerifyStr;
import com.weiwei.jixieche.vo.ClockRecord;
import com.weiwei.jixieche.vo.ClockRecordVo;
import com.weiwei.jixieche.vo.JxcShortDriverPayVo;
import com.weiwei.jixieche.vo.JxcWaitHandleItemsVo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description: 待办事项
 * @Author: liuy
 * @Date: 2019/8/24 10:09
 */
@Service("jxcWaitHandleItemsService")
public class JxcWaitHandleItemsServiceImpl implements JxcWaitHandleItemsService {
    @Resource
    private JxcWaitHandleItemsMapper jxcWaitHandleItemsMapper;

    @Autowired
    private JxcClockUtils jxcClockUtils;

    @Resource
    private JxcClockInOutRecordMapper jxcClockInOutRecordMapper;

    @Resource
    private DriverMapper driverMapper;

    @Resource
    private JxcMachineRefDriverMapper jxcMachineRefDriverMapper;

    /**
     * 添加待处理事项
     *
     * @param jxcWaitHandleItems
     * @return
     */
    @Override
    public ResponseMessage<JxcWaitHandleItems> addObj(JxcWaitHandleItems jxcWaitHandleItems) {
        ResponseMessage result = ResponseMessageFactory.newInstance();
        int res = this.jxcWaitHandleItemsMapper.insertSelective(jxcWaitHandleItems);
        AssertUtil.numberGtZero(res, ErrorCodeConstants.ADD_ERORR.getDescript(), ErrorCodeConstants.ADD_ERORR.getId());
        return result;
    }

    /**
     * 修改待处理事项
     *
     * @param jxcWaitHandleItems
     * @return
     */
    @Override
    public ResponseMessage<JxcWaitHandleItems> modifyObj(JxcWaitHandleItems jxcWaitHandleItems) {
        ResponseMessage result = ResponseMessageFactory.newInstance();
        jxcWaitHandleItems.setIsDeleted(1);
        int res = this.jxcWaitHandleItemsMapper.updateByPrimaryKeySelective(jxcWaitHandleItems);
        AssertUtil.numberGtZero(res, ErrorCodeConstants.EDIT_ERORR.getDescript(), ErrorCodeConstants.EDIT_ERORR.getId());
        return result;
    }

    /**
     * 根据ID查询待处理事项
     *
     * @param id
     * @return
     */
    @Override
    public ResponseMessage<JxcWaitHandleItems> queryObjById(int id) {
        ResponseMessage result = ResponseMessageFactory.newInstance();
        JxcWaitHandleItems model = this.jxcWaitHandleItemsMapper.selectByPrimaryKey(id);
        AssertUtil.notNull(model, ErrorCodeConstants.QUERY_EMPTY_DATA.getDescript(), ErrorCodeConstants.QUERY_EMPTY_DATA.getId());
        result.setData(model);
        return result;
    }

    /**
     * 机主待办事项列表
     *
     * @param ownerUserId
     * @return
     * @author liuy
     * @date 2019/8/24 10:24
     */
    @Override
    public ResponseMessage getOwnerHandelItemsList(Integer ownerUserId, Integer pageNo, Integer pageSize) {
        Map<String, Object> resultMap = new HashMap<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        //待办事项(上班司机设置和未添加司机)
        List<JxcWaitHandleItems> waitHandleItemsList = this.jxcWaitHandleItemsMapper.getHandelItemsList(ownerUserId);
        resultMap.put("waitHandleItemsList", waitHandleItemsList);

        //兼职司机工资支付
        PageHelper.startPage(pageNo, pageSize);
        List<JxcShortDriverPayVo> list = this.jxcWaitHandleItemsMapper.getDriverNoPayList(ownerUserId);
        if (!CollectionUtils.isEmpty(list)) {
            list.stream().forEach(jxcShortDriverPayVo -> {
                if(jxcShortDriverPayVo.getPayMoney() != null){
                    jxcShortDriverPayVo.setPayMoney(DataTypeEmptyUtils.emptyBigDecimalMoney(jxcShortDriverPayVo.getPayMoney()));
                }
            });
        }
        PageInfo<JxcShortDriverPayVo> page = new PageInfo<>(list);
        resultMap.put("shortDriverPayList", new PageUtils<>(page).getPageViewDatatable());
        return new ResponseMessage(resultMap);
    }

    /**
     * 正在跑趟中的司机未打上班卡
     *
     * @return
     * @author liuy
     * @date 2019/8/24 13:54
     */
    @Override
    public ResponseMessage getDriverNoClock() {
        //获取正在跑趟中的机械和司机
        List<JxcWaitHandleItems> waitHandleItemsList = jxcWaitHandleItemsMapper.getMachineRunList();
        if (!CollectionUtils.isEmpty(waitHandleItemsList)) {
            waitHandleItemsList.stream().forEach(jxcWaitHandleItems -> {
                //查询是否正在上班中
                int count = driverMapper.getDriverWorkStateById(jxcWaitHandleItems.getDriverId());
                if (count == 0) {
                    //正在跑趟的司机没有打上班卡时写入待办事项中
                    jxcWaitHandleItems.setItemType(JxcWaitHandleItems.itemType.ITEMTYPE11);
                    jxcWaitHandleItemsMapper.insertSelective(jxcWaitHandleItems);
                }
            });
        }
        return new ResponseMessage();
    }

    /**
     * 正在进行中的订单所对应的机械没有绑定司机
     *
     * @return
     * @author liuy
     * @date 2019/8/24 13:54
     */
    @Override
    public ResponseMessage getOrderRunList(Integer machineId) {
        List<JxcWaitHandleItems> waitHandleItemsList = this.jxcWaitHandleItemsMapper.getOrderRunList(machineId);
        if (!CollectionUtils.isEmpty(waitHandleItemsList)) {
            waitHandleItemsList.stream().forEach(jxcWaitHandleItems -> {
                //该机械没有绑定司机
                Integer count = jxcMachineRefDriverMapper.getMachineBindDriver(jxcWaitHandleItems.getMachineId());
                if (count == 0) {
                    //正在进行中的订单所对应的机械没有绑定司机
                    jxcWaitHandleItems.setItemType(JxcWaitHandleItems.itemType.ITEMTYPE12);
                    int res = jxcWaitHandleItemsMapper.checkWaitHandleItemById(jxcWaitHandleItems);
                    if(res == 0) {
                        jxcWaitHandleItemsMapper.insertSelective(jxcWaitHandleItems);
                    }
                }
            });
        }
        return new ResponseMessage();
    }

    /**
     * 承租方查询待办事项列表
     * @param user
     * @param pageNo
     * @param pageSize
     * @return
     */
    @Override
    public ResponseMessage selectTenantryWaitHandleList(AuthorizationUser user,Integer pageNo,Integer pageSize){
        ResponseMessage result = ResponseMessageFactory.newInstance();
        PageHelper.startPage(pageNo,pageSize,"wh.item_type ASC, wh.create_time DESC");
        List<JxcWaitHandleItemsVo> jxcWaitHandleItemsVos = jxcWaitHandleItemsMapper.selectTenantryWaitHandle(user.getUserId());
        if(jxcWaitHandleItemsVos != null && jxcWaitHandleItemsVos.size() > 0){
            for (JxcWaitHandleItemsVo jxcWaitHandleItemsVo : jxcWaitHandleItemsVos){
                if(jxcWaitHandleItemsVo.getPayAmount() != null) {
                    jxcWaitHandleItemsVo.setPayAmountStr(VerifyStr.strToStr(jxcWaitHandleItemsVo.getPayAmount().toString()));
                }
            }
        }
        PageInfo<JxcWaitHandleItemsVo> page = new PageInfo<>(jxcWaitHandleItemsVos);
        result.setData(new PageUtils<>(page).getPageViewDatatable());
        return result;
    }
}