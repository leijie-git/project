package com.gw.openApi.common.controller;

import com.github.pagehelper.PageInfo;
import com.gw.common.Json;
import com.gw.equipment.data.EquipmentFacInData;
import com.gw.exception.ServiceException;
import com.gw.myAnnotation.PassToken;
import com.gw.openApi.common.data.in.UnitBaseInData;
import com.gw.openApi.common.data.in.UnitDeviceInData;
import com.gw.openApi.common.data.out.UnitBuildingOutData;
import com.gw.openApi.common.data.out.UnitDeviceOutData;
import com.gw.openApi.common.service.IDeviceService;
import com.gw.openApi.common.service.IUnitService;
import com.gw.systemManager.data.CodeOutData;
import com.gw.systemManager.service.CodeService;
import com.gw.util.UtilConst;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@Api(tags = "单位/设备基本信息")
@RestController
@RequestMapping("/rest/baseInfo")
public class OpenApiBaseInfoController {

    private Logger logger = LoggerFactory.getLogger(OpenApiBaseInfoController.class);

    @Autowired
    private IUnitService unitService;

    @Autowired
    private IDeviceService deviceService;

    @Autowired
    private CodeService codeService;

    @ApiOperation(value = "单位列表", notes = "单位列表",httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageSize", value = "页面大小", required = true, dataType = "Integer", paramType = "path"),
            @ApiImplicitParam(name = "pageNumber", value = "当前页码", required = true, dataType = "Integer", paramType = "path")
    })
    @PassToken
    @RequestMapping("/units/{pageSize}/{pageNumber}")
    public Json getUnitList(UnitBaseInData baseInData){
        Json json = new Json();
        try {
            PageInfo<UnitBuildingOutData>list = unitService.getUnitList(baseInData);
            json.setSuccess(true);
            json.setObj(list);
        }catch (ServiceException e) {
            json.setSuccess(false);
            json.setObj(e.getMessage());
        } catch (Exception e) {
            json.setSuccess(false);
            json.setObj(UtilConst.ERRO_SYSTEM);
        }
        return json;
    }


    /**
     * 根据类型获取系统初始化静态数据(SuperviseType:单位类别;UnitType:企业类型;监管等级/危险等级:中文描述)
     * @param typeName
     * @return
     */
    @ApiOperation(value = "根据类型获取系统初始化静态数据", notes = "根据类型获取系统初始化静态数据",httpMethod = "GET")
    @ApiImplicitParam(name = "typeName", value = "类型", required = true, dataType = "String", paramType = "Query")
    @PassToken
    @RequestMapping("/unitTypes")
    public Json getTypeList(String typeName){
        Json json = new Json();

        try {
            List<CodeOutData> typeList = codeService.getListByGroupKey(typeName);
          json.setSuccess(true);
            json.setObj(typeList);
        }catch (ServiceException e){
            json.setSuccess(false);
            json.setMsg(e.getMessage());
        }catch (Exception e){
            logger.error(e.getMessage());
            json.setSuccess(false);
            json.setMsg(UtilConst.ERRO_SYSTEM);
        }
        return json;
    }

    @ApiOperation(value = "设备信息", notes = "设备信息",httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageSize", value = "页面大小", required = true, dataType = "Integer", paramType = "path"),
            @ApiImplicitParam(name = "currentPage", value = "当前页码", required = true, dataType = "Integer", paramType = "path")
    })
    @PassToken
    @RequestMapping("/devices/{pageSize}/{currentPage}")
    public Json getDeviceInfo(UnitDeviceInData deviceInData){
        Json json = new Json();
        try {
            PageInfo<UnitDeviceOutData> buildingOutData = deviceService.getUnitDeviceList(deviceInData);
            json.setSuccess(true);
            json.setObj(buildingOutData);
        }catch (ServiceException e){
            json.setSuccess(false);
            json.setMsg(e.getMessage());
        }catch (Exception e){
            logger.error(e.getMessage());
            json.setSuccess(false);
            json.setMsg(UtilConst.ERRO_SYSTEM);
        }
        return json;
    }

    /**
     *
     * @param facInData
     * unitid :单位id;unitName:单位名称;eqname:设备名称;deviceindex:设备索引(1:报警主机;2传输装置;3:RTU)
     * @return
     */
    @PassToken
    @PostMapping("/devices")
    public Json updteDeviceInfo(EquipmentFacInData facInData){
        Json json = new Json();

        return json;
    }

    @ApiOperation(value = "设备类型", notes = "设备类型",httpMethod = "GET")
    @PassToken
    @RequestMapping("/deviceTypes")
    public Json getDeviceTypeList(UnitDeviceInData deviceInData){
        Json json = new Json();
        try {
            Map<String,String> map = deviceService.getDeviceTypeList();
            json.setSuccess(true);
            json.setObj(map);
        }catch (ServiceException e){
            json.setSuccess(false);
            json.setMsg(e.getMessage());
        }catch (Exception e){
            logger.error(e.getMessage());
            json.setSuccess(false);
            json.setMsg(UtilConst.ERRO_SYSTEM);
        }
        return json;
    }


}
