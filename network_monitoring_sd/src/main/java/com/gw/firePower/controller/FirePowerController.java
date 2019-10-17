package com.gw.firePower.controller;

import com.gw.myAnnotation.PassToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/firePower")
public class FirePowerController {
	@PassToken
	@RequestMapping("/firePowerManage")
	public String firePowerManage(){
		return "/firePower/firePowerManage";
	}
	@PassToken
	@RequestMapping("/fireStationManage")
	public String fireStationManage(){
		return "/firePower/fireStationManage";
	}
	@PassToken
	@RequestMapping("/squadron")
	public String squadron(){
		return "/firePower/squadron";
	}

}
