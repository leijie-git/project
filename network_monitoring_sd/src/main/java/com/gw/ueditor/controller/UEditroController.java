package com.gw.ueditor.controller;

import java.io.InputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@ResponseBody
@RequestMapping("/static/ueditor/jsp")
public class UEditroController {

	@RequestMapping("/controller")
	public String getConfigInfo(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ClassLoader classloader = Thread.currentThread().getContextClassLoader();
		InputStream is = classloader.getResourceAsStream("config.json");
		String str = IOUtils.toString(is, "utf-8");
		return str;
	}
}
