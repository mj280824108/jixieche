package com.weiwei.jixieche.utils;

import com.weiwei.jixieche.vo.PushTemplateVo;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName TemplateCacheUtils
 * @Description TODO
 * @Author houji
 * @Date 2019/8/12 15:14
 * @Version 1.0.1
 **/
@Component
public class TemplateCacheUtils {

    private static Map<Integer,PushTemplateVo> pushTemplateMap = new HashMap<>();

    public Map<Integer,PushTemplateVo> getPushTemplateMap(){
        return pushTemplateMap;
    }

    public PushTemplateVo getPushTemplate(int templateCode){
        return pushTemplateMap.get(templateCode);
    }
}
