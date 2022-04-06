package com.weiwei.jixieche.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * @ClassName ShopsPointNumber
 * @Description TODO
 * @Author houji
 * @Date 2019/8/22 10:43
 * @Version 1.0.1
 **/
@Data
@EqualsAndHashCode
@ApiModel(value="店铺搜索认证(距离,人气,标题)Vo")
public class SearchShopsListVo implements Serializable {

    @ApiModelProperty("(搜索条件)页码数")
    private Integer pageNo = 1;

    @ApiModelProperty("(搜索条件)每页条数")
    private Integer pageSize = 10;

    @ApiModelProperty("店铺id")
    private Integer shopsId;

    @ApiModelProperty("(搜索条件)店铺名称")
    private String shopName;

    @ApiModelProperty("(搜索条件)是否认证(0--未认证  1--已认证)")
    private Integer confirmStatus = 1;

    @ApiModelProperty("(搜索条件)距离(1--由近及远  2--由远及近)")
    private Integer distanceType = 1;

    @ApiModelProperty("(搜索条件)人气(1--由高到低  2--由低到高)")
    private Integer viewType = 1;

    @ApiModelProperty("(搜索条件)经度")
    private String longitude;

    @ApiModelProperty("(搜索条件)纬度")
    private String latitude;

    @ApiModelProperty("(返回值)距离,M/米为单位")
    private Long distance;

    @ApiModelProperty("(返回值)收藏量")
    private Integer collectionNumber;

    @ApiModelProperty("店铺图片")
    private String shopBigImg;

    @ApiModelProperty("店铺小图url,用逗号隔开")
    private String shopSmallImgs;

    @ApiModelProperty("店主id")
    private Integer shopKeeperId;


}
