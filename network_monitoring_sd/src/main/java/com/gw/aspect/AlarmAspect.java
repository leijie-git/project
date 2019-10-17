package com.gw.aspect;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.gw.aspect.data.OriginalAlarmVo;
import com.gw.aspect.data.OriginalRtuFaultVo;
import com.gw.aspect.util.DataTransferUtil;
import com.gw.aspect.util.RpcTokenUtil;
import com.gw.front.couplet.data.FrontCoupletUnitInfo;
import com.gw.mapper.*;
import com.gw.mapper.entity.UtLzBjzjalarm;
import com.gw.mapper.entity.UtLzFireequipmentalarm;
import com.gw.mapper.entity.UtRpcDefination;
import com.gw.util.HttpClientUtil;
import com.gw.util.HttpJson;
import com.gw.util.Util;
import org.apache.commons.lang.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Aspect
@Component
@ComponentScan
public class AlarmAspect {

	private final static Logger logger = LoggerFactory.getLogger(AlarmAspect.class);
	private static List<Long> unitIds;
	@Value("${TL.unitParam.defualtAccountId}")
	private String userId;
	@Value("${TL.outData.url}")
	private String uploadUrl;
	@Value("${TL.outData.auth}")
	private String auth;
	@Resource
	private UtUnitNetdeviceMapper unitNetdeviceMapper;
	@Resource
	private UtLzBjzjalarmMapper bjzjalarmMapper;
	@Resource
	private UtLzFireequipmentalarmMapper fireequipmentalarmMapper;
	@Resource
	private UtEqEquipmentdetialgwMapper equipmentdetialgwMapper;
	@Resource
	private UtUnitBaseinfoMapper unitBaseinfoMapper;
	@Resource
	private UtRpcDefinationMapper rpcDefinationMapper;
	/**
	 * A:@Pointcut("execution(* com.aijava.springcode.service..*.*(..))")
	 * 第一个*表示匹配任意的方法返回值，..(两个点)表示零个或多个，上面的第一个..表示service包及其子包,第二个*表示所有类,第三个*表示所有方法，第二个..表示方法的任意参数个数
	 * B:@Pointcut("within(com.aijava.springcode.service.*)")
	 * within限定匹配方法的连接点,上面的就是表示匹配service包下的任意连接点
	 * C:@Pointcut("this(com.aijava.springcode.service.UserService)")
	 * this用来限定AOP代理必须是指定类型的实例，如上，指定了一个特定的实例，就是UserService
	 * D:@Pointcut("bean(userService)") bean也是非常常用的,bean可以指定IOC容器中的bean的名称
	 */
	//tk.mybatis.mapper.common.base.insert.InsertMapper.insert
	@Pointcut("execution(public * tk.mybatis.mapper.common.base.insert.InsertMapper.insert(..)) "
			+ "|| execution(public * tk.mybatis.mapper.common.base.update.UpdateByPrimaryKeyMapper.updateByPrimaryKey(..))")
	public void sendDataOut() {
	}

	@Before("sendDataOut()")
	public void doBefore(JoinPoint joinPoint) throws Exception {
		if (joinPoint.getArgs()[0] instanceof UtLzFireequipmentalarm || joinPoint.getArgs()[0] instanceof UtLzBjzjalarm) {
			logger.info("=======告警推送切点执行前:" + joinPoint.getArgs()[0].toString());
		}

//		// -----------------------------------------------API测试-----------------------------------------------
//		String className = joinPoint.getTarget().getClass().getName();
//		String methodName = joinPoint.getSignature().getName();
//		logger.info("=======Aspect-className:" + className + " ; Aspect-methodName:" + methodName);
//		// -----------------------------------------------API测试-----------------------------------------------
	}

	/**
	 * 根据单位ID查询推送配置信息
	 *
	 * @param unitId
	 * @return
	 */
	private List<UtRpcDefination> getRpcConfigByUnitId(long unitId, int dataType) {
		UtRpcDefination defination = new UtRpcDefination();
		defination.setUnitId(unitId);
		defination.setDataType(dataType);
		return rpcDefinationMapper.select(defination);
	}

//	private JSONObject buildBjzjParam(UtLzBjzjalarm alarm) {
//		JSONObject json = new JSONObject();
//		String deviceId = unitNetdeviceMapper.getDeviceByAlarm(alarm.getOnwercode(), alarm.getDeviceindex(), alarm.getDeviceno());
//		json.put("id",deviceId);
//		json.put("alarmId", "" + alarm.getId());
//		json.put("alarmDevicedesc",alarm.getAlarmSourcedesc()+" "+alarm.getAlarmDevicedesc()+" "+alarm.getAlarmWheredesc());
//		json.put("level",DataTransferUtil.dealStatus(alarm.getAlarmStatus()));
//		json.put("alarmStatus","1");
//		json.put("alarmTime", UtilConv.date2Str(new Date(), UtilConv.DATE_TIME_PAT_19));
//		return json;
//	}

	private void doPost(String url, String token, JSONObject json) {
		new Thread(() -> {
			try {
				logger.info(json.toString());
				HttpJson httpJson = HttpClientUtil.easemobPost(url, token, json);
				logger.info("===============上传数据至三方平台响应:" + httpJson.toString());
			} catch (Exception e) {
				logger.error(e.getMessage());
			}
		}).start();
	}

	private void doGet(String url, String token) {
		new Thread(() -> {
			try {
				logger.info(url);
				HttpClientUtil.easemobGet(url, token);
			} catch (Exception e) {
				logger.error(e.getMessage());
			}
		}).start();
	}

//	private JSONObject buildRtuParam(UtLzFireequipmentalarm alarm) {
//		JSONObject json = new JSONObject();
//		UtEqEquipmentdetialgw equipmentdetial = equipmentdetialgwMapper.selectByPrimaryKey(alarm.getFireequipmentdetialid());
//		json.put("id", "" + equipmentdetial.getNetdeviceid());
//		json.put("alarmId", "" + alarm.getId());
//		json.put("alarmDevicedesc",alarm.getEquipmentname()+" "+alarm.getEquipmentdetialname());
//		json.put("level",DataTransferUtil.dealStatus(alarm.getAlarmStatus()));
//		json.put("alarmStatus","1");
//		json.put("alarmTime", UtilConv.date2Str(alarm.getAlarmtime(), UtilConv.DATE_TIME_PAT_19));
//		return json;
//	}

//	@After("sendDataOut()")
//	public void doAfter() {
//		logger.info("============执行完成Aspect");
//	}

	@AfterReturning(pointcut = "sendDataOut()", returning = "returnVal")
	public void doAfterReturning(JoinPoint joinPoint, Object returnVal) throws Exception {
		//v1.0逻辑,在配置文件中配置上传url
		if (StringUtils.isNotEmpty(uploadUrl)) {
			if (unitIds == null) {
				logger.info("===========查询需上传数据的单位ID" + unitIds);
				unitIds = new ArrayList<>();
				List<FrontCoupletUnitInfo> units = unitBaseinfoMapper.getUnits(userId, null);
				for (FrontCoupletUnitInfo unitInfo : units) {
					unitIds.add(Long.parseLong(unitInfo.getUnitId()));
				}

			}

			Object[] paramArgs = joinPoint.getArgs();
			if (paramArgs[0] instanceof UtLzFireequipmentalarm) {
				logger.info("=======切点已执行完成:" + paramArgs[0].toString());
				UtLzFireequipmentalarm alarm = (UtLzFireequipmentalarm) paramArgs[0];
				OriginalRtuFaultVo alarmVo = fireequipmentalarmMapper.getOriginalRtuFaultById(alarm.getId());
				if (unitIds.contains(alarm.getUnitid())) {
					doPost(uploadUrl, "", DataTransferUtil.rtuFaultToTelling(alarmVo));
				}
			} else if (paramArgs[0] instanceof UtLzBjzjalarm) {
				logger.info("=======切点已执行完成:" + paramArgs[0].toString());
				UtLzBjzjalarm alarm = (UtLzBjzjalarm) paramArgs[0];
				if (unitIds.contains(alarm.getUnitid())) {
					OriginalAlarmVo alarmVo = bjzjalarmMapper.getOriginalAlarmById(alarm.getId());
					doPost(uploadUrl, "", DataTransferUtil.alarmToTelling(alarmVo));
				}
			}
		}

		Object[] paramArgs = joinPoint.getArgs();
		if (paramArgs[0] instanceof UtLzBjzjalarm) {
			logger.info("=======切点已执行完成:" + paramArgs[0].toString());
			UtLzBjzjalarm alarm = (UtLzBjzjalarm) paramArgs[0];
			List<UtRpcDefination> rpcConfigs = getRpcConfigByUnitId(alarm.getUnitid(), 1);
			OriginalAlarmVo alarmVo;
			if (Util.isNotEmpty(rpcConfigs)) {
				alarmVo = bjzjalarmMapper.getOriginalAlarmById(alarm.getId());
				JSONObject json = buildBdofiReqParams(DataTransferUtil.alarmToXinJiYuan(alarmVo));
				doPost(rpcConfigs.get(0).getPushUrl(), RpcTokenUtil.getBDOFIToken(), json);
			}
			if (Util.isNotEmpty(alarm.getDealresult())) {
				rpcConfigs = getRpcConfigByUnitId(alarm.getUnitid(), 2);
				if (Util.isNotEmpty(rpcConfigs)) {
					alarmVo = bjzjalarmMapper.getOriginalAlarmById(alarm.getId());
					JSONObject json = buildBdofiReqParams(DataTransferUtil.alarmConfirmToXinJiYuan(alarmVo));
					doPost(rpcConfigs.get(0).getPushUrl(), RpcTokenUtil.getBDOFIToken(), json);
				}
			}
			if (Util.isNotEmpty(alarm.getAlarmStatus()) && alarm.getAlarmStatus() == 2) {
				rpcConfigs = getRpcConfigByUnitId(alarm.getUnitid(), 3);
				if (Util.isNotEmpty(rpcConfigs)) {
					alarmVo = bjzjalarmMapper.getOriginalAlarmById(alarm.getId());
					JSONObject json = buildBdofiReqParams(DataTransferUtil.rtuFaultToXinJiYuan(alarmVo));
					doPost(rpcConfigs.get(0).getPushUrl(), RpcTokenUtil.getBDOFIToken(), json);
				}
			}
		}
	}

	private JSONObject buildBdofiReqParams(JSONObject postJson) throws Exception {
		JSONObject json = new JSONObject();
		json.put("token", RpcTokenUtil.getBDOFIToken());
		json.put("type", "0");
		JSONArray array = new JSONArray();
		array.add(postJson);
		json.put("data", array);
		return json;
	}

}
