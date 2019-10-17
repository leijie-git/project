package com.gw.thirdInterface.controller;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gw.common.Json;
import com.gw.exception.ServiceException;
import com.gw.thirdInterface.data.DictOutData;
import com.gw.thirdInterface.data.TreeOutData;
import com.gw.thirdInterface.service.ThirdInterfaceService;

@RestController
@RequestMapping("/upload")
public class ThirdInterfaceController {

	@Resource
	private ThirdInterfaceService thirdInterfaceService;

	private Logger log = LoggerFactory.getLogger(ThirdInterfaceController.class);

	/**
	 * 上传单位信息
	 * 
	 * @param unitID
	 * @return
	 */
	@RequestMapping("/setOrg")
	public Json setOrg(String unitID, String company) {
		Json json = new Json();
		try {
			thirdInterfaceService.setOrg(unitID, company);
			json.setSuccess(true);
		} catch (Exception e) {
			json.setMsg(e.getMessage());
			log.error(e.getMessage(), e);
		}
		return json;
	}

	/**
	 * 上传设备信息
	 * 
	 * @param unitID
	 * @return
	 */
	@RequestMapping("/setDevice")
	public Json setDevice(String netDeviceID, String unitID, String companyName) {
		Json json = new Json();
		try {
			thirdInterfaceService.setDevice(netDeviceID, unitID, companyName);
			json.setSuccess(true);
		} catch (Exception e) {
			json.setMsg(e.getMessage());
			log.error(e.getMessage(), e);
		}
		return json;
	}

	/**
	 * 设备上下线
	 * 
	 * @return
	 */
	@RequestMapping("/setDeviceStatus")
	public Json setDeviceStatus(String unitId, String netDeviceId) {
		Json json = new Json();
		try {
			thirdInterfaceService.setDeviceStatus(unitId, netDeviceId);
			json.setSuccess(true);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return json;
	}

	/**
	 * 上传设备信息
	 * 
	 * @param pointID
	 *            点位id
	 * @param isFire
	 *            1:报警主机点位，0：RTU报警点位
	 * @param unitID
	 *            单位id
	 * @param companyName
	 *            同步单位
	 * @return
	 */
	@RequestMapping("/setPoint")
	public Json setPoint(String pointID, String isFire, String unitID) {
		Json json = new Json();
		try {
			thirdInterfaceService.setPoint(pointID, isFire, unitID);
			json.setSuccess(true);
		} catch (Exception e) {
			json.setMsg(e.getMessage());
			log.error(e.getMessage(), e);
		}
		return json;
	}

	/**
	 * 实时告警 安讯平台
	 * 
	 * @param uuid
	 * @param event
	 * @param date
	 * @return
	 */
	@RequestMapping("/realTimeAlarmAX")
	public Json realTimeAlarmAX(String uuid, String event, String milliseconds, String address) {
		Json json = new Json();
		try {
			thirdInterfaceService.realTimeAlarmAX(uuid, event, milliseconds, address);
			json.setSuccess(true);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return json;
	}

	/**
	 * 消警
	 * 
	 * @param netDeviceId
	 * @param unitId
	 * @return
	 */
	@RequestMapping("/clearAlarm")
	public Json clearAlarm(String netDeviceId, String unitId) {
		Json json = new Json();
		try {
			thirdInterfaceService.clearAlarm(netDeviceId, unitId);
			json.setSuccess(true);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return json;
	}

	/**
	 * 获取字典数据
	 * 
	 * @param t
	 * @return
	 */
	@RequestMapping("/getDict")
	public Json getDict(String t) {
		Json json = new Json();
		try {
			List<DictOutData> list = thirdInterfaceService.getDict(t);
			json.setObj(list);
			json.setSuccess(true);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return json;
	}

	/**
	 * 获取树形数据
	 * 
	 * @param t
	 * @return
	 */
	@RequestMapping("/getTree")
	public Json getTree(String t) {
		Json json = new Json();
		try {
			List<TreeOutData> list = thirdInterfaceService.getTree(t);
			json.setObj(list);
			json.setSuccess(true);
		} catch (ServiceException e) {
			json.setMsg(e.getMessage());
			log.error(e.getMessage(), e);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return json;
	}
}
