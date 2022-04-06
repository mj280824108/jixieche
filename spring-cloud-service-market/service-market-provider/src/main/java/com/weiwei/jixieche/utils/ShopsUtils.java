package com.weiwei.jixieche.utils;

import com.weiwei.jixieche.bean.JxcMarketShops;
import org.springframework.stereotype.Component;

/**
 * @ClassName ShopsUtils
 * @Description TODO
 * @Author houji
 * @Date 2019/9/21 14:24
 * @Version 1.0.1
 **/
@Component
public class ShopsUtils {
    /**
     * 必填系统参数校验
     * @param jxcMarketRelease
     * @return
     */
    public String verifyParam(JxcMarketShops jxcMarketRelease) {
        String result = null;
        if(jxcMarketRelease.getShopService() == null){
            result = "店铺服务不能为空";
            return result;
        }
        if(jxcMarketRelease.getShopName() == null){
            result = "店铺名称不能为空";
            return result;
        }
        if(jxcMarketRelease.getShopAreaId() == null){
            result = "店铺省市区区id不能为空";
            return result;
        }
        if(jxcMarketRelease.getShopAddress() == null){
            result = "店铺详细地址不能为空";
            return result;
        }
        if(jxcMarketRelease.getLicenceImgUrl() == null){
            result = "店铺营业执照不能为空";
            return result;
        }
        if(jxcMarketRelease.getShopSmallImgs() == null){
            result = "店铺图片不能为空";
            return result;
        }
        if(jxcMarketRelease.getPersonName() == null){
            result = "店铺联系人不能为空";
            return result;
        }
        if(jxcMarketRelease.getShopPhone() == null){
            result = "店铺联系电话不能为空";
            return result;
        }
        return result;
    }
}
