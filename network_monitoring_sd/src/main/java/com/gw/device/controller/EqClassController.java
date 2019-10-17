package com.gw.device.controller;

import com.github.pagehelper.PageInfo;
import com.gw.common.Json;
import com.gw.device.data.EqClassInData;
import com.gw.device.data.EqClassOutData;
import com.gw.device.data.EqSystemOutData;
import com.gw.device.service.EqClassService;
import com.gw.mapper.entity.UtBaseEqPort;
import com.gw.systemManager.controller.SysResourceController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/eqClass")
public class EqClassController {

	@Resource
	private EqClassService eqClassService;

	private Logger log = LoggerFactory.getLogger(EqClassController.class);

	/**
	 * 获取端口号分类列表
	 *
	 * @return
	 */
	@RequestMapping("/getportNameArrayList")
	public Json getportNameArrayList(Integer classCode) {
		Json json = new Json();
		try {
			List<UtBaseEqPort> list = eqClassService.getportNameArrayList(classCode);
			json.setSuccess(true);
			json.setObj(list);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}
		return json;
	}

	/**
	 * 获取设备分类列表
	 *
	 * @return
	 */
	@RequestMapping("/getArrayList")
	public Json getArrayList(String systemID) {
		Json json = new Json();
		try {
			List<EqClassOutData> list = eqClassService.getArrayList(systemID);
			json.setSuccess(true);
			json.setObj(list);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}
		return json;
	}

	/**
	 * 获取设备分类列表
	 *
	 * @return
	 */
	@RequestMapping("/getSystemList")
	public Json getSystemList() {
		Json json = new Json();
		try {
			List<EqSystemOutData> list = eqClassService.getSystemList();
			json.setSuccess(true);
			json.setObj(list);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}
		return json;
	}


	/**
	 * 获取设备类型列表
	 *
	 * @param classname
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	@RequestMapping("/pageList")
	public PageInfo<EqClassOutData> pageList(String classname, Integer pageNumber, Integer pageSize) {
		PageInfo<EqClassOutData> pageInfo = null;
		try {
			pageInfo = eqClassService.pageList(classname, pageNumber, pageSize);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return pageInfo;
	}

	@RequestMapping("/addEqClass")
	public Json addEqClass(EqClassInData inData) {
		Json json = new Json();
		try {
			eqClassService.addEqClass(inData);
			json.setSuccess(true);
			json.setMsg("添加成功");
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}
		return json;
	}

	@RequestMapping("/deleteEqClass")
	public Json deleteEqClass(String id) {
		Json json = new Json();
		try {
			eqClassService.deleteEqClass(id);
			json.setSuccess(true);
			json.setMsg("删除成功");
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}
		return json;
	}

	@RequestMapping("/updateEqClass")
	public Json updateEqClass(EqClassInData inData) {
		Json json = new Json();
		try {
			eqClassService.updateEqClass(inData);
			json.setSuccess(true);
			json.setMsg("更新成功");
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return json;
	}

}
