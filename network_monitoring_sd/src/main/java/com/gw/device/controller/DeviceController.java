package com.gw.device.controller;

import com.gw.myAnnotation.PassToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 设备页面跳转Controller
 * 
 * @author SY
 *
 */
@Controller

@RequestMapping("/device")
public class DeviceController {
	@PassToken
	@RequestMapping("/netDevice")
	public String netDevice() {
		return "device/netDevice";
	}
	@PassToken
	@RequestMapping("/equipmentFac")
	public String equipmentFac() {
		return "device/equipments";
	}
	@PassToken
	@RequestMapping("/netDeviceRel")
	public String netDeviceRel() {
		return "device/addressRel";
	}
	@PassToken
	@RequestMapping("/eqClass")
	public String eqClass() {
		return "device/eqClass";
	}
	@PassToken
	@RequestMapping("/eqSystem")
	public String eqSystem() {
		return "device/eqSystem";
	}
	@PassToken
	@RequestMapping("/wirelessDevice")
	public String wirelessDevice() {
		return "device/wirelessDevice";
	}

	
	/**
	 * 灭火器界面
	 * 
	 * @return
	 * @throws Exception
	 */
	@PassToken
	@RequestMapping("/equipmentPage")
	public String equipmentPage() throws Exception {
		return "device/equipment";
	}

	/**
	 * CRT标点
	 * 
	 * @return
	 * @throws Exception
	 */
	@PassToken
	@RequestMapping("/CRTMark")
	public String crtMark() throws Exception {
		return "device/CRTMark";
	}
	
	/**
	 * 点位视频
	 * @return
	 * @throws Exception
	 */
	@PassToken
	@RequestMapping("/pointVideo")
	public String pointVideo() throws Exception {
		return "device/pointVideo";
	}
}
