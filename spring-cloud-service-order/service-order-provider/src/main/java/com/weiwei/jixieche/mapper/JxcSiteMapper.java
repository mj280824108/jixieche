package com.weiwei.jixieche.mapper;

import com.weiwei.jixieche.bean.JxcSite;
import com.weiwei.jixieche.bean.JxcSiteBankAccount;
import com.weiwei.jixieche.bean.JxcSiteCouponOrder;
import com.weiwei.jixieche.bean.JxcSiteCouponType;
import com.weiwei.jixieche.vo.*;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

public interface JxcSiteMapper extends BaseMapper<JxcSite> {

    /**
     * 根据承租方用户ID查询使用过或者正在使用的消纳场列表（我的消纳场）
     * @param userId
     * @return
     */
    List<MySiteListVo> selectMySiteListByUserId(@Param("userId") Integer userId);

    /**
     * 查询银行账户信息
     * @param siteId
     * @return
     */
    JxcSiteBankAccount queryJxcSiteBankAccountBySiteId(@Param("siteId") Integer siteId);

    /**
     * 查询某个用户正在使用的订单
     * @param userId
     * @return
     */
    List<Integer> selectSiteIdList(@Param("userId") Integer userId);

    /**
     * 检查消纳场是否正在使用还是使用过
     * @param userId
     * @param siteId
     * @return
     */
    List<MySiteListVo> checkSiteState(@Param("userId") Integer userId,@Param("siteId") Integer siteId);

    /**
     * 查询某个消纳场的消纳券最低起价
     * @param siteId
     * @return
     */
    BigDecimal getLowerPrice(@Param("siteId") Integer siteId);

    /**
     * 根据消纳场ID查询消纳券类型
     * @param siteId
     * @return
     */
    List<JxcSiteCouponType> selectJxcSiteCouponTypeList(@Param("siteId") Integer siteId);

    /**
     * 插入订单表
     * @param siteOrderVo
     */
    void insertSiteOrder(@Param("siteOrder")JxcSiteOrderVo siteOrderVo);

    /**
     * 查询消纳券单价
     * @param couponTypeId
     * @return
     */
    JxcSiteCouponType getSiteCouponType(@Param("couponTypeId") Long couponTypeId);

    /**
     * 插入订单表明细表
     * @param siteOrderDetail
     */
    void insertSiteOrderDetail(@Param("siteOrderDetail")JxcSiteCouponOrderVo siteOrderDetail);

    /**
     * 查询用户在某个消纳场买的好土或者坏土券的数量
     * @param tenantryId
     * @param siteId
     * @param couponType
     * @return
     */
    Integer countSoilCoupon(@Param("tenantryId") Integer tenantryId,@Param("siteId") Integer siteId,@Param("couponType") Integer couponType);

    /**
     * 统计用户某个消纳场的实际总支出金额
     * @param tenantryId
     * @param siteId
     * @return
     */
    BigDecimal sumRealAmount(@Param("tenantryId") Integer tenantryId,@Param("siteId") Integer siteId);

    /**
     * 查询用户在某个消纳场下的订单数量
     * @param tenantryId
     * @param siteId
     * @return
     */
    Integer countCouponOrder(@Param("tenantryId") Integer tenantryId,@Param("siteId") Integer siteId);

    /**
     * 查询消纳场券订单列表
     * @param siteOrderVo
     * @return
     */
    List<JxcSiteOrderVo> selectSiteOrderList(@Param("siteOrderVo") JxcSiteOrderVo siteOrderVo );

    /**
     * 查询消纳场订单的明细
     * @param orderId
     * @return
     */
    List<JxcSiteCouponOrderVo> selectSiteCouponOrderList(@Param("orderId") Long orderId);

    /**
     * 查询消纳场订单详情
     * @param orderId
     * @return
     */
    SiteOrderDetailVo querySiteOrderDetailVoByOrderId(@Param("orderId") Long orderId);

    /**
     * 查询某个订单的发券时间
     * @param orderId
     * @return
     */
    String getGiveOutTime(@Param("orderId") Long orderId);

    /**
     * 取消消纳券订单
     * @param orderId
     */
    void cancelSiteOrder(@Param("orderId") Long orderId);

    /**
     * 查询消纳券张数
     * @param siteOrderVo
     * @param flag 1：未核销+待核销 2：已核销+已退卡
     * @param capacity 土方类型
     * @return
     */
    Integer countSoilCouponNum(@Param("siteOrderVo") JxcSiteOrderVo siteOrderVo,@Param("flag") Integer flag,@Param("capacity") Integer capacity);

    /**
     * 查询现有的消纳券的土方类型
     * @param siteOrderVo
     * @param flag 1：未核销+待核销 2：已核销+已退卡
     * @return
     */
    List<Integer> queryCouponCapacity(@Param("siteOrderVo") JxcSiteOrderVo siteOrderVo,@Param("flag") Integer flag);

    /**
     * 查询消纳券列表
     * @param siteOrderVo
     * @param flag 1：未核销+待核销 2：已核销+已退卡
     * @return
     */
    List<JxcSiteCouponVo> selectSiteCouponList(@Param("siteOrderVo") JxcSiteOrderVo siteOrderVo,@Param("flag") Integer flag);


    /**
     * app端消纳场管理员查询查询消纳券记录
     * @param siteId
     * @param tabFlag 1：正常 2：异常
     * @return
     */
    List<JxcSiteCouponVo> selectSiteCouponUsedList(@Param("siteId") Integer siteId ,@Param("tabFlag") Integer tabFlag);

    /**
     * 消纳场信息
     * @param userId
     * @return
     */
    JxcSite getSiteByUserId(@Param("userId") Integer userId);
}