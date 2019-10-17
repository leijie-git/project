package com.gw.equipment.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.gw.equipment.data.EquipmentFacInDataUpdate;
import com.gw.front.unit.data.FrontUnitCRTOutData;
import com.gw.myAnnotation.PassToken;
import com.gw.util.JwtUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.github.pagehelper.PageInfo;
import com.gw.common.BaseController;
import com.gw.common.Json;
import com.gw.device.data.InterfaceOutData;
import com.gw.device.data.InterfaceOutImportData;
import com.gw.equipment.data.EquipmentFacInData;
import com.gw.equipment.data.EquipmentFacOutData;
import com.gw.equipment.service.EquipmentFacService;
import com.gw.exception.ServiceException;
import com.gw.login.service.data.GetSessionInfoOutData;
import com.gw.systemManager.controller.SysResourceController;
import com.gw.util.UtilConst;

/**
 * 设备设施管理
 *
 * @author zfg
 */
@RestController
@RequestMapping("/equipmentFac")
public class EquipmentFacController extends BaseController {

    private Logger log = LoggerFactory.getLogger(EquipmentFacController.class);

    @Resource
    private EquipmentFacService equipmentFacService;

    /**
     * 获取所有设备列表
     *
     * @param inData
     * @return
     */
    @RequestMapping("/getList")
    public PageInfo<EquipmentFacOutData> getList(EquipmentFacInData inData, HttpServletRequest request) {
        PageInfo<EquipmentFacOutData> pager = null;
        String authorize = request.getHeader("Authorization");
        long id = 0;
        String Account = null;
        String UnitID = null;


        try {
            if (authorize != null) {

                //解析token
                Map<String, String> tokenToMap = JwtUtil.getTokenToMap(authorize);
                id = Long.parseLong(tokenToMap.get("id"));
                Account = tokenToMap.get("Account");
                UnitID = tokenToMap.get("UnitID");
                if (UnitID != null) {
                    inData.setUnitid(UnitID);
                }

                pager = equipmentFacService.getList(inData, id);
                return pager;

            }

            GetSessionInfoOutData sessionInfo = (GetSessionInfoOutData) getSessinInfo(request, UtilConst.USER_SESSION);
            pager = equipmentFacService.getList(inData, sessionInfo.getId());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return pager;
    }

    /**
     * 添加设备
     *
     * @param inData
     * @return
     */
    @PostMapping("/add")
    public Json add(EquipmentFacInDataUpdate inData, HttpServletRequest request) {
        Json json = new Json();
        try {
            equipmentFacService.add(inData, request);
            json.setSuccess(true);
        } catch (ServiceException e) {
            json.setMsg(e.getMessage());
            log.error(e.getMessage(), e);
        } catch (Exception e) {
            json.setMsg("保存失败");
            log.error(e.getMessage(), e);
        }
        return json;
    }

    /**
     * 批量添加设备
     *
     * @param facInData
     * @return
     */
    @PostMapping("/addAll")
    public Json addAll(@RequestBody List<EquipmentFacInData> facInData, HttpServletRequest request) {
        Json json = new Json();
        try {
            equipmentFacService.addAll(facInData, request);
            json.setSuccess(true);
        } catch (ServiceException e) {
            json.setMsg(e.getMessage());
            log.error(e.getMessage(), e);
        } catch (Exception e) {
            json.setMsg("保存失败");
            log.error(e.getMessage(), e);
        }
        return json;
    }

    /**
     * 修改设备
     *
     * @param inData
     * @return
     */
    @PostMapping("/update")
    public Json update(EquipmentFacInDataUpdate inData, HttpServletRequest request) {
        Json json = new Json();
        try {
            equipmentFacService.update(inData, request);
            json.setSuccess(true);
        } catch (Exception e) {
            json.setMsg(e.getMessage());
            log.error(e.getMessage(), e);
        }
        return json;
    }

    /**
     * 批量修改接口
     *
     * @param inData
     * @param request
     * @return
     */
    @PostMapping("/updateAll")
    public Json updateAll(@RequestBody List<EquipmentFacInData> inData, HttpServletRequest request) {
        Json json = new Json();
        try {
            equipmentFacService.updateAll(inData, request);
            json.setSuccess(true);
        } catch (Exception e) {
            json.setMsg(e.getMessage());
            log.error(e.getMessage(), e);
        }
        return json;
    }

    /**
     * 删除设备
     *
     * @param id
     * @return
     */
    @RequestMapping("/remove")
    public Json remove(String id, HttpServletRequest request) {
        Json json = new Json();
        try {
            equipmentFacService.remove(id, request);
            json.setSuccess(true);
        } catch (Exception e) {
            json.setMsg(e.getMessage());
            log.error(e.getMessage(), e);
        }
        return json;
    }

    /**
     * 新增或者更新控制接口
     *
     * @param indata
     * @return
     */
    @RequestMapping("/updateInterfaceOut")
    public Json updateInterfaceOut(InterfaceOutData indata) {
        Json json = new Json();
        try {
            equipmentFacService.updateInterfaceOut(indata);
            json.setSuccess(true);
        } catch (ServiceException e) {
            json.setMsg(e.getMessage());
            log.error(e.getMessage(), e);
        } catch (Exception e) {
            log.error(e.getMessage(), e);

        }
        return json;
    }

    /**
     * 删除控制接口
     *
     * @param id
     * @return
     */
    @RequestMapping("/deleteInterfaceOut")
    public Json deleteInterfaceOut(String id) {
        Json json = new Json();
        try {
            equipmentFacService.deleteInterfaceOut(id);
            json.setSuccess(true);
        } catch (ServiceException e) {
            json.setMsg(e.getMessage());
            log.error(e.getMessage(), e);
        } catch (Exception e) {
            log.error(e.getMessage(), e);

        }
        return json;
    }

    /**
     * 导入
     *
     * @param uploadExcel
     * @param request
     * @param deviceId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Json importExcel(@RequestParam("uploadExcel") MultipartFile uploadExcel, HttpServletRequest request,
                            String deviceId) {
        Json json = new Json();
        try {
            List<InterfaceOutImportData> interfaceImportDatas = importExcel(uploadExcel, 0, InterfaceOutImportData.class);
            equipmentFacService.importData(interfaceImportDatas, deviceId);
            json.setSuccess(true);
            json.setMsg("导入成功");
        } catch (ServiceException se) {
            log.error(se.getMessage(), se);
            json.setMsg(se.getMessage());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            json.setMsg("导入失败");
        }
        return json;
    }

    /**
     * 获取设备关联输出口
     *
     * @param deviceId
     * @return
     */
    @RequestMapping("/getInterfaceOutList")
    public PageInfo<InterfaceOutData> getInterfaceOutList(String deviceId) {
        try {
            PageInfo<InterfaceOutData> pageInfo = equipmentFacService.getInterfaceOutList(deviceId);
            return pageInfo;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * 查询设备设施平面图
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/getBuildImgbg")
    public Json getOneCRT(Long id) throws Exception {
        Json json = new Json();
        try {
            EquipmentFacOutData outData = equipmentFacService.getBuildImgbg(id);
            json.setObj(outData);
            json.setSuccess(true);
        } catch (Exception e) {
            json.setMsg(e.getMessage());
        }
        return json;
    }

    /**
     * 删除接口
     *
     * @param eqId
     * @param detailid
     * @param netDevcieId
     * @return
     */
    @RequestMapping("/delete")
    public Json delete(String eqId, String detailid, String netDevcieId) {
        Json json = new Json();
        try {
            equipmentFacService.delete(eqId, detailid, netDevcieId);
            json.setSuccess(true);
        } catch (Exception e) {
            json.setMsg(e.getMessage());
            log.error(e.getMessage(), e);
        }
        return json;
    }
}
