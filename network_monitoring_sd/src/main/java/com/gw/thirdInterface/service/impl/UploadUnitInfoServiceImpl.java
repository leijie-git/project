package com.gw.thirdInterface.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.gw.device.data.NetDeviceInData;
import com.gw.device.data.NetDeviceOutData;
import com.gw.mapper.UtEqEquipmentdetialgwMapper;
import com.gw.mapper.UtUnitBaseinfoMapper;
import com.gw.mapper.UtUnitNetdeviceMapper;
import com.gw.thirdInterface.service.UploadUnitInfoService;
import com.gw.unit.data.UnitBaseInfoInData;
import com.gw.unit.data.UnitBaseInfoOutData;
import com.gw.upload.data.UploadEquipmentOutData;

@Service
public class UploadUnitInfoServiceImpl implements UploadUnitInfoService {
	@Autowired
	private UtUnitBaseinfoMapper utUnitBaseinfoMapper;
	@Autowired
	private UtUnitNetdeviceMapper utUnitNetdeviceMapper;
	@Autowired
	private UtEqEquipmentdetialgwMapper utEqEquipmentdetialgwMapper;

	@Override
	public PageInfo<UnitBaseInfoOutData> getAllUnitInfo(UnitBaseInfoInData inData) throws Exception {
		PageHelper.startPage(inData.getPageNumber(), inData.getPageSize());
		List<UnitBaseInfoOutData> list = utUnitBaseinfoMapper.getUploadUnits(inData);
		PageInfo<UnitBaseInfoOutData> pageInfo = new PageInfo<UnitBaseInfoOutData>(list);
		return pageInfo;
	}

	@Override
	public PageInfo<NetDeviceOutData> getUploadDevice(NetDeviceInData inData) throws Exception {
		PageHelper.startPage(inData.getPageNumber(), inData.getPageSize());
		List<NetDeviceOutData> list = utUnitNetdeviceMapper.getNetDeviceListByUnitId(inData.getUnitid(), null);
		PageInfo<NetDeviceOutData> pageInfo = new PageInfo<NetDeviceOutData>(list);
		return pageInfo;
	}

	@Override
	public PageInfo<UploadEquipmentOutData> getUploadEquipment(String unitid, Integer pageNumber, Integer pageSize) throws Exception {
		PageHelper.startPage(pageNumber, pageSize);
		List<UploadEquipmentOutData> list = utEqEquipmentdetialgwMapper.getUploadEquipment(unitid);
		PageInfo<UploadEquipmentOutData> pageInfo = new PageInfo<UploadEquipmentOutData>(list);
		return pageInfo;
	}

}
