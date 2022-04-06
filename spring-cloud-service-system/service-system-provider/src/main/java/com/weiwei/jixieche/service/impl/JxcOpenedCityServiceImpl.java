package com.weiwei.jixieche.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.weiwei.jixieche.bean.JxcOpenedCity;
import com.weiwei.jixieche.bean.TransCosts;
import com.weiwei.jixieche.constant.ErrorCodeConstants;
import com.weiwei.jixieche.mapper.JxcOpenedCityMapper;
import com.weiwei.jixieche.mapper.JxcQuartzMapper;
import com.weiwei.jixieche.page.PageUtils;
import com.weiwei.jixieche.response.ResponseMessage;
import com.weiwei.jixieche.response.ResponseMessageFactory;
import com.weiwei.jixieche.service.JxcOpenedCityService;
import com.weiwei.jixieche.verify.AssertUtil;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @ClassName UserRegisterVo
 * @Description TODO
 * @Author houji
 * @Date 2019/8/13 18:08
 * @Version 2.0
 **/
@Service("jxcOpenedCityService")
public class JxcOpenedCityServiceImpl implements JxcOpenedCityService {
    @Resource
    private JxcOpenedCityMapper jxcOpenedCityMapper;

    @Autowired
    private JxcQuartzMapper jxcQuartzMapper;


    /**
     * 前端分页查询开放城市车载容量表
     *
     * @param pageNo        分页索引
     * @param pageSize      每页显示数量
     * @param jxcOpenedCity 查询条件
     * @return
     */
    @Override
    public ResponseMessage<JxcOpenedCity> findByPageForFront(Integer pageNo, Integer pageSize, JxcOpenedCity jxcOpenedCity) {
        ResponseMessage result = ResponseMessageFactory.newInstance();
        PageHelper.startPage(pageNo, pageSize);
        List<JxcOpenedCity> list = this.jxcOpenedCityMapper.selectListByConditions(jxcOpenedCity);
        PageInfo<JxcOpenedCity> page = new PageInfo<>(list);
        result.setData(new PageUtils<>(page).getPageViewDatatable());
        return result;
    }

    /**
     * 添加开放城市车载容量表
     *
     * @param jxcOpenedCity
     * @return
     */
    @Override
    public ResponseMessage<JxcOpenedCity> addObj(JxcOpenedCity jxcOpenedCity) {
        ResponseMessage result = ResponseMessageFactory.newInstance();
        int res = this.jxcOpenedCityMapper.insertSelective(jxcOpenedCity);
        AssertUtil.numberGtZero(res, ErrorCodeConstants.ADD_ERORR.getDescript(), ErrorCodeConstants.ADD_ERORR.getId());
        return result;
    }

    /**
     * 修改开放城市车载容量表
     *
     * @param jxcOpenedCity
     * @return
     */
    @Override
    public ResponseMessage<JxcOpenedCity> modifyObj(JxcOpenedCity jxcOpenedCity) {
        ResponseMessage result = ResponseMessageFactory.newInstance();
        int res = this.jxcOpenedCityMapper.updateByPrimaryKeySelective(jxcOpenedCity);
        AssertUtil.numberGtZero(res, ErrorCodeConstants.EDIT_ERORR.getDescript(), ErrorCodeConstants.EDIT_ERORR.getId());
        return result;
    }

    /**
     * 根据ID查询开放城市车载容量表
     *
     * @param id
     * @return
     */
    @Override
    public ResponseMessage<JxcOpenedCity> queryObjById(int id) {
        ResponseMessage result = ResponseMessageFactory.newInstance();
        JxcOpenedCity model = this.jxcOpenedCityMapper.selectByPrimaryKey(id);
        AssertUtil.notNull(model, ErrorCodeConstants.QUERY_EMPTY_DATA.getDescript(), ErrorCodeConstants.QUERY_EMPTY_DATA.getId());
        result.setData(model);
        return result;
    }

    /**
     * 查询开放城市的扣除佣金以后给到机主的实际金额
     * @param orderId
     * @param amount
     * @return
     */
    @Override
    public Integer getToOwnerAmount(Long orderId, Integer amount){
        int i = 0;
        //获取当前订单所在城市费率
        Map<String, Object> cityCodeAndEarthType = jxcQuartzMapper.getCityCodeByOrderId(orderId);
        TransCosts transCosts = new TransCosts();
        if (cityCodeAndEarthType != null || cityCodeAndEarthType.size() > 0) {
            transCosts = jxcQuartzMapper.getTransCosts(cityCodeAndEarthType);
        }
        BigDecimal divide1 = BigDecimal.ZERO;
        if (transCosts != null) {
            Integer platCommission = transCosts.getPlatCommission();
            if (platCommission != 0) {
                divide1 = new BigDecimal(platCommission).divide(new BigDecimal(100));
            }
        }
        if (new BigDecimal(amount).compareTo(BigDecimal.ZERO) > 0) {
            i = (new BigDecimal(amount).multiply(new BigDecimal(1).subtract(divide1))).setScale(0, BigDecimal.ROUND_HALF_UP).intValue();
        } else {
            i = 0;
        }
        return i;
    }
}