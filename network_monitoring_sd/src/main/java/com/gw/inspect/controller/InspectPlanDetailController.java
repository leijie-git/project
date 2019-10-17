package com.gw.inspect.controller;


import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.gw.util.JwtUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gw.common.BaseController;
import com.gw.common.Json;
import com.gw.inspect.data.InspectPlanDetailInData;
import com.gw.inspect.data.InspectTaskInData;
import com.gw.inspect.service.InspectPlanDetailService;
import com.gw.login.service.data.GetSessionInfoOutData;
import com.gw.systemManager.controller.SysResourceController;
import com.gw.unit.data.PlanUserRoleLOutData;
import com.gw.util.UtilConst;

import java.util.Map;

@RestController
@RequestMapping("/inspectPlanDetail")
public class InspectPlanDetailController extends BaseController {
    private Logger log = LoggerFactory.getLogger(InspectPlanDetailController.class);
    @Resource
    private InspectPlanDetailService inspectPlanDetailService;

    /**
     * 为巡查计划添加巡查点
     *
     * @param siteId
     * @param planId
     * @return
     */
    @PostMapping("/add")
    public Json add(InspectPlanDetailInData inData) {
        Json json = new Json();
        try {
            inspectPlanDetailService.add(inData);
            json.setSuccess(true);
        } catch (Exception e) {
            json.setMsg(e.getMessage());
            log.error(e.getMessage(), e);
        }
        return json;
    }

    /**
     * 删除巡查计划中的点位
     *
     * @param planDetailId
     * @return
     */
    @PostMapping("/remove")
    public Json remove(@RequestParam("planDetailId") String planDetailId) {
        Json json = new Json();
        try {
            inspectPlanDetailService.remove(planDetailId);
            json.setSuccess(true);
        } catch (Exception e) {
            json.setMsg(e.getMessage());
            log.error(e.getMessage(), e);
        }
        return json;
    }

    @PostMapping("/update")
    public Json update(InspectPlanDetailInData inData) {
        Json json = new Json();
        try {
            inspectPlanDetailService.update(inData);
            json.setSuccess(true);
        } catch (Exception e) {
            json.setMsg(e.getMessage());
            log.error(e.getMessage(), e);
        }
        return json;
    }

    /**
     * 获取巡查人列表
     *
     * @param id
     * @return
     */
    @PostMapping("/getUserList")
    public Json getUserList(String id, String UnitID, String userrole) {
        Json json = new Json();
        try {
            PlanUserRoleLOutData list = inspectPlanDetailService.getUserList(id, UnitID, userrole);
            json.setObj(list);
            json.setSuccess(true);
        } catch (Exception e) {
            json.setMsg(e.getMessage());
            log.error(e.getMessage(), e);
        }
        return json;
    }

    /**
     * 生成任务
     *
     * @param inData
     * @return
     */
    @PostMapping("/createTask")
    public Json createTask(InspectTaskInData inData, HttpServletRequest request) {

        Json json = new Json();
//获取token
        String authorize = request.getHeader("Authorization");


        try {
            if (authorize != null) {
                long id = 0;
//解析token
                Map<String, String> tokenToMap = JwtUtil.getTokenToMap(authorize);
                id = Long.parseLong(tokenToMap.get("id"));

                inspectPlanDetailService.createTask(inData, id);
                json.setSuccess(true);
            } else {
                GetSessionInfoOutData sessionInfo = (GetSessionInfoOutData) getSessinInfo(request, UtilConst.USER_SESSION);
                inspectPlanDetailService.createTask(inData, sessionInfo.getId());
                json.setSuccess(true);
            }
        } catch (Exception e) {
            json.setMsg(e.getMessage());
            e.printStackTrace();
        }
        return json;
    }

    /**
     * 批量生成任务
     *
     * @param
     * @return
     */
    @PostMapping("/createAllTask")
    public Json createAllTask(String siteID, String planID, HttpServletRequest request) {

//获取token
        String authorize = request.getHeader("Authorization");

        Json json = new Json();
        try {
            if (authorize != null) {
                long id = 0;
                String Account = null;
                String UnitID = null;

//解析token
                Map<String, String> tokenToMap = JwtUtil.getTokenToMap(authorize);
                id = Long.parseLong(tokenToMap.get("id"));
                Account = tokenToMap.get("Account");
                UnitID = tokenToMap.get("UnitID");
                inspectPlanDetailService.createAllTask(siteID, planID, id);
                json.setSuccess(true);
            } else {
                GetSessionInfoOutData sessionInfo = (GetSessionInfoOutData) getSessinInfo(request, UtilConst.USER_SESSION);
                inspectPlanDetailService.createAllTask(siteID, planID, sessionInfo.getId());
                json.setSuccess(true);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return json;
    }
}
