package com.weiwei.jixieche.utils;

import com.weiwei.jixieche.bean.JxcProjectType;
import com.weiwei.jixieche.mapper.JxcProjectTypeMapper;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//配置工程类型
@Component
public class JxcProjectTypeUtils {

    @Resource
    private JxcProjectTypeMapper jxcProjectTypeMapper;

    public List<JxcProjectType> typeList = new ArrayList<JxcProjectType>();

    public Map<Integer, JxcProjectType> typeMap = new HashMap<Integer,JxcProjectType>();

    public void init(){
        typeList = jxcProjectTypeMapper.selectListByConditions(null);
        for(JxcProjectType pt:typeList){
            typeMap.put(pt.getTypeId(), pt);
        }
    }
}
