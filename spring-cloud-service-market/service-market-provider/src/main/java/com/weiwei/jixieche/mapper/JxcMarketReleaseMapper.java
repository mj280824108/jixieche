package com.weiwei.jixieche.mapper;

import com.weiwei.jixieche.bean.JxcMarketRelease;
import com.weiwei.jixieche.vo.AreaNameVo;
import com.weiwei.jixieche.vo.JxcMarketReleaseVo;
import com.weiwei.jixieche.vo.MarketTradeTabVo;
import com.weiwei.jixieche.vo.UserShopsInfoVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @ClassName dd
 * @Description TODO
 * @Author houji
 * @Date 2019/8/21 14:00
 * @Version 2.0
 **/
public interface JxcMarketReleaseMapper extends BaseMapper<JxcMarketRelease> {


    /**
     * 市场交易筛选
     * @param marketTradeTabVo
     * @return
     */
    List<JxcMarketReleaseVo> queryMarketTrade(MarketTradeTabVo marketTradeTabVo);

    /**
     * 根据区查询省市名称
     * @param districtId
     * @return
     */
    AreaNameVo queryAreaName(@Param("districtId") Integer districtId);

    /**
     * 查询我的店铺中的机械
     */
    List<JxcMarketReleaseVo> queryOwnShopMachineList(UserShopsInfoVo userShopsInfoVo);

    /**
     * 根据区查询市的id
     * @param districtId
     * @return
     */
    Integer queryCityIdByDistrictId(@Param("districtId")Integer districtId);

}