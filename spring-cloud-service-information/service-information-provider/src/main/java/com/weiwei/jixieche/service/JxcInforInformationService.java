package com.weiwei.jixieche.service;

import com.weiwei.jixieche.bean.JxcInforInformation;
import com.weiwei.jixieche.jwt.AuthorizationUser;
import com.weiwei.jixieche.response.ResponseMessage;
import com.weiwei.jixieche.vo.InforFireListVo;
import com.weiwei.jixieche.vo.InforParamVo;
import com.weiwei.jixieche.vo.InforPointNumberVo;

/**
 * @ClassName s
 * @Description TODO
 * @Author houji
 * @Date 2019/8/20 16:32
 * @Version 2.0
 **/
public interface JxcInforInformationService extends BaseService<JxcInforInformation> {
       /**
     * 前端分页查询咨询信息表
     * @param pageNo  分页索引
     * @param pageSize  每页显示数量
     * @param jxcInforInformation 查询条件
     * @return 
     */
       ResponseMessage findByPageForFront(Integer pageNo, Integer pageSize, JxcInforInformation jxcInforInformation);

       /**
        * 查询机主首页轮播资讯
        * @return
        */
       ResponseMessage<InforFireListVo> queryInforFireList();

       /**
        * 资讯点赞(浏览)(分享)量增+1
        * @param inforPointNumberVo
        * @return
        */
       ResponseMessage operation(InforPointNumberVo inforPointNumberVo);

       /**
        * 查询资讯详情
        * @param inforParamVo
        * @return
        */
       ResponseMessage queryById(InforParamVo inforParamVo);
}