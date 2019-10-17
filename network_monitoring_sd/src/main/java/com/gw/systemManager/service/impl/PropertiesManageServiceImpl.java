package com.gw.systemManager.service.impl;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gw.common.CacheUtils;
import com.gw.mapper.SysPropertiesMapper;
import com.gw.mapper.entity.SysProperties;
import com.gw.systemManager.data.SysPropertiesInData;
import com.gw.systemManager.data.SysPropertiesOutData;
import com.gw.systemManager.service.PropertiesManageService;
import com.gw.util.HttpGetAXToken;
import com.gw.util.HttpGetWXToken;
import com.gw.util.HttpGetXMHToken;
import com.gw.util.Token;
import com.gw.util.Util;

@Service
public class PropertiesManageServiceImpl implements PropertiesManageService {
	@Autowired
	private SysPropertiesMapper sysPropertiesMapper;

	@Override
	public SysPropertiesOutData getProperties() throws Exception{
		SysPropertiesOutData sysPropertiesOutData = (SysPropertiesOutData) CacheUtils.get("properties");
		if(Util.isEmpty(sysPropertiesOutData)){
			sysPropertiesOutData = sysPropertiesMapper.getProperties();
			CacheUtils.put("properties", sysPropertiesOutData);
		}
		return sysPropertiesOutData;
	}

	@Override
	public void updateProperties(SysPropertiesInData inData) throws Exception {
		if(Util.isNotEmpty(inData.getId())){
			SysProperties sysProperties = sysPropertiesMapper.selectByPrimaryKey(Long.valueOf(inData.getId()));
			BeanUtils.copyProperties(inData, sysProperties);
			sysPropertiesMapper.updateByPrimaryKey(sysProperties);
		}else{
			SysProperties sysProperties = new SysProperties();
			BeanUtils.copyProperties(inData, sysProperties);
			sysProperties.setId(1l);
			sysPropertiesMapper.insert(sysProperties);
		}
		//更新token过期时间
		HttpGetWXToken.setExpiresin(0l);
		HttpGetAXToken.setExpiresin(0l);
		HttpGetXMHToken.setExpiresin(0l);
		Token.setExpiresin(0l);
		//从缓存中删除
		CacheUtils.remove("properties");
	}
	
}
