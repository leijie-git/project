package com.gw.device.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.github.pagehelper.PageInfo;
import com.gw.common.BaseController;
import com.gw.common.Json;
import com.gw.device.data.AddressRelImportData;
import com.gw.device.data.AddressRelInData;
import com.gw.device.data.AddressRelOutData;
import com.gw.device.data.EquipmentSelectData;
import com.gw.device.service.AddressRelService;
import com.gw.exception.ServiceException;

/**
 * 联网设备关联地址层
 * @author SY
 * @data 2018年9月13日
 */

@Controller
@RequestMapping("/netDeviceRel")
@ResponseBody
public class AddressRelController extends BaseController{
	private Logger log = LoggerFactory.getLogger(AddressRelController.class);
	
	@Autowired
	private AddressRelService deviceRelService;
	
	@RequestMapping("/list")
	public PageInfo<AddressRelOutData> getList(Integer pageNumber, Integer pageSize, String eqid, String unitName){
		try {
			PageInfo<AddressRelOutData> pageInfo = deviceRelService.list(pageNumber, pageSize, eqid, unitName);
			return pageInfo;
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			e.printStackTrace();
		}
		return null;
		
	}
	
	@RequestMapping("/add")
	public Json add(AddressRelInData inData){
		Json json = new Json();
		try {
			deviceRelService.add(inData);
			json.setSuccess(true);
			json.setMsg("添加成功");
		} catch (ServiceException se) {
			json.setMsg(se.getMessage());
			log.error(se.getMessage(),se);
		} catch (Exception e) {
			json.setMsg("添加失败");
			log.error(e.getMessage(),e);
		}
		return json;
	}
	
	@RequestMapping("/delete")
	public Json delete(String id){
		Json json = new Json();
		try {
			deviceRelService.delete(id);
			json.setSuccess(true);
			json.setMsg("删除成功");
		} catch (Exception e) {
			json.setMsg("删除失败");
			log.error(e.getMessage(),e);
		}
		return json;
	}
	
	@RequestMapping("/update")
	public Json update(AddressRelInData inData){
		Json json = new Json();
		try {
			deviceRelService.update(inData);
			json.setSuccess(true);
			json.setMsg("修改成功");
		} catch (ServiceException se) {
			json.setMsg(se.getMessage());
			log.error(se.getMessage(),se);
		} catch (Exception e) {
			json.setMsg("修改失败");
			log.error(e.getMessage(),e);
		}
		return json;
	}
	
	@RequestMapping("/getEquipmentSelect")
	public Json getEquipmentSelect(Long unitid){
		Json json = new Json();
		try {
			List<EquipmentSelectData> list = deviceRelService.getEquipmentSelect(unitid);
			json.setSuccess(true);
			json.setObj(list);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}
		return json;
	}
	
	
	/**
	 * 导入
	 * 
	 * @param uploadExcel
	 * @param request
	 * @param importProjectPid
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/importExcel", method = RequestMethod.POST)
	public Json importExcel(@RequestParam("uploadExcel") MultipartFile uploadExcel, HttpServletRequest request,
			String eqid) {
		Json json = new Json();
		try {
			List<AddressRelImportData> projectImportDatas = importExcel(uploadExcel, 0, AddressRelImportData.class);
			deviceRelService.importData(projectImportDatas, eqid);
			json.setSuccess(true);
			json.setMsg("导入成功");
		} catch (ServiceException se) {
			log.error(se.getMessage(), se);
			json.setMsg(se.getMessage());
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			json.setMsg("导入失败");
		}
		return json;
	}
}
