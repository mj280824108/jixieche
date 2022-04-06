package com.weiwei.jixieche.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.weiwei.jixieche.bean.JxcProject;
import com.weiwei.jixieche.constant.ErrorCodeConstants;
import com.weiwei.jixieche.constant.UserRoleContants;
import com.weiwei.jixieche.jwt.AuthorizationUser;
import com.weiwei.jixieche.mapper.JxcProjectMapper;
import com.weiwei.jixieche.mapper.JxcProjectOrderMapper;
import com.weiwei.jixieche.page.PageUtils;
import com.weiwei.jixieche.response.ResponseMessage;
import com.weiwei.jixieche.response.ResponseMessageFactory;
import com.weiwei.jixieche.service.JxcProjectService;
import com.weiwei.jixieche.verify.AssertUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import javax.annotation.Resource;

import com.weiwei.jixieche.verify.VerifyStr;
import com.weiwei.jixieche.vo.JxcMachineListVo;
import com.weiwei.jixieche.vo.JxcProjectProgressVo;
import com.weiwei.jixieche.vo.JxcProjectTotalProgressVo;
import com.weiwei.jixieche.vo.TabPageListVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Author 钟焕
 * @Description
 * @Date 11:51 2019-08-14
 **/
@Service("jxcProjectService")
public class JxcProjectServiceImpl implements JxcProjectService {
    @Resource
    private JxcProjectMapper jxcProjectMapper;

    @Autowired
    private JxcProjectOrderMapper jxcProjectOrderMapper;

    /**
     * @return result
     * @Author 钟焕
     * @Description 前端分页查询承租方项目表
     * @Date 11:51 2019-08-14
     * @Param [pageNo, pageSize, jxcProject]
     **/
    @Override
    public ResponseMessage<JxcProject> findByPageForFront(Integer pageNo, Integer pageSize, JxcProject jxcProject) {
        ResponseMessage result = ResponseMessageFactory.newInstance();
        PageHelper.startPage(pageNo, pageSize);
        List<JxcProject> list = this.jxcProjectMapper.selectListByConditions(jxcProject);
        PageInfo<JxcProject> page = new PageInfo<>(list);
        result.setData(new PageUtils<>(page).getPageViewDatatable());
        return result;
    }

    /**
     * 查询项目详情
     * @param user
     * @param id
     * @return
     */
    @Override
    public ResponseMessage<JxcProject> queryProjectById(AuthorizationUser user, Integer id) {
        ResponseMessage result = ResponseMessageFactory.newInstance();
        //如果是承租方管理员  则不需要传项目ID
        if(user.getRoleId().equals(UserRoleContants.TEN_MAN.getRoleId())){
            Integer projectId = jxcProjectOrderMapper.getProjectIdByTenantryManId(user.getUserId());
            if(projectId == null) {
                return new ResponseMessage(ErrorCodeConstants.QUERY_EMPTY_DATA.getId(),"该管理员名下目前没有项目！");
            }else{
                id = projectId;
            }
        }
        JxcProject model = this.jxcProjectMapper.selectByPrimaryKey(id);
        AssertUtil.notNull(model, ErrorCodeConstants.QUERY_EMPTY_DATA.getDescript(), ErrorCodeConstants.QUERY_EMPTY_DATA.getId());
        result.setData(model);
        return result;
    }

    /**
     * @return ResponseMessage
     * @Author 钟焕
     * @Description 新增或编辑项目
     * @Date 14:56 2019-08-14
     * @Param [jxcProject]
     **/
    @Override
    public ResponseMessage addOrEditJxcProject(JxcProject jxcProject) {
        //项目名校验
        String projectName = jxcProject.getProjectName();
        if (VerifyStr.isEmpty(projectName)) {
            return new ResponseMessage(ErrorCodeConstants.PARAM_EMPTY.getId(), "项目名称不能为空");
        }

        //项目负责人校验
        String projectLeader = jxcProject.getProjectLeader();
        if (VerifyStr.isEmpty(projectLeader)) {
            return new ResponseMessage(ErrorCodeConstants.PARAM_EMPTY.getId(), "项目负责人不能为空");
        }
        //负责人电话校验
        String leaderPhone = jxcProject.getLeaderPhone();
        if (VerifyStr.isEmpty(leaderPhone)) {
            return new ResponseMessage(ErrorCodeConstants.PARAM_EMPTY.getId(), "项目负责人电话不能为空");
        }
        if (!VerifyStr.isPhone(leaderPhone)) {
            return new ResponseMessage(ErrorCodeConstants.PHONE_FORMAT_ERROR.getId(), "联系电话格式不正确");
        }
        //开工时间、竣工时间校验
        String projectStartTime = jxcProject.getStartDate();
        if (VerifyStr.isEmpty(projectStartTime)) {
            return new ResponseMessage(ErrorCodeConstants.PARAM_EMPTY.getId(), "项目开始时间不能为空");
        }
        if (!VerifyStr.isYYYYMMDD(projectStartTime)) {
            return new ResponseMessage(ErrorCodeConstants.PARAM_FORMAT_ERROR.getId(), "项目开工时间格式错误");
        }

        String projectEndTime = jxcProject.getEndDate();
        if (VerifyStr.isEmpty(projectEndTime)) {
            return new ResponseMessage(ErrorCodeConstants.PARAM_EMPTY.getId(), "项目结束时间不能为空");
        }
        if (!VerifyStr.isYYYYMMDD(projectEndTime)) {
            return new ResponseMessage(ErrorCodeConstants.PARAM_FORMAT_ERROR.getId(), "项目结束时间格式错误");
        }
        //以下参数校验以及操作只有当新增项目时才需要做
        if (jxcProject.getId() == null) {
            //项目地址校验
            String projectAddress = jxcProject.getProjectAddress();
            if (VerifyStr.isEmpty(projectAddress)) {
                return new ResponseMessage(ErrorCodeConstants.PARAM_EMPTY.getId(), "项目地址不能为空");
            }
            //经纬度校验
            String lon = jxcProject.getLongitude();
            if (VerifyStr.isEmpty(lon)) {
                return new ResponseMessage(ErrorCodeConstants.PARAM_EMPTY.getId(), "项目经度不能为空");
            }

            String lat = jxcProject.getLatitude();
            if (VerifyStr.isEmpty(lat)) {
                return new ResponseMessage(ErrorCodeConstants.PARAM_EMPTY.getId(), "项目纬度不能为空");
            }
            //区编码校验
            Integer districtCode = jxcProject.getDistrictCode();
            if (districtCode == null) {
                return new ResponseMessage(ErrorCodeConstants.PARAM_EMPTY.getId(), "所属区/县不能为空");
            }
            int len = 6;
            if (districtCode.toString().length() != len) {
                return new ResponseMessage(ErrorCodeConstants.PARAM_FORMAT_ERROR.getId(), "所属区/县编码格式不正确");
            }

            //开放城市校验
            Map<String, Object> districtCheck = jxcProjectMapper.checkDistrictCode(jxcProject.getDistrictCode());
            if (districtCheck == null) {
                return new ResponseMessage(ErrorCodeConstants.PARAM_TYPE_UNKNOW.getId(), "无此地区");
            }
            String str = "0";
            if (str.equals(String.valueOf(districtCheck.get("isOpened")))) {
                return new ResponseMessage(ErrorCodeConstants.PARAM_TYPE_UNKNOW.getId(), "该地区所属城市尚未开放");
            }

            int cityCode = Integer.valueOf(String.valueOf(districtCheck.get("pid")));

            //设置城市编码
            jxcProject.setCityCode(cityCode);

            //mark: 选择图片的算法: 图片索引是从 100 开始的，随机选取之后的范围为 5 以内的图片
            jxcProject.setImgId(new Random().nextInt(5) + 100);
        } else {
            JxcProject jxcProject1 = jxcProjectMapper.selectByPrimaryKey(jxcProject.getId());
            if (jxcProject1 == null) {
                return new ResponseMessage(ErrorCodeConstants.NO_SUCH_DATA.getId(), "没有该项目");
            }

            if (!jxcProject1.getUserId().equals(jxcProject.getUserId())) {
                return new ResponseMessage(ErrorCodeConstants.NO_SUCH_DATA.getId(), "该项目不属于您，不能修改！");
            }
        }

        if (jxcProject.getId() == null) {
            jxcProjectMapper.insertSelective(jxcProject);
        } else {
            jxcProjectMapper.updateByPrimaryKeySelective(jxcProject);
        }
        return new ResponseMessage();
    }

    /**
     * 查询未竣工或已竣工列表
     *
     * @param user
     * @param vo
     * @return
     */
    @Override
    public ResponseMessage selectJxcProjectList(AuthorizationUser user, TabPageListVo vo) {
        ResponseMessage result = ResponseMessageFactory.newInstance();
        PageHelper.startPage(vo.getPageNo(), vo.getPageSize(), "create_time desc");
        List<JxcProject> list = this.jxcProjectMapper.selectJxcProjectList(user.getUserId(), vo.getFlag());
        for (JxcProject dto : list) {
            dto.setFlag(vo.getFlag());
        }
        PageInfo<JxcProject> page = new PageInfo<>(list);
        result.setData(new PageUtils<>(page).getPageViewDatatable());
        return result;
    }

    /**
     * 查询项目列表不分页
     *
     * @param user
     * @return
     */
    @Override
    public ResponseMessage selectJxcProjectListNoPage(AuthorizationUser user) {
        ResponseMessage result = ResponseMessageFactory.newInstance();
        List<JxcProject> list = this.jxcProjectMapper.selectJxcProjectList(user.getUserId(), null);
        Map<String, Object> map = new HashMap<>(1);
        map.put("projectList", list);
        result.setData(map);
        return result;
    }

    @Override
    public ResponseMessage<JxcProject> addObj(JxcProject jxcProject) {
        return null;
    }

    /**
     * @return result
     * @Author 钟焕
     * @Description 修改承租方项目表
     * @Date 11:52 2019-08-14
     * @Param [t]
     **/
    @Override
    public ResponseMessage<JxcProject> modifyObj(JxcProject t) {
        ResponseMessage result = ResponseMessageFactory.newInstance();
        int res = this.jxcProjectMapper.updateByPrimaryKeySelective(t);
        AssertUtil.numberGtZero(res, ErrorCodeConstants.EDIT_ERORR.getDescript(), ErrorCodeConstants.EDIT_ERORR.getId());
        return result;
    }

    /**
     * @return result
     * @Author 钟焕
     * @Description 根据ID查询承租方项目表
     * @Date 11:52 2019-08-14
     * @Param [id]
     **/
    @Override
    public ResponseMessage<JxcProject> queryObjById(int id) {
        ResponseMessage result = ResponseMessageFactory.newInstance();
        JxcProject model = this.jxcProjectMapper.selectByPrimaryKey(id);
        AssertUtil.notNull(model, ErrorCodeConstants.QUERY_EMPTY_DATA.getDescript(), ErrorCodeConstants.QUERY_EMPTY_DATA.getId());
        result.setData(model);
        return result;
    }

    /**
     * 查询项目中的车辆 项目ID
     *
     * @param jxcMachineListVo
     * @return
     */
    @Override
    public ResponseMessage selectMachineListByProjectId(JxcMachineListVo jxcMachineListVo) {
        ResponseMessage result = ResponseMessageFactory.newInstance();
        PageHelper.startPage(jxcMachineListVo.getPageNo(), jxcMachineListVo.getPageSize(), "oo.id desc");
        if (jxcMachineListVo.getProjectId() == null) {
            return new ResponseMessage(ErrorCodeConstants.PARAM_EMPTY.getId(), "项目ID不能为空");
        }
        List<JxcMachineListVo> maps = jxcProjectMapper.tenantryViewMachineList(jxcMachineListVo.getProjectId(), jxcMachineListVo.getTabFlag());
        //去评价表中查询该机械是否已经被评价
        if (maps != null && maps.size() > 0) {
            maps.stream().forEach(dto -> {
            	PageHelper.clearPage();
                Long scoreIdBy = jxcProjectMapper.getScoreIdByTargetId(dto.getMachineId(), String.valueOf(dto.getOrderId()));
                if (scoreIdBy == null) {
                    dto.setScoreId(-1L);
                } else {
                    dto.setScoreId(scoreIdBy);
                }
            });
        }
        PageInfo<JxcMachineListVo> page = new PageInfo<>(maps);
        result.setData(new PageUtils<>(page).getPageViewDatatable());
        return result;
    }

    /**
     * 查询项目进度
     *
     * @param projectId
     * @param pageNo
     * @param pageSize
     * @return
     */
    @Override
    public ResponseMessage selectProjectProgress(Integer projectId, Integer pageNo, Integer pageSize) {
        ResponseMessage result = ResponseMessageFactory.newInstance();
        Map<String, Object> map = new HashMap<>(2);
        JxcProjectTotalProgressVo jxcProjectTotalProgressVo = jxcProjectMapper.selectProjectTotalProgress(projectId);
        jxcProjectTotalProgressVo.setTotalPayAmount(VerifyStr.strToStr(jxcProjectTotalProgressVo.getTotalPayAmount()));
        map.put("totalProgress", jxcProjectTotalProgressVo);
        PageHelper.startPage(pageNo, pageSize,"t.dateStr DESC");
        List<JxcProjectProgressVo> jxcProjectProgressVos = jxcProjectMapper.selectProjectProgressByDay(projectId);
        if (jxcProjectProgressVos != null && jxcProjectProgressVos.size() > 0) {
            for (JxcProjectProgressVo jxcProjectProgressVo : jxcProjectProgressVos) {
                jxcProjectProgressVo.setTotalPay(VerifyStr.strToStr(jxcProjectProgressVo.getTotalPay()));
                jxcProjectProgressVo.setTotalNoPay(VerifyStr.strToStr(jxcProjectProgressVo.getTotalNoPay()));
            }
        }
        PageInfo<JxcProjectProgressVo> page = new PageInfo<>(jxcProjectProgressVos);
        map.put("progress", new PageUtils<>(page).getPageViewDatatable());
        result.setData(map);
        return result;
    }

    /**
     * 删除空项目
     * @param projectId
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseMessage delProject(Integer projectId) {
        //检查该项目是否有订单
        int i = jxcProjectMapper.countOrderByProjectId(projectId);
        if(i > 0){
            return new ResponseMessage(ErrorCodeConstants.EDIT_ERORR.getId(),"此项目中有订单，不能删除");
        }
        //否则，直接删除
        try {
            jxcProjectMapper.delProject(projectId);
        } catch (Exception e) {
            return new ResponseMessage(ErrorCodeConstants.EDIT_ERORR.getId(),"删除失败");
        }
        return new ResponseMessage();
    }

    @Override
    public ResponseMessage checkIsForbidSendOrder(AuthorizationUser user) {
        Integer sendOrderFlag = jxcProjectMapper.getSendOrderFlag(user.getUserId());
        if(sendOrderFlag != null && sendOrderFlag.equals(1)){
            return new ResponseMessage(ErrorCodeConstants.FORBID_SEND_ORDER.getId(),"抱歉，由于您有订单逾期未支付，该功能已被限制！");
        }
        return new ResponseMessage();
    }
}