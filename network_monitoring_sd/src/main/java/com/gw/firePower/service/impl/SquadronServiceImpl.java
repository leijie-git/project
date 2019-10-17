package com.gw.firePower.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.gw.common.SnowflakeIdWorker;
import com.gw.exception.ServiceException;
import com.gw.firePower.data.SquadronInData;
import com.gw.firePower.data.SquadronOutData;
import com.gw.firePower.service.SquadronService;
import com.gw.mapper.UtSquadronMapper;
import com.gw.mapper.entity.UtSquadron;
import com.gw.util.Util;

@Service
public class SquadronServiceImpl implements SquadronService{
	@Autowired
	private UtSquadronMapper utSquadronMapper;
	@Autowired
	private SnowflakeIdWorker snowflakeIdWorker;
	
	@Override
	public PageInfo<SquadronOutData> getSquadronList(Integer pageNumber, Integer pageSize,String name, String powerName) throws Exception{
		PageHelper.startPage(pageNumber, pageSize);
		List<SquadronOutData> list = utSquadronMapper.getSquadronList(name,powerName);
		PageInfo<SquadronOutData> pageInfo = new PageInfo<SquadronOutData>(list);
		return pageInfo;
	}

	@Override
	public void addSquadron(Long id, SquadronInData inData) throws Exception{
		UtSquadron utSquadron = new UtSquadron();
		BeanUtils.copyProperties(inData, utSquadron);
		utSquadron.setId(snowflakeIdWorker.nextId());
		utSquadron.setCreateDate(new Date());
		utSquadron.setCreateUser(id);
		if(Util.isNotEmpty(inData.getPowerId())){
			utSquadron.setPowerId(Long.valueOf(inData.getPowerId()));
		}
		utSquadronMapper.insert(utSquadron);
	}

	@Override
	public void updateSquadron(Long id, SquadronInData inData) throws Exception{
		UtSquadron utSquadron = utSquadronMapper.selectByPrimaryKey(Long.valueOf(inData.getId()));
		BeanUtils.copyProperties(inData, utSquadron);
		utSquadron.setUpdateDate(new Date());
		utSquadron.setUpdateUser(id);
		if(Util.isNotEmpty(inData.getPowerId())){
			utSquadron.setPowerId(Long.valueOf(inData.getPowerId()));
		}else{
			throw new ServiceException("消防中队不能为空!");
		}
		utSquadronMapper.updateByPrimaryKey(utSquadron);
	}

	@Override
	public void deleteSquadron(String id) throws Exception{
		utSquadronMapper.deleteByPrimaryKey(Long.valueOf(id));
	}

}
