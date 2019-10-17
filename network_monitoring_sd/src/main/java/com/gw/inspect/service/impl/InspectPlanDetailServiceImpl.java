package com.gw.inspect.service.impl;

import com.gw.common.SnowflakeIdWorker;
import com.gw.exception.ServiceException;
import com.gw.inspect.data.InspectPlanDetailInData;
import com.gw.inspect.data.InspectSiteOutData;
import com.gw.inspect.data.InspectTaskInData;
import com.gw.inspect.service.InspectPlanDetailService;
import com.gw.mapper.UTInspectBaseRelMapper;
import com.gw.mapper.UtInspectPlandetialMapper;
import com.gw.mapper.UtInspectSiteMapper;
import com.gw.mapper.UtInspectTaskMapper;
import com.gw.mapper.entity.UTInspectBaseRel;
import com.gw.mapper.entity.UtInspectPlandetial;
import com.gw.mapper.entity.UtInspectSite;
import com.gw.mapper.entity.UtInspectTask;
import com.gw.unit.data.MaintenanceUserOutData;
import com.gw.unit.data.PlanUserRoleLOutData;
import com.gw.unit.service.MaintenanceUserService;
import com.gw.util.Util;
import com.gw.util.UtilDateTime;
import com.gw.util.UtilMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class InspectPlanDetailServiceImpl implements InspectPlanDetailService {

	@Autowired
	private MaintenanceUserService maintenanceUserService;
	@Resource
	private UtInspectPlandetialMapper utInspectPlandetialMapper;
	@Resource
	private UtInspectTaskMapper utInspectTaskMapper;
	@Resource
	private UtInspectSiteMapper utInspectSiteMapper;
	@Autowired
	private SnowflakeIdWorker snowflakeIdWorker;
	@Autowired
	private UTInspectBaseRelMapper utInspectBaseRelMapper;

	@Override
	public void add(InspectPlanDetailInData inData) throws Exception {
		if (inData.getSiteID() != null && inData.getSiteID() != "" && inData.getPlanID() != null && inData.getPlanID() != "") {
			String[] ids = inData.getSiteID().split(",");
			for (String id : ids) {
				UtInspectPlandetial detail = new UtInspectPlandetial();
				detail.setId(snowflakeIdWorker.nextId());
				detail.setPlanid(Long.parseLong(inData.getPlanID()));
				detail.setSiteid(Long.parseLong(id));
				detail.setTaskUserId(inData.getTaskUserID());
				detail.setTaskUserName(inData.getTaskUserName());
				Integer status = 0;
				detail.setStatus(status);
				Integer flag = utInspectPlandetialMapper.insert(detail);
				if (flag < 1) {
					throw new ServiceException(UtilMessage.SAVE_MESSAGE_ERROR);
				}
			}
		}

	}

	@Override
	public void remove(String planDetailId) throws Exception {
		if (planDetailId != null && planDetailId != "") {
			Integer flag = utInspectPlandetialMapper.deleteByPrimaryKey(Long.parseLong(planDetailId));
			if (flag < 1) {
				throw new ServiceException(UtilMessage.DEL_MESSAGE_ERROR);
			}
		}
	}

	@Override
	public void update(InspectPlanDetailInData inData) throws Exception {
		if (inData.getID() != null && inData.getID() != "" && inData.getTaskUserID() != null && inData.getTaskUserID() != "") {
			UtInspectPlandetial detail = new UtInspectPlandetial();
			detail.setId(Long.parseLong(inData.getID()));
			detail.setTaskUserId(inData.getTaskUserID());
			detail.setTaskUserName(inData.getTaskUserName());
			Integer flag = utInspectPlandetialMapper.updateByPrimaryKeySelective(detail);
			if (flag < 1) {
				throw new ServiceException(UtilMessage.UPDATE_ERROR);
			}
		}
	}

	@Override
	public PlanUserRoleLOutData getUserList(String id, String UnitID, String userrole) throws Exception {
		PlanUserRoleLOutData outData = new PlanUserRoleLOutData();
		List<MaintenanceUserOutData> userList = maintenanceUserService.getUserList(UnitID, userrole);//单位下所有人员信息
		List<String> idList = new ArrayList<String>();
		List<String> allIDList = new ArrayList<String>();
		List<PlanUserRoleLOutData> haslist = new ArrayList<PlanUserRoleLOutData>();
		List<PlanUserRoleLOutData> nolist = new ArrayList<PlanUserRoleLOutData>();
		UtInspectPlandetial list = utInspectPlandetialMapper.selectByPrimaryKey(Long.parseLong(id));
		String id1 = list.getTaskUserId();//计划已分配人员id
		Integer index = null;
		if (Util.isNotEmpty(id1)) {
			index = id1.indexOf(",");
		}
		if (index != -1) {
			String[] i = id1.split(",");
			for (String a : i) {
				idList.add(a);
			}
		} else {
			idList.add(id1);
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
		return outData;
	}

	/**
	 * 生成任务
	 *
	 * @throws Exception
	 */
	@Override
	public void createTask(InspectTaskInData inData, Long userID) throws Exception {
		String[] users = {};
		Integer counts = 0;
		if (inData.getOrderindex() != null && inData.getOrderindex() != "") {
			counts = Integer.parseInt(inData.getOrderindex());
		}
		if (inData.getInspectuserid() != null && inData.getInspectuserid() != "") {
			users = inData.getInspectuserid().split(",");
		}
		//获取点位巡查周期
		UtInspectSite site = utInspectSiteMapper.selectByPrimaryKey(Long.parseLong(inData.getSiteid()));
		String startTime = inData.getTaskstart();//计划开始时间
		String endTime = inData.getTaskend();//计划结束时间
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date parseStartTime = format.parse(startTime);
		Date parseEndTime = format.parse(endTime);
		String date = format.format(new Date());
		Date parseDate = format.parse(date);
		if (UtilDateTime.compare(parseEndTime, parseDate) < 0) {
			throw new ServiceException("请在计划结束时间之前生成任务！");
		} else {
			if (UtilDateTime.compare(parseStartTime, parseDate) < 0) {
				Date dateStartTime = UtilDateTime.calcDate(format.parse(date), 0, 0, 1);
				startTime = format.format(dateStartTime);
			}
		}

		if (site.getInspectcycletype() == 0) {
			//周期为日
			List<String> list = Util.getDay(startTime, endTime);
			for (String d : list) {
				String start = d.substring(0, d.indexOf("~"));
				String end = d.substring(d.indexOf("~") + 1, d.length());
				inData.setTaskstart(start);
				inData.setTaskend(end);
				Task(users, counts, inData);
			}
		} else if (site.getInspectcycletype() == 1) {
			//周期为周
			List<String> list = Util.getWeek(startTime, endTime);
			for (String time : list) {
				String start = time.substring(0, time.indexOf("/"));
				String end = time.substring(time.indexOf("~") + 1, time.length());
				String epirationTime = time.substring(time.indexOf("/") + 1, time.indexOf("~"));
				inData.setTaskstart(start);
				inData.setTaskend(end);
				inData.setEpirationTime(epirationTime);
				Task(users, counts, inData);
			}
		} else if (site.getInspectcycletype() == 2) {
			//周期为月
			List<String> list = Util.getMouth(startTime, endTime);
			for (String time : list) {
				String start = time.substring(0, time.indexOf("/"));
				String end = time.substring(time.indexOf("~") + 1, time.length());
				String epirationTime = time.substring(time.indexOf("/") + 1, time.indexOf("~"));
				inData.setTaskstart(start);
				inData.setTaskend(end);
				inData.setEpirationTime(epirationTime);
				Task(users, counts, inData);
			}
		} else if (site.getInspectcycletype() == 3) {
			//周期为年
			List<String> list = Util.getYear(startTime, endTime);
			for (String time : list) {
				String start = time.substring(0, time.indexOf("/"));
				String end = time.substring(time.indexOf("~") + 1, time.length());
				String epirationTime = time.substring(time.indexOf("/") + 1, time.indexOf("~"));
				inData.setTaskstart(start);
				inData.setTaskend(end);
				inData.setEpirationTime(epirationTime);
				Task(users, counts, inData);
			}
		}
	}

	/**
	 * 生成任务表
	 *
	 * @param users
	 * @param counts
	 * @param inData
	 * @return
	 * @throws Exception
	 */
	public void Task(String[] users, Integer counts, InspectTaskInData inData) throws Exception {
		for (int i = 0; i < users.length; i++) {
			for (int j = 0; j < counts; j++) {
				UtInspectTask task = new UtInspectTask();
				task.setId(snowflakeIdWorker.nextId());
				task.setInspectuserid(Long.parseLong(users[i]));
				task.setSupervisorid(Long.parseLong(inData.getSupervisorID()));
				task.setReceivestatus(0);
				if (inData.getUnitid() != null && inData.getUnitid() != "") {
					task.setUnitid(Long.parseLong(inData.getUnitid()));
				}
				if (inData.getPlandetialid() != null && inData.getPlandetialid() != "") {
					task.setPlandetialid(Long.parseLong(inData.getPlandetialid()));
				}
				task.setOrderindex(1);
				task.setSitecode(inData.getSitecode());
				task.setSitename(inData.getSitename());
				task.setSitedesc(inData.getSitedesc());
				task.setNfccode(inData.getNfccode());
				task.setIschange(0);
				task.setIsreceive(0);
				task.setTaskend(Util.StringToDateTime(inData.getTaskend()));
				task.setTaskstart(Util.StringToDateTime(inData.getTaskstart()));
				if (Util.isNotEmpty(inData.getEpirationTime())) {
					task.setExpirationTime(Util.StringToDateTime(inData.getEpirationTime()));
				}
				if (inData.getSiteid() != null && inData.getSiteid() != "") {
					UTInspectBaseRel utInspectBaseRel = new UTInspectBaseRel();
					utInspectBaseRel.setSiteID(Long.valueOf(inData.getSiteid()));
					List<UTInspectBaseRel> select = utInspectBaseRelMapper.select(utInspectBaseRel);
					task.setSitecount(select.size());
					task.setSiteid(Long.parseLong(inData.getSiteid()));
				}
				task.setOkcheckcount(0);
				Integer flag = utInspectTaskMapper.insert(task);
				if (flag < 1) {
					throw new ServiceException(UtilMessage.SAVE_MESSAGE_ERROR);
				}
			}
		}
		//生成任务时需要修改计划详情表中的状态
		if (inData.getSiteid() != null && inData.getSiteid() != null) {
			UtInspectPlandetial detail = utInspectPlandetialMapper.getDetailBySiteID(Long.parseLong(inData.getPlandetialid()));
			detail.setStatus(1);
			Integer flag1 = utInspectPlandetialMapper.updateByPrimaryKeySelective(detail);
			if (flag1 < 1) {
				throw new ServiceException(UtilMessage.UPDATE_ERROR);
			}
		}
	}

	@Override
	public void createAllTask(String siteID, String planID, Long id) throws Exception {
		if (Util.isNotEmpty(siteID)) {
			String[] siteIDs = siteID.split(",");
			for (String site : siteIDs) {
				InspectSiteOutData outData = utInspectSiteMapper.getSiteListByID(site, planID);
				InspectTaskInData inData = new InspectTaskInData();
				inData.setInspectuserid(outData.getExecutorID());
				inData.setPlandetialid(outData.getPlanDetailID());
				inData.setUnitid(outData.getUnitID());
				inData.setSiteid(site);
				inData.setSitedesc(outData.getSiteDesc());
				inData.setSitecode(outData.getSiteCode());
				inData.setSitename(outData.getSiteName());
				inData.setOrderindex(outData.getInspectCount());
				inData.setNfccode(outData.getNFCCode());
				inData.setTaskstart(outData.getTaskStart());
				inData.setTaskend(outData.getTaskEnd());
				inData.setSupervisorID(outData.getSupervisorID());
				if (!"1".equals(outData.getStatus())) {
					createTask(inData, id);
				}
			}
		}

	}
}
