package com.gw.common;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gw.myAnnotation.PassToken;
import com.gw.systemManager.data.SysPropertiesOutData;
import com.gw.systemManager.service.PropertiesManageService;

/**
 * 获取网站静态资源
 * @author zfg
 *
 */
@RestController

public class StaticController {

//	@Value("${wz.title}")
//	private String title;
//	@Value("${wz.record}")
//	private String record;
//	@Value("${wz.recordAddress}")
//	private String recordAddress;
//	@Value("${gw.url}")
//	private String gwUrl;
//	@Value("${js.providers}")
//	private String providers;
	@Resource
	private PropertiesManageService propertiesManageService;
	
	@RequestMapping("/getStatic")
	@PassToken
	public Json getStatic() throws Exception {
		SysPropertiesOutData properties = propertiesManageService.getProperties();
		Json json = new Json();
		StaticOutData outData = new StaticOutData();
		outData.setGwUrl(properties.getGwUrl());
		outData.setTitle(properties.getWzTitle());
		outData.setProviders(properties.getJsProviders());
		outData.setRecord(properties.getWzRecord());
		outData.setRecordAddress(properties.getWzRecordaddress());
		json.setObj(outData);
		json.setSuccess(true);
		return json;
		
	}
}
