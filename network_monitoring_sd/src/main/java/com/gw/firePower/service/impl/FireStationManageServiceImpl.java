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
import com.gw.firePower.data.FireStationManageInData;
import com.gw.firePower.data.FireStationManageOutData;
import com.gw.firePower.service.FireStationManageService;
import com.gw.mapper.UtFireStationMapper;
import com.gw.mapper.entity.UtFireStation;
import com.gw.util.Util;

@Service
public class FireStationManageServiceImpl implements FireStationManageService {
	@Autowired
	private SnowflakeIdWorker snowflakeIdWorker;
	@Autowired
	private UtFireStationMapper utFireStationMapper;

	@Override
	public void addFireStation(Long id, FireStationManageInData inData) throws Exception{
		UtFireStation utFireStation = new UtFireStation();
		BeanUtils.copyProperties(inData, utFireStation);
		utFireStation.setId(snowflakeIdWorker.nextId());
		utFireStation.setCreateDate(new Date());
		utFireStation.setCreateUser(id);
		if(Util.isNotEmpty(inData.getPowerId())){
			utFireStation.setPowerId(Long.valueOf(inData.getPowerId()));
		}
		utFireStationMapper.insert(utFireStation);
	}

	@Override
	public void updateFireStation(Long id, FireStationManageInData inData) throws Exception{
		UtFireStation utFireStation = utFireStationMapper.selectByPrimaryKey(Long.valueOf(inData.getId()));
		BeanUtils.copyProperties(inData, utFireStation);
		utFireStation.setUpdateDate(new Date());
		utFireStation.setUpdateUser(id);
		if(Util.isNotEmpty(inData.getPowerId())){
			utFireStation.setPowerId(Long.valueOf(inData.getPowerId()));
		}else{
			throw new ServiceException("消防站不能为空!");
		}
		utFireStationMapper.updateByPrimaryKey(utFireStation);
	}

	@Override
	public PageInfo<FireStationManageOutData> getFireStationList(Integer pageNumber, Integer pageSize, String fireStationName, String name) throws Exception {
		PageHelper.startPage(pageNumber, pageSize);
		List<FireStationManageOutData> list = utFireStationMapper.getFireStationList(fireStationName,name);
		PageInfo<FireStationManageOutData> pageInfo = new PageInfo<FireStationManageOutData>(list);
		return pageInfo;
	}

	@Override
	public void deleteFireStation(String id) throws Exception{
		utFireStationMapper.deleteByPrimaryKey(Long.valueOf(id));
	}

}
