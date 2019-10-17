package com.gw.common;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.stereotype.Component;

/**
 * 
 * Spring的工具类，用来获得配置文件中的bean
 * 
 */
@Component
public class SpringBeanTool implements ApplicationContextAware {
	private static ApplicationContext applicationContext = null;

	@Override
	public void setApplicationContext(ApplicationContext context) throws BeansException {
		if (SpringBeanTool.applicationContext == null) {
			SpringBeanTool.applicationContext = context;
		}
	}

	public static ApplicationContext getaApplicationContext() {
		// 如果为空，则手动创建
		if (applicationContext == null) {
			applicationContext = new FileSystemXmlApplicationContext("classpath:applicationContext-local.xml");
		}
		return applicationContext;
	}

	public static Object getBean(String name) {
		return getaApplicationContext().getBean(name);
	}
}
