package com.gw.inspect.controller;

import javax.annotation.Resource;

import com.gw.inspect.data.InspectSiteInData;
import com.gw.inspect.data.InspectSiteOutData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageInfo;
import com.gw.common.Json;
import com.gw.inspect.data.SiteClassDetialInData;
import com.gw.inspect.data.SiteClassDetialOutData;
import com.gw.inspect.service.SiteClassDetialService;
import com.gw.systemManager.controller.SysResourceController;

import java.util.List;

/**
 * 巡查点位分类详细
 *
 * @author zfg
 */
@RestController
@RequestMapping("/siteClassDetial")
public class SiteClassDetialController {
	private Logger log = LoggerFactory.getLogger(SiteClassDetialController.class);
	@Resource
	private SiteClassDetialService siteClassDetialService;

	/**
	 * 获取巡查点分类检查项
	 *
	 * @param SiteClassID
	 * @return
	 */
	@GetMapping("/getList")
	public PageInfo<SiteClassDetialOutData> getList(SiteClassDetialInData inData) {
		PageInfo<SiteClassDetialOutData> pager = siteClassDetialService.getList(inData);
		return pager;
	}

	/**
	 * 获取巡查点分类检查项
	 *
	 * @return
	 */
	@GetMapping("/getArrayList")
	public Json getArrayList(SiteClassDetialInData inData) {
		Json json = new Json();
		try {
			List<SiteClassDetialOutData> list = siteClassDetialService.getListByUnitID(inData);
			json.setSuccess(true);
			json.setObj(list);
		} catch (Exception e) {
			json.setMsg(e.getMessage());
			log.error(e.getMessage(),e);
		}
		return json;
	}

	/**
	 * 添加检查项
	 *
	 * @param inData
	 * @return
	 */
	@PostMapping("/add")
	public Json add(SiteClassDetialInData inData) {
		Json json = new Json();
		try {
			siteClassDetialService.add(inData);
			json.setSuccess(true);
		} catch (Exception e) {
			json.setMsg(e.getMessage());
			log.error(e.getMessage(),e);
		}
		return json;
	}

	/**
	 * 编辑检查项
	 *
	 * @param inData
	 * @return
	 */
	@PostMapping("/update")
	public Json update(SiteClassDetialInData inData) {
		Json json = new Json();
		try {
			siteClassDetialService.update(inData);
			json.setSuccess(true);
		} catch (Exception e) {
			json.setMsg(e.getMessage());
			log.error(e.getMessage(),e);
		}
		return json;
	}

	/**
	 * 删除检查项
	 *
	 * @param inData
	 * @return
	 */
	@PostMapping("/remove")
	public Json remove(@RequestParam("id") String id) {
		Json json = new Json();
		try {
			siteClassDetialService.remove(id);
			json.setSuccess(true);
		} catch (Exception e) {
			json.setMsg(e.getMessage());
			log.error(e.getMessage(),e);
		}
		return json;
	}
}
