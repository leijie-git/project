package com.gw.fireStation.controller;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageInfo;
import com.gw.common.Json;
import com.gw.fireStation.data.EquipmentInData;
import com.gw.fireStation.data.EquipmentOutData;
import com.gw.fireStation.service.EquipmentService;

/**
 * 消防设施
 * @author zfg
 *
 */
@RestController
@RequestMapping("/equipment")
public class EquipmentController {
	
	@Resource 
	private EquipmentService equipmentService;
	
	/**
	 * 获取消防设施列表
	 * @param inData
	 * @return
	 */
	@GetMapping("/getList")
	public PageInfo<EquipmentOutData> getList(EquipmentInData inData, String equipmentname){
		try {
			PageInfo<EquipmentOutData> pager = equipmentService.getList(inData, equipmentname);
			return pager;
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}
	
	/**
	 * 添加消防设施
	 * @param inData
	 * @return
	 */
	@PostMapping("/add")
	public Json addEquipment(EquipmentInData inData) {
		Json json = new Json();
		try {
			equipmentService.add(inData);
			json.setSuccess(true);
			json.setMsg("添加成功");
		} catch (Exception e) {
			json.setMsg(e.getMessage());
			e.printStackTrace();
		}
		return json;
	}
	
	/**
	 * 修改消防设施
	 * @param inData
	 * @return
	 */
	@PostMapping("/update")
	public Json update(EquipmentInData inData) {
		Json json = new Json();
		try {
			equipmentService.update(inData);
			json.setSuccess(true);
			json.setMsg("更新成功");
		} catch (Exception e) {
			json.setMsg(e.getMessage());
			e.printStackTrace();
		}
		return json;
	}
	
	/**
	 * 删除消防设施
	 * @param inData
	 * @return
	 */
	@PostMapping("/remove")
	public Json remove(@RequestParam("id") String id) {
		Json json = new Json();
		try {
			equipmentService.remove(id);
			json.setSuccess(true);
		} catch (Exception e) {
			json.setMsg(e.getMessage());
			e.printStackTrace();
		}
		return json;
	}
	
	
	@RequestMapping("/delete")
	public Json delete(Long id) {
		Json json = new Json();
		try {
			equipmentService.delete(id);
			json.setSuccess(true);
			json.setMsg("删除成功");
		} catch (Exception e) {
			json.setMsg("删除失败");
			e.printStackTrace();
		}
		return json;
	}

}
