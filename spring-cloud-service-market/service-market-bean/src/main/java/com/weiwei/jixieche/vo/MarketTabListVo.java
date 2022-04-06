package com.weiwei.jixieche.vo;

import com.weiwei.jixieche.bean.JxcMarketBrand;
import com.weiwei.jixieche.bean.JxcMarketMachineType;
import com.weiwei.jixieche.bean.JxcMarketResourceType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName MarketTabListVo
 * @Description TODO
 * @Author houji
 * @Date 2019/9/4 19:35
 * @Version 1.0.1
 **/
@Data
@EqualsAndHashCode
@ApiModel(value="市场搜索tab集合Vo")
public class MarketTabListVo implements Serializable {

    @ApiModelProperty("机械交易类型 求购/出售")
    private List<MachineTradeTypeVo> machineTradeType = new ArrayList<>();

    @ApiModelProperty("机械类型 机械类型")
    private List<MachineTypeChildVo> machineType = new ArrayList<>();

    @ApiModelProperty("机械交易新机二手机 新机/二手机")
    private List<MachineNewOldVo> machineNewOld = new ArrayList<>();

    @ApiModelProperty("机械价格搜索")
    private List<MachinePriceVo> machinePrice = new ArrayList<>();

    @ApiModelProperty("机械交易 筛选")
    private List<MachineTradeSwitchVo> machineSwitch = new ArrayList<>();

    @ApiModelProperty("租赁交易类型 求租/出租")
    private List<RentTradeTypeVo> rentTradeType = new ArrayList<>();

    @ApiModelProperty("租赁交易 日结/月结/台班结")
    private List<RentLeaseTypeVo> rentLeaseType = new ArrayList<>();

    @ApiModelProperty("租赁交易 筛选")
    private List rentTradeSwitch = new ArrayList<>();

    @ApiModelProperty("品牌")
    private List<JxcMarketBrand> brand = new ArrayList<>();

    @ApiModelProperty("资源交易 资源类型")
    private List<JxcMarketResourceType> sourceType = new ArrayList<>();

    @ApiModelProperty("资源交易类型 求购/出售")
    private List<SourceTradeTypeVo> sourceTradeType = new ArrayList<>();

    @ApiModelProperty("资源交易 筛选")
    private List sourceTradeSwitch = new ArrayList<>();

}
