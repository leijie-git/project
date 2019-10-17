package com.gw.repairr.controller;

import com.github.pagehelper.PageInfo;
import com.gw.common.BaseController;
import com.gw.common.Json;
import com.gw.exception.ServiceException;
import com.gw.login.service.data.GetSessionInfoOutData;
import com.gw.repairr.data.MaintenanceTaskData;
import com.gw.repairr.data.RepairData;
import com.gw.repairr.data.UserListData;
import com.gw.repairr.service.MaintenanceTaskService;
import com.gw.util.JwtUtil;
import com.gw.util.Util;
import com.gw.util.UtilConst;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/maintenanceTask")
public class MaintenanceTaskController extends BaseController {

    private Logger log = LoggerFactory.getLogger(MaintenanceTaskController.class);

    @Resource
    private MaintenanceTaskService maintenanceTaskService;

    /**
     * 列表
     * @param inData
     * @return
     * @throws Exception
     */
    @RequestMapping("/getList")
    public PageInfo<MaintenanceTaskData> getList(MaintenanceTaskData inData) throws Exception{
        PageInfo<MaintenanceTaskData> pageInfo = null;
        try {
            pageInfo = maintenanceTaskService.getList(inData);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return pageInfo;
    }

    /**
     * 选择计划
     * @return
     * @throws Exception
     */
    @GetMapping("/getPlanList")
    public Json getPlanList(MaintenanceTaskData inData) throws Exception{
        Json json = new Json();
        List<MaintenanceTaskData> list = null;
        try {
            list = maintenanceTaskService.getPlanList(inData);
            json.setObj(list);
            json.setSuccess(true);
        } catch (ServiceException e) {
            json.setSuccess(false);
            json.setMsg(e.getMessage());
        } catch (Exception e) {
            json.setSuccess(false);
            json.setMsg(UtilConst.ERRO_SYSTEM);
        }
        return json;
    }

    /**
     * 选择维保中的计划
     * @return
     * @throws Exception
     */
    @GetMapping("/getMaintenancePlanList")
    public Json getMaintenancePlanList(MaintenanceTaskData inData) throws Exception{
        Json json = new Json();
        List<UserListData> list = null;
        try {
            list = maintenanceTaskService.getMaintenancePlanList(inData);
            json.setObj(list);
            json.setSuccess(true);
        } catch (ServiceException e) {
            json.setSuccess(false);
            json.setMsg(e.getMessage());
        } catch (Exception e) {
            json.setSuccess(false);
            json.setMsg(UtilConst.ERRO_SYSTEM);
        }
        return json;
    }

    /**
     * 选择维保中的执行人
     * @return
     * @throws Exception
     */
    @GetMapping("/getMaintenanceUserList")
    public Json getMaintenanceUserList(MaintenanceTaskData inData) throws Exception{
        List<UserListData> list = null;
        Json json = new Json();
        try {
            list = maintenanceTaskService.getMaintenanceUserList(inData);
            json.setObj(list);
            json.setSuccess(true);
        } catch (ServiceException e) {
            json.setSuccess(false);
            json.setMsg(e.getMessage());
        } catch (Exception e) {
            json.setSuccess(false);
            json.setMsg(UtilConst.ERRO_SYSTEM);
        }
        return json;
    }

    /**
     * 新增
     * @param inData
     * @param request
     * @return
     * @throws Exception
     */
    @PostMapping("/taskAdd")
    public Json taskAdd(MaintenanceTaskData inData,HttpServletRequest request) throws Exception{
        //获取token，如果为空则从session中获取
        String authorize = request.getHeader("Authorization");
        if (!Util.isEmpty(authorize)){
            //解析token
            Map<String, String> tokenToMap = JwtUtil.getTokenToMap(authorize);
            inData.setCreateUserID(tokenToMap.get("id"));
        }else {
            GetSessionInfoOutData sessionInfo = (GetSessionInfoOutData) getSessinInfo(request, UtilConst.USER_SESSION);
            if (!Util.isEmpty(sessionInfo)){
                inData.setCreateUserID(sessionInfo.getId().toString());
            }
        }
        Json json = new Json();
        try {
            maintenanceTaskService.taskAdd(inData);
            json.setSuccess(true);
            json.setMsg("添加成功");
        } catch (ServiceException e) {
            json.setSuccess(false);
            json.setMsg(e.getMessage());
        } catch (Exception e) {
            json.setSuccess(false);
            json.setMsg(UtilConst.ERRO_SYSTEM);
        }
        return json;
    }

    @PostMapping("/taskModify")
    public Json taskModify(MaintenanceTaskData inData,HttpServletRequest request) throws Exception{
        //获取token，如果为空则从session中获取
        String authorize = request.getHeader("Authorization");
        if (!Util.isEmpty(authorize)){
            //解析token
            Map<String, String> tokenToMap = JwtUtil.getTokenToMap(authorize);
            inData.setCreateUserID(tokenToMap.get("id"));
        }else {
            GetSessionInfoOutData sessionInfo = (GetSessionInfoOutData) getSessinInfo(request, UtilConst.USER_SESSION);
            if (!Util.isEmpty(sessionInfo)){
                inData.setCreateUserID(sessionInfo.getId().toString());
            }
        }
        Json json = new Json();
        try {
            maintenanceTaskService.taskModify(inData);
            json.setSuccess(true);
            json.setMsg("修改成功");
        } catch (ServiceException e) {
            json.setSuccess(false);
            json.setMsg(e.getMessage());
        } catch (Exception e) {
            json.setSuccess(false);
            json.setMsg(UtilConst.ERRO_SYSTEM);
        }
        return json;
    }

    @PostMapping("/taskDelete")
    public Json taskDelete(String id) throws Exception{
        Json json = new Json();
        try {
            maintenanceTaskService.taskDelete(id);
            json.setSuccess(true);
            json.setMsg("删除成功");
        } catch (ServiceException e) {
            json.setSuccess(false);
            json.setMsg(e.getMessage());
        } catch (Exception e) {
            json.setSuccess(false);
            json.setMsg(UtilConst.ERRO_SYSTEM);
        }
        return json;
    }

}
