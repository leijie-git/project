package com.gw.aspect;

import org.aspectj.lang.annotation.Aspect;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

@Aspect
@Component
@ComponentScan
public class DealAlarmAspect {
//	private final static Logger logger = LoggerFactory.getLogger(DealAlarmAspect.class);
//	private static List<Long> unitIds;
//	@Value("${TL.unitParam.defualtAccountId}")
//	private String userId;
//	@Value("${TL.outData.url}")
//	private String uploadUrl;
//	@Value("${TL.outData.auth}")
//	private String auth;
//	@Resource
//	private UtUnitBaseinfoMapper unitBaseinfoMapper;
//	@Resource
//	private UtLzBjzjalarmMapper utLzBjzjalarmMapper;
//	@Resource
//	private UtLzFireequipmentalarmMapper utLzFireequipmentalarmMapper;
//	@Resource
//	private UtEqEquipmentdetialgwMapper equipmentdetialgwMapper;
//	@Resource
//	private UtUnitNetdeviceMapper unitNetdeviceMapper;
//    @Resource
//    private UtRpcDefinationMapper rpcDefinationMapper;
//
//	@Pointcut("execution(public * com.gw.front.couplet.controller.FrontCoupletController.dealRTUAlarmBatch(..))"
//			+ "|| execution(public * com.gw.front.couplet.controller.FrontCoupletController.dealAlarm(..))"
//			+ "|| execution(public * com.gw.front.couplet.controller.FrontCoupletController.dealAlarmBatch(..))"
//			+ "|| execution(public * com.gw.front.couplet.controller.FrontCoupletController.dealRTUAlarm(..))"
//			+ "|| execution(public * com.gw.wechat.service.impl.PhoneServiceImpl.dealAlarm(..))"
//			+ "|| execution(public * com.gw.wechat.service.impl.PhoneServiceImpl.dealRTUAlarm(..))")
//	public void sendDataOut() {
//	}
//
//	@Before("sendDataOut()")
//	public void doBefore(JoinPoint joinPoint) throws Exception {
//        Object[] paramArgs = joinPoint.getArgs();
//        String methodName = joinPoint.getSignature().getName();
//        // ------------API测试-------------
//        logger.info("=====Aspect-className:" + joinPoint.getTarget().getClass().getName());
//        logger.info("=====Aspect-methodInParams:" + joinPoint.getArgs().length);
//        // ------------API测试-------------
//		if(StringUtils.isEmpty(uploadUrl)){
//            if (unitIds == null) {
//                unitIds = new ArrayList<>();
//                List<FrontCoupletUnitInfo> units = unitBaseinfoMapper.getUnits(userId, null);
//                for (FrontCoupletUnitInfo unitInfo : units) {
//                    unitIds.add(Long.parseLong(unitInfo.getUnitId()));
//                }
//
//            }
//            //单一处理
//            if (paramArgs.length == 2 && paramArgs[1] instanceof FrontCoupletAlarmInfoOutData) {
//                FrontCoupletAlarmInfoOutData data = (FrontCoupletAlarmInfoOutData) paramArgs[1];
//                if ("dealRTUAlarmBatch".equals(methodName)) {
//                    //rtu报警信息
//                    sendRtuDealAlarm(Long.parseLong(data.getId()));
//                } else if ("dealAlarm".equals(methodName)) {
//                    //报警主机报警信息
//                    sendBjzjDealAlarm(Long.valueOf(data.getId()));
//                }
//            } else if (paramArgs.length == 3) {
//                if ("dealAlarmBatch".equals(methodName)) {
//                    Example example = new Example(UtLzBjzjalarm.class);
//                    example.createCriteria().andEqualTo("alarmStatus", paramArgs[2]).andEqualTo("isdeal", 0);
//                    List<UtLzBjzjalarm> utLzBjzjalarms = utLzBjzjalarmMapper.selectByExample(example);
//                    for (UtLzBjzjalarm alarm : utLzBjzjalarms) {
//                        if (unitIds.contains(alarm.getUnitid())) {
//                            doPost(uploadUrl, "", buildBjzjParam(alarm));
//                        }
//                    }
//                } else if ("dealRTUAlarm".equals(methodName)) {
//                    Example example = new Example(UtLzBjzjalarm.class);
//                    example.createCriteria().andEqualTo("alarmStatus", paramArgs[2]).andEqualTo("isdeal", 0);
//                    List<UtLzFireequipmentalarm> utLzFireequipmentalarms = utLzFireequipmentalarmMapper.selectByExample(example);
//                    for (UtLzFireequipmentalarm alarm : utLzFireequipmentalarms) {
//                        if (unitIds.contains(alarm.getUnitid())) {
//                            doPost(uploadUrl, "", buildRtuParam(alarm));
//                        }
//                    }
//                }
//            } else if (paramArgs.length == 1 && paramArgs[0] instanceof PhoneAlarmInfoIndata) {
//                PhoneAlarmInfoIndata data = (PhoneAlarmInfoIndata) paramArgs[0];
//                if ("dealRTUAlarm".equals(methodName)) {
//                    sendRtuDealAlarm(Long.parseLong(data.getId()));
//                } else if ("dealAlarm".equals(methodName)) {
//                    sendBjzjDealAlarm(Long.parseLong(data.getId()));
//                }
//            }
//        }
//        //通用处理
//		if(paramArgs.length==2 && paramArgs[1]instanceof FrontCoupletAlarmInfoOutData){
//			FrontCoupletAlarmInfoOutData data = (FrontCoupletAlarmInfoOutData)paramArgs[1];
//            if ("dealAlarm".equals(methodName)) {
//				//报警主机报警信息
//                OriginalAlarmVo alarmVo = utLzBjzjalarmMapper.getOriginalAlarmById(Long.valueOf(data.getId()));
//                List<UtRpcDefination> rpcConfigs = getRpcConfigByUnitId(alarmVo.getUnitid(), 1);
//                if (Util.isNotEmpty(rpcConfigs)) {
//                    JSONObject jsonObject = DataTransferUtil.alarmToXinJiYuan(alarmVo);
//                    doPost(rpcConfigs.get(0).getPushUrl(), RpcTokenUtil.getBDOFIToken(), jsonObject);
//                }
//            }
//		}else if(paramArgs.length==3){
//			if("dealAlarmBatch".equals(methodName)){
//				Example example = new Example(UtLzBjzjalarm.class);
//				example.createCriteria().andEqualTo("alarmStatus", paramArgs[2]).andEqualTo("isdeal", 0);
//				List<UtLzBjzjalarm> utLzBjzjalarms = utLzBjzjalarmMapper.selectByExample(example);
//				for (UtLzBjzjalarm alarm :utLzBjzjalarms ) {
//					if (unitIds.contains(alarm.getUnitid())) {
//                        doPost(uploadUrl, "", buildBjzjParam(alarm));
//					}
//				}
//			}
//		}else if(paramArgs.length==1 && paramArgs[0] instanceof PhoneAlarmInfoIndata){
//			PhoneAlarmInfoIndata data = (PhoneAlarmInfoIndata) paramArgs[0];
//            if ("dealAlarm".equals(methodName)) {
//				sendBjzjDealAlarm(Long.parseLong(data.getId()));
//			}
//		}
//
//    }
//
////	@After("sendDataOut()")
////	public void doAfter() {
////		logger.info("============执行后拦截");
////	}
////
////	@AfterReturning(pointcut = "sendDataOut()", returning = "returnVal")
////	public void doAfterReturning(JoinPoint joinPoint, Object returnVal) {
////		logger.info("============返回后拦截");
////	}
//
//    /**
//     * 根据单位ID查询推送配置信息
//     *
//     * @param unitId
//     * @return
//     */
//    private List<UtRpcDefination> getRpcConfigByUnitId(long unitId, int dataType) {
//        UtRpcDefination defination = new UtRpcDefination();
//        defination.setUnitId(unitId);
//        defination.setDataType(dataType);
//        return rpcDefinationMapper.select(defination);
//    }
//	private void sendRtuDealAlarm(long alarmId){
//		UtLzFireequipmentalarm alarm = utLzFireequipmentalarmMapper.selectByPrimaryKey(alarmId);
//		if (unitIds.contains(alarm.getUnitid())) {
//            doPost(uploadUrl, "", buildRtuParam(alarm));
//		}
//	}
//	private void sendBjzjDealAlarm(long alarmId){
//		UtLzBjzjalarm alarm = utLzBjzjalarmMapper.selectByPrimaryKey(alarmId);
//		if (unitIds.contains(alarm.getUnitid())) {
//            doPost(uploadUrl, "", buildBjzjParam(alarm));
//		}
//	}
//
//	private JSONObject buildBjzjParam(UtLzBjzjalarm alarm) {
//		JSONObject json = new JSONObject();
//		String deviceId = unitNetdeviceMapper.getDeviceByAlarm(alarm.getOnwercode(), alarm.getDeviceindex(), alarm.getDeviceno());
//		json.put("id",deviceId);
//		json.put("alarmId",alarm.getId().toString());
//		json.put("alarmDevicedesc",alarm.getAlarmSourcedesc()+" "+alarm.getAlarmDevicedesc()+" "+alarm.getAlarmWheredesc());
//		json.put("alarmStatus","0");
//		json.put("alarmTime",UtilConv.date2Str(new Date(), UtilConv.DATE_TIME_PAT_19));
//		return json;
//	}
//
//	private JSONObject buildRtuParam(UtLzFireequipmentalarm alarm) {
//		JSONObject json=new JSONObject();
//		UtEqEquipmentdetialgw equipmentdetial = equipmentdetialgwMapper.selectByPrimaryKey(alarm.getFireequipmentdetialid());
//		json.put("id",equipmentdetial.getNetdeviceid().toString());
//		json.put("alarmId",alarm.getId().toString());
//		json.put("alarmDevicedesc",alarm.getEquipmentname()+" "+alarm.getEquipmentdetialname());
//		json.put("alarmStatus","0");
//		json.put("alarmTime",UtilConv.date2Str(new Date(),UtilConv.DATE_TIME_PAT_19));
//		return json;
//	}
//
//    private void doPost(String url, String token, JSONObject json) {
//        new Thread(() -> {
//            try {
//                logger.info(json.toString());
//                HttpJson httpJson = HttpClientUtil.easemobPost(url, token, json);
//                logger.info("===============上传数据至三方平台响应:" + httpJson.toString());
//            } catch (Exception e) {
//                logger.error(e.getMessage());
//            }
//        }).start();
//    }
}
