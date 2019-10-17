package com.gw.unit.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.gw.mapper.UtInspectSiteMapper;
import com.gw.mapper.entity.UtInspectSite;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.gw.common.SnowflakeIdWorker;
import com.gw.exception.ServiceException;
import com.gw.mapper.UtBaseUserreunitMapper;
import com.gw.mapper.UtInspectPlanMapper;
import com.gw.mapper.UtUnitUserMapper;
import com.gw.mapper.entity.UtBaseUserreunit;
import com.gw.mapper.entity.UtInspectPlan;
import com.gw.mapper.entity.UtUnitUser;
import com.gw.unit.data.GetUserUnitsData;
import com.gw.unit.data.MaintenanceUserInData;
import com.gw.unit.data.MaintenanceUserOutData;
import com.gw.unit.data.PlanUserRoleLOutData;
import com.gw.unit.data.UserReUnitInData;
import com.gw.unit.service.MaintenanceUserService;
import com.gw.util.Util;
import com.gw.util.UtilConv;
import com.gw.util.UtilMessage;

import tk.mybatis.mapper.entity.Example;

@Service
public class MaintenanceUserServiceImpl implements MaintenanceUserService {

	@Autowired
	private UtUnitUserMapper unitUserMapper;

	@Autowired
	private UtBaseUserreunitMapper userreunitMapper;

	@Autowired
	private UtInspectPlanMapper utInspectPlanMapper;

	@Autowired
	private SnowflakeIdWorker snowflakeIdWorker;
	@Resource
	private UtInspectSiteMapper utInspectSiteMapper;

	@Override
	public void addMaintenanceUser(Long userId, MaintenanceUserInData inData) throws Exception {
		// 判断当前用户名是否已存在
		Example example = new Example(UtUnitUser.class);
		example.createCriteria().andEqualTo("account", inData.getAccount());
		//通过account在单位用户查询
		List<UtUnitUser> selectByExample = unitUserMapper.selectByExample(example);
		if (Util.isNotEmpty(selectByExample) && selectByExample.size() > 0) {
			throw new ServiceException("该用户名已经存在!");
		}
		//如果用户名不存在
		UtUnitUser unitUser = new UtUnitUser();
		//将注册用户赋值到unitUser对象
		BeanUtils.copyProperties(inData, unitUser);
		Long id = snowflakeIdWorker.nextId();
		unitUser.setId(id);
		//设置用户类型 用户类型（0维保单位，1联网单位，2其他）
		unitUser.setUsertype(0);
		//设置是否删除
		unitUser.setIsdelete(0);
		//创建时间
		unitUser.setAdddate(new Date());
		//创建人ID（当前登录用户id）
		unitUser.setAdduserid(userId.toString());
		//设置单位ID
		if(Util.isNotEmpty(inData.getUnitid())){
			unitUser.setUnitid(Long.valueOf(inData.getUnitid()));
		}
		//维保用户角色
		if (Util.isNotEmpty(inData.getUserrole())) {
			unitUser.setUserrole(Integer.parseInt(inData.getUserrole()));
		}
		//性别
		if (Util.isNotEmpty(Integer.parseInt(inData.getSex()))) {
			unitUser.setSex(Integer.parseInt(inData.getSex()));
		}
		//生日
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		if (Util.isNotEmpty(inData.getBirthday())) {
			unitUser.setBirthday(format.parse(inData.getBirthday()));
		}
		//发证日期
		if (Util.isNotEmpty(inData.getCertificatesdate())) {
			unitUser.setCertificatesdate(format.parse(inData.getCertificatesdate()));
		}
		//过期时间
		if(Util.isNotEmpty(inData.getExpirytime())){
			unitUser.setExpirytime(UtilConv.str2Date(inData.getExpirytime(), UtilConv.DATE_YYYY_MM_DD_CHN));
		}
		//密码
		unitUser.setPassword(DigestUtils.md5Hex(unitUser.getPassword()));
		unitUserMapper.insert(unitUser);
	}

	@Override
	public PageInfo<MaintenanceUserOutData> list(HttpServletRequest request, String username, String unitname,
			String account,String UnitID) throws Exception{
		int pageNumber = Integer.parseInt(request.getParameter("pageNumber"));
		int pageSize = Integer.parseInt(request.getParameter("pageSize"));
		PageHelper.startPage(pageNumber, pageSize);
		List<MaintenanceUserOutData> list = unitUserMapper.selectAllMaintenanceUser(username, unitname, account,UnitID);
		PageInfo<MaintenanceUserOutData> pageInfo = new PageInfo<MaintenanceUserOutData>(list);
		return pageInfo;
	}

	@Override
	public void updateMaintenanceUser(MaintenanceUserInData inData) throws Exception {
		//判断当前用户名是否已存在
		Example example = new Example(UtUnitUser.class);
		example.createCriteria().andEqualTo("account", inData.getAccount());
		List<UtUnitUser> selectByExample = unitUserMapper.selectByExample(example);
		if(Util.isNotEmpty(selectByExample) && selectByExample.size()>0){
			UtUnitUser utUnitUser = selectByExample.get(0);
			if(!utUnitUser.getId().equals(Long.valueOf(inData.getId()))){
				throw new ServiceException("该用户名已经存在!");
			}
		}
		
		UtUnitUser unitUser = unitUserMapper.selectByPrimaryKey(Long.valueOf(inData.getId()));
		String password = unitUser.getPassword();
		BeanUtils.copyProperties(inData, unitUser);
		unitUser.setUsertype(0);
		unitUser.setPassword(password);
		if(Util.isNotEmpty(inData.getUnitid())){
			unitUser.setUnitid(Long.valueOf(inData.getUnitid()));
		}else{
			unitUser.setUnitid(null);
		}
		if (Util.isNotEmpty(inData.getUserrole())) {
			unitUser.setUserrole(Integer.parseInt(inData.getUserrole()));
		} else {
			unitUser.setUserrole(null);
		}
		if (Util.isNotEmpty(Integer.parseInt(inData.getSex()))) {
			unitUser.setSex(Integer.parseInt(inData.getSex()));
		} else {
			unitUser.setSex(null);
		}
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		if (Util.isNotEmpty(inData.getBirthday())) {
			unitUser.setBirthday(format.parse(inData.getBirthday()));
		} else {
			unitUser.setBirthday(null);
		}
		if (Util.isNotEmpty(inData.getCertificatesdate())) {
			unitUser.setCertificatesdate(format.parse(inData.getCertificatesdate()));
		} else {
			unitUser.setCertificatesdate(null);
		}
		if(Util.isNotEmpty(inData.getExpirytime())){
			unitUser.setExpirytime(UtilConv.str2Date(inData.getExpirytime(), UtilConv.DATE_YYYY_MM_DD_CHN));
		}else{
			unitUser.setExpirytime(null);
		}

		unitUserMapper.updateByPrimaryKey(unitUser);
	}

	@Override
	public void deleteMaintenanceUser(Long id) throws Exception {
		unitUserMapper.deleteByPrimaryKey(id);
	}

	@Override
	public List<MaintenanceUserOutData> getUserList(String unitID,String userrole) throws Exception {
		if ("0".equals(unitID)) {
			unitID = null;
		}
		List<MaintenanceUserOutData> list = unitUserMapper.getUserList(unitID,userrole);
		if (list == null) {
			throw new ServiceException(UtilMessage.GET_MSG_ERROR);
		}
		return list;
	}

	@Override
	public PageInfo<GetUserUnitsData> getUserReUnit(UserReUnitInData inData) throws Exception {
		if (Util.isEmpty(inData.getUserId())) {
			return null;
		}
		PageHelper.startPage(inData.getPageNumber(), inData.getPageSize());
		List<GetUserUnitsData> userReUnit = null;
		if(inData.getIsRelation() == 0){
			userReUnit = userreunitMapper.getUserNotReUnit(inData.getUserId(),inData.getUnitName());
		}else{
			userReUnit = userreunitMapper.getUserReUnit(inData.getUserId(),inData.getUnitName());
		}
		PageInfo<GetUserUnitsData> pageInfo = new PageInfo<GetUserUnitsData>(userReUnit);
		return pageInfo;
	}

	@Override
	@Transactional
	public void setUserReUnit(String userId, String manyUnitId) throws Exception {
		if (Util.isEmpty(userId)) {
			throw new Exception("用户不存在");
		}
		if (Util.isEmpty(manyUnitId)) {
			return;
		} else {
			String[] split = manyUnitId.split(",");
			for (String id : split) {
				UtBaseUserreunit userreunit = new UtBaseUserreunit();
				userreunit.setUserId(Long.valueOf(userId));
				userreunit.setUnitId(Long.valueOf(id));
				userreunitMapper.insert(userreunit);
			}
		}

	}
	
	@Override
	@Transactional
	public void delUserReUnit(String userId, String unitIds) throws Exception {
		if (Util.isEmpty(userId)) {
			throw new Exception("用户不存在");
		}
		if (Util.isEmpty(unitIds)) {
			return;
		} else {
			String[] split = unitIds.split(",");
			for (String id : split) {
				Example example = new Example(UtBaseUserreunit.class);
				example.createCriteria().andEqualTo("userId", Long.valueOf(userId)).andEqualTo("unitId", Long.valueOf(id));
				userreunitMapper.deleteByExample(example);
			}
		}
	}

	@Override
	public PlanUserRoleLOutData getUserRoleList(String siteID, String UnitID,String userrole) throws Exception {
		PlanUserRoleLOutData outData = new PlanUserRoleLOutData();
		List<MaintenanceUserOutData> userList = getUserList(UnitID,userrole);// 单位下所有人员信息
		if (Util.isEmpty(siteID)) {// 第一次新增计划时
			List<PlanUserRoleLOutData> noList = new ArrayList<PlanUserRoleLOutData>();
			for (MaintenanceUserOutData data : userList) {
				PlanUserRoleLOutData noData = new PlanUserRoleLOutData();
				noData.setId(data.getId());
				noData.setName(data.getUsername());
				noList.add(noData);
			}
			outData.setNoRole(noList);
		} else {
			List<String> idList = new ArrayList<String>();
			List<String> allIDList = new ArrayList<String>();
			List<PlanUserRoleLOutData> haslist = new ArrayList<PlanUserRoleLOutData>();
			List<PlanUserRoleLOutData> nolist = new ArrayList<PlanUserRoleLOutData>();
			UtInspectSite plan = utInspectSiteMapper.selectByPrimaryKey(Long.parseLong(siteID));
			String id = plan.getExecutorID();// 计划已分配人员id
			Integer index = null;
			if(Util.isNotEmpty(id)) {
				index = id.indexOf(",");
			}
			if (index != -1) {
				String[] i = id.split(",");
				for (String a : i) {
					idList.add(a);
				}
			} else {
				idList.add(id);
			}
			for (MaintenanceUserOutData data : userList) {
				allIDList.add(data.getId());
			}
			idList.retainAll(allIDList);
			for (String ids : idList) {
				for (MaintenanceUserOutData data : userList) {
					if (ids.equals(data.getId())) {
						PlanUserRoleLOutData has = new PlanUserRoleLOutData();
						has.setId(data.getId());
						has.setName(data.getUsername());
						haslist.add(has);
					}
				}
			}
			allIDList.removeAll(idList);
			for (String ids : allIDList) {
				for (MaintenanceUserOutData data : userList) {
					if (ids.equals(data.getId())) {
						PlanUserRoleLOutData has = new PlanUserRoleLOutData();
						has.setId(data.getId());
						has.setName(data.getUsername());
						nolist.add(has);
					}
				}
			}
			outData.setHasRole(haslist);
			outData.setNoRole(nolist);
		}

		return outData;
	}

	@Override
	public void resetPas(String id) throws Exception {
		UtUnitUser utUnitUser = unitUserMapper.selectByPrimaryKey(Long.valueOf(id));
		utUnitUser.setPassword(DigestUtils.md5Hex("123456"));
		unitUserMapper.updateByPrimaryKey(utUnitUser);
	}


}
