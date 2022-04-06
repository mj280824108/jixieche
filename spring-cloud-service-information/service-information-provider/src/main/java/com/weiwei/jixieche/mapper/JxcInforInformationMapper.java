package com.weiwei.jixieche.mapper;

import com.weiwei.jixieche.bean.JxcInforInformation;
import com.weiwei.jixieche.vo.InforFireListVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

/**
 * @ClassName s
 * @Description TODO
 * @Author houji
 * @Date 2019/8/20 16:32
 * @Version 2.0
 **/
public interface JxcInforInformationMapper extends BaseMapper<JxcInforInformation> {

    /**
     * 查询分类的置顶项
     * @param jxcInforInformation
     * @return
     */
    JxcInforInformation queryTopInfor(JxcInforInformation jxcInforInformation);

    /**
     * 查询用户是否收藏
     */
    Integer queryCollection(@Param("map")Map<String,Object> map);

    /**
     * 用户点赞记录查询
     * @param map
     * @return
     */
    Integer queryPointId(@Param("map")Map<String,Object> map);

    /**
     * 资讯点赞量和浏览量
     * @param id
     * @return
     */
    Integer queryInfoCollectionNum(@Param("id") Integer id);
    Integer queryInfoPointNum(@Param("id") Integer id);

    /**
     * 查询机主首页轮播资讯
     * @param
     * @return
     */
    List<InforFireListVo> queryInforFireList();

    /**
     * 根据用户userId查询用户角色id
     */
    Integer queryRoleIdByUserId(@Param("userId")Integer userId);
}