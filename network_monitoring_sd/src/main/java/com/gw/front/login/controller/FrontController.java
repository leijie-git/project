package com.gw.front.login.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gw.common.BaseController;
import com.gw.myAnnotation.PassToken;

@Controller
@RequestMapping("/")
public class FrontController extends BaseController {

	@PassToken
	@RequestMapping("")
	public String index(HttpServletRequest request) {
		return "redirect:html/View/Login.html";
	}
	@PassToken
	@RequestMapping("/login")
	public String login(HttpServletRequest request) {
		return "redirect:console/index";
	}
}
