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
import com.gw.firePower.data.FirePowerInData;
import com.gw.firePower.data.FirePowerOutData;
import com.gw.firePower.service.FirePowerManageService;
import com.gw.mapper.UtFirePowerMapper;
import com.gw.mapper.UtFireStationMapper;
import com.gw.mapper.UtSquadronMapper;
import com.gw.mapper.entity.UtFirePower;
import com.gw.mapper.entity.UtFireStation;
import com.gw.mapper.entity.UtSquadron;
import com.gw.util.Util;

import tk.mybatis.mapper.entity.Example;

@Service
public class FirePowerManageServiceImpl implements FirePowerManageService {
	@Autowired
	private SnowflakeIdWorker snowflakeIdWorker;
	@Autowired
	private UtFirePowerMapper utFirePowerMapper;
	@Autowired
	private UtFireStationMapper utFireStationMapper;
	@Autowired
	private UtSquadronMapper utSquadronMapper;

	@Override
	public void addFirePower(FirePowerInData inData) throws Exception{
		UtFirePower firePower = new UtFirePower();
		BeanUtils.copyProperties(inData, firePower);
		firePower.setId(snowflakeIdWorker.nextId());
		firePower.setCreateDate(new Date());
		if(Util.isNotEmpty(inData.getUnitId())){
			firePower.setUnitId(Long.valueOf(inData.getUnitId()));
		}
		if(Util.isNotEmpty(inData.getCreateUser())){
			firePower.setCreateUser(Long.valueOf(inData.getCreateUser()));
		}
		if(Util.isNotEmpty(inData.getPid())){
			firePower.setPid(Long.valueOf(inData.getPid()));
		}
		utFirePowerMapper.insert(firePower);
	}

	@Override
	public void updateFirePower(FirePowerInData inData) throws Exception {
		UtFirePower firePower = utFirePowerMapper.selectByPrimaryKey(Long.valueOf(inData.getId()));
		BeanUtils.copyProperties(inData, firePower);
		firePower.setUpdateDate(new Date());
		firePower.setUpdateUser(Long.valueOf(inData.getUpdateUser()));
		if(Util.isNotEmpty(inData.getUnitId())){
			firePower.setUnitId(Long.valueOf(inData.getUnitId()));
		}else{
			firePower.setUnitId(null);
		}
		if(Util.isNotEmpty(inData.getPid())){
			firePower.setPid(Long.valueOf(inData.getPid()));
		}else{
			firePower.setPid(null);
		}
		utFirePowerMapper.updateByPrimaryKey(firePower);
	}

	@Override
	public PageInfo<FirePowerOutData> getFirePowerList(Integer pageNumber, Integer pageSize, FirePowerInData inData) throws Exception{
		PageHelper.startPage(pageNumber, pageSize);
		List<FirePowerOutData> outData = utFirePowerMapper.getFirePowerList(inData);
		PageInfo<FirePowerOutData> pageInfo = new PageInfo<FirePowerOutData>(outData);
		return pageInfo;
	}

	@Override
	public void deleteFirePower(String id) throws Exception{
		UtFirePower utFirePower = utFirePowerMapper.selectByPrimaryKey(Long.valueOf(id));
		if(Long.parseLong(utFirePower.getType()) == 0 || Long.parseLong(utFirePower.getType()) == 2){
			Example example = new Example(UtFireStation.class);
			example.createCriteria().andEqualTo("powerId", utFirePower.getId());
			int countByExample = utFireStationMapper.selectCountByExample(example);
			if(countByExample > 0){
				throw new ServiceException("删除失败，该消防站下有设备信息未删除！");
			}
		}else if(Long.parseLong(utFirePower.getType())==1){
			Example example = new Example(UtSquadron.class);
			example.createCriteria().andEqualTo("powerId", utFirePower.getId());
			int countByExample = utSquadronMapper.selectCountByExample(example);
			if(countByExample > 0){
				throw new ServiceException("删除失败，该中队下有中队配置信息未删除！");
			}
		}
		Example example = new Example(UtFirePower.class);
		example.createCriteria().andEqualTo("pid", utFirePower.getId());
		int i = utFirePowerMapper.selectCountByExample(example);
		if(i>0){
			if(Long.parseLong(utFirePower.getType()) == 6){
				throw new ServiceException("删除失败，该消防大队下有消防中队信息未删除！");
			}else if(Long.parseLong(utFirePower.getType()) == 7){
				throw new ServiceException("删除失败，该消防支队下有消防大队信息未删除！");
			}else{
				throw new ServiceException("删除失败！");
			}
		}
		
		utFirePowerMapper.deleteByPrimaryKey(Long.valueOf(id));
	}

	@Override
	public List<FirePowerOutData> getFireStationNameByType(String type) throws Exception {
		return utFirePowerMapper.getFireStationNameByType(type);
	}

}
