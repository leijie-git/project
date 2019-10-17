package com.gw.thirdInterface.controller;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageInfo;
import com.gw.device.data.NetDeviceInData;
import com.gw.device.data.NetDeviceOutData;
import com.gw.thirdInterface.service.UploadUnitInfoService;
import com.gw.unit.data.UnitBaseInfoInData;
import com.gw.unit.data.UnitBaseInfoOutData;
import com.gw.upload.data.UploadEquipmentOutData;

@Controller
@RequestMapping("/uploadUnitInfo")
@ResponseBody
public class UploadUnitInfoController {
	private Logger logger = LoggerFactory.getLogger(UploadUnitInfoController.class);
	@Resource
	private UploadUnitInfoService uploadUnitInfoService;
	
	/**
	 * 获取所有单位
	 * @param inData
	 * @return
	 */
	@RequestMapping("/getAllUnitInfo")
	public PageInfo<UnitBaseInfoOutData> getAllUnitInfo(UnitBaseInfoInData inData){
		PageInfo<UnitBaseInfoOutData> pageInfo = null;
		try {
			pageInfo = uploadUnitInfoService.getAllUnitInfo(inData);
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		}
		return pageInfo;
	}
	
	/**
	 * 获取设备
	 * @param inData
	 * @return
	 */
	@RequestMapping("/getUploadDevice")
	public PageInfo<NetDeviceOutData> getUploadDevice(NetDeviceInData inData){
		PageInfo<NetDeviceOutData> pageInfo = null;
		try {
			pageInfo = uploadUnitInfoService.getUploadDevice(inData);
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		}
		return pageInfo;
	}
	
	/**
	 * 获取报警主机和RTU
	 * @param unitid
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	@RequestMapping("/getUploadEquipment")
	public PageInfo<UploadEquipmentOutData> getUploadEquipment(String unitid, Integer pageNumber, Integer pageSize){
		PageInfo<UploadEquipmentOutData> pageInfo = null;
		try {
			pageInfo = uploadUnitInfoService.getUploadEquipment(unitid, pageNumber, pageSize);
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		}
		return pageInfo;
	}
}
