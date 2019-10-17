package com.gw.common;

import com.alibaba.fastjson.JSONObject;
import com.gw.alarm.data.EmployeeForSendMsgData;
import com.gw.alarm.data.TemplateMessage;
import com.gw.alarm.service.AlarmInfoService;
import com.gw.apppush.PushMsgManage;
import com.gw.device.service.ExtinguisherService;
import com.gw.front.history.data.FrontHisSDDeviceStatusOutByIdData;
import com.gw.front.index.data.FrontUnitInfoStatOutData;
import com.gw.front.unit.data.FrontUnitExtinguisherOutData;
import com.gw.generatereport.RemindExecutorIDAndLiableUserData;
import com.gw.mapper.*;
import com.gw.mapper.entity.*;
import com.gw.systemManager.data.NotifyInData;
import com.gw.systemManager.data.SysPropertiesOutData;
import com.gw.systemManager.service.NotifyService;
import com.gw.systemManager.service.PropertiesManageService;
import com.gw.util.PushMessage;
import com.gw.util.Util;
import com.gw.util.UtilConst;
import com.gw.util.UtilConv;
import com.gw.webSocket.AlarmWebSocket;
import org.apache.commons.lang.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.*;

@Component
public class CommonTimeTask {

	private static final Logger LOGGER = LoggerFactory.getLogger(CommonTimeTask.class);
	@Resource
	private UtLzBjzjalarmMapper utLzBjzjalarmMapper;
	@Resource
	private UtLzBjzjalarmHistoryMapper utLzBjzjalarmHistoryMapper;
	@Resource
	private UtUnitNetdeviceMapper utUnitNetdeviceMapper;
	@Resource
	private UtDevicestatusStatMapper utDevicestatusStatMapper;
	@Resource
	private UtUnitBaseinfoMapper utUnitBaseinfoMapper;
	@Resource
	private UtBaseUserreunitMapper userreunitMapper;
	@Resource
	private SnowflakeIdWorker snowflakeIdWorker;
	@Resource
	private UtUnitUserMapper utUnitUserMapper;
	@Resource
	private UtHdSiterwellMapper utHdSiterwellMapper;
	@Resource
	private UtLzRepairMapper utLzRepairMapper;
	@Resource
	private PropertiesManageService propertiesManageService;
	@Resource
	private ExtinguisherService extinguisherService;
	@Resource
	private AlarmInfoService alarmInfoService;
	@Resource
	private NotifyService notifyService;
	@Resource
	private UtLzFireequipmentalarmMapper utLzFireequipmentalarmMapper;
	@Resource
	private UtLzFireequipmentalarmHistoryMapper utLzFireequipmentalarmHistoryMapper;
	@Value("${raw.data.database}")
	private String database;
	@Value("${server_expire_time}")
	private String expireTime;
//	@Value("${server_time_out_phone}")
//	private String phone;
//	@Value("${short.message.api.key}")
//	private String apiKey;
//	@Value("${short.message.api.secret}")
//	private String apiSecret;
//	@Value("${sign.name}")
//	private String signName;

	private Date signDate = null;

	/**
	 * 统计单位设备在离线状态（每两个小时获取一次）
	 *
	 * @throws Exception
	 */
	@Scheduled(cron = "0 0 0/2 * * ?")
	public void statDeviceStatus() throws Exception {
		Date date = new Date();
		List<FrontUnitInfoStatOutData> list = utUnitNetdeviceMapper.getDeviceStatusByUnit(database);
		for (FrontUnitInfoStatOutData frontUnitInfoStatOutData : list) {
			UtDevicestatusStat devicestatusStat = new UtDevicestatusStat();
			devicestatusStat.setCreateDate(date);
			devicestatusStat.setId(snowflakeIdWorker.nextId());
			devicestatusStat.setOnline(frontUnitInfoStatOutData.getOnlineDeviceCount());
			devicestatusStat.setOutline(frontUnitInfoStatOutData.getOfflineDeviceCount());
			devicestatusStat.setRemark("");
			devicestatusStat.setUnitId(frontUnitInfoStatOutData.getUnitId());
			utDevicestatusStatMapper.insert(devicestatusStat);
		}
	}

	/**
	 * 统计单位设备在离线状态同步更新蓝版设备表状态（每两个小时获取一次）
	 *
	 * @throws Exception
	 */
	@Scheduled(cron = "0 */10 * * * ? ")
	public void getSDDeviceStatusById() throws Exception {
		List<FrontHisSDDeviceStatusOutByIdData> list = utUnitNetdeviceMapper.getSDDeviceStatusById(database);
		for (FrontHisSDDeviceStatusOutByIdData outData : list) {
			if (Util.isEmpty(outData.getNetDeviceStatus())) {
				outData.setNetDeviceStatus(1);
			}
			if ("在线".equals(outData.getDeviceStatus())) {
				if (outData.getNetDeviceStatus() != 1) {
					outData.setStatus(1);
					webSendNetdeviceMsg(outData);
					UtUnitNetdevice utUnitNetdevice = new UtUnitNetdevice();
					utUnitNetdevice.setId(Long.valueOf(outData.getId()));
					utUnitNetdevice.setDeviceStatus(1);
					utUnitNetdeviceMapper.updateByPrimaryKeySelective(utUnitNetdevice);
				}
			} else {
				if (outData.getNetDeviceStatus() != 0) {
					outData.setStatus(0);
					webSendNetdeviceMsg(outData);
					UtUnitNetdevice utUnitNetdevice = new UtUnitNetdevice();
					utUnitNetdevice.setId(Long.valueOf(outData.getId()));
					utUnitNetdevice.setDeviceStatus(0);
					utUnitNetdeviceMapper.updateByPrimaryKeySelective(utUnitNetdevice);
				}
			}
		}
	}

	/**
	 * 前台页面推送设备在离线接口
	 */
	private void webSendNetdeviceMsg(FrontHisSDDeviceStatusOutByIdData outData) throws Exception {
		Example example = new Example(UtBaseUserreunit.class);
		example.createCriteria().andEqualTo("unitId", outData.getUnitID());
		List<UtBaseUserreunit> userReUnits = userreunitMapper.selectByExample(example);
		LOGGER.info("=========webSendMsg:在离线涉及的用户个数" + userReUnits.size());
		StringBuffer sb = new StringBuffer();
		for (UtBaseUserreunit userUnit : userReUnits) {
			sb.append(userUnit.getUserId() + ",");
		}
		String userIds = sb.toString();
		Map<AlarmWebSocket, String> map = AlarmWebSocket.map;
		if (Util.isEmpty(map)) {
			LOGGER.info("=====无登陆用户");
			return;
		}
		AlarmWebSocket webSocket;
		for (Map.Entry<AlarmWebSocket, String> entry : map.entrySet()) {
			String user = entry.getValue();
			String userId = user.substring(0, user.indexOf(","));
			if (userIds.contains(userId)) {
				webSocket = entry.getKey();
				webSocket.sendMessage(JSONObject.toJSONString(outData));
				LOGGER.info("========推送前端信息：userId:" + userId + "设备在离线信息:" + outData.toString());
			}
		}
	}

	/**
	 * 更新单位短信条数（每月清空一次）
	 *
	 * @throws Exception
	 */
	@Scheduled(cron = "0 0 0 1 * ?")
	public void clearUnitMsgCount() throws Exception {
		List<UtUnitBaseinfo> list = utUnitBaseinfoMapper.selectAll();
		for (UtUnitBaseinfo data : list) {
			data.setQuantitysent(0);
			utUnitBaseinfoMapper.updateByPrimaryKey(data);
		}
	}

	/**
	 * 账号校验
	 *
	 * @throws Exception
	 */
	@Scheduled(cron = "0 0 5 * * ?")
	public void checkAccount() throws Exception {
		SysPropertiesOutData properties = propertiesManageService.getProperties();
		Date nowDate = new Date();
		String afterMonth = UtilConv.date2Str(DateUtils.addDays(nowDate, 30), UtilConv.DATE_YYYY_MM_DD_CHN);
		String afterFif = UtilConv.date2Str(DateUtils.addDays(nowDate, 15), UtilConv.DATE_YYYY_MM_DD_CHN);
		String afterThr = UtilConv.date2Str(DateUtils.addDays(nowDate, 3), UtilConv.DATE_YYYY_MM_DD_CHN);
		String nowStr = UtilConv.date2Str(nowDate, UtilConv.DATE_YYYY_MM_DD_CHN);
		String msg = "";
		// 服务到期时间校验
		if (Util.isNotEmpty(expireTime) && Util.isNotEmpty(properties.getServerTimeOutPhone())) {
			// 一个月
			if (expireTime.equals(afterMonth)) {
				msg = properties.getSignName() + "您购买的服务还有一个月就过期了,若要继续使用请及时续费。";
			} else if (expireTime.equals(afterFif)) {
				// 15天
				msg = properties.getSignName() + "您购买的服务还有15天就过期了,若要继续使用请及时续费。";
			} else if (expireTime.equals(afterThr)) {
				// 3天
				msg = properties.getSignName() + "您购买的服务还有3天就过期了,若要继续使用请及时续费。";

			} else if (expireTime.equals(nowStr)) {
				// 1天
				msg = properties.getSignName() + "您购买的服务可使用最后一天,若要继续使用请及时续费。";

			}
			if (Util.isNotEmpty(msg)) {
				PushMessage.sendShortMessage(properties.getShortMessageApiKey(), properties.getShortMessageApiSecret(), properties.getServerTimeOutPhone(), msg);
			}

		}
		List<UtUnitUser> userList = utUnitUserMapper.selectAll();
		for (UtUnitUser utUnitUser : userList) {
			String expirytime = UtilConv.date2Str(utUnitUser.getExpirytime(), UtilConv.DATE_YYYY_MM_DD_CHN);
			String mobilephone = utUnitUser.getMobilephone();
			if (Util.isEmpty(expirytime) || Util.isEmpty(mobilephone)) {// 空 没有过期时间
				continue;
			}
			// 一个月
			if (expirytime.equals(afterMonth)) {
				msg = properties.getSignName() + "您的账号还有一个月就过期了,若要继续使用请及时联系管理员。";
				PushMessage.sendShortMessage(properties.getShortMessageApiKey(), properties.getShortMessageApiSecret(), mobilephone, msg);
				continue;
			}
			if (expirytime.equals(afterFif)) {
				// 15天
				msg = properties.getSignName() + "您的账号还有15天就过期了,若要继续使用请及时联系管理员。";
				PushMessage.sendShortMessage(properties.getShortMessageApiKey(), properties.getShortMessageApiSecret(), mobilephone, msg);
				continue;
			}
			if (expirytime.equals(afterThr)) {
				// 3天
				msg = properties.getSignName() + "您的账号还有3天就过期了,若要继续使用请及时联系管理员。";
				PushMessage.sendShortMessage(properties.getShortMessageApiKey(), properties.getShortMessageApiSecret(), mobilephone, msg);
				continue;

			}
			if (expirytime.equals(nowStr)) {
				// 1天
				msg = properties.getSignName() + "您的账号可使用最后一天,若要继续使用请及时联系管理员。";
				PushMessage.sendShortMessage(properties.getShortMessageApiKey(), properties.getShortMessageApiSecret(), mobilephone, msg);
				continue;

			}
		}
	}

	/**
	 * 更新无线设备离在线状态
	 *
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
//	@Scheduled(cron = "${scheduled.time}")
	public void updateHdSiteWellStatus() throws Exception {
		if (signDate == null) {
			return;
		}
		// 从缓存中去数据，如果取不到就查询数据库，并将查询到的数据放到缓存中
		List<UtHdSiterwell> list = (List<UtHdSiterwell>) CacheUtils.get(UtilConst.UT_HD_SITERWELL);
		if (Util.isEmpty(list)) {
			Example example = new Example(UtHdSiterwell.class);
			example.createCriteria().andEqualTo("isrelation", 1).andIsNotNull("devicecode");
			// list = utHdSiterwellMapper.getHdSiterwells();
			list = utHdSiterwellMapper.selectByExample(example);
			CacheUtils.put(UtilConst.UT_HD_SITERWELL, list);
			if (Util.isEmpty(list)) {
				return;
			}
		}
		for (UtHdSiterwell utHdSiterwell : list) {
			// lastupdate不为空
			if (Util.isNotEmpty(utHdSiterwell.getLastupate())) {
				// 取当前时间减lastupdate得到的值和心跳时间的两倍做比较
				// 毫秒值
				long diff = (new Date()).getTime() - utHdSiterwell.getLastupate().getTime();
				long minute = diff / (1000 * 60);
				if (minute > utHdSiterwell.getHeartbeat() * 2) {
					// 查询记录并更新状态
					Integer count = utHdSiterwellMapper.getSiteWellRelInfo(
							database + ".dbo.D" + utHdSiterwell.getOwnercode(), utHdSiterwell.getDeviceindex(),
							utHdSiterwell.getDeviceno(), utHdSiterwell.getDevicecode(),
							utHdSiterwell.getHeartbeat() * 2);
					if (count > 0) {
						// 如果状态不等于1，再去更新数据库
						if (Util.isNotEmpty(utHdSiterwell.getCurrentstatus())
								&& utHdSiterwell.getCurrentstatus() != 1) {
							utHdSiterwell.setCurrentstatus(1);
							utHdSiterwell.setLastupate(new Date());
							utHdSiterwellMapper.updateByPrimaryKey(utHdSiterwell);
							// 同步修改DB_FireProtection.hd_siterwell 表的状态与时间
							if (utHdSiterwell.getUsingtype() == 0) {
								utHdSiterwellMapper.updateHdSiterwellStatus(database, utHdSiterwell.getDeviceindex(),
										utHdSiterwell.getDeviceno(), utHdSiterwell.getOwnercode(),
										utHdSiterwell.getUsingtype(), utHdSiterwell.getGatewayid().toString());
							} else if (utHdSiterwell.getUsingtype() == 1) {
								utHdSiterwellMapper.updateHdSiterwellStatus(database, utHdSiterwell.getDeviceindex(),
										utHdSiterwell.getDeviceno(), utHdSiterwell.getOwnercode(),
										utHdSiterwell.getUsingtype(), utHdSiterwell.getGatewaycode());
							}
						}
					}
				}
			} else {
				// lastupdate为空，则为第一次。直接查询记录并更新状态
				Integer count = utHdSiterwellMapper.getSiteWellRelInfo(
						database + ".dbo.D" + utHdSiterwell.getOwnercode(), utHdSiterwell.getDeviceindex(),
						utHdSiterwell.getDeviceno(), utHdSiterwell.getDevicecode(), utHdSiterwell.getHeartbeat() * 2);
				if (count > 0) {
					// 如果状态不等于1，再去更新数据库
					if (Util.isNotEmpty(utHdSiterwell.getCurrentstatus()) && utHdSiterwell.getCurrentstatus() != 1) {
						utHdSiterwell.setCurrentstatus(1);
						utHdSiterwell.setLastupate(new Date());
						utHdSiterwellMapper.updateByPrimaryKey(utHdSiterwell);
						// 同步修改DB_FireProtection.hd_siterwell 表的状态与时间
						if (utHdSiterwell.getUsingtype() == 0) {
							utHdSiterwellMapper.updateHdSiterwellStatus(database, utHdSiterwell.getDeviceindex(),
									utHdSiterwell.getDeviceno(), utHdSiterwell.getOwnercode(),
									utHdSiterwell.getUsingtype(), utHdSiterwell.getGatewayid().toString());
						} else if (utHdSiterwell.getUsingtype() == 1) {
							utHdSiterwellMapper.updateHdSiterwellStatus(database, utHdSiterwell.getDeviceindex(),
									utHdSiterwell.getDeviceno(), utHdSiterwell.getOwnercode(),
									utHdSiterwell.getUsingtype(), utHdSiterwell.getGatewaycode());
						}
					}
				}
			}
		}
	}

	/**
	 * 统计单位已报销灭火器设备（每天00：10分执行）
	 *
	 * @throws Exception
	 */
	@Scheduled(cron = "0 10 0 * * ?")
	public void checkExtinguisher() {
		try {
			List<FrontUnitExtinguisherOutData> list = extinguisherService.getExpireExtinguisherList();
			for (FrontUnitExtinguisherOutData frontUnitExtinguisherOutData : list) {
				//单位Id
				String unitId = frontUnitExtinguisherOutData.getUnitId();
				//灭火器类型
				String extinguisherType = frontUnitExtinguisherOutData.getExtinguisherType();
				//灭火器报废时间
				String validityDate = frontUnitExtinguisherOutData.getValidityDate();
				//灭火器位置
				String extinguisherPosition = frontUnitExtinguisherOutData.getExtinguisherPosition();
				//备注
				String remark = frontUnitExtinguisherOutData.getRemark();
				//灭火器编号
				String extinguisherCode = frontUnitExtinguisherOutData.getExtinguisherCode();
				//药剂到期时间
				String expireDate = frontUnitExtinguisherOutData.getExpireDate();
				//灭火器即将报废
				String validityCount = frontUnitExtinguisherOutData.getValidityCount();
				//火器即将到期
				String expireCount = frontUnitExtinguisherOutData.getExpireCount();
				//灭火器已经报废
				String tovalidityCount = frontUnitExtinguisherOutData.getTovalidityCount();
				//灭火器已经到期
				String toexpireCount = frontUnitExtinguisherOutData.getToexpireCount();

				if (Util.isNotEmpty(unitId)) {
					List<EmployeeForSendMsgData> employees = utLzBjzjalarmMapper.getEmployeeByBJZJDevice(unitId);
					List<EmployeeForSendMsgData> validityCountList = new ArrayList();
					List<EmployeeForSendMsgData> expireCountList = new ArrayList();
					List<EmployeeForSendMsgData> tovalidityCountList = new ArrayList();
					List<EmployeeForSendMsgData> toexpireCountList = new ArrayList();
					if (employees != null) {
						for (EmployeeForSendMsgData user : employees) {
							user.setExtinguisherCode(extinguisherCode);
							user.setExtinguisherType(extinguisherType);
							user.setExtinguisherPosition(extinguisherPosition);
							user.setExpireDate(expireDate);
							user.setValidityDate(validityDate);
							if ("1".equals(validityCount)) {
								validityCountList.add(user);
								extinguisherService.sendExtinguisherMsg(validityCountList, false, "9", 1);
							}
							if ("1".equals(expireCount)) {
								expireCountList.add(user);
								extinguisherService.sendExtinguisherMsg(expireCountList, false, "9", 2);
							}
							if ("1".equals(tovalidityCount)) {
								tovalidityCountList.add(user);
								extinguisherService.sendExtinguisherMsg(tovalidityCountList, false, "9", 3);
							}
							if ("1".equals(toexpireCount)) {
								toexpireCountList.add(user);
								extinguisherService.sendExtinguisherMsg(toexpireCountList, false, "9", 4);
							}
						}
					}

				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 用于清理UT_LZ_BJZJAlarm表及UT_LZ_FireEquipmentAlarm表中的数据（每天00：05分执行，保留两天内的数据）
	 *
	 * @throws Exception
	 */
	@Scheduled(cron = "0 5 0 * * ?")
	public void configureTasksTo() throws Exception {

		//获取报警主机差异数据
		List<UtLzBjzjalarm> list = utLzBjzjalarmMapper.getDifference();
		List<Long> listId = utLzBjzjalarmMapper.getInsertId();

		for (UtLzBjzjalarm utLzBjzjalarm : list) {
			if (listId.contains(utLzBjzjalarm.getId())) {
				//当临时表id存在时，历史表id不存在时新增
				System.out.println("新增的id==================" + utLzBjzjalarm.getId());
				UtLzBjzjalarmHistory utLzBjzjalarmHistory = new UtLzBjzjalarmHistory();
				BeanUtils.copyProperties(utLzBjzjalarm, utLzBjzjalarmHistory);
				utLzBjzjalarmHistoryMapper.insert(utLzBjzjalarmHistory);
			} else {
				//当临时表id存在时，历史表id也存在时，说明数据不一致，此时作同步修改
				System.out.println("修改的id==================" + utLzBjzjalarm.getId());
				UtLzBjzjalarmHistory utLzBjzjalarmHistory = new UtLzBjzjalarmHistory();
				BeanUtils.copyProperties(utLzBjzjalarm, utLzBjzjalarmHistory);
				utLzBjzjalarmHistoryMapper.updateByPrimaryKey(utLzBjzjalarmHistory);
			}
		}

		//获取消防设备报警差异数据
		List<UtLzFireequipmentalarm> alarm = utLzFireequipmentalarmMapper.getDifference();
		List<Long> alarmTwo = utLzFireequipmentalarmMapper.getInsertId();
		for (UtLzFireequipmentalarm utLzFireequipmentalarm : alarm) {
			if (alarmTwo.contains(utLzFireequipmentalarm.getId())) {
				//当临时表id存在时，历史表id不存在时新增
				UtLzFireequipmentalarmHistory utLzFireequipmentalarmHistory = new UtLzFireequipmentalarmHistory();
				BeanUtils.copyProperties(utLzFireequipmentalarm, utLzFireequipmentalarmHistory);
				utLzFireequipmentalarmHistoryMapper.insert(utLzFireequipmentalarmHistory);
			} else {
				UtLzFireequipmentalarmHistory utLzFireequipmentalarmHistory = new UtLzFireequipmentalarmHistory();
				BeanUtils.copyProperties(utLzFireequipmentalarm, utLzFireequipmentalarmHistory);
				utLzFireequipmentalarmHistoryMapper.updateByPrimaryKey(utLzFireequipmentalarmHistory);
			}
		}

		//获取报警主机两天前最大的id
		String id = utLzBjzjalarmMapper.getUtLzBjzjalarmMinId();
		if (Util.isNotEmpty(id)) {
			//根据最小的id把之前的数据删除
			utLzBjzjalarmMapper.deleteUtLzBjzjalarmData(id);
		}

		//获取消防设备报警表两天前最大的id
		String idTwo = utLzFireequipmentalarmMapper.getUtLzFireequipmentalarmMinId();
		if (Util.isNotEmpty(idTwo)) {
			//根据最小的id把之前的数据删除
			utLzFireequipmentalarmMapper.deleteUtLzFireequipmentalarmData(idTwo);
		}
	}


	/**
	 * 每10s获取维保任务开始时间
	 *
	 * @return
	 */

	@Scheduled(cron = "${Scheduled.task.time}")
	public void TaskManager() throws Exception {
		Json json = new Json();
		int sum = 0;
		//创建一个 List 存储今天存在的维保任务
		List<RemindExecutorIDAndLiableUserData> listDate = new ArrayList<>();
		//获取全部维保任务
		List<RemindExecutorIDAndLiableUserData> list = utLzRepairMapper.remindExecutorIDAndLiableUser();
		//判断是否存在维保任务
		if (!list.isEmpty()) {
			//存放执行人ID
			Set<String> set = new HashSet();
			//遍历每个任务 获取已分配的
			for (RemindExecutorIDAndLiableUserData data : list) {
				if (data.getStartAsNow().equals(data.getNowTime())) {
					//将执行人ID存进set集合
					set.add(data.getExecutorID());
					listDate.add(data);
				}
			}
			//获取set的执行人
			for (String s : set) {
				for (int i = 0; i < listDate.size(); i++) {
					if (listDate.get(i).getExecutorID().equals(s)) {
						sum++;
					}
				}
				//通过执行人ID 查出单位用户对象
				UtUnitUser utUnitUser = utUnitUserMapper.selectByPrimaryKey(Long.valueOf(s));
				//APP推送维保任务
				if (Util.isNotEmpty(utUnitUser.getChannelid()) && Util.isNotEmpty(utUnitUser.getPhoneName())) {
					PushMsgManage.pushMsg(utUnitUser.getPhoneName(), "通知", "您今天有" + sum + "条待完成的维保任务" + "请您及时处理", utUnitUser.getChannelid());
					System.out.println("AAP推送" + new Date());
				}

				//PC推送维保任务
				try {
					NotifyInData notifyInData = new NotifyInData();
					//推送给谁
					notifyInData.setReceiver(utUnitUser.getId().toString());
					notifyInData.setContent("您今日有" + sum + "条维保任务" + "请您及时处理");
					notifyInData.setTitle("维保任务通知");
					//发送方
					notifyService.addNotify(Long.valueOf(1), notifyInData);
					System.out.println("PC推送" + new Date());
				} catch (Exception e) {
					e.getMessage();
				}
				//推送微信

				TemplateMessage templateMessage = new TemplateMessage();
				templateMessage.setTemplateId("7hOFqnO2h8I1AdqKh7uNdkIsHrJbLk9Dru3ES-fzLDM");

				if (Util.isNotEmpty(utUnitUser.getOpenid())) {
					templateMessage.setTouser(utUnitUser.getOpenid());
				} else {
					throw new SecurityException("该执行人没有登录该公众号");
				}

				// #FF0000
				templateMessage.setTopcolor("#4960A2");
				JSONObject jsonObject = new JSONObject();
				//巡查计划名称
				//jsonObject.put("keyword1", SendMsg.getJsonData(employeeOutData.getExtinguisherCode()));
				//巡查执行人员
				jsonObject.put("keyword2", SendMsg.getJsonData(utUnitUser.getUsername()));
				//巡查任务描述
				//jsonObject.put("keyword3", SendMsg.getJsonData(employeeOutData.getExtinguisherPosition()));
				//巡查任务时间
				//jsonObject.put("keyword4", SendMsg.getJsonData(employeeOutData.getExpireDate()));
				jsonObject.put("first", SendMsg.getJsonData("任务提醒"));
				jsonObject.put("remark", SendMsg.getJsonData("您今日有" + sum + "条维保任务，请您及时处理"));
				templateMessage.setTemplateData(jsonObject);
				PushMessage.sendTemplateMSGToUser(templateMessage, SendMsg.getToken());
				sum = 0;
			}
		}
	}


}
