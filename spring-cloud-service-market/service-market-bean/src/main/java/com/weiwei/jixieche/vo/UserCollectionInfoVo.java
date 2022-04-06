package com.weiwei.jixieche.vo;

import com.weiwei.jixieche.bean.JxcInforInformation;
import com.weiwei.jixieche.bean.JxcMarketRelease;
import com.weiwei.jixieche.bean.JxcMarketShops;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.List;

/**
 * @ClassName ShopsPointNumber
 * @Description TODO
 * @Author houji
 * @Date 2019/8/22 10:43
 * @Version 1.0.1
 **/
@Data
@EqualsAndHashCode
@ApiModel(value="查询用户收藏信息Vo")
public class UserCollectionInfoVo implements Serializable {

    @ApiModelProperty("页码数")
    private Integer pageNo = 1;

    @ApiModelProperty("每页条数")
    private Integer pageSize = 10;

    @ApiModelProperty("用户userId")
    private Integer userId;

    @ApiModelProperty("收藏类型(1--机械 2--店铺 3--资讯),第一次默认1")
    private Integer collectionType;

    @ApiModelProperty("发布类型(1--机械求购 2--机械出售 3--机械求租 4--机械出租)，第一次请求默认1")
    private Integer realseType;

    @ApiModelProperty("收藏机械集合List")
    private JxcMarketRelease marketRelease;

    @ApiModelProperty("收藏店铺集合List")
    private JxcMarketShops marketShops;

    @ApiModelProperty("收藏机械集合List")
    private JxcInforInformation inforInformation;

    @ApiModelProperty("发布商品的id")
    private Integer markeRealseId;

    @ApiModelProperty("收藏的资讯id")
    private Integer informationId;

    @ApiModelProperty("收藏店铺id")
    private Integer shopId;

    /**
     * 1--机械 2--店铺 3--资讯
     */
    public interface collectionType{
        Integer MACHINE = 1;
        Integer SHOP = 2;
        Integer INFOR = 3;
    }

}
