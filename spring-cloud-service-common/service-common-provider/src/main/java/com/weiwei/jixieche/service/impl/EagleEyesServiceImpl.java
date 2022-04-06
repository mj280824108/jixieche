package com.weiwei.jixieche.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.weiwei.jixieche.config.EagleEyesConfig;
import com.weiwei.jixieche.constant.ErrorCodeConstants;
import com.weiwei.jixieche.httprequest.HttpClientUtil;
import com.weiwei.jixieche.response.ResponseMessage;
import com.weiwei.jixieche.response.ResponseMessageFactory;
import com.weiwei.jixieche.service.EagleEyesService;
import com.weiwei.jixieche.vo.EagleEyesVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName EagleEyesService
 * @Description TODO
 * @Author houji
 * @Date 2019/5/27 11:20
 * @Version 1.0.1
 **/
@Service
public class EagleEyesServiceImpl implements EagleEyesService {

    @Autowired
    private EagleEyesConfig eagleEyesConfig;

    //百度鹰眼轨迹服务
    @Override
    public ResponseMessage baiduEntity(EagleEyesVo eagleEyesVo) {
        ResponseMessage result = ResponseMessageFactory.newInstance();
        //验证为空判断
        String resEntity = null;
        if(!StringUtils.isEmpty(eagleEyesVo.getActionCode()) && !StringUtils.isEmpty(eagleEyesVo.getEntityName())){
            Map<String,Object> paramMap =  new HashMap<>();
            //添加一个新的entity。一个entity代表现实中的一个终端用户，可以是一个人、车或任何运动的物体。
            if(eagleEyesVo.getActionCode().equals(EagleEyesVo.actionCode.ADDENTITY)){
                paramMap.put("ak",eagleEyesConfig.getAk());
                paramMap.put("service_id",eagleEyesConfig.getServiceId());
                paramMap.put("entity_name",eagleEyesVo.getEntityName());
                paramMap.put("actionCode",eagleEyesVo.getActionCode());
                resEntity = HttpClientUtil.sendPost(eagleEyesConfig.getUrlAddEntity().toString(),paramMap);
            //根据entity_name、自定义字段或活跃时间，查询符合条件的entity。
            }else if(eagleEyesVo.getActionCode().equals(EagleEyesVo.actionCode.CHECKENTITYEXISTS)){
                paramMap.put("ak",eagleEyesConfig.getAk());
                paramMap.put("service_id",eagleEyesConfig.getServiceId());
                paramMap.put("entity_name",eagleEyesVo.getEntityName());
                paramMap.put("actionCode",eagleEyesVo.getActionCode());
                resEntity = HttpClientUtil.sendGet(eagleEyesConfig.getUrlQueryEntity().toString(),paramMap);
            }else if(eagleEyesVo.getActionCode().equals(EagleEyesVo.actionCode.GETDISTANCE)){
                paramMap.put("ak",eagleEyesConfig.getAk());
                paramMap.put("service_id",eagleEyesConfig.getServiceId());
                paramMap.put("entity_name",eagleEyesVo.getEntityName());
                paramMap.put("actionCode",eagleEyesVo.getActionCode());
                //is_processed 0：关闭轨迹纠偏，返回原始轨迹  1：打开轨迹纠偏，返回纠偏后轨迹。
                paramMap.put("is_processed",1);
                paramMap.put("start_time",eagleEyesVo.getStartTime());
                paramMap.put("end_time",eagleEyesVo.getEndTime());
                //driving：使用最短驾车路线距离补充
                paramMap.put("supplement_mode","driving");
                resEntity = HttpClientUtil.sendGet(eagleEyesConfig.getUrlGetDistace().toString(),paramMap);
            }else{
                result.setCode(ErrorCodeConstants.PARAM_UNKNOW.getId());
                result.setMessage(ErrorCodeConstants.PARAM_UNKNOW.getDescript());
                return result;
            }
            //返回结果值
            if(!StringUtils.isEmpty(resEntity) && Integer.parseInt(JSONObject.parseObject(resEntity).getString("status"))==0){
                result.setData(JSONObject.parseObject(resEntity));
            }else if(Integer.parseInt(JSONObject.parseObject(resEntity).getString("status"))==3005){
                result.setCode(200);
                result.setMessage("鹰眼已经添加，无需再次添加。");
                return result;
            }else{
                result.setCode(ErrorCodeConstants.SERVICE_EXCEPTION_ERROR.getId());
                result.setMessage(ErrorCodeConstants.SERVICE_EXCEPTION_ERROR.getDescript());
                return result;
            }
        }else {
            result.setCode(ErrorCodeConstants.PARAM_EMPTY.getId());
            result.setMessage("客户端请求参数 actionCode 或entityName 不能为空");
            return result;
        }
        return result;
    }
}
