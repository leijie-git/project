package com.gw.unit.controller;

import java.util.List;

import com.gw.front.unit.data.FrontUnitBuildPOutData;
import com.gw.front.unit.service.FrontUnitService;
import com.gw.unit.data.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageInfo;
import com.gw.common.Json;
import com.gw.exception.ServiceException;
import com.gw.unit.service.UnitBaseInfoService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/baseInfo")
@ResponseBody
public class UnitBaseInfoController {
	private Logger log = LoggerFactory.getLogger(UnitBaseInfoController.class);

	@Autowired
	private UnitBaseInfoService unitBaseInfoService;
	@Resource
	private FrontUnitService extinguisherService;

	/**
	 * 展示联网单位列表
	 *
	 * @param inData
	 * @return
	 */
	@RequestMapping("/baseInfoList")
	public PageInfo<UnitBaseInfoOutData> baseInfoList(HttpServletRequest request,UnitBaseInfoInData inData) {
		String authorize = request.getHeader("Authorization");
		PageInfo<UnitBaseInfoOutData> pageInfo = null;
		try {
			pageInfo = unitBaseInfoService.getBaseInfoList(inData);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return pageInfo;
	}

	/**
	 * 根据ID查看所有UnitBaseInfo详细信息
	 * @return
	 */
	@RequestMapping("/baseInfoListById")
	public Json baseInfoListById(UnitBaseInfoInData inData) {
		Json json = new Json();
		try {
			List<UnitBaseInfoOutData> list = unitBaseInfoService.selectAllUnitBaseInfoById(inData);
			json.setSuccess(true);
			json.setObj(list);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return json;
	}

	/**
	 *根据ID查看所有关联单位信息
	 * @return
	 */
	@RequestMapping("/selectAllUnitById")
	public Json selectAllUnitById(UnitBaseInfoInData inData) {
		Json json = new Json();
		try {
			List<UnitBaseInfoOutUnitData> list = unitBaseInfoService.selectAllUnitById(inData);
			json.setSuccess(true);
			json.setObj(list);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return json;
	}


	/**
	 * 获取省市区
	 *
	 * @param type
	 * @param parentId
	 * @return
	 */
	@RequestMapping("/locateList")
	public Json getProvinceList(Integer type, String parentId) {
		Json json = new Json();
		try {
			List<BaseProvicecityregionOutData> list = unitBaseInfoService.getProvinceList(type, parentId);
			json.setSuccess(true);
			json.setObj(list);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return json;
	}

	/**
	 * 添加联网单位
	 *
	 * @param baseInfoInData
	 * @return
	 */
	@RequestMapping("/addBaseInfo")
	public Json addBaseInfo(UnitBaseInfoInData baseInfoInData) {
		Json json = new Json();
		try {
			unitBaseInfoService.addBaseInfo(baseInfoInData);
			json.setSuccess(true);
			json.setMsg("添加成功");
		} catch (ServiceException se) {
			log.error(se.getMessage(), se);
			json.setMsg(se.getMessage());
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return json;
	}

	/**
	 * 删除联网单位
	 *
	 * @param id
	 * @return
	 */
	@RequestMapping("/deleteBaseInfo")
	public Json deleteBaseInfo(String id) {
		Json json = new Json();
		try {
			unitBaseInfoService.deleteBaseInfoById(id);
			json.setSuccess(true);
			json.setMsg("删除成功");
		} catch (ServiceException se) {
			log.error(se.getMessage(), se);
			json.setMsg(se.getMessage());
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			json.setMsg("删除失败");
		}
		return json;
	}

	/**
	 * 更新联网单位
	 *
	 * @param baseInfoInData
	 * @return
	 */
	@RequestMapping("/updateBaseInfo")
	public Json updateBaseInfo(UnitBaseInfoInData baseInfoInData) {
		Json json = new Json();
		try {
			unitBaseInfoService.updateBaseInfo(baseInfoInData);
			json.setSuccess(true);
			json.setMsg("更新成功");
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return json;
	}

	/**
	 * 联网单位下拉框
	 *
	 * @return
	 */
	@RequestMapping("/baseSelect")
	public Json baseInfoSelect() {
		Json json = new Json();
		try {
			List<BaseInfoSelectOutData> list = unitBaseInfoService.baseInfoSelect();
			json.setSuccess(true);
			json.setObj(list);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return json;
	}

	/**
	 * @return
	 */
	@RequestMapping("/getArrayList")
	public Json getArrayList() {
		Json json = new Json();
		try {
			List<BaseInfoSelectOutData> list = unitBaseInfoService.getArrayList();
			json.setSuccess(true);
			json.setObj(list);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return json;
	}

	/**
	 * 获得联网单位关联的维保人员
	 *
	 * @param id
	 * @return
	 */
	@RequestMapping("/getUserReUnit")
	public Json getUserReUnit(String id) {
		Json json = new Json();
		try {
			GetUnitUsersData unitReUser = unitBaseInfoService.getUnitReUser(id);
			json.setObj(unitReUser);
			json.setSuccess(true);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return json;
	}

	/**
	 * 设置联网单位关联的维保人员
	 *
	 * @param unitId
	 * @param manyUserId
	 * @return
	 */
	@RequestMapping("/setUserReUnit")
	public Json setUserReUnit(String unitId, String manyUserId) {
		Json json = new Json();
		try {
			unitBaseInfoService.setUnitReUser(unitId, manyUserId);
			json.setSuccess(true);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return json;
	}

	/**
	 * 从外库导入所有单位数据
	 */
	@RequestMapping("/importAllUnits")
	public Json importAllUnits() {
		Json json = new Json();
		try {
			unitBaseInfoService.importAllUnits();
			json.setSuccess(true);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return json;
	}
	/**
	 * 根据单位id查询建筑物和区域Pid分类
	 *
	 * @param unitID
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getUnitBuildAndArea")
	public Json getUnitBuildAndArea(String unitID) throws Exception {
		Json json = new Json();
		try {
			List<FrontUnitBuildPOutData> outData = extinguisherService.getUnitBuildAndArea(unitID);
			json.setObj(outData);
			json.setSuccess(true);
		} catch (Exception e) {
			json.setMsg(e.getMessage());
		}
		return json;
	}

	/**
	 * 根据单位id查询建筑物和区域Pid分类
	 *
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getUserBuildAndArea")
	public Json getUserBuildAndArea(String userId) throws Exception {
		Json json = new Json();
		try {
			List<FrontUnitBuildPOutData> outData = extinguisherService.getUserBuildAndArea(userId);
			json.setObj(outData);
			json.setSuccess(true);
		} catch (Exception e) {
			json.setMsg(e.getMessage());
		}
		return json;
	}
}
