package com.weiwei.jixieche.service;

import com.weiwei.jixieche.bean.JxcMarketShops;
import com.weiwei.jixieche.jwt.AuthorizationUser;
import com.weiwei.jixieche.response.ResponseMessage;
import com.weiwei.jixieche.vo.SearchShopsListVo;
import com.weiwei.jixieche.vo.ShopDetailsVo;
import com.weiwei.jixieche.vo.ShopsPointNumberVo;
import com.weiwei.jixieche.vo.UserShopsInfoVo;

/**
 * @ClassName dd
 * @Description TODO
 * @Author houji
 * @Date 2019/8/21 14:00
 * @Version 2.0
 **/
public interface JxcMarketShopsService extends BaseService<JxcMarketShops> {

       /**
        * 用户开店
        * @param t
        * @return
        */
       ResponseMessage<JxcMarketShops> addObj(AuthorizationUser user, JxcMarketShops t);

       /**
     * 前端分页查询市场店铺表
     * @param pageNo  分页索引
     * @param pageSize  每页显示数量
     * @param jxcMarketShops 查询条件
     * @return 
     */
       ResponseMessage findByPageForFront(Integer pageNo, Integer pageSize, JxcMarketShops jxcMarketShops);

       /**
        * 店铺浏览量点击量+1操作
        * @param shopsPointNumberVo
        * @return
        */
       ResponseMessage operation(ShopsPointNumberVo shopsPointNumberVo);

       /**
        * 店铺搜索认证(距离,人气,标题)
        * @param searchShopsListVo
        * @return
        */
       ResponseMessage<SearchShopsListVo> searchShopsList(SearchShopsListVo searchShopsListVo);

       /**
        * 查询我的店铺
        * @param user
        * @param userShopsInfoVo
        * @return
        */
       ResponseMessage<UserShopsInfoVo> queryOwnShopsInfo(AuthorizationUser user,UserShopsInfoVo userShopsInfoVo);

       /**
        * 查询用户店铺是否存在
        */
       ResponseMessage queryUserShopsExist(AuthorizationUser user);

       /**
        * 通过ID查询市场店铺表
        * @param shopDetailsVo
        * @return
        */
       ResponseMessage<JxcMarketShops> queryObjById(ShopDetailsVo shopDetailsVo);


}