package com.gw.systemManager.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.gw.common.SnowflakeIdWorker;
import com.gw.mapper.UtUnitPrivatekeyRelMapper;
import com.gw.mapper.entity.UtUnitPrivatekeyRel;
import com.gw.systemManager.data.UnitPrivatekeyRelInData;
import com.gw.systemManager.data.UnitPrivatekeyRelOutData;
import com.gw.systemManager.service.UnitPrivatekeyRelService;
import com.gw.util.Util;

import tk.mybatis.mapper.entity.Example;

@Service
public class UnitPrivatekeyRelServiceImpl implements UnitPrivatekeyRelService {
	@Autowired
	private UtUnitPrivatekeyRelMapper utUnitPrivatekeyRelMapper;
	@Autowired
	private SnowflakeIdWorker snowflakeIdWorker;

	@Override
	public PageInfo<UnitPrivatekeyRelOutData> privatekeyList(UnitPrivatekeyRelInData inData) throws Exception{
		PageHelper.startPage(inData.getPageNumber(), inData.getPageSize());
		List<UnitPrivatekeyRelOutData> list = utUnitPrivatekeyRelMapper.privatekeyList(inData);
		PageInfo<UnitPrivatekeyRelOutData> pageInfo = new PageInfo<UnitPrivatekeyRelOutData>(list);
		return pageInfo;
	}

	@Override
	public void addUnitPrivatekeyRel(UnitPrivatekeyRelInData inData) throws Exception{
		Example example = new Example(UtUnitPrivatekeyRel.class);
		example.createCriteria().andEqualTo("privateKey", inData.getPrivateKey());
		List<UtUnitPrivatekeyRel> list = utUnitPrivatekeyRelMapper.selectByExample(example);
		if(Util.isNotEmpty(list)){
			return;
		}
		UtUnitPrivatekeyRel utUnitPrivatekeyRel = new UtUnitPrivatekeyRel();
		utUnitPrivatekeyRel.setId(snowflakeIdWorker.nextId());
		utUnitPrivatekeyRel.setPrivateKey(inData.getPrivateKey());
		utUnitPrivatekeyRelMapper.insert(utUnitPrivatekeyRel);
	}

	@Override
	public void deleteUnitPrivatekeyRel(String privateKey) {
		Example example = new Example(UtUnitPrivatekeyRel.class);
		example.createCriteria().andEqualTo("privateKey", privateKey);
		utUnitPrivatekeyRelMapper.deleteByExample(example);
	}

	@Override
	public PageInfo<UnitPrivatekeyRelOutData> getAssociatedUnit(UnitPrivatekeyRelInData inData) throws Exception{
		PageHelper.startPage(inData.getPageNumber(), inData.getPageSize());
		List<UnitPrivatekeyRelOutData> list = utUnitPrivatekeyRelMapper.getAssociatedUnit(inData);
		PageInfo<UnitPrivatekeyRelOutData> pageInfo = new PageInfo<UnitPrivatekeyRelOutData>(list);
		return pageInfo; 
	}

	@Override
	public PageInfo<UnitPrivatekeyRelOutData> getUnassociatedUnit(UnitPrivatekeyRelInData inData) throws Exception{
		PageHelper.startPage(inData.getPageNumber(), inData.getPageSize());
		List<UnitPrivatekeyRelOutData> list = utUnitPrivatekeyRelMapper.getUnassociatedUnit(inData);
		PageInfo<UnitPrivatekeyRelOutData> pageInfo = new PageInfo<UnitPrivatekeyRelOutData>(list);
		return pageInfo; 
	}

	@Override
	@Transactional
	public void bindUnit(String privateKey, String unitIds) throws Exception{
		if(Util.isEmpty(unitIds)){
			return;
		}
		String[] split = unitIds.split(",");
		for (String unitId : split) {
			UtUnitPrivatekeyRel utUnitPrivatekeyRel = new UtUnitPrivatekeyRel();
			utUnitPrivatekeyRel.setId(snowflakeIdWorker.nextId());
			utUnitPrivatekeyRel.setPrivateKey(privateKey);
			utUnitPrivatekeyRel.setUnitId(Long.valueOf(unitId));
			utUnitPrivatekeyRelMapper.insert(utUnitPrivatekeyRel);
		}
	}

	@Override
	public void unbindUnit(String privateKey, String ids) throws Exception{
		if(Util.isEmpty(ids)){
			return;
		}
		String[] split = ids.split(",");
		for (String id : split) {
			utUnitPrivatekeyRelMapper.deleteByPrimaryKey(Long.valueOf(id));
		}
	}

}
