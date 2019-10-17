package com.gw.device.service.impl;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.gw.common.SnowflakeIdWorker;
import com.gw.device.data.EqSystemInData;
import com.gw.device.data.EqSystemOutData;
import com.gw.device.service.EqSystemService;
import com.gw.mapper.UtBaseEqsystemMapper;
import com.gw.mapper.entity.UtBaseEqsystem;
import com.gw.util.Util;

@Service
public class EqSystemServiceImpl implements EqSystemService{
	
	@Autowired
	private UtBaseEqsystemMapper eqsystemMapper;
	@Autowired
	private SnowflakeIdWorker snowflakeIdWorker;

	@Override
	public PageInfo<EqSystemOutData> pageList(String eqsystemname, Integer pageNumber, Integer pageSize) throws Exception{
		PageHelper.startPage(pageNumber, pageSize);
		List<EqSystemOutData> list = eqsystemMapper.getEqSystemList(eqsystemname);
		PageInfo<EqSystemOutData> pageInfo = new PageInfo<EqSystemOutData>(list);
		return pageInfo;
	}

	@Override
	public void addEqSystem(EqSystemInData inData) throws Exception{
		UtBaseEqsystem eqsystem = new UtBaseEqsystem();
		BeanUtils.copyProperties(inData, eqsystem);
		eqsystem.setId(snowflakeIdWorker.nextId());
		if(Util.isNotEmpty(inData.getEqsystemcode())){
			eqsystem.setEqsystemcode(Integer.parseInt(inData.getEqsystemcode()));
		}
		eqsystemMapper.insert(eqsystem);
	}

	@Override
	public void updateEqSystem(EqSystemInData inData) throws Exception{
		UtBaseEqsystem eqsystem = eqsystemMapper.selectByPrimaryKey(Long.parseLong(inData.getId()));
		BeanUtils.copyProperties(inData, eqsystem);
		if(Util.isNotEmpty(inData.getEqsystemcode())){
			eqsystem.setEqsystemcode(Integer.parseInt(inData.getEqsystemcode()));
		}else{
			eqsystem.setEqsystemcode(null);
		}
		eqsystemMapper.updateByPrimaryKey(eqsystem);
	}

	@Override
	public void deleteEqSystem(String id) throws Exception{
		eqsystemMapper.deleteByPrimaryKey(Long.valueOf(id));
	}

}
