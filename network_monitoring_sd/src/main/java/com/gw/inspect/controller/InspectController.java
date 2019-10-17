package com.gw.inspect.controller;

import com.gw.myAnnotation.PassToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
@Controller
@RequestMapping("/inspect")
public class InspectController {

	/**
	 * 巡查点分类管理
	 * @return
	 */
	@PassToken
	@GetMapping("/inspectSiteClass")
	public String inspectSiteClass() {
		return "/inspect/inspectSiteClass";
	}
	
	/**
	 * 巡查点分类检查项管理
	 * @return
	 */
	@PassToken
	@GetMapping("/siteClassDetial")
	public String siteClassDetial() {
		return "/inspect/siteClassDetial";
	}
	
	/**
	 * 巡查计划
	 * @return
	 */
	@PassToken
	@GetMapping("/inspectPlan")
	public String inspectPlan() {
		return "/inspect/inspectPlan";
	}
	
	/**
	 * 巡查点管理
	 * @return
	 */
	@PassToken
	@GetMapping("/inspectSite")
	public String inspectSite() {
		return "/inspect/inspectSite";
	}
}
