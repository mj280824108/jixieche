package com.weiwei.jixieche.mapper;

import com.weiwei.jixieche.bean.JxcInforInformation;
import com.weiwei.jixieche.bean.JxcMarketCollectionRecord;
import com.weiwei.jixieche.vo.UserCollectionInfoVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @ClassName dd
 * @Description TODO
 * @Author houji
 * @Date 2019/8/21 14:00
 * @Version 2.0
 **/
public interface JxcMarketCollectionRecordMapper extends BaseMapper<JxcMarketCollectionRecord> {

    /**
     * 根据id查询资讯的信息
     * @param id
     * @return
     */
    JxcInforInformation selectInforById(@Param("id")Integer id);

    /**
     * 查询用户收藏记录列表
     * @param userCollectionInfoVo
     * @return
     */
    List<UserCollectionInfoVo> queryUserCollectionRecordList(UserCollectionInfoVo userCollectionInfoVo);

    /**
     * 查询店铺用户收藏人数
     * @param shopId
     * @return
     */
    Integer queryShopCollectionCount(@Param("shopId")Integer shopId);

}