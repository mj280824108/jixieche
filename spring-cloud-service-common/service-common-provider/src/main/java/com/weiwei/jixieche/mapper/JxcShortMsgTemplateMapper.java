package com.weiwei.jixieche.mapper;

import com.weiwei.jixieche.bean.JxcShortMsgTemplate;
import com.weiwei.jixieche.vo.ShortMsgTemplate;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @ClassName ss
 * @Description TODO
 * @Author houji
 * @Date 2019/8/12 9:36
 * @Version 1.0.1
 **/
public interface JxcShortMsgTemplateMapper extends BaseMapper<JxcShortMsgTemplate> {

    /**
     * 查询所有的短信模板集合
     * @return
     */
    List<ShortMsgTemplate> selectShortMsgTemplateList();

    /**
     * 根据阿里云模板code查询模板信息
     * @param templateCode
     * @return
     */
    JxcShortMsgTemplate queryByTemplateCode(@Param("templateCode")String templateCode);
}