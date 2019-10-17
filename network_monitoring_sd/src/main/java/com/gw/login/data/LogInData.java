package com.gw.login.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * @author lxy
 * @date 2018年4月16日
 * @Description: 日志对象
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LogInData {
	private String requestURL;
	private String requestURI;
	private String queryString;
	private String remoteAddr;
	private String remoteHost;
	private String remotePort;
	private String localAddr;
	private String localName;
	private String method;
	private String headers;
	private String parameters;
	private String classMethod;
	private String args;

}
