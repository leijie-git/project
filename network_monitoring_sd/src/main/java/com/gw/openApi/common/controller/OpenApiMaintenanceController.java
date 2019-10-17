package com.gw.openApi.common.controller;

import com.github.pagehelper.PageInfo;
import com.gw.common.Json;
import com.gw.exception.ServiceException;
import com.gw.front.maintenance.data.FrontMaintenanceOutData;
import com.gw.myAnnotation.PassToken;
import com.gw.openApi.common.data.in.MaintenanceInData;
import com.gw.openApi.common.service.IMaintenanceService;
import com.gw.util.UtilConst;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@Api(tags ="主机信息")
@RestController
@RequestMapping("/rest/maintenance")
public class OpenApiMaintenanceController {
    private Logger logger = LoggerFactory.getLogger(OpenApiMaintenanceController.class);

    @Resource
    private IMaintenanceService maintenanceService;

    @ApiOperation(value = "主机列表", notes = "主机列表",httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageSize", value = "页面大小", required = true, dataType = "Integer", paramType = "path"),
            @ApiImplicitParam(name = "pageNumber", value = "当前页码", required = true, dataType = "Integer", paramType = "path")
    })
    @PassToken
    @RequestMapping("/list/{pageSize}/{pageNumber}")
    public Json getMaintenanceList(MaintenanceInData inData){
        Json json =  new Json();
        try {
            PageInfo<FrontMaintenanceOutData> maintenanceList = maintenanceService.getMaintenanceList(inData);
            json.setSuccess(true);
            json.setObj(maintenanceList);
        } catch (ServiceException e) {
            json.setSuccess(false);
            json.setMsg(e.getMessage());
        } catch (Exception e) {
            json.setSuccess(false);
            json.setMsg(UtilConst.ERRO_SYSTEM);
        }
        return json;
    }

    @ApiOperation(value = "主机列表详情", notes = "主机列表详情",httpMethod = "GET")
    @ApiImplicitParam(name = "repairId", value = "维修单ID", required = true, dataType = "String", paramType = "Query")
    @PassToken
    @RequestMapping("/detail")
    public Json getMaintenanceDetail(String repairId){
        Json json =  new Json();
        try {
            FrontMaintenanceOutData outData = maintenanceService.getMaintenanceDetail(repairId);
            json.setSuccess(true);
            json.setObj(outData);
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
