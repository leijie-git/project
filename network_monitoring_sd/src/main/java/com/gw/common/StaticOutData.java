package com.gw.common;

import lombok.Data;

/**
 * 网站信息
 * @author zfg
 *
 */
@Data
public class StaticOutData {
	
	private String title;//网站标题
	private String record;//网站备案号
	private String recordAddress;//备案地址
	private String gwUrl;//官网地址
	private String providers;//技术提供商
}
