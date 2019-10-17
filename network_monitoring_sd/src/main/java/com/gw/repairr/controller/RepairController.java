package com.gw.repairr.controller;

import com.github.pagehelper.PageInfo;
import com.gw.common.BaseController;
import com.gw.common.Json;
import com.gw.exception.ServiceException;
import com.gw.front.maintenance.service.FrontMaintenanceService;
import com.gw.generatereport.GenerateMaintenanceUserData;
import com.gw.login.service.data.GetSessionInfoOutData;
import com.gw.repairr.data.MaintenanceTaskData;
import com.gw.repairr.data.ParameterData;
import com.gw.repairr.data.RepairData;
import com.gw.repairr.service.RepairService;
import com.gw.unit.controller.UnitBaseInfoController;
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
@RequestMapping("/repair")
public class RepairController extends BaseController {
    private Logger log = LoggerFactory.getLogger(UnitBaseInfoController.class);

    @Resource
    private RepairService repairService;

    @Resource
    private FrontMaintenanceService frontMaintenanceService;

    /**
     * 列表
     * @param inData
     * @return
     * @throws Exception
     */
    @RequestMapping("/getRepairList")
    public PageInfo<RepairData> getRepairrList(RepairData inData) throws Exception{
        Json json = new Json();
        PageInfo<RepairData> pageInfo = null;
        try {
            pageInfo = repairService.getRepairList(inData);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return pageInfo;
    }

    /**
     * 根据单位查找执行人
     * @param generateMaintenanceUserData
     * @return
     */
    @RequestMapping("/getMaintenanceUserBynetworkingID")
    public Json getMaintenanceUserBynetworkingID(GenerateMaintenanceUserData generateMaintenanceUserData) {
        Json json = new Json();
        try {
            List<GenerateMaintenanceUserData> maintenanceUserBynetworkingID = repairService.getMaintenanceUserBynetworkingID(generateMaintenanceUserData);
            json.setMsg("返回成功");
            json.setSuccess(true);
            json.setObj(maintenanceUserBynetworkingID);
        } catch (Exception e) {
            json.setMsg(e.getMessage());
            log.error(e.getMessage(), e);
        }
        return json;
    }

    /**
     * 新增维保计划
     * @param inData
     * @return
     * @throws Exception
     */
    @PostMapping("/repairAdd")
    public Json repairAdd(ParameterData inData) throws Exception{
        Json json = new Json();
        try {
            repairService.repairAdd(inData);
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

    /**
     * 修改维保计划
     * @param inData
     * @return
     * @throws Exception
     */
    @PostMapping("/repairModify")
    public Json repairModify(ParameterData inData) throws Exception{
        Json json = new Json();
        try {
            repairService.repairModify(inData);
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

    /**
     * 删除维保计划
     * @param id
     * @return
     * @throws Exception
     */
    @PostMapping("/repairDelete")
    public Json repairDelete(String id) throws Exception{
        Json json = new Json();
        try {
            repairService.repairDelete(id);
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

    /**
     * 重置维保计划
     * @param id
     * @return
     * @throws Exception
     */
    @PostMapping("/repairReset")
    public Json repairReset(String id) throws Exception{
        Json json = new Json();
        try {
            repairService.repairReset(id);
            json.setSuccess(true);
            json.setMsg("重置成功");
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
     * 获取生成维保任务的行数
     * @param inData
     * @return
     * @throws Exception
     */
    @PostMapping("/repairGenerateNumber")
    public Json repairGenerateNumber(ParameterData inData) throws Exception{
        Json json = new Json();
        try {
            Integer num = repairService.repairGenerateNumber(inData);
            json.setObj(num);
            json.setSuccess(true);
            json.setMsg("获取成功");
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
     * 生成维保任务
     * @param inData
     * @return
     * @throws Exception
     */
    @PostMapping("/repairGenerate")
    public Json repairGenerate(ParameterData inData, HttpServletRequest request) throws Exception{
        //获取token，如果为空则从session中获取
        String authorize = request.getHeader("Authorization");
        if (!Util.isEmpty(authorize)){
            //解析token
            Map<String, String> tokenToMap = JwtUtil.getTokenToMap(authorize);
            inData.setCreateUserId(tokenToMap.get("id"));
        }else {
            GetSessionInfoOutData sessionInfo = (GetSessionInfoOutData) getSessinInfo(request, UtilConst.USER_SESSION);
            if (!Util.isEmpty(sessionInfo)){
                inData.setCreateUserId(sessionInfo.getId().toString());
            }
        }

        Json json = new Json();
        try {
            repairService.repairGenerate(inData);
            json.setSuccess(true);
            json.setMsg("生成成功");
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
     * 获取当前单位的联网单位
     * @param inData
     * @return
     * @throws Exception
     */
    @GetMapping("/getUnitList")
    public Json getPlanList(RepairData inData) throws Exception{
        Json json = new Json();
        List<RepairData> list = null;
        try {
            list = repairService.getUnitList(inData);
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


}
