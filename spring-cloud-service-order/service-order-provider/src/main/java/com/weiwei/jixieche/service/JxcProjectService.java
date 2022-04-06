package com.weiwei.jixieche.service;

import com.weiwei.jixieche.bean.JxcProject;
import com.weiwei.jixieche.jwt.AuthorizationUser;
import com.weiwei.jixieche.response.ResponseMessage;
import com.weiwei.jixieche.vo.JxcMachineListVo;
import com.weiwei.jixieche.vo.TabPageListVo;
import org.apache.ibatis.annotations.Param;

/**
 * @Author 钟焕
 * @Description
 * @Date 11:52 2019-08-14
 **/
public interface JxcProjectService extends BaseService<JxcProject> {
    /**
     * 前端分页查询承租方项目表
     * @param pageNo  分页索引
     * @param pageSize  每页显示数量
     * @param jxcProject 查询条件
     * @return
     */
    ResponseMessage findByPageForFront(Integer pageNo, Integer pageSize, JxcProject jxcProject);

    /**
     * 查询项目详情
     * @param user
     * @param projectId
     * @return
     */
    ResponseMessage<JxcProject> queryProjectById(AuthorizationUser user,Integer projectId);

    /**
     * 新增或编辑项目
     * @param jxcProject
     * @return
     */
    ResponseMessage addOrEditJxcProject(JxcProject jxcProject);

    /**
     * 查询未竣工或已竣工列表
     * @param user
     * @param vo
     * @return
     */
    ResponseMessage selectJxcProjectList(AuthorizationUser user, TabPageListVo vo);

    /**
     * 查询项目列表不分页
     * @param user
     * @return
     */
    ResponseMessage selectJxcProjectListNoPage(AuthorizationUser user);

    /**
     * 查询项目中的车辆 项目ID  tabFlag 1-待接单 2-进行中 3-已完结
     * @param jxcMachineListVo
     * @return
     */
    ResponseMessage selectMachineListByProjectId(JxcMachineListVo jxcMachineListVo);

    /**
     * 查询项目进度
     *
     * @param projectId
     * @param pageNo
     * @param pageSize
     * @return
     */
    ResponseMessage selectProjectProgress(Integer projectId,Integer pageNo,Integer pageSize);


    /**
     * 删除空项目
     * @param projectId
     * @return
     */
    ResponseMessage delProject(Integer projectId);

    /**
     * 检验是否被禁止发单
     * @param user
     * @return
     */
    ResponseMessage checkIsForbidSendOrder(AuthorizationUser user);


}