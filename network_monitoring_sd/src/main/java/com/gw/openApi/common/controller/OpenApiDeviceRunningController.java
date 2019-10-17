package com.gw.openApi.common.controller;

import com.gw.common.Json;
import com.gw.exception.ServiceException;
import com.gw.front.history.data.FrontHistoryInData;
import com.gw.myAnnotation.PassToken;
import com.gw.openApi.common.data.out.DeviceRunningData;
import com.gw.openApi.common.data.out.EquipmentBaseData;
import com.gw.openApi.common.service.IDeviceService;
import com.gw.util.UtilConst;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags ="设备运行状态")
@RestController
@RequestMapping("/rest/device")
public class OpenApiDeviceRunningController {
    private Logger logger = LoggerFactory.getLogger(OpenApiDeviceRunningController.class);

    @Autowired
    private IDeviceService deviceService;

    /**
     * 根据单位/建筑id查询设备信息
     * @param inData
     * @return
     */
    @ApiOperation(value = "设备列表", notes = "设备列表",httpMethod = "GET")
    @PassToken
    @RequestMapping("/equipments")
    public Json getDeviceList(FrontHistoryInData inData){
        Json json = new Json();
        try {
            Object deviceList = deviceService.getDeviceList(inData);
            json.setSuccess(true);
            json.setObj(deviceList);
        } catch (ServiceException e) {
            json.setSuccess(false);
            json.setMsg(e.getMessage());
        } catch (Exception e) {
            logger.error(e.getMessage());
            json.setSuccess(false);
            json.setMsg(UtilConst.ERRO_SYSTEM);
        }
        return json;
    }

    /**
     * 根据设备信息查询设备状态及当前值
     * @param inData
     * @return
     */
    @ApiOperation(value = "设备运行情况", notes = "设备运行情况",httpMethod = "GET")
    @PassToken
    @RequestMapping("/equipmentStatus")
    public Json getEquipmentRunningData(EquipmentBaseData inData){
        Json json = new Json();
        try {
            DeviceRunningData equipmentRunningInfo = deviceService.getEquipmentRunningInfo(inData);
            json.setSuccess(true);
            json.setObj(equipmentRunningInfo);
        } catch (ServiceException e) {
            json.setSuccess(false);
            json.setMsg(e.getMessage());
        } catch (Exception e) {
            logger.error(e.getMessage());
            json.setSuccess(false);
            json.setMsg(UtilConst.ERRO_SYSTEM);
        }
        return json;
    }

}
