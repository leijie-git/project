package com.gw.openApi.common.controller;

import com.github.pagehelper.PageInfo;
import com.gw.common.BaseController;
import com.gw.common.Json;
import com.gw.exception.ServiceException;
import com.gw.front.couplet.data.FrontCoupletAlarmInfoOutData;
import com.gw.front.history.data.FrontHistoryAlarmInfoOutData;
import com.gw.front.history.data.FrontHistoryInterfaceAlarmData;
import com.gw.myAnnotation.PassToken;
import com.gw.openApi.common.data.in.AlarmInData;
import com.gw.openApi.common.service.IAlarmHistoryService;
import com.gw.util.UtilConst;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Api(tags = "主机报警数据明细")
@RestController
@RequestMapping("/rest/alarm")
public class OpenApiAlarmInfoController extends BaseController{
    private Logger logger = LoggerFactory.getLogger(OpenApiAlarmInfoController.class);

    @Autowired
    private IAlarmHistoryService alarmHistoryService;

    @ApiOperation(value = "主机报警数据明细", notes = "主机报警数据明细",httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageSize", value = "页面大小", required = true, dataType = "Integer", paramType = "path"),
            @ApiImplicitParam(name = "pageNumber", value = "当前页码", required = true, dataType = "Integer", paramType = "path")
    })
    @PassToken
    @RequestMapping("/mainframe/{pageSize}/{pageNumber}")
    public Json getMainframeAlarmList(AlarmInData inDate){
        logger.info("=========主机告警数据请求参数"+inDate.toString());
        Json json = new Json();
        try {
            PageInfo<FrontHistoryAlarmInfoOutData> mainframeAlarmList = alarmHistoryService.getMainframeAlarmList(inDate);
            json.setObj(mainframeAlarmList);
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

    @ApiOperation(value = "报警类型", notes = "报警类型",httpMethod = "GET")
    @PassToken
    @RequestMapping("/alarmType")
    public Json getAlarmTpyeList(){
        Json json = new Json();
        try {
            Map<Integer,String> map = alarmHistoryService.getAlarmTypeList();
            json.setObj(map);
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

    @ApiOperation(value = "主机报警明细", notes = "主机报警明细",httpMethod = "GET")
    @ApiImplicitParam(name = "alarmId", value = "报警数据ID", required = true, dataType = "String", paramType = "Query")
    @PassToken
    @RequestMapping("/mainframeDealDetail")
    public Json getMainframeDealDetail(String alarmId){
        Json json = new Json();
        try {
            FrontCoupletAlarmInfoOutData dealDetail = alarmHistoryService.getMainframeDealDetail(alarmId);
            json.setObj(dealDetail);
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

    @ApiOperation(value = "RTU报警明细", notes = "RTU报警明细",httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageSize", value = "页面大小", required = true, dataType = "Integer", paramType = "path"),
            @ApiImplicitParam(name = "pageNumber", value = "当前页码", required = true, dataType = "Integer", paramType = "path")
    })
    @PassToken
    @RequestMapping("/rtu/{pageSize}/{pageNumber}")
    public Json getRtuAlarmList(AlarmInData inDate){
        Json json = new Json();
        try {
            PageInfo<FrontHistoryInterfaceAlarmData> rtuAlarmList = alarmHistoryService.getRTUAlarmList(inDate);
            json.setObj(rtuAlarmList);
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

    @ApiOperation(value = "RTU报警明细", notes = "RTU报警明细",httpMethod = "GET")
    @ApiImplicitParam(name = "alarmId", value = "报警数据ID", required = true, dataType = "String", paramType = "Query")
    @PassToken
    @RequestMapping("/rtuDealDetail")
    public Json getRtuDealDetail(String alarmId){
        Json json = new Json();
        try {
            FrontCoupletAlarmInfoOutData dealDetail = alarmHistoryService.getRtuDealDetail(alarmId);
            json.setObj(dealDetail);
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
