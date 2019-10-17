package com.gw.equipment.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.gw.device.data.NetDeviceInData;
import com.gw.device.data.NetDeviceOutData;
import com.gw.equipment.data.WirelessDeviceImportData;
import com.gw.equipment.data.WirelessDeviceInData;
import com.gw.equipment.data.WirelessDeviceOutData;
import com.gw.equipment.service.WirelessDeviceService;
import com.gw.exception.ServiceException;
import com.gw.mapper.UtHdSiterwellMapper;
import com.gw.mapper.UtUnitNetdeviceMapper;
import com.gw.mapper.entity.UtHdSiterwell;
import com.gw.mapper.entity.UtUnitNetdevice;
import com.gw.util.Util;
import com.gw.util.UtilConv;
import com.gw.util.UtilMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class WirelessDeviceServiceImpl implements WirelessDeviceService{

	@Resource
	private UtUnitNetdeviceMapper utUnitNetdeviceMapper;
	@Resource
	private UtHdSiterwellMapper utHdSiterwellMapper;
	@Value("${raw.data.database}")
	private String database;
	@Override
	public PageInfo<WirelessDeviceOutData> getList(WirelessDeviceInData inData) throws Exception {
		PageHelper.startPage(inData.getPageNumber(), inData.getPageSize());
		inData.setDatabase(database);;//这里只是用于传递参数
		List<WirelessDeviceOutData> list = utHdSiterwellMapper.getAllWirelessDevice(inData);
		for(WirelessDeviceOutData data:list) {
			if(0 == data.getUsingtype()) {
				data.setCode(Util.intToHex(data.getGatewayid()));
			}else{
				data.setCode(data.getGatewaycode().toString());
			}
			if("1".equals(data.getIsrelation().toString())) {
				UtUnitNetdevice netdevice = utUnitNetdeviceMapper.getNetDevice(data);
				if(Util.isNotEmpty(netdevice)) {
					data.setNetdeviceid(netdevice.getId().toString());
				}
			}
		}
		PageInfo<WirelessDeviceOutData> pageInfo = new PageInfo<WirelessDeviceOutData>(list);
		return pageInfo;
	}

	@Override
	public List<NetDeviceOutData> getNetDevice() throws Exception {
		NetDeviceInData inData = new NetDeviceInData();
		inData.setDeviceindex("1");
		return utUnitNetdeviceMapper.list(inData);
	}

	@Override
	@Transactional
	public void edit(WirelessDeviceInData inData) throws Exception {
		if(Util.isNotEmpty(inData.getId())) {
			//我们表中有此数据
			WirelessDeviceOutData well = utHdSiterwellMapper.getWirelessDevice(database, inData.getId());
			if(Util.isEmpty(well.getGatewayid())) {
				well.setGatewayid(0);
			}
			if(Util.isEmpty(well.getGatewaycode())) {
				well.setGatewaycode("");
			}
			if(Util.isNotEmpty(inData.getLat())) {
				well.setLat(inData.getLat());
			}
			if(Util.isNotEmpty(inData.getLon())) {
				well.setLon(inData.getLon());
			}
			well.setDevicecode(inData.getDevicecode());
			well.setInstalladdr(inData.getInstalladdr());
			well.setIsphone(inData.getIsphone());
			well.setNotifyphone(inData.getNotifyphone());
			well.setLastupate(UtilConv.date2Str(new Date(), UtilConv.DATE_YYYY_MM_DD_SS));
			if(Util.isNotEmpty(inData.getNetdeviceid())) {
				UtUnitNetdevice netdevice = utUnitNetdeviceMapper.selectByPrimaryKey(Long.parseLong(inData.getNetdeviceid()));
				well.setOwnercode(netdevice.getOwnercode());
				well.setDeviceindex(netdevice.getDeviceindex());
				well.setDeviceno(netdevice.getDeviceno());
				well.setIsrelation(1);
			}
			well.setDatabase(database);
			well.setId(inData.getId());
			Integer flag = utHdSiterwellMapper.updateSiteWell(well);
			if(flag<1) {
				throw new ServiceException(UtilMessage.UPDATE_ERROR);
			}
//			CacheUtils.remove(UtilConst.UT_HD_SITERWELL);
		}
	}

	@Override
	@Transactional
	public void delelte(String id) {
		Integer flag = utHdSiterwellMapper.deleteSiteWell(database,id);
		if(flag<1) {
			throw new ServiceException(UtilMessage.DEL_MESSAGE_ERROR);
		}
	}

	@Override
	@Transactional
	public void importData(List<WirelessDeviceImportData> inData,String usingtype,String heartbeats) throws Exception {
		if (Util.isEmpty(inData)) {
			return;
		}
		String errorMsg = "";
		UtHdSiterwell well = new UtHdSiterwell();

		for(WirelessDeviceImportData wirelessDeviceInData:inData) {
			if (Util.isEmpty(wirelessDeviceInData.getDeviceId())) {
				errorMsg = "网关ID不能为空！";
			}
			well.setUsingtype(Integer.parseInt(usingtype));
			WirelessDeviceOutData siteWell = new WirelessDeviceOutData();
			if("0".equals(usingtype)) {
				//罗拉设备
				well.setNetdeviceid(database);
				well.setGatewayid(Integer.parseInt(wirelessDeviceInData.getDeviceId()));
				siteWell = utHdSiterwellMapper.getOneWireless(well);
			}else {
				//NB设备
				well.setNetdeviceid(database);
				well.setGatewaycode(wirelessDeviceInData.getDeviceId());
				siteWell = utHdSiterwellMapper.getOneWireless(well);
			}

			if(Util.isNotEmpty(siteWell)) {
				siteWell.setDevicecode(wirelessDeviceInData.getNodeId());
				siteWell.setDevicetype(wirelessDeviceInData.getDeviceType());
				siteWell.setImsi(wirelessDeviceInData.getImsi());
				siteWell.setProtocoltype(wirelessDeviceInData.getProtocolType());
				siteWell.setModel(wirelessDeviceInData.getModel());
				siteWell.setManufacturename(wirelessDeviceInData.getManufacturerName());
				siteWell.setDatabase(database);
				siteWell.setLastupate(UtilConv.date2Str(new Date(), UtilConv.DATE_YYYY_MM_DD_SS));
				Integer flag1 = utHdSiterwellMapper.updateSiteWell(siteWell);
				if(flag1<1) {
					errorMsg = "导入失败！";
				}
			}else {
				UtHdSiterwell wells = new UtHdSiterwell();
				wells.setDevicecode(wirelessDeviceInData.getNodeId());
				wells.setDevicetype(wirelessDeviceInData.getDeviceType());
				wells.setImsi(wirelessDeviceInData.getImsi());
				wells.setProtocoltype(wirelessDeviceInData.getProtocolType());
				wells.setModel(wirelessDeviceInData.getModel());
				wells.setManufacturename(wirelessDeviceInData.getManufacturerName());
				wells.setGatewayid(0);
				if(Util.isNotEmpty(well.getGatewayid())) {
					wells.setGatewayid(well.getGatewayid());
				}
				wells.setGatewaycode("");
				if(Util.isNotEmpty(well.getGatewaycode())) {
					wells.setGatewaycode(well.getGatewaycode());
				}
				wells.setIsrelation(0);
				wells.setUsingtype(Integer.parseInt(usingtype));
				wells.setLat(0F);
				wells.setLon(0F);
				wells.setNetdeviceid(database);//这里只是用于传递参数
				wells.setLastupate(new Date());
				Integer flag1 = utHdSiterwellMapper.insertSiteWell(wells);
				if(flag1<1) {
					errorMsg = "导入失败！";
				}
			}
		}
		if(Util.isNotEmpty(errorMsg)) {
			throw new ServiceException(errorMsg);
		}
	}

	@Override
	public PageInfo<WirelessDeviceOutData> getAssociatedList(WirelessDeviceInData inData) throws Exception {
		inData.setDatabase(database);
		UtUnitNetdevice netdevice = utUnitNetdeviceMapper.selectByPrimaryKey(Long.parseLong(inData.getNetdeviceid()));
		if(Util.isNotEmpty(netdevice)) {
			inData.setOwnercode(netdevice.getOwnercode());
			inData.setDeviceindex(netdevice.getDeviceindex());
			inData.setDeviceno(netdevice.getDeviceno());
		}
		PageHelper.startPage(inData.getPageNumber(), inData.getPageSize());
		List<WirelessDeviceOutData> list = utHdSiterwellMapper.getAssociatedList(inData);
		for(WirelessDeviceOutData data:list) {
			if(0 == data.getUsingtype()) {
				data.setCode(Util.intToHex(data.getGatewayid()));
			}else{
				data.setCode(data.getGatewaycode().toString());
			}
		}
		PageInfo<WirelessDeviceOutData> pageInfo = new PageInfo<WirelessDeviceOutData>(list);
		return pageInfo;
	}

	@Override
	public PageInfo<WirelessDeviceOutData> getUnrelatedList(WirelessDeviceInData inData) {
		PageHelper.startPage(inData.getPageNumber(), inData.getPageSize());
		inData.setDatabase(database);
		List<WirelessDeviceOutData> list = utHdSiterwellMapper.getUnrelatedList(inData);
		for(WirelessDeviceOutData data:list) {
			if(0 == data.getUsingtype()) {
				data.setCode(Util.intToHex(data.getGatewayid()));
			}else{
				data.setCode(data.getGatewaycode().toString());
			}
		}
		PageInfo<WirelessDeviceOutData> pageInfo = new PageInfo<WirelessDeviceOutData>(list);
		return pageInfo;
	}






}
