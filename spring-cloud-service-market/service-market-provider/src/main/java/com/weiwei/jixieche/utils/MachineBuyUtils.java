package com.weiwei.jixieche.utils;

import com.weiwei.jixieche.bean.JxcMarketMachineType;
import com.weiwei.jixieche.bean.JxcMarketRelease;
import com.weiwei.jixieche.mapper.JxcMarketMachineTypeMapper;
import com.weiwei.jixieche.verify.VerifyStr;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @ClassName MachineBuyUtils
 * @Description 机械求购发布验证
 * @Author houji
 * @Date 2019/9/6 15:44
 * @Version 1.0.1
 **/
@Component
public class MachineBuyUtils {

    @Resource
    private JxcMarketMachineTypeMapper jxcMarketMachineTypeMapper;

    /**
     * 必填系统参数校验
     * @param jxcMarketRelease
     * @return
     */
    public String verifyParam(JxcMarketRelease jxcMarketRelease){
        String result = null;
        if(jxcMarketRelease.getRealeseType() == null){
            result = "发布类型不能为空";
            return result;
        }
        if(jxcMarketRelease.getRealeseType() != JxcMarketRelease.ReleaseType.MACHINE_BUY
                && jxcMarketRelease.getRealeseType() != JxcMarketRelease.ReleaseType.MACHINE_SALE
                && jxcMarketRelease.getRealeseType() != JxcMarketRelease.ReleaseType.MACHINE_RENT_IN
                && jxcMarketRelease.getRealeseType() != JxcMarketRelease.ReleaseType.MACHINE_RENT_OUT
                && jxcMarketRelease.getRealeseType() != JxcMarketRelease.ReleaseType.SOURCE_BUY
                && jxcMarketRelease.getRealeseType() != JxcMarketRelease.ReleaseType.SOURCE_SALE){
            result = "发布类型不识别";
            return result;
        }
        if(jxcMarketRelease.getTitle() == null){
            result = "发布标题不能为空";
            return result;
        }
        if(jxcMarketRelease.getPersonName() == null){
            result = "联系人姓名不能为空";
            return result;
        }
        if(jxcMarketRelease.getPersonPhone() == null){
            result = "联系人电话不能为空";
            return result;
        }
        if(VerifyStr.isPhone(jxcMarketRelease.getPersonPhone()) != true){
            result = "联系人电话必须是数字";
            return result;
        }
        //当发布机械类型id不能为空，则机械类型必须是小类
        if(jxcMarketRelease.getMachineTypeId() != null){
            JxcMarketMachineType jxcMarketMachineType =  this.jxcMarketMachineTypeMapper.selectByPrimaryKey(jxcMarketRelease.getMachineTypeId());
            if(jxcMarketMachineType.getCode().equals("0")){
                result = "机械类型必须是小类";
                return result;
            }
        }

        //各种类型发布验证
        //发布类型(1--机械求购 2--机械出售 3--机械求租 4--机械出租 5--资源求购 6--资源出售 7--其他)
        if(jxcMarketRelease.getRealeseType() == JxcMarketRelease.ReleaseType.MACHINE_BUY){
            result = this.verifyMachineBuyParam(jxcMarketRelease);
        }
        if(jxcMarketRelease.getRealeseType() == JxcMarketRelease.ReleaseType.MACHINE_SALE){
            result = this.verifyMachineSaleParam(jxcMarketRelease);
        }
        if(jxcMarketRelease.getRealeseType() == JxcMarketRelease.ReleaseType.MACHINE_RENT_IN){
            result = this.verifyMachineRentInParam(jxcMarketRelease);
        }
        if(jxcMarketRelease.getRealeseType() == JxcMarketRelease.ReleaseType.MACHINE_RENT_OUT){
            result = this.verifyMachineRentOutParam(jxcMarketRelease);
        }
        if(jxcMarketRelease.getRealeseType() == JxcMarketRelease.ReleaseType.SOURCE_BUY){
            result = this.verifySourceRentParam(jxcMarketRelease);
        }
        if(jxcMarketRelease.getRealeseType() == JxcMarketRelease.ReleaseType.SOURCE_SALE){
            result = this.verifySourceSaleParam(jxcMarketRelease);
        }
        return result;
    }

    /**
     * 验证机械求购参数
     * @param jxcMarketRelease
     * @return
     */
    public String verifyMachineBuyParam(JxcMarketRelease jxcMarketRelease){
        String result = null;
        if(jxcMarketRelease.getMachineTypeId() == null){
            result = "机械求购机械类型不能为空";
            return result;
        }
        if(jxcMarketRelease.getNewDegreeType() == null){
            result = "机械求购新旧程度不能为空";
            return result;
        }
        if(jxcMarketRelease.getBrandTypeId() == null){
            result = "机械求购品牌类型不能为空";
            return result;
        }
        if(jxcMarketRelease.getDistrictId() == null){
            result = "机械求购省市区的区id不能为空";
            return result;
        }
        if(jxcMarketRelease.getBuyType() == null){
            result = "机械求购价格类型不能为空";
            return result;
        }
        if(jxcMarketRelease.getParkeAddress() == null){
            result = "机械求购详细地址不能为空";
            return result;
        }

        return result;
    }

    /**
     * 验证机械出售参数
     * @param jxcMarketRelease
     * @return
     */
    public String verifyMachineSaleParam(JxcMarketRelease jxcMarketRelease){
        String result = null;
        if(jxcMarketRelease.getMachineTypeId() == null){
            result = "机械出售机械类型不能为空";
            return result;
        }
        if(jxcMarketRelease.getNewDegreeType() == null){
            result = "机械出售新旧程度不能为空";
            return result;
        }
        if(jxcMarketRelease.getProductTime() == null){
            result = "机械出售出厂日期不能为空";
            return result;
        }
        if(jxcMarketRelease.getBrandTypeId() == null){
            result = "机械出售品牌类型不能为空";
            return result;
        }
        if(jxcMarketRelease.getParkeAddress() == null){
            result = "机械出售停放地址不能为空";
            return result;
        }
        if(jxcMarketRelease.getBuyType() == null){
            result = "机械出售价格类型不能为空";
            return result;
        }
        if(jxcMarketRelease.getParkeAddress() == null){
            result = "机械出售详细地址不能为空";
            return result;
        }
        return result;
    }

    /**
     * 验证机械求租参数
     * @param jxcMarketRelease
     * @return
     */
    public String verifyMachineRentInParam(JxcMarketRelease jxcMarketRelease){
        String result = null;
        if(jxcMarketRelease.getMachineTypeId() == null){
            result = "机械求租机械类型不能为空";
            return result;
        }
        if(jxcMarketRelease.getEstimateProjectTime() == null){
            result = "机械求租预计工期天数不能为空";
            return result;
        }
        if(jxcMarketRelease.getProjectStartTime() == null){
            result = "机械求租进程日期不能为空";
            return result;
        }
        if(jxcMarketRelease.getRentType() == null){
            result = "机械求租租赁方式不能为空";
            return result;
        }
        if(jxcMarketRelease.getDistrictId() == null){
            result = "机械求租施工地址省市区区的id不能为空";
            return result;
        }
        if(jxcMarketRelease.getParkeAddress() == null){
            result = "机械求租详细地址不能为空";
            return result;
        }
        return result;
    }

    /**
     * 验证机械出租参数
     * @param jxcMarketRelease
     * @return
     */
    public String verifyMachineRentOutParam(JxcMarketRelease jxcMarketRelease){
        String result = null;
        if(jxcMarketRelease.getMachineTypeId() == null){
            result = "机械出租机械类型不能为空";
            return result;
        }
        if(jxcMarketRelease.getProductTime() == null){
            result = "机械出租出厂日期不能为空";
            return result;
        }
        if(jxcMarketRelease.getBrandTypeId() == null){
            result = "机械出租机械品牌类型不能为空";
            return result;
        }
        if(jxcMarketRelease.getParkeAddress() == null){
            result = "机械出租停放地址不能为空";
            return result;
        }
        if(jxcMarketRelease.getDistrictId() == null){
            result = "机械出租地址省市区区的id不能为空";
            return result;
        }
        if(jxcMarketRelease.getLeaseHourPrice() == null
                && jxcMarketRelease.getLeaseMonthPrice() == null
                && jxcMarketRelease.getLeaseTeamPrice() == null){
            result = "机械出租价格设置不能为空";
            return result;
        }
        if(jxcMarketRelease.getRentType() == null ) {
            result = "机械出租方式不能为空";
            return result;
        }
        if(jxcMarketRelease.getParkeAddress() == null){
            result = "机械出租详细地址不能为空";
            return result;
        }

        return result;
    }

    /**
     * 验证资源出售参数
     * @param jxcMarketRelease
     * @return
     */
    public String verifySourceSaleParam(JxcMarketRelease jxcMarketRelease){
        String result = null;
        if(jxcMarketRelease.getSourceId() == null){
            result = "资源出售资源种类不能为空";
            return result;
        }
        if(jxcMarketRelease.getDistrictId() == null){
            result = "资源出售资源地址省市区区的地址不能为空";
            return result;
        }
        if(jxcMarketRelease.getSourceType() == null){
            result = "资源出售价格类型不能为空";
            return result;
        }
        if(jxcMarketRelease.getParkeAddress() == null){
            result = "资源出售详细地址不能为空";
            return result;
        }
        return result;
    }
    /**
     * 验证资源求购
     * @param jxcMarketRelease
     * @return
     */
    public String verifySourceRentParam(JxcMarketRelease jxcMarketRelease){
        String result = null;
        if(jxcMarketRelease.getSourceId() == null){
            result = "资源求购资源种类不能为空";
            return result;
        }
        if(jxcMarketRelease.getDistrictId() == null){
            result = "资源求购资源地址省市区区的地址不能为空";
            return result;
        }
        if(jxcMarketRelease.getSourceType() == null){
            result = "资源求购价格类型不能为空";
            return result;
        }
        if(jxcMarketRelease.getParkeAddress() == null){
            result = "资源求购详细地址不能为空";
            return result;
        }
        return result;
    }

}
