package com.weiwei.jixieche.utils;

import com.weiwei.jixieche.vo.PushTemplateVo;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName PushTempateUtils
 * @Description TODO
 * @Author houji
 * @Date 2019/8/12 14:46
 * @Version 2.0
 **/
@Component
public class PushTempateUtils {

    public static Map<Integer,PushTemplateVo> pushTemplateVoMap = new HashMap<>();

    public Map<Integer,PushTemplateVo> getPushTemplateMap(){
        return pushTemplateVoMap;
    }

    public PushTemplateVo getPushTemplate(int templateCode){
        return pushTemplateVoMap.get(templateCode);
    }

}
