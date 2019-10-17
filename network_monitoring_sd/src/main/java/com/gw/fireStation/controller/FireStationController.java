package com.gw.fireStation.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/fireStation")
public class FireStationController {
	
	/**
	 * 消防人员管理
	 * @return
	 */
	@RequestMapping("/xfzUser")
	public String XZFUser(){
		return "fireStation/xfzUser";
	}
	
	/**
	 * 消防设施管理
	 * @return
	 */
	@RequestMapping("/xfzEquipment")
	public String XFZEquipment(){
		return "fireStation/xfzEquipment";
	}
}


