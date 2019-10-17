package com.gw.alarm.controller;

import com.github.pagehelper.PageInfo;
import com.gw.alarm.data.AlarmInData;
import com.gw.alarm.data.PhoneOutData;
import com.gw.alarm.data.ReturnData;
import com.gw.alarm.service.AlarmInfoService;
import com.gw.common.Json;
import com.gw.exception.ServiceException;
import com.gw.mapper.entity.AddresselHostpointOut;
import com.gw.myAnnotation.PassToken;
import com.gw.util.CodecUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Api
@RestController
@RequestMapping("/webHttp")
public class AlarmInfoController {

	private Logger log = LoggerFactory.getLogger(AlarmInfoController.class);
	@Resource
	private AlarmInfoService alarmInfoService;

	@ApiOperation(value = "接收RTU告警", notes = "接收RTU告警",httpMethod = "POST")
	@PassToken
	@PostMapping("/SendRTUAlarm")
	public ReturnData sendRTUAlarm(HttpServletRequest request) throws Exception {
		ReturnData outData = new ReturnData();
		try {
			alarmInfoService.sendRTUAlarm(request);
			outData.setIsSuccess(true);
			outData.setMessage("true success");
		} catch (ServiceException e) {
			log.error(e.getMessage(), e);
			outData.setIsSuccess(false);
			outData.setMessage(e.getMessage());
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			outData.setIsSuccess(false);
			outData.setMessage("failed due to null");
		}
		return outData;
	}

	@ApiOperation(value = "接收报警主机告警", notes = "含传输装置/NB设备",httpMethod = "POST")
	@PassToken
	@PostMapping("/SendBJZJAlarm")
	public ReturnData sendBJZJAlarm(HttpServletRequest request) throws Exception {
		ReturnData outData = new ReturnData();
		try {
			alarmInfoService.sendBJZJAlarm(request);
			outData.setIsSuccess(true);
			outData.setMessage("true success");
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			outData.setIsSuccess(false);
			outData.setMessage("failed due to null");
		}
		return outData;

	}

	@ApiOperation(value = "获取需要拨打的电话",httpMethod = "GET")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "phone",value = "手机号",required = true,dataType = "String"),
			@ApiImplicitParam(name = "msg",value = "描述",required = true,dataType = "String"),
			@ApiImplicitParam(name = "status",value = "状态",required = true,dataType = "String")
	})
	@PassToken
	@RequestMapping("/getPhones")
	public Json getPhones() throws Exception {
		Json json = new Json();
		try {
			List<PhoneOutData> outData = alarmInfoService.getPhones();
			json.setSuccess(true);
			json.setObj(outData);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			json.setMsg(e.getMessage());
		}
		return json;

	}


	@ApiOperation(value = "更新状态",httpMethod = "GET")
	@ApiImplicitParam(name = "ids",value = "告警数据主键ID（逗号分隔的字符串）",required = true,dataType = "String")
	@PassToken
	@RequestMapping("/updatePhoneStatus")
	public Json updatePhoneStatus(String ids) throws Exception {
		Json json = new Json();
		try {
			alarmInfoService.updatePhoneStatus(ids);
			json.setSuccess(true);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			json.setMsg(e.getMessage());
		}
		return json;
	}

	@PassToken
	@RequestMapping("/produceEncodeCipter")
	public String produceEncodeCipter(String cipter,String key){
		return CodecUtil.encode64(key,cipter);
	}

	/**
	 *@描述  根据主机ID查询当前主机的所有点位
	 *@创建人 Jie.Lei
	 *@参数
	 *@返回值
	 *@创建时间 2019/7/24
	 */
	@PassToken
	@RequestMapping("/getPointByEqid")
	public Json getPointByEqid(AlarmInData alarmInData){

		Json json=new Json();
		try {
			PageInfo<AddresselHostpointOut> pointList = alarmInfoService.getPointByEqid(alarmInData);
			json.setSuccess(true);
			json.setObj(pointList);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			json.setMsg(e.getMessage());
		}
		return  json;
	}
}
