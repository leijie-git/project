package com.gw.openApi.common.controller;

import com.gw.common.Json;
import com.gw.exception.ServiceException;
import com.gw.myAnnotation.PassToken;
import com.gw.openApi.common.data.in.CheckedAlarmVo;
import com.gw.openApi.common.data.out.HistoryAlarmCount;
import com.gw.openApi.common.service.IAlarmApiService;
import com.gw.util.UtilConst;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(tags = "历史报警数据明细")
@RestController
@RequestMapping("/api/alarm")
public class AlarmApiController {

    @Autowired
    private IAlarmApiService alarmApiService;

    @ApiOperation(value = "报警数据明细", notes = "报警数据明细",httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageSize", value = "页面大小", required = true, dataType = "Integer", paramType = "path"),
            @ApiImplicitParam(name = "pageNumber", value = "当前页码", required = true, dataType = "Integer", paramType = "path")
    })
    @PassToken
    @RequestMapping("/list/{pageSize}/{pageNumber}")
    public Json getAlarmList(CheckedAlarmVo alarmInData) {
        Json json = new Json();
        try {
            Object alarmList = alarmApiService.getAlarmList(alarmInData);
            json.setSuccess(true);
            json.setObj(alarmList);
        } catch (ServiceException e) {
            json.setSuccess(false);
            json.setMsg(e.getMessage());
        } catch (Exception e) {
            json.setSuccess(false);
            json.setMsg(UtilConst.ERRO_SYSTEM);
        }
        return json;
    }

    @ApiOperation(value = "报警数据明细", notes = "报警数据明细",httpMethod = "GET")
    @PassToken
    @RequestMapping("/alarmCount")
    public Json getAlarmCount(CheckedAlarmVo alarmInData) {
        Json json = new Json();
        try {
            List<HistoryAlarmCount> alarmList = alarmApiService.getAlarmCount(alarmInData);
            json.setSuccess(true);
            json.setObj(alarmList);
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
