package com.gw.generatereport;

import com.gw.exception.ServiceException;
import com.gw.front.maintenance.data.FrontMaintenanceExport;
import com.gw.front.maintenance.data.FrontMaintenanceInData;
import com.gw.front.maintenance.data.FrontMaintenanceOutData;
import com.gw.front.maintenance.data.FrontMaintenanceTaskOutData;
import com.gw.mapper.*;
import com.gw.mapper.entity.*;
import com.gw.util.Util;
import com.gw.util.UtilConv;
import org.springframework.beans.BeanUtils;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 生成报告（巡查维保）
 *
 * @author zfg
 */
@Component
public class GenerateReport {

	@Resource
	private UtUnitBaseinfoMapper utUnitBaseinfoMapper;
	@Resource
	private UtLzRepairMapper utLzRepairMapper;
	@Resource
	private UtInspectTaskMapper utInspectTaskMapper;
	@Resource
	private UtMaintenanceUnitMapper utMaintenanceUnitMapper;
	@Resource
	private UtUnitUserMapper utUnitUserMapper;
	@Resource
	private UtInspectSiteMapper utInspectSiteMapper;
	@Resource
	private UtInspectTaskdetialMapper utInspectTaskdetialMapper;
	@Resource
	private SendMail sendMail;
	private static DecimalFormat df = new DecimalFormat("0.00");//设置保留位数

	@Scheduled(cron = "0 0 1 1 * ?")
	public void generateReportDo() {
		List<UtUnitUser> userList = utUnitUserMapper.selectAll();// 所有人员信息
		List<UtUnitUser> netWorkUserList = new ArrayList<UtUnitUser>();//所有联网单位
		List<UtUnitUser> maintenanceUserList = new ArrayList<UtUnitUser>();//所有维保单位
		for (UtUnitUser user : userList) {
			if ("0".equals(user.getUsertype().toString())) {
				maintenanceUserList.add(user);
			} else {
				netWorkUserList.add(user);
			}
		}
		try {
			inspectReport(userList);
			maintenanceReport(maintenanceUserList);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 导出巡查报告
	 *
	 * @param inData
	 * @throws Exception
	 */
	public void exportInspectReport(FrontMaintenanceInData inData, HttpServletResponse response) throws Exception {
		List<UtUnitUser> userList = utUnitUserMapper.selectAll();// 所有人员信息
		List<UtUnitUser> netWorkUserList = new ArrayList<UtUnitUser>();//当前联网单位人员
		for (UtUnitUser user : userList) {
			if ("1".equals(user.getUsertype().toString()) && user.getUnitid().toString().equals(inData.getUnitId())) {
				netWorkUserList.add(user);
			}
		}
		if (Util.isEmpty(inData.getUnitId())) {
			throw new ServiceException("单位不存在！");
		}
		UtUnitBaseinfo baseInfo = utUnitBaseinfoMapper.selectByPrimaryKey(Long.parseLong(inData.getUnitId()));
		if (Util.isEmpty(baseInfo)) {
			throw new ServiceException("单位不存在！");
		}

		String firstDay = "";
		String lastDay = "";
		Calendar cale = Calendar.getInstance();
		Integer nowMouth = cale.get(Calendar.MONTH) + 1;
		String year = inData.getNowDate().substring(0, inData.getNowDate().indexOf("-"));
		String mouth = inData.getNowDate().substring(inData.getNowDate().indexOf("-") + 1, inData.getNowDate().length());
		if (mouth.equals(nowMouth.toString())) {
			//当前月度巡查报告
			firstDay = getMouthDay(0, 1);
			lastDay = UtilConv.date2Str(new Date(), UtilConv.DATE_YYYY_MM_DD_SS);

		} else {
			//非当前月度巡查报告
			firstDay = Util.getFirstDayOfMonth1(Integer.parseInt(year), Integer.parseInt(mouth));
			lastDay = Util.getLastDayOfMonth1(Integer.parseInt(year), Integer.parseInt(mouth));
		}
		Date dt = new Date();
		String fileName = new Long(dt.getTime()).toString();
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xls");
		List<UtInspectTask> taskList = utInspectTaskMapper.getMouthTask(firstDay, lastDay, inData.getUnitId());
		List<UtInspectSite> siteList = utInspectSiteMapper.getUnitSiteList(inData.getUnitId());
		FrontMaintenanceInData selectData = new FrontMaintenanceInData();
		selectData.setStartDate(firstDay);
		selectData.setEndDate(lastDay);
		selectData.setUnitId(inData.getUnitId());
		List<FrontMaintenanceTaskOutData> list = utInspectTaskdetialMapper.getInspectTaskList(selectData);
		inspectNowReport(netWorkUserList, taskList, siteList, list, baseInfo, response.getOutputStream());

	}

	/**
	 * 导出维保报告
	 *
	 * @param inData
	 * @throws Exception
	 */
	public void exportMaintenanceReport(FrontMaintenanceInData inData, HttpServletResponse response) throws Exception {
		List<UtUnitUser> userList = utUnitUserMapper.selectAll();// 所有人员信息
		List<UtUnitUser> maintenanceUserList = new ArrayList<UtUnitUser>();//当前维保单位人员
		for (UtUnitUser user : userList) {
			if ("0".equals(user.getUsertype().toString()) && user.getUnitid().toString().equals(inData.getUnitId())) {
				maintenanceUserList.add(user);
			}
		}
		if (Util.isEmpty(inData.getUnitId())) {
			throw new ServiceException("单位不存在！");
		}
		UtUnitBaseinfo baseInfo = utUnitBaseinfoMapper.selectByPrimaryKey(Long.parseLong(inData.getUnitId()));
		String firstDay = "";
		String lastDay = "";
		Calendar cale = Calendar.getInstance();
		Integer nowMouth = cale.get(Calendar.MONTH) + 1;
		String year = inData.getNowDate().substring(0, inData.getNowDate().indexOf("-"));
		String mouth = inData.getNowDate().substring(inData.getNowDate().indexOf("-") + 1, inData.getNowDate().length());
		if (mouth.equals(nowMouth.toString())) {
			//当前月度维保报告
			firstDay = getMouthDay(0, 1);
			lastDay = UtilConv.date2Str(new Date(), UtilConv.DATE_YYYY_MM_DD_SS);

		} else {
			//非当前月度维保报告
			firstDay = Util.getFirstDayOfMonth1(Integer.parseInt(year), Integer.parseInt(mouth));
			lastDay = Util.getLastDayOfMonth1(Integer.parseInt(year), Integer.parseInt(mouth));
		}
		Date dt = new Date();
		String fileName = new Long(dt.getTime()).toString();
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xls");
		List<RepairSiteOutData> bjzjRepairList = utLzRepairMapper.getBJZJRepairList(firstDay, lastDay);//当前月份报警主机维修列表
		List<RepairSiteOutData> rtuRepairList = utLzRepairMapper.getRTURepairList(firstDay, lastDay);//当前月份RTU维修列表
		List<RepairSiteOutData> inspectRepairList = utLzRepairMapper.getInspectRepairList(firstDay, lastDay);//当前月份巡查维修列表
		FrontMaintenanceInData mdata = new FrontMaintenanceInData();
		mdata.setStartDate(firstDay);
		mdata.setEndDate(lastDay);
		mdata.setUnitId(inData.getUnitId());
		List<FrontMaintenanceOutData> list = utLzRepairMapper.getRepairList(mdata);
		List<UtLzRepair> repairList = utLzRepairMapper.selectNowMouthList(firstDay, lastDay, inData.getUnitId());
		maintenanceNowReport(bjzjRepairList, rtuRepairList, inspectRepairList, maintenanceUserList, list, repairList, baseInfo, response.getOutputStream());

	}

	/**
	 * 巡查报告(随机月份)
	 *
	 * @throws FileNotFoundException
	 */
	public void inspectNowReport(List<UtUnitUser> userList, List<UtInspectTask> taskList, List<UtInspectSite> siteList,
								 List<FrontMaintenanceTaskOutData> list, UtUnitBaseinfo baseInfo, OutputStream out) throws Exception {
		GenerateNetWorkOutData unitData = new GenerateNetWorkOutData();
		unitData = dealOneUnitMsg(userList, taskList, siteList, baseInfo);
		if (Util.isNotEmpty(unitData)) {
			String[] header = new String[]{"单位编号", "单位名称", "巡查人员", "计划名称", "计划时间", "位置", "处理状态", "处理时间"};
			List<MouthInspectTaskOutData> exportDatas = new ArrayList<>();
			for (FrontMaintenanceTaskOutData frontMaintenanceTaskOutData : list) {
				MouthInspectTaskOutData data = new MouthInspectTaskOutData();
				BeanUtils.copyProperties(frontMaintenanceTaskOutData, data);
				exportDatas.add(data);
			}
			MailExcel<MouthInspectTaskOutData> export = new MailExcel<MouthInspectTaskOutData>();
			export.exportInspectExcel(out, unitData, exportDatas, header);
			out.flush();
		} else {
			throw new ServiceException("该单位没有巡查记录！");
		}


	}

	/**
	 * 维保报告(随机月份)
	 *
	 * @throws FileNotFoundException
	 */
	public void maintenanceNowReport(List<RepairSiteOutData> bjzjRepairList, List<RepairSiteOutData> rtuRepairList,
									 List<RepairSiteOutData> inspectRepairList, List<UtUnitUser> userList, List<FrontMaintenanceOutData> list,
									 List<UtLzRepair> repairList, UtUnitBaseinfo baseInfo, OutputStream out) throws Exception {
		GenerateMaintenanceOutData unitData = new GenerateMaintenanceOutData();
		unitData = dealOneUnitMsg(bjzjRepairList, rtuRepairList, inspectRepairList, userList, repairList, baseInfo);
		if (Util.isNotEmpty(unitData)) {
			String[] header = new String[]{"单位编号", "单位名称", "巡查人员", "计划名称", "计划时间", "位置", "处理状态", "处理时间"};
			List<FrontMaintenanceExport> exportDatas = new ArrayList<>();
			for (FrontMaintenanceOutData frontMaintenanceOutData : list) {
				FrontMaintenanceExport data = new FrontMaintenanceExport();
				BeanUtils.copyProperties(frontMaintenanceOutData, data);
				exportDatas.add(data);
			}
			MailExcel<FrontMaintenanceExport> export = new MailExcel<FrontMaintenanceExport>();
			export.exportMaintenanceExcel(out, unitData, exportDatas, header);
			out.flush();
		} else {
			throw new ServiceException("该单位没有维保记录！");
		}


	}

	/**
	 * 处理某个联网单位信息
	 *
	 * @param userList
	 * @param taskList
	 * @param siteList
	 * @return
	 */
	public GenerateNetWorkOutData dealOneUnitMsg(List<UtUnitUser> userList, List<UtInspectTask> taskList,
												 List<UtInspectSite> siteList, UtUnitBaseinfo unit) {
		GenerateNetWorkOutData unitData = new GenerateNetWorkOutData();
		Set<UtUnitUser> userUnitList = new HashSet<UtUnitUser>();
		List<UtInspectTask> unitTaskList = new ArrayList<UtInspectTask>();
		List<UtInspectSite> unitSiteList = new ArrayList<UtInspectSite>();
		Integer inspectTaskCount = 0;
		for (UtInspectTask task : taskList) {
			if (unit.getId().toString().equals(task.getUnitid().toString())) {
				unitTaskList.add(task);
			}
		}
		for (UtInspectSite site : siteList) {
			if (unit.getId().toString().equals(site.getUnitid().toString())) {
				unitSiteList.add(site);
			}
		}
		for (UtInspectTask task : unitTaskList) {
			for (UtUnitUser user : userList) {
				if (task.getInspectuserid().equals(user.getId().toString())) {
					userUnitList.add(user);
				}
			}
			if (Util.isNotEmpty(task.getIsinspect())) {
				inspectTaskCount = inspectTaskCount + 1;
			}
		}
		if (Util.isEmpty(unit)) {
			return null;
		}
		unitData.setTaskCount(unitTaskList.size());
		unitData.setInspectCount(inspectTaskCount);
		unitData.setInspectPersonCount(userUnitList.size());
		unitData.setUnitName(unit.getUnitname());
		unitData.setUnitAddress(unit.getUnitaddress());
		unitData.setUnitChargePerson(unit.getSafemanagername());
		unitData.setPersonPhone(unit.getPhone());
		unitData.setUnitId(unit.getId());
		unitData.setReceviceAccount(unit.getEmail());
		unitData.setCompletionRate("0%");
		if (taskList.size() != 0) {
			String rate = df.format(((float) inspectTaskCount / taskList.size()) * 100) + "%";
			unitData.setCompletionRate(rate);
		}
		unitData = addInspectUnitUserData(userList, unitTaskList, unitData);
		unitData = addInspectUnitSiteData(unitTaskList, unitSiteList, userList, unitData);
		return unitData;


	}

	/**
	 * 处理某个维保单位信息
	 *
	 * @param userList
	 * @param taskList
	 * @param siteList
	 * @return
	 * @throws Exception
	 */
	public GenerateMaintenanceOutData dealOneUnitMsg(List<RepairSiteOutData> bjzjRepairList, List<RepairSiteOutData> rtuRepairList,
													 List<RepairSiteOutData> inspectRepairList, List<UtUnitUser> userList, List<UtLzRepair> repairList, UtUnitBaseinfo unit) throws Exception {
		List<RepairSiteOutData> bjzjRepairUnitList = new ArrayList<RepairSiteOutData>();//当前月份报警主机维修列表
		List<RepairSiteOutData> rtuRepairUnitList = new ArrayList<RepairSiteOutData>();//当前月份RTU维修列表
		List<RepairSiteOutData> inspectRepairUnitList = new ArrayList<RepairSiteOutData>();//当前月份巡查维修列表
		List<UtLzRepair> repairUnitList = new ArrayList<UtLzRepair>();
		List<UtUnitUser> userUnitList = new ArrayList<UtUnitUser>();
		Integer maintenanceCount = 0;
		if (Util.isEmpty(unit)) {
			return null;
		}
		GenerateMaintenanceOutData unitData = new GenerateMaintenanceOutData();
		for (RepairSiteOutData bjzjRepair : bjzjRepairList) {
			if (bjzjRepair.getUnitID().equals(unit.getId().toString())) {
				bjzjRepairUnitList.add(bjzjRepair);
			}
		}
		for (RepairSiteOutData rtuRepair : rtuRepairList) {
			if (rtuRepair.getUnitID().equals(unit.getId().toString())) {
				rtuRepairUnitList.add(rtuRepair);
			}
		}
		for (RepairSiteOutData inspectRepair : inspectRepairList) {
			if (inspectRepair.getUnitID().equals(unit.getId().toString())) {
				inspectRepairUnitList.add(inspectRepair);
			}
		}
		for (UtLzRepair repair : repairList) {
			if (repair.getUnitid().toString().equals(unit.getId().toString()) && 3 == repair.getDealstatus()) {
				repairUnitList.add(repair);
			}
		}
		for (UtUnitUser user : userList) {
			for (UtLzRepair repair : repairUnitList) {
				if (user.getUsername().equals(repair.getWbclr())) {
					userUnitList.add(user);
				}
			}
		}
		unitData.setMaintenanceCount(repairUnitList.size());
		unitData.setMaintenancePersonCount(userUnitList.size());
		unitData.setUnitName(unit.getUnitname());
		unitData.setUnitAddress(unit.getUnitaddress());
		unitData.setUnitChargePerson(unit.getSafemanagername());
		unitData.setPersonPhone(unit.getPhone());
		unitData.setUnitId(unit.getId());
		unitData.setReceviceAccount(unit.getEmail());
		List<MaintenanceSiteOutData> siteOutDataList = getWBSiteList(bjzjRepairUnitList, rtuRepairUnitList, inspectRepairUnitList);
		for (MaintenanceSiteOutData data : siteOutDataList) {
			maintenanceCount = data.getMaintenanceCount() + maintenanceCount;
		}
		unitData.setSiteData(siteOutDataList);
//		unitData.setMaintenanceCount(maintenanceCount);
//		List<RepairSiteOutData> unitSiteList = new ArrayList<RepairSiteOutData>();
//		unitSiteList.addAll(bjzjRepairUnitList);
//		unitSiteList.addAll(rtuRepairUnitList);
//		unitSiteList.addAll(inspectRepairUnitList);
		unitData.setUserData(getWBUserList(userUnitList, repairUnitList));
		return unitData;


	}

	/**
	 * 巡查报告(月度邮件)
	 *
	 * @throws FileNotFoundException
	 */
	public void inspectReport(List<UtUnitUser> userList) throws Exception {
		List<GenerateNetWorkOutData> outData = new ArrayList<GenerateNetWorkOutData>();
		List<UtInspectTask> taskList = utInspectTaskMapper.getNowMouthTask();// 当月所有巡查任务
		List<UtUnitBaseinfo> unitList = utUnitBaseinfoMapper.selectAll();// 所有联网单位
		List<UtInspectSite> siteList = utInspectSiteMapper.selectAll();// 所有点位
//		List<UtUnitBaseinfo> unitList = new ArrayList<UtUnitBaseinfo>();
//		UtUnitBaseinfo baseInfo = new UtUnitBaseinfo();
//		baseInfo.setId(Long.parseLong("559684399608627200"));
//		baseInfo.setEmail("18434782027@163.com");
//		unitList.add(baseInfo);
		outData = addInspectUnitMsg(siteList, userList, taskList, unitList);
		if (Util.isNotEmpty(outData)) {
			for (GenerateNetWorkOutData unitData : outData) {
				Date dt = new Date();
				String fileName = new Long(dt.getTime()).toString();
				File file = new File("E:\\" + fileName + ".xls");
				file.createNewFile();
				FileOutputStream fileOut = new FileOutputStream(file);
				String[] header = new String[]{"单位编号", "单位名称", "巡查人员", "计划名称", "计划时间", "位置", "处理状态", "处理时间"};
				FrontMaintenanceInData inData = new FrontMaintenanceInData();
				inData.setStartDate(getMouthDay(0, 1));
				inData.setEndDate(getMouthDay(1, 0));
				inData.setUnitId(unitData.getUnitId().toString());
				List<FrontMaintenanceTaskOutData> list = utInspectTaskdetialMapper.getInspectTaskList(inData);
				List<MouthInspectTaskOutData> exportDatas = new ArrayList<>();
				for (FrontMaintenanceTaskOutData frontMaintenanceTaskOutData : list) {
					MouthInspectTaskOutData data = new MouthInspectTaskOutData();
					BeanUtils.copyProperties(frontMaintenanceTaskOutData, data);
					exportDatas.add(data);
				}
				MailExcel<MouthInspectTaskOutData> export = new MailExcel<MouthInspectTaskOutData>();
				export.exportInspectExcel(fileOut, unitData, exportDatas, header);
				try {
					sendMail.testSendEmail(file, unitData.getReceviceAccount(), "巡查报告", "");
				} catch (Exception e) {
					continue;
				}
			}

		}


	}

	/**
	 * 维保报告(月度邮件)
	 *
	 * @throws Exception
	 */
	public void maintenanceReport(List<UtUnitUser> userList) throws Exception {
		List<GenerateMaintenanceOutData> outData = new ArrayList<GenerateMaintenanceOutData>();
		List<UtMaintenanceUnit> unitList = utMaintenanceUnitMapper.selectAll();// 所有维保单位
		String fristTime = getMouthDay(0, 1);
		String lastTime = getMouthDay(1, 0);
		List<UtLzRepair> repairList = utLzRepairMapper.selectNowMouthList(fristTime, lastTime, "");
		List<RepairSiteOutData> bjzjRepairList = utLzRepairMapper.getBJZJRepairList(fristTime, lastTime);//当前月份报警主机维修列表
		List<RepairSiteOutData> rtuRepairList = utLzRepairMapper.getRTURepairList(fristTime, lastTime);//当前月份RTU维修列表
		List<RepairSiteOutData> inspectRepairList = utLzRepairMapper.getInspectRepairList(fristTime, lastTime);//当前月份巡查维修列表
		outData = addmaintenanceUnitMsg(bjzjRepairList, rtuRepairList, inspectRepairList, userList, repairList, unitList);
		if (Util.isNotEmpty(outData)) {
			for (GenerateMaintenanceOutData unitData : outData) {
				Date dt = new Date();
				String fileName = new Long(dt.getTime()).toString();
				File file = new File("E:\\" + fileName + ".xls");
				file.createNewFile();
				FileOutputStream fileOut = new FileOutputStream(file);
				String[] header = new String[]{"单位编号", "单位名称", "维保人员", "维保内容", "位置", "处理状态", "执行时间", "计划时间"};
				List<FrontMaintenanceExport> exportDatas = new ArrayList<>();
				FrontMaintenanceInData inData = new FrontMaintenanceInData();
				inData.setStartDate(fristTime);
				inData.setEndDate(lastTime);
				inData.setUnitId(unitData.getUnitId().toString());
				List<FrontMaintenanceOutData> list = utLzRepairMapper.getRepairList(inData);
				for (FrontMaintenanceOutData frontMaintenanceOutData : list) {
					FrontMaintenanceExport data = new FrontMaintenanceExport();
					BeanUtils.copyProperties(frontMaintenanceOutData, data);
					exportDatas.add(data);
				}
				MailExcel<FrontMaintenanceExport> export = new MailExcel<FrontMaintenanceExport>();
				export.exportMaintenanceExcel(fileOut, unitData, exportDatas, header);
				try {
					sendMail.testSendEmail(file, unitData.getReceviceAccount(), "维保报告", "");
				} catch (Exception e) {
					continue;
				}
			}
		}
	}


	/**
	 * 每个单位数据封装(联网巡查)
	 *
	 * @param siteList
	 * @param userList
	 * @param taskList
	 * @param unitList
	 * @return
	 */
	public List<GenerateNetWorkOutData> addInspectUnitMsg(List<UtInspectSite> siteList, List<UtUnitUser> userList, List<UtInspectTask> taskList, List<UtUnitBaseinfo> unitList) {
		List<GenerateNetWorkOutData> outData = new ArrayList<GenerateNetWorkOutData>();
		for (UtUnitBaseinfo unit : unitList) {
			Integer inspectTaskCount = 0;
			List<UtUnitUser> userUnitList = new ArrayList<UtUnitUser>();//单位下的联网人员
			List<UtInspectTask> taskUnitList = new ArrayList<UtInspectTask>();//单位下的所有当前月的任务
			List<UtInspectSite> siteUnitList = new ArrayList<UtInspectSite>();//单位下的所有检查点位
			for (UtUnitUser user : userList) {
				if (unit.getId().toString().equals(user.getUnitid().toString())) {
					userUnitList.add(user);
				}
			}
			for (UtInspectTask task : taskList) {
				if (unit.getId().toString().equals(task.getUnitid().toString())) {
					taskUnitList.add(task);
					if (Util.isNotEmpty(task.getIsinspect())) {
						inspectTaskCount = inspectTaskCount + 1;
					}
				}
			}
			for (UtInspectSite site : siteList) {
				if (unit.getId().toString().equals(site.getUnitid().toString())) {
					siteUnitList.add(site);
				}
			}
			if (Util.isEmpty(userUnitList)) {
				continue;
			}
			if (Util.isEmpty(taskUnitList)) {
				continue;
			}
			if (Util.isEmpty(siteUnitList)) {
				continue;
			}
			GenerateNetWorkOutData unitData = new GenerateNetWorkOutData();
			unitData.setTaskCount(taskUnitList.size());
			unitData.setInspectCount(inspectTaskCount);
			unitData.setInspectPersonCount(userUnitList.size());
			unitData.setUnitName(unit.getUnitname());
			unitData.setUnitAddress(unit.getUnitaddress());
			unitData.setUnitChargePerson(unit.getSafemanagername());
			unitData.setPersonPhone(unit.getPhone());
			unitData.setUnitId(unit.getId());
			unitData.setReceviceAccount(unit.getEmail());
			if (taskUnitList.size() != 0) {
				String rate = df.format(((float) inspectTaskCount / taskUnitList.size()) * 100) + "%";
				unitData.setCompletionRate(rate);
			}
			unitData = addInspectUnitUserData(userUnitList, taskUnitList, unitData);
			outData.add(addInspectUnitSiteData(taskUnitList, siteUnitList, userUnitList, unitData));
		}
		return outData;
	}


	/**
	 * 单位下巡查人员数据封装
	 *
	 * @param userList 人员列表(单位)
	 * @param taskList 任务列表(单位)
	 * @param unit     单位信息
	 * @return
	 */
	private GenerateNetWorkOutData addInspectUnitUserData(List<UtUnitUser> userList, List<UtInspectTask> taskList, GenerateNetWorkOutData unitData) {
		List<InspectUserOutData> userDataList = new ArrayList<InspectUserOutData>();// 单位下人员巡查信息
		List<UtUnitUser> unitUserList = new ArrayList<UtUnitUser>();
		for (UtUnitUser user : userList) {
			if (unitData.getUnitId().toString().equals(user.getUnitid().toString())) {
				unitUserList.add(user);
			}
		}
		for (UtUnitUser user : unitUserList) {
			Integer userTaskCount = 0;// 该巡查人员任务数量
			Integer userInspectCount = 0;// 该巡查人员已巡查任务数量
			Boolean flag = false;
			for (UtInspectTask task : taskList) {// 单位人员对应任务
				if (user.getId().toString().equals(task.getInspectuserid())) {
					userTaskCount = userTaskCount + 1;
					flag = true;
					if (Util.isNotEmpty(task.getIsinspect())) {
						userInspectCount = userInspectCount + 1;
					}
				}
			}
			if (flag) {
				InspectUserOutData userData = new InspectUserOutData();
				userData.setUserName(user.getUsername());
				userData.setInspectCount(userInspectCount);
				userData.setTaskCount(userTaskCount);
				userData.setUserName(user.getUsername());
				if (userTaskCount != 0) {
					String rate = df.format(((float) userInspectCount / userTaskCount) * 100) + "%";
					userData.setCompletionRate(rate);
				}
				userDataList.add(userData);
			}
		}
		unitData.setUserData(userDataList);
		return unitData;
	}

	/**
	 * 单位下点位数据封装
	 *
	 * @param taskList
	 * @param siteList
	 * @param userList
	 * @param inData
	 * @return
	 */
	private GenerateNetWorkOutData addInspectUnitSiteData(List<UtInspectTask> taskList, List<UtInspectSite> siteList,
														  List<UtUnitUser> userList, GenerateNetWorkOutData inData) {
		List<InspectSiteOutData> siteDataList = new ArrayList<InspectSiteOutData>();
		for (UtInspectSite site : siteList) {
			Integer taskCount = 0;// 点位巡查总次数
			Integer inspectCount = 0;// 已巡查次数
			Integer execptionCount = 0;// 异常数
			Boolean flag = false;
			Set<String> userIDs = new HashSet<String>();
			for (UtInspectTask task : taskList) {
				if (task.getSiteid().toString().equals(site.getId().toString())) {
					flag = true;
					userIDs.add(String.valueOf(task.getInspectuserid()));
					taskCount = taskCount + 1;
					if (Util.isNotEmpty(task.getIsok())) {
						execptionCount = execptionCount + 1;
					}
					if (Util.isNotEmpty(task.getIsinspect())) {
						inspectCount = inspectCount + 1;
					}
				}
			}
			if (flag) {
				String userName = "";
				for (String userid : userIDs) {
					for (UtUnitUser user : userList) {
						if (user.getId().toString().equals(userid)) {
							userName = userName + "," + user.getUsername();
						}
					}
				}
				InspectSiteOutData siteData = new InspectSiteOutData();
				siteData.setExpectionCount(execptionCount);
				siteData.setInspectCount(inspectCount);
				siteData.setTaskCount(taskCount);
				siteData.setSiteName(site.getSitename());
				if (Util.isNotEmpty(userName)) {
					siteData.setInspectUserName(userName.substring(1, userName.length()));
				}
				if (taskCount != 0) {
					String rate = df.format(((float) execptionCount / taskCount) * 100) + "%";
					siteData.setExpectionRate(rate);
				}
				siteDataList.add(siteData);
			}
		}
		inData.setSiteData(siteDataList);
		return inData;
	}

	/**
	 * 每个单位数据封装(维保)
	 *
	 * @param siteList
	 * @param userList
	 * @param taskList
	 * @param unitList
	 * @return
	 */
	public List<GenerateMaintenanceOutData> addmaintenanceUnitMsg(List<RepairSiteOutData> bjzjRepairList, List<RepairSiteOutData> rtuRepairList,
																  List<RepairSiteOutData> inspectRepairList, List<UtUnitUser> userList, List<UtLzRepair> repairList, List<UtMaintenanceUnit> unitList) throws Exception {
		List<GenerateMaintenanceOutData> outData = new ArrayList<GenerateMaintenanceOutData>();
		List<RepairSiteOutData> bjzjRepairUnitList = new ArrayList<RepairSiteOutData>();//当前月份报警主机维修列表
		List<RepairSiteOutData> rtuRepairUnitList = new ArrayList<RepairSiteOutData>();//当前月份RTU维修列表
		List<RepairSiteOutData> inspectRepairUnitList = new ArrayList<RepairSiteOutData>();//当前月份巡查维修列表
		List<UtLzRepair> repairUnitList = new ArrayList<UtLzRepair>();
		List<UtUnitUser> userUnitList = new ArrayList<UtUnitUser>();
		Integer maintenanceCount = 0;
		Integer maintenancePersonCount = 0;
		for (UtMaintenanceUnit unit : unitList) {
			GenerateMaintenanceOutData unitData = new GenerateMaintenanceOutData();
			for (RepairSiteOutData bjzjRepair : bjzjRepairList) {
				if (bjzjRepair.getUnitID().equals(unit.getId().toString())) {
					bjzjRepairUnitList.add(bjzjRepair);
				}
			}
			for (RepairSiteOutData rtuRepair : rtuRepairList) {
				if (rtuRepair.getUnitID().equals(unit.getId().toString())) {
					rtuRepairUnitList.add(rtuRepair);
				}
			}
			for (RepairSiteOutData inspectRepair : inspectRepairList) {
				if (inspectRepair.getUnitID().equals(unit.getId().toString())) {
					inspectRepairUnitList.add(inspectRepair);
				}
			}
			for (UtLzRepair repair : repairList) {
				if (repair.getUnitid().toString().equals(unit.getId().toString()) && 3 == repair.getDealstatus()) {
					repairUnitList.add(repair);
				}
			}
			for (UtUnitUser user : userList) {
				if (user.getUnitid().toString().equals(unit.getId().toString())) {
					maintenancePersonCount = maintenancePersonCount + 1;
					userUnitList.add(user);
				}
			}
			unitData.setMaintenancePersonCount(maintenancePersonCount);
			unitData.setUnitName(unit.getUnitname());
			unitData.setUnitAddress(unit.getAddress());
			unitData.setUnitChargePerson(unit.getContacts());
			unitData.setPersonPhone(unit.getTelephone());
			unitData.setUnitId(unit.getId());
			unitData.setReceviceAccount(unit.getEmail());
			List<MaintenanceSiteOutData> siteOutDataList = getWBSiteList(bjzjRepairUnitList, rtuRepairUnitList, inspectRepairUnitList);
			for (MaintenanceSiteOutData data : siteOutDataList) {
				maintenanceCount = data.getMaintenanceCount() + maintenanceCount;
			}
			unitData.setMaintenanceCount(maintenanceCount);
			unitData.setSiteData(siteOutDataList);
//			List<RepairSiteOutData> unitSiteList = new ArrayList<RepairSiteOutData>();
//			unitSiteList.addAll(bjzjRepairUnitList);
//			unitSiteList.addAll(rtuRepairUnitList);
//			unitSiteList.addAll(inspectRepairUnitList);
			unitData.setUserData(getWBUserList(userUnitList, repairUnitList));
			outData.add(unitData);
		}
		return outData;
	}

	/**
	 * 获取单位维保单位人员列表
	 *
	 * @param userUnitList
	 * @param repairUnitList
	 * @return
	 */
	public List<MaintenanceUserOutData> getWBUserList(List<UtUnitUser> userUnitList, List<UtLzRepair> repairUnitList) {
		List<MaintenanceUserOutData> outData = new ArrayList<MaintenanceUserOutData>();
		for (UtUnitUser user : userUnitList) {
			MaintenanceUserOutData userData = new MaintenanceUserOutData();
			Integer wbCount = 0;
			for (UtLzRepair repair : repairUnitList) {
				if (user.getUsername().equals(repair.getWbclr())) {
					wbCount = wbCount + 1;
				}
			}
			userData.setMaintenanceCount(wbCount);
			userData.setUserName(user.getUsername());
			outData.add(userData);
		}

		return outData;
	}

	/**
	 * 获取单位每个点位维保信息
	 *
	 * @param bjzjRepairUnitList
	 * @param rtuRepairUnitList
	 * @param inspectRepairUnitList
	 * @return
	 * @throws Exception
	 */
	public List<MaintenanceSiteOutData> getWBSiteList(List<RepairSiteOutData> bjzjRepairUnitList, List<RepairSiteOutData> rtuRepairUnitList,
													  List<RepairSiteOutData> inspectRepairUnitList) throws Exception {
		List<MaintenanceSiteOutData> siteList = new ArrayList<MaintenanceSiteOutData>();
		for (RepairSiteOutData bjzjRepair : bjzjRepairUnitList) {
			MaintenanceSiteOutData siteData = new MaintenanceSiteOutData();
			StringBuilder builder = new StringBuilder();
			List<String> bjzjUserList = utLzRepairMapper.getBJZJSiteUserList(bjzjRepair.getSiteID());
			for (String site : bjzjUserList) {
				builder.append(site).append(",");
			}
			siteData.setMaintenanceCount(bjzjRepair.getCount());
			siteData.setMaintenanceUserName(builder.toString().substring(0, builder.length() - 1));
			siteData.setSiteName(bjzjRepair.getSiteName());
			siteList.add(siteData);
		}
		for (RepairSiteOutData rtuRepair : rtuRepairUnitList) {
			MaintenanceSiteOutData siteData = new MaintenanceSiteOutData();
			StringBuilder builder = new StringBuilder();
			List<String> rtuUserList = utLzRepairMapper.getRTUSiteUserList(rtuRepair.getSiteID());
			for (String site : rtuUserList) {
				builder.append(site).append(",");
			}
			siteData.setMaintenanceCount(rtuRepair.getCount());
			siteData.setMaintenanceUserName(builder.toString().substring(0, builder.length() - 1));
			siteData.setSiteName(rtuRepair.getSiteName());
			siteList.add(siteData);
		}
		for (RepairSiteOutData inspectRepair : inspectRepairUnitList) {
			MaintenanceSiteOutData siteData = new MaintenanceSiteOutData();
			StringBuilder builder = new StringBuilder();
			List<String> inspectUserList = utLzRepairMapper.getInspectSiteUserList(inspectRepair.getSiteID());
			for (String site : inspectUserList) {
				builder.append(site).append(",");
			}
			siteData.setMaintenanceCount(inspectRepair.getCount());
			siteData.setMaintenanceUserName(builder.toString().substring(0, builder.length() - 1));
			siteData.setSiteName(inspectRepair.getSiteName());
			siteList.add(siteData);
		}
		return siteList;
	}

	/**
	 * 获取当前月份第一天和最后一天
	 *
	 * @param a （a=0,b=1  第一天）
	 * @param b （a=1,b=0  最后一天）
	 * @return
	 */
	public String getMouthDay(int a, int b) {
		// 获取当月第一天和最后一天  
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		String day;
		Calendar cale = Calendar.getInstance();
		//获取当前时间的前一个小时
		cale.set(Calendar.HOUR_OF_DAY, cale.get(Calendar.HOUR_OF_DAY) - 1);
		// 获取前月的第一天
		cale.add(Calendar.MONTH, a);
		cale.set(Calendar.DAY_OF_MONTH, b);
		day = format.format(cale.getTime());
		return day;
	}

}
