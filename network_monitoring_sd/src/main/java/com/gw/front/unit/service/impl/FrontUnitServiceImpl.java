package com.gw.front.unit.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import com.gw.front.unit.data.*;
import com.gw.mapper.entity.*;
import com.gw.util.DataConvertUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.gw.build.data.BuildAreaOutData;
import com.gw.device.data.AreaAssociatedEquipmentOutData;
import com.gw.firePower.data.FireStationManageOutData;
import com.gw.front.couplet.data.FrontCoupletCommonOutData;
import com.gw.front.login.data.FrontUnitUserOutData;
import com.gw.front.unit.service.FrontUnitService;
import com.gw.mapper.UtFireStationMapper;
import com.gw.mapper.UtUnitBaseinfoMapper;
import com.gw.mapper.UtUnitBuildMapper;
import com.gw.mapper.UtUnitBuildareaMapper;
import com.gw.mapper.UtUnitDangerousMapper;
import com.gw.mapper.UtUnitImportMapper;
import com.gw.mapper.UtUnitUserMapper;
import com.gw.mapper.UtUnitVideoMapper;
import com.gw.mapper.UtUnitXfzequipmentMapper;
import com.gw.mapper.UtUnitXfzuserMapper;
import com.gw.systemManager.data.SysPropertiesOutData;
import com.gw.systemManager.service.PropertiesManageService;
import com.gw.util.Token;
import com.gw.util.Util;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

/**
 * 灭火器服务层
 * 
 * @author SY
 *
 */

@Service
public class FrontUnitServiceImpl implements FrontUnitService {

	@Autowired
	private UtUnitBaseinfoMapper utUnitBaseinfoMapper;
	@Autowired
	private UtUnitBuildMapper utUnitBuildMapper;
	@Autowired
	private UtUnitVideoMapper utUnitVideoMapper;
	@Autowired
	private UtUnitImportMapper utUnitImportMapper;
	@Autowired
	private UtUnitDangerousMapper utUnitDangerousMapper;
	@Autowired
	private UtUnitUserMapper utUnitUserMapper;
	@Autowired
	private UtUnitXfzuserMapper utUnitXfzuserMapper;
	@Autowired
	private UtUnitXfzequipmentMapper utUnitXfzequipmentMapper;
	@Autowired
	private UtUnitBuildareaMapper utUnitBuildareaMapper;
	@Resource
	private PropertiesManageService propertiesManageService;


	@Autowired
	private UtFireStationMapper utFireStationMapper;
//	@Value("${video.appkey}")
//	private String appkey;
//	@Value("${video.appsecret}")
//	private String appsecret;

	@Override
	public List<FrontUnitInfoOutData> getUnitInfoById(String unitId) throws Exception {
		return utUnitBaseinfoMapper.getUnitInfoById(unitId);
	}

	@Override
	public PageInfo<FrontUnitBuildOutData> getUnitBuilds(FrontUnitInData inData) throws Exception {
		PageHelper.startPage(inData.getPageNumber(), inData.getPageSize());
		List<FrontUnitBuildOutData> list = utUnitBuildMapper.getUnitBuilds(inData);
		return new PageInfo<>(list);
	}

	@Override
	public FrontUnitBuildOutData getUnitBuildInfoById(String buildId) throws Exception {
		return utUnitBuildMapper.getUnitBuildInfoById(buildId);
	}

	@Override
	public List<FrontUnitVideoOutData> getUnitVideoInfo(String unitId, String name) throws Exception {
		SysPropertiesOutData properties = propertiesManageService.getProperties();
		List<FrontUnitVideoOutData> unitVideoInfo = utUnitVideoMapper.getUnitVideoInfo(unitId,name);
		UtUnitBaseinfo utUnitBaseinfo = utUnitBaseinfoMapper.selectByPrimaryKey(DataConvertUtil.parseLong(unitId));

		String token;
		String appKey;
		if (Util.isNotEmpty(utUnitBaseinfo) &&
				Util.isNotEmpty(utUnitBaseinfo.getVideoAppkey()) &&
				Util.isNotEmpty(utUnitBaseinfo.getVideoAppsecret())){
			appKey = utUnitBaseinfo.getVideoAppkey();
			token = Token.getToken(appKey, utUnitBaseinfo.getVideoAppsecret());
		}else {
			appKey = properties.getVideoAppkey();
			token = Token.getToken(appKey, properties.getVideoAppsecret());
		}
		for (FrontUnitVideoOutData frontUnitVideoOutData : unitVideoInfo) {
			frontUnitVideoOutData.setAppKey(appKey);
			frontUnitVideoOutData.setToken(token);
		}
		return unitVideoInfo;
	}

	@Override
	public String getUnitImport(String unitId) throws Exception {
		Example example = new Example(UtUnitImport.class);
		example.createCriteria().andEqualTo("unitid", unitId);
		List<UtUnitImport> list = utUnitImportMapper.selectByExample(example);
		StringBuilder sb = new StringBuilder();
		for (UtUnitImport utUnitImport : list) {
			String importname = utUnitImport.getImportname();
			sb.append(importname).append(",");
		}
		if (sb.length() > 0) {
			return sb.substring(0, sb.length() - 1);
		}
		return null;
	}

	@Override
	public List<FrontCoupletCommonOutData> getUnitDangerous(String unitId) throws Exception {
		Example example = new Example(UtUnitDangerous.class);
		example.createCriteria().andEqualTo("unitid", unitId);
		List<UtUnitDangerous> list = utUnitDangerousMapper.selectByExample(example);
		StringBuilder sb1 = new StringBuilder();
		StringBuilder sb2 = new StringBuilder();
		StringBuilder sb3 = new StringBuilder();
		for (UtUnitDangerous utUnitDangerous : list) {
			String dangerousname = utUnitDangerous.getDangerousname();
			// 危险品状态(1气体2液体3固体)
			String dangerousstate = utUnitDangerous.getDangerousstate();
			if ("1".equals(dangerousstate)) {
				sb1.append(dangerousname).append(",");
			} else if ("2".equals(dangerousstate)) {
				sb2.append(dangerousname).append(",");
			} else if ("3".equals(dangerousstate)) {
				sb3.append(dangerousname).append(",");
			}
		}
		List<FrontCoupletCommonOutData> outData = new ArrayList<>();
		if (sb1.length() > 0) {
			FrontCoupletCommonOutData data = new FrontCoupletCommonOutData();
			data.setCoupletKey("气体");
			data.setCoupletValue(sb1.substring(0, sb1.length() - 1));
			outData.add(data);
		}
		if (sb2.length() > 0) {
			FrontCoupletCommonOutData data = new FrontCoupletCommonOutData();
			data.setCoupletKey("液体");
			data.setCoupletValue(sb2.substring(0, sb2.length() - 1));
			outData.add(data);
		}
		if (sb3.length() > 0) {
			FrontCoupletCommonOutData data = new FrontCoupletCommonOutData();
			data.setCoupletKey("固体");
			data.setCoupletValue(sb3.substring(0, sb3.length() - 1));
			outData.add(data);
		}
		return outData;
	}

	@Override
	public List<FrontUnitUserOutData> getUnitUserList(String unitId, String userRole) throws Exception {
		List<FrontUnitUserOutData> outData = new ArrayList<>();
		Example example = new Example(UtUnitUser.class);
		Criteria createCriteria = example.createCriteria();
		createCriteria.andEqualTo("unitid", unitId).andEqualTo("usertype", 1);
		if (Util.isNotEmpty(userRole)) {
			createCriteria.andEqualTo("userrole", userRole);
		}
		List<UtUnitUser> list = utUnitUserMapper.selectByExample(example);
		for (UtUnitUser utUnitUser : list) {
			FrontUnitUserOutData data = new FrontUnitUserOutData();
			BeanUtils.copyProperties(utUnitUser, data);
			data.setUsertype("联网用户");
			Integer userrole = utUnitUser.getUserrole();
			// 0单位负责人，1单位管理人，2巡查员，3工程人员，4监控人员
			if (userrole == 0) {
				data.setUserrole("单位负责人");
			} else if (userrole == 1) {
				data.setUserrole("单位管理员");
			} else if (userrole == 2) {
				data.setUserrole("巡查员");
			} else if (userrole == 3) {
				data.setUserrole("工程人员");
			} else if (userrole == 4) {
				data.setUserrole("监控人员");
			}
			outData.add(data);
		}
		return outData;
	}

	@Override
	public FrontUnitXFZOutData getUnitXFZInfo(String unitId) throws Exception {
		FrontUnitXFZOutData outData = new FrontUnitXFZOutData();
		Example example = new Example(UtUnitXfzuser.class);
		example.createCriteria().andEqualTo("unitid", unitId);
		List<UtUnitXfzuser> userList = utUnitXfzuserMapper.selectByExample(example);
		outData.setUserCount(userList.size());
		List<FrontUnitXFUserOutData> outUserList = new ArrayList<>();
		for (UtUnitXfzuser utUnitXfzuser : userList) {
			FrontUnitXFUserOutData user = new FrontUnitXFUserOutData();
			user.setUserName(utUnitXfzuser.getUsername());
			user.setTel(utUnitXfzuser.getTel());
			user.setUserPost(utUnitXfzuser.getUserpost());
			outUserList.add(user);
		}
		outData.setUserList(outUserList);

		Example example1 = new Example(UtUnitXfzequipment.class);
		example1.createCriteria().andEqualTo("unitid", unitId);
		List<UtUnitXfzequipment> eqList = utUnitXfzequipmentMapper.selectByExample(example1);
		outData.setEqCount(eqList.size());
		List<FrontCoupletCommonOutData> outEqList = new ArrayList<>();
		for (UtUnitXfzequipment utUnitXfzequipment : eqList) {
			FrontCoupletCommonOutData eq = new FrontCoupletCommonOutData();
			eq.setCoupletKey(utUnitXfzequipment.getEquipmentname());
			eq.setCoupletValue(utUnitXfzequipment.getEquipmentcount() + "");
			outEqList.add(eq);
		}
		outData.setEqList(outEqList);
		return outData;
	}

	@Override
	public List<BuildAreaOutData> getUnitAreaImgList(String unitId) throws Exception {
		return utUnitBuildareaMapper.getBuildAreaByUnitId(unitId);
	}

	@Override
	public List<AreaAssociatedEquipmentOutData> getAreaEqSiteList(String buildAreaId) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<FireStationManageOutData> fireEquipment(String unitID) throws Exception {
		return utFireStationMapper.getFireEquipmentList(unitID);
	}

	@Override
	public List<FrontUnitBuildPOutData> getUnitBuildAndArea(String unitID) throws Exception {
		return utUnitBuildareaMapper.getUnitBuildAndArea(unitID);
	}

	@Override
	public List<FrontUnitBuildPOutData> getUserBuildAndArea(String userId) throws Exception {
		return utUnitBuildareaMapper.getUserBuildAndArea(userId);
	}
}
