package com.gw.unit.controller;


import javax.servlet.http.HttpServletRequest;

import com.gw.build.data.BuildOutData;
import com.gw.util.JwtUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageInfo;
import com.gw.common.BaseController;
import com.gw.common.Json;
import com.gw.exception.ServiceException;
import com.gw.login.service.data.GetSessionInfoOutData;
import com.gw.unit.data.GetUserUnitsData;
import com.gw.unit.data.MaintenanceUserInData;
import com.gw.unit.data.MaintenanceUserOutData;
import com.gw.unit.data.PlanUserRoleLOutData;
import com.gw.unit.data.UserReUnitInData;
import com.gw.unit.service.MaintenanceUserService;
import com.gw.util.UtilConst;

import java.util.Map;

@Controller
@RequestMapping("/maintenanceUser")
@ResponseBody
public class MaintenanceUserController extends BaseController {
    private Logger log = LoggerFactory.getLogger(MaintenanceUserController.class);

    @Autowired
    private MaintenanceUserService maintenanceUserService;

    /**
     * 添加维保单位人员
     *
     * @param inData
     * @return
     */
    @RequestMapping("/add")
    public Json add(HttpServletRequest request, MaintenanceUserInData inData) {
        Json json = new Json();

        //获取token
        String authorize = request.getHeader("Authorization");
        try {
            if (authorize != null) {
                long id = 0;
                //解析token
                Map<String, String> tokenToMap = JwtUtil.getTokenToMap(authorize);
                id = Long.parseLong(tokenToMap.get("id"));
                maintenanceUserService.addMaintenanceUser(id, inData);
                json.setSuccess(true);
                json.setMsg("添加成功");
            } else {
                GetSessionInfoOutData userSession = (GetSessionInfoOutData) getSessinInfo(request, UtilConst.USER_SESSION);

                maintenanceUserService.addMaintenanceUser(userSession.getId(), inData);
                json.setSuccess(true);
                json.setMsg("添加成功");
            }
        } catch (ServiceException se) {
            json.setMsg(se.getMessage());
            log.error(se.getMessage(), se);
        } catch (Exception e) {
            json.setMsg("添加失败");
            log.error(e.getMessage(), e);
        }
        return json;
    }

    /**
     * 查看维保单位人员
     *
     * @param request
     * @param username
     * @return
     */
    @RequestMapping("/list")
    public PageInfo<MaintenanceUserOutData> list(HttpServletRequest request, String username, String unitname, String account) {
        try {
            String authorize = request.getHeader("Authorization");
            String UnitID = null;
            if (authorize != null) {
                long id = 0;
                String Account = null;

                PageInfo<BuildOutData> pager = null;
                //解析token
                Map<String, String> tokenToMap = JwtUtil.getTokenToMap(authorize);
                id = Long.parseLong(tokenToMap.get("id"));
                Account = tokenToMap.get("Account");
                UnitID = tokenToMap.get("UnitID");
                PageInfo<MaintenanceUserOutData> list = maintenanceUserService.list(request, username, unitname, account, UnitID);
                return list;
            } else {
                PageInfo<MaintenanceUserOutData> list = maintenanceUserService.list(request, username, unitname, account, UnitID);
                return list;
            }

        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * 更新维保单位人员
     *
     * @param inData
     * @return
     */
    @RequestMapping("/update")
    public Json update(MaintenanceUserInData inData) {
        Json json = new Json();
        try {
            maintenanceUserService.updateMaintenanceUser(inData);
            json.setSuccess(true);
            json.setMsg("更新成功");
        } catch (ServiceException se) {
            json.setMsg(se.getMessage());
            log.error(se.getMessage(), se);
        } catch (Exception e) {
            json.setMsg("更新失败");
            log.error(e.getMessage(), e);
        }
        return json;
    }

    /**
     * 删除维保单位人员
     *
     * @param id
     * @return
     */
    @RequestMapping("/delete")
    public Json delete(Long id) {
        Json json = new Json();
        try {
            maintenanceUserService.deleteMaintenanceUser(id);
            json.setMsg("删除成功");
            json.setSuccess(true);
        } catch (Exception e) {
            json.setMsg("删除失败");
            e.printStackTrace();
            log.error(e.getMessage(), e);
        }
        return json;
    }

    /**
     * 根据单位，和计划查询人员
     *
     * @param UnitID
     * @return
     */
    @RequestMapping("/getUserRoleList")
    public Json getUserRoleList(String siteID, String UnitID, String userrole) {
        Json json = new Json();
        try {
            PlanUserRoleLOutData outData = maintenanceUserService.getUserRoleList(siteID, UnitID, userrole);
            json.setObj(outData);
            json.setSuccess(true);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage(), e);
        }
        return json;
    }

    /**
     * 获得维保单位人员关联单位列表
     *
     * @param
     * @return
     */
    @RequestMapping("/getUserReUnit")
    public PageInfo<GetUserUnitsData> getUserReUnit(UserReUnitInData inData) {
        PageInfo<GetUserUnitsData> pageInfo = null;
        try {
            pageInfo = maintenanceUserService.getUserReUnit(inData);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return pageInfo;
    }

    /**
     * 设置维保单位人员关联单位列表
     *
     * @param userId
     * @param
     * @return
     */
    @RequestMapping("/setUserReUnit")
    public Json setUserReUnit(String userId, String unitIds) {
        Json json = new Json();
        try {
            maintenanceUserService.setUserReUnit(userId, unitIds);
            json.setSuccess(true);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return json;
    }

    /**
     * 删除维保用户关联的联网单位
     *
     * @param userId
     * @param unitIds
     * @return
     */
    @RequestMapping("/delUserReUnit")
    public Json delUserReUnit(String userId, String unitIds) {
        Json json = new Json();
        try {
            maintenanceUserService.delUserReUnit(userId, unitIds);
            json.setSuccess(true);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return json;
    }

    /**
     * 维保用户重置密码
     *
     * @param id
     * @return
     */
    @RequestMapping("/resetPas")
    public Json resetPas(String id) {
        Json json = new Json();
        try {
            maintenanceUserService.resetPas(id);
            json.setSuccess(true);
            json.setMsg("重置成功");
        } catch (Exception e) {
            json.setMsg("重置失败");
            log.error(e.getMessage(), e);
        }
        return json;
    }

}
