package com.gw.equipment.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.github.pagehelper.PageInfo;
import com.gw.common.BaseController;
import com.gw.common.Json;
import com.gw.device.controller.AddressRelController;
import com.gw.device.data.NetDeviceOutData;
import com.gw.equipment.data.WirelessDeviceImportData;
import com.gw.equipment.data.WirelessDeviceInData;
import com.gw.equipment.data.WirelessDeviceOutData;
import com.gw.equipment.service.WirelessDeviceService;

/**
 * NB设备
 * @author zfg
 *
 */
@RestController
@RequestMapping("/wirelessDevice")
public class WirelessDeviceController extends BaseController{

	private Logger log = LoggerFactory.getLogger(WirelessDeviceController.class);
	@Resource
	private WirelessDeviceService wirelessDeviceService;
	
	/**
	 * 分页查询无线设备列表
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	@RequestMapping("/getList")
	public PageInfo<WirelessDeviceOutData> getList(WirelessDeviceInData inData){
		try {
			PageInfo<WirelessDeviceOutData> pageInfo = wirelessDeviceService.getList(inData);
			return pageInfo;
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			e.printStackTrace();
		}
		return null;
		
	}
	
	/**
	 * 导入无线设备数据
	 * @param uploadExcel
	 * @param request
	 * @return
	 */
	@RequestMapping("/importData")
	public Json importData(@RequestParam("uploadExcel") MultipartFile uploadExcel, HttpServletRequest request,String usingtype,String heartbeats) {
		Json json = new Json();
		try {
			List<WirelessDeviceImportData> projectImportDatas = importExcel(uploadExcel, 0, WirelessDeviceImportData.class);
			wirelessDeviceService.importData(projectImportDatas,usingtype,heartbeats);
			json.setSuccess(true);
			json.setMsg("导入成功！");
		} catch (Exception e) {
			json.setMsg(e.getMessage());
			log.error(e.getMessage(),e);
			e.printStackTrace();
		}
		return json;
		
	}
	
	/**
	 * 获取联网设备列表
	 * @return
	 */
	@RequestMapping("/getNetDeviceList")
	public Json getNetDevice() {
		Json json = new Json();
		
		try {
			List<NetDeviceOutData> outData = wirelessDeviceService.getNetDevice();
			json.setObj(outData);
			json.setSuccess(true);
		} catch (Exception e) {
			json.setMsg(e.getMessage());
			log.error(e.getMessage(),e);
			e.printStackTrace();
		}
		return json;
	}
	
	/**
	 * 更新无线设备信息
	 * @param inData
	 * @return
	 */
	@RequestMapping("/edit")
	public Json edit(WirelessDeviceInData inData) {
		Json json = new Json();
		try {
			wirelessDeviceService.edit(inData);
			json.setSuccess(true);
			json.setMsg("更新成功");
		} catch (Exception e) {
			json.setMsg(e.getMessage());
			log.error(e.getMessage(),e);
			e.printStackTrace();
		}
		
		return json;
	}
	
	/**
	 * 删除无线设备信息
	 * @param inData
	 * @return
	 */
	@RequestMapping("/delete")
	public Json delelte(String id) {
		Json json = new Json();
		try {
			wirelessDeviceService.delelte(id);
			json.setSuccess(true);
			json.setMsg("删除成功");
		} catch (Exception e) {
			json.setMsg(e.getMessage());
			log.error(e.getMessage(),e);
			e.printStackTrace();
		}
		
		return json;
	}
	
	
	/**
	 * 分页查询该设备关联的无线设备列表
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	@RequestMapping("/getAssociatedList")
	public PageInfo<WirelessDeviceOutData> getAssociatedList(WirelessDeviceInData inData){
		try {
			PageInfo<WirelessDeviceOutData> pageInfo = wirelessDeviceService.getAssociatedList(inData);
			return pageInfo;
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			e.printStackTrace();
		}
		return null;
		
	}
	
	/**
	 * 分页查询该设备未关联的无线设备列表
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	@RequestMapping("/getUnrelatedList")
	public PageInfo<WirelessDeviceOutData> getUnrelatedList(WirelessDeviceInData inData){
		try {
			PageInfo<WirelessDeviceOutData> pageInfo = wirelessDeviceService.getUnrelatedList(inData);
			return pageInfo;
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			e.printStackTrace();
		}
		return null;
		
	}
	
}
