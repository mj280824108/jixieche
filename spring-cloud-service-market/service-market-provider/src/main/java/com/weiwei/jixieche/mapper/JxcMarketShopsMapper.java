package com.weiwei.jixieche.mapper;

import com.weiwei.jixieche.bean.JxcMarketShops;
import com.weiwei.jixieche.vo.SearchShopsListVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @ClassName dd
 * @Description TODO
 * @Author houji
 * @Date 2019/8/21 14:00
 * @Version 2.0
 **/
public interface JxcMarketShopsMapper extends BaseMapper<JxcMarketShops> {

    /**
     * 店铺搜索认证(距离,人气,标题)
     * @param searchShopsListVo
     * @return
     */
    List<SearchShopsListVo> searchShopsList(SearchShopsListVo searchShopsListVo);

    /**
     * 根据userId查询自己的店铺
     */
    JxcMarketShops queryShopsByUserId(@Param("userId") Integer userId);

    /**
     * 根据userId查询用户头像作为店铺头像
     * @param userId
     * @return
     */
    String queryHeadImgByUserId(@Param("userId")Integer userId);

    /**
     * 根据店铺id查询开店人的userId
     * @param shopId
     * @return
     */
    Integer queryUserIdByShopId(@Param("shopId")Integer shopId);
}