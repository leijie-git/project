package com.gw.unit.controller;

import com.gw.myAnnotation.PassToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/unit")
public class UnitManagerController {

	
	/**
	 * 维保单位管理
	 * @return
	 */
	@PassToken
	@RequestMapping("/maintenanceUnit")
	public String maintenanceUnit(){

		return "unit/maintenanceUnit";
	}
	
	/**
	 * 联网单位管理+维保+监管
	 * @return
	 */

	@RequestMapping("/baseInfo")
	@PassToken
	public String baseInfo(){
		return "unit/baseInfo";
	}
	
	/**
	 * 单位编号绑定
	 * @return
	 */
	@PassToken
	@RequestMapping("/baseInfoRelation")
	public String baseInfoRelation(){
		return "unit/baseInfoRelation";
	}
	
	/**
	 * 维保单位人员管理
	 * @return
	 */
	@PassToken
	@RequestMapping("/maintenanceUser")
	public String maintenanceUser(){
		return "unit/maintenanceUser";
	}
	
	/**
	 * 联网单位人员管理
	 * @return
	 */
	@PassToken
	@RequestMapping("/networkingUser")
	public String networkingUser(){
		return "unit/networkingUser";
	}

	
	/**
	 * 单位视频管理
	 * @return
	 */
	@PassToken
	@RequestMapping("/video")
	public String video(){
		
		return "unit/video";
	}
	
	/**
	 * 单位危险品管理
	 * @return
	 */
	@PassToken
	@RequestMapping("/dangerous")
	public String dangerous(){
		
		return "unit/dangerous";
	}
	
	/**
	 * 重点单位管理
	 * @return
	 */
	@PassToken
	@RequestMapping("/keyParts")
	public String keyParts(){
		
		return "unit/keyParts";
	}

	/**
	 * 建筑物管理
	 * @return
	 */
	@RequestMapping("/build")
	@PassToken
	public String build(){
		
		return "unit/build";
	}
	
	/**
	 * 建筑物管理
	 * @return
	 */
	@PassToken
	@RequestMapping("/buildArea")
	public String buildArea(){
		
		return "unit/buildArea";
	}
	
	/**
	 * 人员经历管理
	 * @return
	 */
	@PassToken
	@RequestMapping("/userExperience")
	public String userExperience(){
		return "unit/userExperience";
	}
}
