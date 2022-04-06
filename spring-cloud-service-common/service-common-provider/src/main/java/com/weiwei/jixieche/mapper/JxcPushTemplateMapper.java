package com.weiwei.jixieche.mapper;

import com.weiwei.jixieche.bean.JxcPushTemplate;
import com.weiwei.jixieche.vo.PushTemplateVo;

import java.util.List;

/**
 * @ClassName ss
 * @Description TODO
 * @Author houji
 * @Date 2019/8/12 9:36
 * @Version 1.0.1
 **/
public interface JxcPushTemplateMapper extends BaseMapper<JxcPushTemplate> {

    /**
     * 查询所有的推送模板(有父类)
     * @return
     */
    List<PushTemplateVo> selectPushTemplateList();

}