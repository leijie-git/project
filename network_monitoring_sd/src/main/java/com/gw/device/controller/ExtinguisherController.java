package com.gw.device.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.gw.equipment.data.EquipmentFacOutData;
import com.gw.util.JwtUtil;
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
import com.gw.device.data.ExtinguisherImportData;
import com.gw.device.service.ExtinguisherService;
import com.gw.exception.ServiceException;
import com.gw.front.unit.data.FrontUnitExtinguisherInData;
import com.gw.front.unit.data.FrontUnitExtinguisherOutData;
import com.gw.login.service.data.GetSessionInfoOutData;
import com.gw.util.UtilConst;

/**
 * 灭火器
 * 
 * @author Administrator
 *
 */

@Controller
@ResponseBody
@RequestMapping("/equipment")
public class ExtinguisherController extends BaseController {

	private Logger log = LoggerFactory.getLogger(ExtinguisherController.class);
	@Autowired
	private ExtinguisherService extinguisherService;

	/**
	 * 单位灭火器列表
	 * 
	 * @param
	 * @param
	 * @param
	 * @return
	 */
	@RequestMapping("/getExtinguisherList")
	public PageInfo<FrontUnitExtinguisherOutData> getExtinguisherList(HttpServletRequest request, FrontUnitExtinguisherInData inData) {

		String authorize = request.getHeader("Authorization");
		long id=0;
		String Account=null;
		String UnitID=null;
		try {
		if(authorize!=null) {

			//解析token
			Map<String, String> tokenToMap = JwtUtil.getTokenToMap(authorize);
			id = Long.parseLong(tokenToMap.get("id"));
			Account = tokenToMap.get("Account");
			UnitID = tokenToMap.get("UnitID");
			if (UnitID != null) {
				inData.setUnitId(UnitID);
			}
			PageInfo<FrontUnitExtinguisherOutData> pageInfo = extinguisherService.getExtinguisherList(id,inData);
			return pageInfo;
		}



		GetSessionInfoOutData sessinInfo = (GetSessionInfoOutData) getSessinInfo(request, UtilConst.USER_SESSION);


			PageInfo<FrontUnitExtinguisherOutData> pageInfo = extinguisherService.getExtinguisherList(sessinInfo.getId(),inData);
			return pageInfo;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return null;
	}

	/**
	 * 新增灭火器数据
	 * 
	 * @param inData
	 * @return
	 */
	@RequestMapping("/addExtinguisher")
	public Json addExtinguisher(HttpServletRequest request, FrontUnitExtinguisherInData inData) {
		Json json = new Json();
		GetSessionInfoOutData sessinInfo = (GetSessionInfoOutData)getSessinInfo(request,UtilConst.USER_SESSION);
		String authorize = request.getHeader("Authorization");
		long id = 0;
		String Account = null;
		String UnitID = null;
       try {
			if (authorize != null) {
				//解析token
				Map<String, String> tokenToMap = JwtUtil.getTokenToMap(authorize);
				id = Long.parseLong(tokenToMap.get("id"));
				Account = tokenToMap.get("Account");
				UnitID = tokenToMap.get("UnitID");
	           extinguisherService.addExtinguisher(id,inData);
		}else {
				extinguisherService.addExtinguisher(sessinInfo.getId(), inData);
			}



			json.setSuccess(true);
		} catch (ServiceException se) {
			log.error(se.getMessage(), se);
			json.setMsg(se.getMessage());
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			json.setMsg("添加失败");
		}
		return json;
	}

	/**
	 * 更新灭火器数据
	 * 
	 * @param inData
	 * @return
	 */
	@RequestMapping("/updateExtinguisher")
	public Json updateExtinguisher(HttpServletRequest request, FrontUnitExtinguisherInData inData) {
		Json json = new Json();
		GetSessionInfoOutData sessinInfo = (GetSessionInfoOutData) getSessinInfo(request, UtilConst.USER_SESSION);
		String authorize = request.getHeader("Authorization");
		long id = 0;
		String Account = null;
		String UnitID = null;
        	try {
			if (authorize != null) {
				//解析token
				Map<String, String> tokenToMap = JwtUtil.getTokenToMap(authorize);
				id = Long.parseLong(tokenToMap.get("id"));
				Account = tokenToMap.get("Account");
				extinguisherService.updateExtinguisher(id, inData);
			}else {
			extinguisherService.updateExtinguisher(sessinInfo.getId(), inData);}
			json.setSuccess(true);
		} catch (ServiceException se) {
			log.error(se.getMessage(), se);
			json.setMsg(se.getMessage());
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return json;
	}

	/**
	 * 删除
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("/deleteExtinguisher")
	public Json deleteExtinguisher(Long id) {
		Json json = new Json();
		try {
			extinguisherService.deleteExtinguisher(id);
			json.setSuccess(true);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return json;
	}
	
	/**
	 * 导入
	 * 
	 * @param uploadExcel
	 * @param request
	 * @param
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/importExcel", method = RequestMethod.POST)
	public Json importExcel(@RequestParam("uploadExcel") MultipartFile uploadExcel, HttpServletRequest request) {
		Json json = new Json();
		GetSessionInfoOutData sessinInfo = (GetSessionInfoOutData)getSessinInfo(request,UtilConst.USER_SESSION);
		try {
			List<ExtinguisherImportData> projectImportDatas = importExcel(uploadExcel, 0, ExtinguisherImportData.class);
			extinguisherService.importData(sessinInfo.getId(), projectImportDatas);
			json.setSuccess(true);
			json.setMsg("导入成功");
		} catch (Exception e) {
			json.setSuccess(false);
			log.error(e.getMessage(), e);
			json.setMsg("导入失败");
		}
		return json;
	}
}
