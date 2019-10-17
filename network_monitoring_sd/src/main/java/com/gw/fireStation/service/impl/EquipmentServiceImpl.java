package com.gw.fireStation.service.impl;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.gw.common.SnowflakeIdWorker;
import com.gw.exception.ServiceException;
import com.gw.fireStation.data.EquipmentInData;
import com.gw.fireStation.data.EquipmentOutData;
import com.gw.fireStation.service.EquipmentService;
import com.gw.mapper.UtUnitXfzequipmentMapper;
import com.gw.mapper.entity.UtUnitXfzequipment;
import com.gw.util.Util;
import com.gw.util.UtilMessage;

/**
 * 消防设施服务层
 * @author SY
 *
 */
@Service
public class EquipmentServiceImpl implements EquipmentService{
	
	@Autowired
	private UtUnitXfzequipmentMapper xfzequipmentMapper;
	
	@Autowired
	private SnowflakeIdWorker snowflakeIdWorker;

	@Override
	public PageInfo<EquipmentOutData> getList(EquipmentInData inData, String equipmentname) throws Exception{
		PageHelper.startPage(inData.getPageNumber(), inData.getPageSize());
		List<EquipmentOutData> list = xfzequipmentMapper.selectAllEquipment(equipmentname);
		PageInfo<EquipmentOutData> pager = new PageInfo<EquipmentOutData>(list);
		return pager;
	}

	@Override
	public void add(EquipmentInData inData) throws Exception {
		UtUnitXfzequipment xfzequipment = new UtUnitXfzequipment();
		BeanUtils.copyProperties(inData, xfzequipment);
		xfzequipment.setId(snowflakeIdWorker.nextId());
		if(Util.isNotEmpty(inData.getEquipmentcount())){
			xfzequipment.setEquipmentcount(Integer.parseInt(inData.getEquipmentcount()));
		}
		if(Util.isNotEmpty(inData.getUnitid())){
			xfzequipment.setUnitid(Long.valueOf(inData.getUnitid()));
		}
		
		xfzequipmentMapper.insert(xfzequipment);
		
	}

	@Override
	public void update(EquipmentInData inData) throws Exception {
		UtUnitXfzequipment xfzequipment = xfzequipmentMapper.selectByPrimaryKey(Long.valueOf(inData.getId()));
		BeanUtils.copyProperties(inData, xfzequipment);
		if(Util.isNotEmpty(inData.getEquipmentcount())){
			xfzequipment.setEquipmentcount(Integer.parseInt(inData.getEquipmentcount()));
		}else{
			xfzequipment.setEquipmentcount(null);
		}
		if(Util.isNotEmpty(inData.getUnitid())){
			xfzequipment.setUnitid(Long.valueOf(inData.getUnitid()));
		}else{
			xfzequipment.setUnitid(null);
		}
		
		xfzequipmentMapper.updateByPrimaryKey(xfzequipment);
	}

	@Override
	public void remove(String id) throws Exception {
		String[] ids = id.split(",");
		for(String key:ids) {
			System.out.println("key:"+key);
			Integer flag = 0;
			if(flag<1) {
				throw new ServiceException(UtilMessage.DEL_MESSAGE_ERROR);
			}
		}
		
	}

	@Override
	public void delete(Long id) throws Exception {
		xfzequipmentMapper.deleteByPrimaryKey(id);
	}

}
