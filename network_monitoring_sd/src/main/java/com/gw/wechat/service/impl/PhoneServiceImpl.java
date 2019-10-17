package com.gw.wechat.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.gw.common.SnowflakeIdWorker;
import com.gw.device.data.EqSystemOutData;
import com.gw.eqdetail.data.AnalogPortVo;
import com.gw.eqdetail.data.DigitalPortVo;
import com.gw.exception.ServiceException;
import com.gw.front.couplet.data.FrontCoupletAlarmInfoOutData;
import com.gw.front.couplet.data.FrontCoupletFireAlarmOutData;
import com.gw.front.couplet.data.FrontCoupletInData;
import com.gw.front.couplet.data.FrontCoupletUnitInfo;
import com.gw.front.couplet.data.FrontInterFaceStatusOutData;
import com.gw.front.couplet.data.FrontInterfaceAppOutData;
import com.gw.front.history.data.FrontHisSDDeviceStatusOutData;
import com.gw.front.history.data.FrontHistoryAlarmInfoOutData;
import com.gw.front.history.data.FrontHistoryInData;
import com.gw.front.index.data.FrontVideoOutData;
import com.gw.front.login.data.FrontUnitUserOutData;
import com.gw.front.lookup.data.FrontLookupInData;
import com.gw.front.lookup.data.FrontLookupLogOutData;
import com.gw.front.lookup.data.FrontLookupUnitInfoData;
import com.gw.front.maintenance.data.FrontMaintenanceFireOutData;
import com.gw.front.maintenance.data.FrontMaintenanceInData;
import com.gw.front.maintenance.data.FrontMaintenanceOutData;
import com.gw.front.maintenance.data.FrontMaintenanceStatOutData;
import com.gw.front.maintenance.data.FrontMaintenanceTaskOutData;
import com.gw.front.maintenance.service.FrontMaintenanceService;
import com.gw.front.unit.data.FrontUnitInfoOutData;
import com.gw.inspect.data.DownLoadTaskInData;
import com.gw.mapper.*;
import com.gw.mapper.entity.InspectTaskDownlog;
import com.gw.mapper.entity.SysOperationLog;
import com.gw.mapper.entity.UTInspectBaseRel;
import com.gw.mapper.entity.UtBaseSiteclassdetial;
import com.gw.mapper.entity.UtBaseUseRelation;
import com.gw.mapper.entity.UtFeedback;
import com.gw.mapper.entity.UtInspectSite;
import com.gw.mapper.entity.UtInspectTask;
import com.gw.mapper.entity.UtInspectTaskdetial;
import com.gw.mapper.entity.UtLzBjzjalarm;
import com.gw.mapper.entity.UtLzBjzjalarmHistory;
import com.gw.mapper.entity.UtLzFireequipmentalarm;
import com.gw.mapper.entity.UtLzFireequipmentalarmHistory;
import com.gw.mapper.entity.UtLzRepair;
import com.gw.mapper.entity.UtLzRepairdetial;
import com.gw.mapper.entity.UtMaintenanceLoginLog;
import com.gw.mapper.entity.UtUnitCalibration;
import com.gw.mapper.entity.UtUnitNetdevice;
import com.gw.mapper.entity.UtUnitUser;
import com.gw.systemManager.data.SysPropertiesOutData;
import com.gw.systemManager.service.PropertiesManageService;
import com.gw.thirdInterface.service.ThirdInterfaceService;
import com.gw.unit.data.NetworkingUserInData;
import com.gw.unit.data.NetworkingUserOutData;
import com.gw.util.DataConvertUtil;
import com.gw.util.HttpClientUtil;
import com.gw.util.HttpJson;
import com.gw.util.ReqApiConst;
import com.gw.util.SnsToken;
import com.gw.util.Util;
import com.gw.util.UtilConst;
import com.gw.util.UtilConv;
import com.gw.util.UtilMessage;
import com.gw.util.UtilWechat;
import com.gw.wechat.controller.WeChatController;
import com.gw.wechat.data.FrontRepairDetailOutData;
import com.gw.wechat.data.MaintenanceDealInData;
import com.gw.wechat.data.PhoneAlarmInData;
import com.gw.wechat.data.PhoneAlarmInfoIndata;
import com.gw.wechat.data.PhoneCalibrationOutData;
import com.gw.wechat.data.PhoneFeedbackInData;
import com.gw.wechat.data.PhoneSiteClassDetialBaseOutData;
import com.gw.wechat.data.PhoneTaskDetailOutData;
import com.gw.wechat.data.PhoneUploadProblem;
import com.gw.wechat.data.SiteClassDetailOutData;
import com.gw.wechat.data.TaskSheetOutData;
import com.gw.wechat.data.UpLoadAllTaskInData;
import com.gw.wechat.data.WeChatAlarmStatOutData;
import com.gw.wechat.data.WechatAlarmInfoOutData;
import com.gw.wechat.service.PhoneService;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;
import java.util.UUID;
import java.util.stream.Collectors;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

@Service
public class PhoneServiceImpl implements PhoneService {
    private Logger log = LoggerFactory.getLogger(WeChatController.class);

	@Value("${cbs.imagesPath}")
	private String mImagesPath;
	@Resource
	private SnowflakeIdWorker snowflakeIdWorker;
	@Resource
	private UtUnitUserMapper utUnitUserMapper;
	@Resource
	private UtMaintenanceLoginLogMapper utMaintenanceLoginLogMapper;
    @Resource
    private UtInspectSiteMapper inspectSiteMapper;
	@Resource
	private UtLzBjzjalarmMapper utLzBjzjalarmMapper;
@Resource
    private UtLzBjzjalarmHistoryMapper utLzBjzjalarmHistoryMapper;//	@Value("${wx.appid}")
//	private String appid;
//	@Value("${wx.secret}")
//	private String wxSecret;
//	@Value("${wx.redirect_uri}")
//	private String redirect_uri;
	@Resource
	private UtLzFireequipmentalarmMapper utLzFireequipmentalarmMapper;
	@Resource
	private UtLzFireequipmentalarmHistoryMapper utLzFireequipmentalarmHistoryMapper;
    @Resource
    private UtUnitNetdeviceMapper utUnitNetdeviceMapper;
	@Value("${raw.data.database}")
	private String database;// 源数据所在数据库
	@Resource
	private UtUnitCalibrationMapper utUnitCalibrationMapper;
	@Resource
	private UtUnitBaseinfoMapper utUnitBaseinfoMapper;
	@Resource
	private UtUnitLookupLogMapper utUnitLookupLogMapper;
	@Resource
	private UtInspectTaskMapper utInspectTaskMapper;
	@Resource
	private UtInspectTaskdetialMapper utInspectTaskdetialMapper;
	@Resource
	private UtLzRepairMapper utLzRepairMapper;
	@Resource
	private UtLzRepairdetialMapper utLzRepairdetialMapper;
	@Resource
	private UtBaseEqsystemMapper utBaseEqsystemMapper;
	@Resource
	private UtBaseUserreunitMapper utBaseUserreunitMapper;
	@Resource
	private UtFeedbackMapper utFeedbackMapper;
	@Resource
	private UtEqExtinguisherMapper utEqExtinguisherMapper;
	@Resource
	private UtBaseSiteclassdetialbaseMapper utBaseSiteclassdetialbaseMapper;
	@Resource
	private SysOperationLogMapper sysOperationLogMapper;
	@Resource
	private UtUnitEventMapper utUnitEventMapper;
	@Resource
	private UtEqEquipmentdetialgwMapper utEqEquipmentdetialgwMapper;
	@Resource
	private UtUnitVideoMapper utUnitVideoMapper;
	@Resource
	private UtUnitUserMapper unitUserMapper;
	@Resource
	private PropertiesManageService propertiesManageService;@Resource
    private UtBaseUserRelationMapper utBaseUserRelationMapper;
//	@Value("${red.server_port}")
//	private String url;// 原始数据同步服务器路径
//	@Value("${access_token}")
//	private String access_token;// 原始数据同步令牌
    @Resource
    private ThirdInterfaceService thirdInterfaceService;
    @Resource
    private FrontMaintenanceService frontMaintenanceService;
    @Value("${AX.status}")
    private Integer clearAlarm;
    @Resource
    private UtBaseSiteclassdetialMapper utBaseSiteclassdetialMapper;
    @Resource
    private InspectTaskDownlogMapper inspectTaskDownlogMapper;

    @Resource
	private UTInspectBaseRelMapper utInspectBaseRelMapper;

	@Override
    public FrontUnitUserOutData login(HttpServletRequest request, FrontUnitUserOutData inData) throws Exception {
        String password = inData.getPassword();
        String md5Hex = DigestUtils.md5Hex(password);
        Example example = new Example(UtUnitUser.class);
        example.createCriteria().andEqualTo("account", inData.getAccount()).andEqualTo("password", md5Hex)
                .andEqualTo("isdelete", "0");
        List<UtUnitUser> userList = utUnitUserMapper.selectByExample(example);
        if (Util.isEmpty(userList)) {
            throw new ServiceException(UtilConst.ERRO_ACCOUNT_PASSWORD);
        }
        String openId = inData.getOpenId();
        //清空openid除了自己
        utUnitUserMapper.updateOpenId(openId, inData.getAccount());

        UtUnitUser user = userList.get(0);
        if (Util.isNotEmpty(openId) && !openId.equals(user.getOpenid())) {
            user.setOpenid(openId);
            utUnitUserMapper.updateByPrimaryKey(user);
        }

        // 保存登录记录
        UtMaintenanceLoginLog log = new UtMaintenanceLoginLog();
        String ip = UtilConv.getIpAddr(request);
        log.setLoginIp(ip);
        log.setLoginAddr(UtilConv.baiduGetCityCode(ip));
        Date loginDate = new Date();
        log.setLoginDate(loginDate);
        log.setLoginOutDate(new Date(loginDate.getTime() + 1800000));// 默认退出时间半个小时以后
        log.setLoginName(inData.getAccount());
        long nextId = snowflakeIdWorker.nextId();
        log.setId(nextId);
        log.setDataFrom("2");// 微信登录记录
        utMaintenanceLoginLogMapper.insert(log);
        request.getSession().setAttribute("utMaintenanceLoginLog", log);

        FrontUnitUserOutData outData = new FrontUnitUserOutData();
        outData.setAccount(user.getAccount());
        outData.setUsertype(user.getUsertype() + "");
        outData.setUsername(user.getUsername());
        outData.setUserrole(user.getUserrole() + "");
        outData.setEmail(user.getEmail());
        outData.setMobilephone(user.getMobilephone());
        outData.setOpenId(user.getOpenid());
        outData.setSex(user.getSex());
        outData.setUnitid(user.getUnitid() + "");
        // outData.setUserid(user.getUserid()+"");
        outData.setId(user.getId() + "");
        outData.setPassword(password);
        request.getSession().setAttribute(UtilConst.WECHAT_USER_SESSION, outData);

        return outData;
    }

    @Override
    public String getUrl() throws Exception {
        SysPropertiesOutData properties = propertiesManageService.getProperties();
        return UtilWechat.connectOauth2Authorize(properties.getWxAppid(), properties.getWxRedirectUri(), true, "STATE", null);
    }

    @Override
    public String getOpenId(String code) throws Exception {
        SysPropertiesOutData properties = propertiesManageService.getProperties();
        SnsToken oauth2AccessToken = UtilWechat.oauth2AccessToken(properties.getWxAppid(), properties.getWxSecret(), code);
        if (oauth2AccessToken == null) {
            throw new ServiceException("oauth2AccessToken获取出错");
        }
        log.error("oauth2AccessToken:" + oauth2AccessToken.toString());
        String openid = oauth2AccessToken.getOpenid();
        log.error("openid:" + openid);
        if (openid == null) {
            throw new ServiceException("openid获取出错");
        }
        return openid;
    }

	@Override
	public List<WechatAlarmInfoOutData> getAlarmList(PhoneAlarmInData phoneAlarmInData) throws Exception {
        Example example = new Example(UtBaseUseRelation.class);
        example.createCriteria().andEqualTo(" userId", Long.valueOf(phoneAlarmInData.getUserId()));
			List<UtBaseUseRelation> userIds = utBaseUserRelationMapper.selectByExample(example);
        // 报警主机告警列表
        phoneAlarmInData.setSelectType("2");
        List<WechatAlarmInfoOutData> outData = utLzBjzjalarmMapper.getWechatAlarmList(phoneAlarmInData);
        // RTU告警信息
        List<WechatAlarmInfoOutData> alarmList = utLzFireequipmentalarmMapper.getAlarmByBuildList(phoneAlarmInData.getUserId(),
                phoneAlarmInData.getIsDeal(), phoneAlarmInData.getAlarmStatus());
        if (Util.isEmpty(userIds)) {
            // RTU告警信息
		if ("1".equals(phoneAlarmInData.getAlarmType())) {
			 alarmList = utLzFireequipmentalarmMapper.getAlarmList(phoneAlarmInData.getUserId(), phoneAlarmInData.getIsDeal(),
                        phoneAlarmInData.getAlarmStatus());
			return alarmList;
		}
		if ("100".equals(phoneAlarmInData.getAlarmType())) {// 报警系统其他
                phoneAlarmInData.setSelectType("1");
                List<WechatAlarmInfoOutData> outDataOther = utLzBjzjalarmMapper.getWechatAlarmList(phoneAlarmInData);
                if (Util.isEmpty(outDataOther)) {
                    phoneAlarmInData.setSelectType("1");
                    outDataOther = utLzBjzjalarmMapper.getWechatAlarmList(phoneAlarmInData);
                }
                for (WechatAlarmInfoOutData wechatAlarmInfoOutData : outDataOther) {
                    wechatAlarmInfoOutData.setAlarmStatus(dealStatus(wechatAlarmInfoOutData.getAlarmStatus()));
                }
                return outDataOther;
            }// 报警主机告警列表
		phoneAlarmInData.setSelectType("1");
            outData = utLzBjzjalarmMapper.getWechatAlarmList(phoneAlarmInData);
            for (WechatAlarmInfoOutData wechatAlarmInfoOutData : outData) {
                wechatAlarmInfoOutData.setAlarmStatus(dealStatus(wechatAlarmInfoOutData.getAlarmStatus()));
            }
            return outData;
        } else {
            if ("1".equals(phoneAlarmInData.getAlarmType())) {// RTU告警信息
                return alarmList;
            }
            if ("100".equals(phoneAlarmInData.getAlarmType())) {// 报警系统其他
                phoneAlarmInData.setSelectType("2");List<WechatAlarmInfoOutData> outDataOther = utLzBjzjalarmMapper.getWechatAlarmList(phoneAlarmInData);
                if (Util.isEmpty(outDataOther)) {
                    phoneAlarmInData.setSelectType("3");
                    outDataOther = utLzBjzjalarmMapper.getWechatAlarmList(phoneAlarmInData);
                } else {
                    phoneAlarmInData.setSelectType("3");
                    List<WechatAlarmInfoOutData> outDataBJZJOther = utLzBjzjalarmMapper.getWechatAlarmList(phoneAlarmInData);
                    if (Util.isNotEmpty(outDataBJZJOther)) {
                        outDataOther.addAll(outDataBJZJOther);
                        Collections.sort(outDataOther, new Comparator<WechatAlarmInfoOutData>() {
                            @Override
                            public int compare(WechatAlarmInfoOutData o1, WechatAlarmInfoOutData o2) {
                                return o2.getAlarmTime().compareTo(o1.getAlarmTime());
                            }
                        });
                    }
                }
                for (WechatAlarmInfoOutData wechatAlarmInfoOutData : outDataOther) {
                    wechatAlarmInfoOutData.setAlarmStatus(dealStatus(wechatAlarmInfoOutData.getAlarmStatus()));
                }
                return outDataOther;
            }
				outData = getWechatAlarmInfoOutData(phoneAlarmInData, outData);
		for (WechatAlarmInfoOutData wechatAlarmInfoOutData : outData) {
			wechatAlarmInfoOutData.setAlarmStatus(dealStatus(wechatAlarmInfoOutData.getAlarmStatus()));
		}
		return outData;
	}}

    private List<WechatAlarmInfoOutData> getWechatAlarmInfoOutData(PhoneAlarmInData phoneAlarmInData, List<WechatAlarmInfoOutData> outData) throws Exception {
        phoneAlarmInData.setSelectType("3");
        if (Util.isEmpty(outData)) {
            outData = utLzBjzjalarmMapper.getWechatAlarmList(phoneAlarmInData);
        } else {
            List<WechatAlarmInfoOutData> outBJZJData = utLzBjzjalarmMapper.getWechatAlarmList(phoneAlarmInData);
            if (Util.isNotEmpty(outBJZJData)) {
                outData.addAll(outBJZJData);
                Collections.sort(outData, new Comparator<WechatAlarmInfoOutData>() {
                    @Override
                    public int compare(WechatAlarmInfoOutData o1, WechatAlarmInfoOutData o2) {
                        return o2.getAlarmTime().compareTo(o1.getAlarmTime());
                    }
                });
            }
        }
        return outData;
    }

    /*
     * private String dealEqSystemId(String eqSystemId){ if
     * (Util.isEmpty(eqSystemId)) { return ""; } switch (eqSystemId) { case "1":
     * return "灭火系统"; case "2": return "电气火灾"; case "3": return "报警系统"; case "4":
     * return "防火分隔"; case "5": return "气体系统"; case "6": return "燃气系统"; case "7":
     * return "应急疏散"; case "8": return "无线烟感"; case "9": return "防排烟系统"; default:
     * break; } return ""; }
     */

	private String dealStatus(String alarmStatus) {
		if (Util.isEmpty(alarmStatus)) {
			return "";
		}
		switch (alarmStatus) {
		case "0":
			return "手动报警";
		case "1":
			return "火警";
		case "2":
			return "故障";
		case "3":
			return "启动";
		case "4":
			return "反馈";
		case "5":
			return "监管";
		case "6":
			return "屏蔽";
		case "7":
			return "恢复";
		case "8":
			return "复位";
		case "9":
			return "用户传输装置操作";
		case "11":
			return "火警恢复";
		case "12":
			return "故障恢复";
		case "13":
			return "停止";
		case "14":
			return "反馈撤销";
		case "15":
			return "监管撤销";
		case "16":
			return "屏蔽撤销";
		case "20":
			return "消音";
		case "21":
			return "解除报警";
		case "22":
			return "自动火警有人";
		case "23":
			return "自动火警超时";
		case "24":
			return "自动火警无人";
		case "25":
			return "确认火警";
		case "26":
			return "紧急火警";
		case "27":
			return "预警";
		case "28":
			return "报警";
		case "30":
			return "传输装置开机";
		case "31":
			return "传输装置关机";
		case "32":
			return "控制器开机";
		case "33":
			return "控制器关机";
		case "34":
			return "RTU开机";
		case "35":
			return "RTU关机";
		case "200":
			return "水系统异常";
		case "201":
			return "水系统恢复";
		case "211":
			return "消火栓系统异常";
		case "212":
			return "自动喷水灭火系统异常";
		case "218":
			return "防烟排烟系统异常";
		case "219":
			return "防火门及卷帘系统异常";
		case "261":
			return "消火栓系统恢复";
		case "262":
			return "自动喷水灭火系统恢复";
		case "268":
			return "防烟排烟系统恢复";
		case "269":
			return "防火门及卷帘恢复";
		case "300":
			return "电气火灾异常";
		case "301":
			return "电气火灾恢复";
		default:
			break;
		}
		return "";
	}

    @Override
    public WeChatAlarmStatOutData getAlarmNodealStat(String userId) throws Exception {

        Example example = new Example(UtBaseUseRelation.class);
        example.createCriteria().andEqualTo("userId", Long.valueOf(userId));
        List<UtBaseUseRelation> userIds = utBaseUserRelationMapper.selectByExample(example);
        WeChatAlarmStatOutData alarmNodealStat = utLzBjzjalarmMapper.getAlarmNodealStat(userId, "2");
        FrontMaintenanceStatOutData maintenanceStat = frontMaintenanceService.getMaintenanceStat(null, userId);
        if (Util.isEmpty(userIds)) {
            alarmNodealStat = utLzBjzjalarmMapper.getAlarmNodealStat(userId, "1");
        } else {
            if (Util.isEmpty(alarmNodealStat)) {
                alarmNodealStat = utLzBjzjalarmMapper.getAlarmNodealStat(userId, "3");
            } else {
                WeChatAlarmStatOutData alarmNodealBJZJStat = utLzBjzjalarmMapper.getAlarmNodealStat(userId, "3");
                if (Util.isNotEmpty(alarmNodealBJZJStat)) {
                    Integer fireCount = alarmNodealBJZJStat.getFireCount();
                    Integer interfaceCount = alarmNodealBJZJStat.getInterfaceCount();
                    Integer fireCount1 = alarmNodealStat.getFireCount();
                    alarmNodealStat.setFireCount(fireCount + fireCount1);
                    alarmNodealStat.setInterfaceCount(interfaceCount);
                }
            }
        }
        Integer totalCount = Integer.parseInt(maintenanceStat.getTotalCount() == null ? "0" : maintenanceStat.getTotalCount());    //灭火器总数量
        alarmNodealStat.setFireExtCount(totalCount);

        Integer wbNodeal = Integer.parseInt(maintenanceStat.getWbNodeal() == null ? "0" : maintenanceStat.getWbNodeal());    //维保未处理量
        alarmNodealStat.setMtcTaskCount(wbNodeal);

        int wbdealed = Integer.parseInt(maintenanceStat.getWbdealed() == null ? "0" : maintenanceStat.getWbdealed());    //维保已处理
        int wbdealing = Integer.parseInt(maintenanceStat.getWbdealing() == null ? "0" : maintenanceStat.getWbdealing());    //维保处理中

        int xcNodeal = Integer.parseInt(maintenanceStat.getXcNodeal() == null ? "0" : maintenanceStat.getXcNodeal());    //巡查未处理
        int xcdealed = Integer.parseInt(maintenanceStat.getXcdealed() == null ? "0" : maintenanceStat.getXcdealed());    //巡查已处理
        int xcdealing = Integer.parseInt(maintenanceStat.getXcdealing() == null ? "0" : maintenanceStat.getXcdealing());    //巡查处理中
        Integer xcExpire = maintenanceStat.getXcExpire();    //巡查已过期

        alarmNodealStat.setHisRecordCount(wbNodeal + wbdealed + wbdealing + xcNodeal + xcdealed + xcdealing + xcExpire);
        return alarmNodealStat;
    }

    @Override
    public FrontCoupletAlarmInfoOutData getAlarmInfo(String id) throws Exception {
        FrontCoupletAlarmInfoOutData outData = utLzBjzjalarmMapper.getAlarmInfo(id);
        outData.setAlarmStatus(dealStatus(outData.getAlarmStatus()));
        return outData;
    }

    @Override
    @Transactional
    public void dealAlarm(PhoneAlarmInfoIndata inData) throws Exception {
        UtLzBjzjalarm bjzjalarm = utLzBjzjalarmMapper.selectByPrimaryKey(Long.valueOf(inData.getId()));

        String userId = inData.getUserId();
        String openId = inData.getOpenId();
        UtUnitUser user;
        if (Util.isEmpty(userId) && !Util.isEmpty(openId)) {
            Example example = new Example(UtUnitUser.class);
            example.createCriteria().andEqualTo("openid", openId);
            List<UtUnitUser> users = utUnitUserMapper.selectByExample(example);
            if (Util.isEmpty(users)) {
                user = users.get(0);
                bjzjalarm.setDealuser(user.getId() + "");
            } else {
                user = new UtUnitUser();
            }
        } else {
            user = utUnitUserMapper.selectByPrimaryKey(Long.parseLong(userId));
            bjzjalarm.setDealuser(userId);
        }
        bjzjalarm.setDealtime(new Date());
        bjzjalarm.setIsdeal(1);
        // 处理编码生成规则 F+当前时间时分秒毫秒
        String dealcode = "F" + UtilConv.date2Str(new Date(), UtilConv.DATE_TIME_FULL_PAT_17);
        bjzjalarm.setDealcode(dealcode);
        bjzjalarm.setDealinfo(inData.getDealInfo());
        bjzjalarm.setDealresult(Integer.valueOf(inData.getDealResult()));
        // bjzjalarm.setLastupdate(new Date());
        bjzjalarm.setIsneedmaintain(Integer.valueOf(inData.getIsPushWb()));
        bjzjalarm.setMaintaindesc(inData.getPushDesc());
        bjzjalarm.setPicurl(inData.getPicUrl());
        utLzBjzjalarmMapper.updateByPrimaryKey(bjzjalarm);
        UtLzBjzjalarmHistory alarmHistory = new UtLzBjzjalarmHistory();
        BeanUtils.copyProperties(bjzjalarm, alarmHistory);
        utLzBjzjalarmHistoryMapper.updateByPrimaryKey(alarmHistory);

        SysOperationLog operationLog = new SysOperationLog();
        operationLog.setUnitId(bjzjalarm.getUnitid());
        // log.setAddress(sessinInfo.getCurrAddress());
        operationLog.setContent(inData.getPushDesc());
        operationLog.setCreateDate(new Date());
        operationLog.setCurrentStatus("");
        operationLog.setId(snowflakeIdWorker.nextId());
        // log.setRemark("批量处理类型:" + dealStatus(alarmStatus));
        operationLog.setUrl("/front/couplet/dealAlarmBatch");
        operationLog.setRecordType("1");
        operationLog.setUserId(user.getId());
        sysOperationLogMapper.insert(operationLog);
        if ("1".equals(inData.getIsPushWb())) {
            UtLzRepair repair = new UtLzRepair();
            repair.setId(snowflakeIdWorker.nextId());
            repair.setUnitid(bjzjalarm.getUnitid());
            repair.setBaddesc(inData.getPushDesc());
            repair.setCreatetime(new Date());
            repair.setBaseid(Long.parseLong(inData.getId()));
            repair.setDatafrom(0);
            repair.setIswbfk(1);
            repair.setWbclr(user.getUsername());
            repair.setRepairsite(bjzjalarm.getAlarmWheredesc());
            repair.setDealstatus(0);
            repair.setPicurl(inData.getPicUrl());
            Integer flag = utLzRepairMapper.insert(repair);
            if (flag < 1) {
                throw new ServiceException("生成维修失败！");
            }
        }
        String netDeviceId = utUnitNetdeviceMapper.getDeviceByAlarm(bjzjalarm.getOnwercode(), bjzjalarm.getDeviceindex(), bjzjalarm.getDeviceno());
        if (1 == clearAlarm) {
            try {
                thirdInterfaceService.clearAlarm(netDeviceId, null);
            } catch (Exception e) {
                log.error("clearAlarm error!" + e.getMessage());
            }
        }

    }

    @Override
    public FrontCoupletAlarmInfoOutData getRTUAlarmInfo(String id) throws Exception {
        FrontCoupletAlarmInfoOutData outData = utLzFireequipmentalarmMapper.getRTUAlarmInfo(id);
        return outData;
    }

    @Override
    public void dealRTUAlarm(PhoneAlarmInfoIndata inData) throws Exception {
        UtLzFireequipmentalarm utLzFireequipmentalarm = utLzFireequipmentalarmMapper
                .selectByPrimaryKey(Long.valueOf(inData.getId()));

        String userId = inData.getUserId();
        String oppenId = inData.getOpenId();
        String username = "";
        if (Util.isEmpty(userId) && !Util.isEmpty(oppenId)) {
            Example example = new Example(UtUnitUser.class);
            example.createCriteria().andEqualTo("openid", oppenId);
            List<UtUnitUser> users = utUnitUserMapper.selectByExample(example);
            if (Util.isEmpty(users)) {
                utLzFireequipmentalarm.setDealuser(users.get(0).getUserid() + "");
                username = users.get(0).getUsername();
            }
        } else {
            utLzFireequipmentalarm.setDealuser(userId);
            UtUnitUser user = utUnitUserMapper.selectByPrimaryKey(Long.parseLong(userId));
            username = user.getUsername();
        }
        utLzFireequipmentalarm.setDealtime(new Date());
        utLzFireequipmentalarm.setIsdeal(1);
        // 处理编码生成规则 F+当前时间时分秒毫秒
        String dealcode = "F" + UtilConv.date2Str(new Date(), UtilConv.DATE_TIME_FULL_PAT_17);
        utLzFireequipmentalarm.setDealcode(dealcode);
        utLzFireequipmentalarm.setDealinfo(inData.getDealInfo());
        utLzFireequipmentalarm.setDealresult(Integer.valueOf(inData.getDealResult()));
        // utLzFireequipmentalarm.setLastupdate(new Date());
        utLzFireequipmentalarm.setIsneedmaintain(Integer.valueOf(inData.getIsPushWb()));
        utLzFireequipmentalarm.setMaintaindesc(inData.getPushDesc());
        utLzFireequipmentalarm.setPicurl(inData.getPicUrl());
        utLzFireequipmentalarmMapper.updateByPrimaryKey(utLzFireequipmentalarm);
        UtLzFireequipmentalarmHistory alarmHistory = new UtLzFireequipmentalarmHistory();
        BeanUtils.copyProperties(utLzFireequipmentalarm, alarmHistory);
        utLzFireequipmentalarmHistoryMapper.updateByPrimaryKey(alarmHistory);
        if ("1".equals(inData.getIsPushWb())) {
            UtLzRepair repair = new UtLzRepair();
            repair.setId(snowflakeIdWorker.nextId());
            repair.setUnitid(utLzFireequipmentalarm.getUnitid());
            repair.setBaddesc(inData.getDealInfo());
            repair.setCreatetime(new Date());
            repair.setBaseid(Long.parseLong(inData.getId()));
            repair.setDatafrom(1);
            repair.setIswbfk(1);
            repair.setWbclr(username);
            repair.setDealstatus(0);
            repair.setPicurl(inData.getPicUrl());
            Integer flag = utLzRepairMapper.insert(repair);
            if (flag < 1) {
                throw new ServiceException("生成维修失败！");
            }
        }
    }

    @Override
    public PageInfo<FrontHisSDDeviceStatusOutData> getSDDeviceStatusList(FrontHistoryInData inData) throws Exception {
        inData.setDatabase(database);
        PageHelper.startPage(inData.getPageNumber(), inData.getPageSize());
        List<FrontHisSDDeviceStatusOutData> list = utUnitNetdeviceMapper.getSDDeviceStatusList(inData);
        return new PageInfo<>(list);
    }

    @Override
    public List<PhoneCalibrationOutData> calibrationList() throws Exception {
        List<PhoneCalibrationOutData> outData = new ArrayList<>();
        List<UtUnitCalibration> list = utUnitCalibrationMapper.selectAll();
        for (UtUnitCalibration utUnitCalibration : list) {
            PhoneCalibrationOutData data = new PhoneCalibrationOutData();
            BeanUtils.copyProperties(utUnitCalibration, data);
            data.setId(utUnitCalibration.getId() + "");
            outData.add(data);
        }
        return outData;
    }

    @Override
    public void updateIsPushFault(String isPushFault, String userId) throws Exception {
        UtUnitUser unitUser = new UtUnitUser();
        unitUser.setId(Long.valueOf(userId));
        unitUser.setIspushfault(isPushFault);
        utUnitUserMapper.updateByPrimaryKeySelective(unitUser);
    }

    @Override
    public List<FrontCoupletUnitInfo> getUnitList(String userId, String keyword) throws Exception {
        return utUnitBaseinfoMapper.getUnits(userId, keyword);
    }

    @Override
    public List<FrontUnitInfoOutData> getUnitInfoById(String unitId) throws Exception {
        return utUnitBaseinfoMapper.getUnitInfoById(unitId);
    }

    @Override
    public boolean lookup(String ownerCode, String userId) throws Exception {
        SysPropertiesOutData properties = propertiesManageService.getProperties();
        Example example = new Example(UtUnitNetdevice.class);
        Criteria criteria = example.createCriteria();
        criteria.andEqualTo("ownercode", ownerCode);
        List<UtUnitNetdevice> netdevices = utUnitNetdeviceMapper.selectByExample(example);
        if (Util.isEmpty(netdevices)) {
            return false;
        }
        Long deviceId = netdevices.get(0).getId();

        UtUnitNetdevice utUnitNetdevice = utUnitNetdeviceMapper.selectByPrimaryKey(Long.valueOf(deviceId));
        boolean flag = true;
        String ownercode = utUnitNetdevice.getOwnercode();

        StringBuilder sb = new StringBuilder();

        // 调用远程接口
        String api = String.format(ReqApiConst.GET_REDSERVER_CHECKDEVICE_API, properties.getAccessToken(), userId, 20, ownercode, 2, 1);
        sb.append(properties.getRedServerPort()).append(api);
        HttpJson httpGet = HttpClientUtil.httpGet(sb.toString());
        if (httpGet.isSuccess()) {
            String msg = httpGet.getMsg();
            Map<String, Object> map = (Map<String, Object>) JSONObject.parse(msg);
            Integer code = (Integer) map.get("Code");
            if (200 != code) {
                flag = false;
            }
        }

        return flag;
    }

    @Override
    public List<FrontLookupLogOutData> getLookupLogs(String userId, FrontLookupInData indata) throws Exception {
        indata.setDatabase(database);
        indata.setUserId(userId);
        List<FrontLookupLogOutData> lookupLogs = utUnitLookupLogMapper.getLookupLogs(indata);
        return lookupLogs;
    }

    @Override
    public List<EqSystemOutData> getEqSystemByUnitId(String unitId) throws Exception {
        return utBaseEqsystemMapper.getEqSystemByUnitId(unitId, null);
    }

	@Override
	public List<PhoneTaskDetailOutData> getTaskList(String userid, String inspectcycletype) throws Exception {
		DownLoadTaskInData inData = new DownLoadTaskInData();
		inData.setUserID(userid);
		inData.setInspectcycleType(inspectcycletype);
		//根据用户查询 角色
		UtUnitUser utUnitUser = unitUserMapper.selectByPrimaryKey(Long.valueOf(userid));
		inData.setUserRole(utUnitUser.getUserrole());
		Example example = new Example(UtInspectTask.class);
		example.createCriteria().andEqualTo("receiveuserid", utUnitUser.getId());
		List<UtInspectTask> utInspectTasks = utInspectTaskMapper.selectByExample(example);
		if (Util.isNotEmpty(utInspectTasks)) {
			for (UtInspectTask utInspectTask : utInspectTasks){
		inData.setReceiveuserid(String.valueOf(utInspectTask.getReceiveuserid()));
			}
		}
		if (utUnitUser.getUserrole() == 2) {
			inData.setInspectuserid(String.valueOf(utUnitUser.getId()));
		} else {
			inData.setSupervisorID(String.valueOf(utUnitUser.getId()));
		}
		List<PhoneTaskDetailOutData> list = utInspectTaskMapper.getUserTaskList(inData);
		list = getPhoneTaskDetailOutData(list);List<InspectTaskDownlog> downlogs = new ArrayList<>();
		for (PhoneTaskDetailOutData data : list) {
			if (Util.isNotEmpty(data.getSiteid())) {List<SiteClassDetailOutData> outData = utInspectTaskMapper.getSiteClassDetail(data.getSiteid());
			data.setSiteClassDetailList(outData);
			data.setBindStatus(Util.isNotEmpty(data.getNfcCode()));

			if (Util.isNotEmpty(userid) && Util.isNotEmpty(data.getTaskid()))
				downlogs.add(new InspectTaskDownlog(DataConvertUtil.parseLong(data.getTaskid()),DataConvertUtil.parseLong(userid)));
		}
		}//批量插入任务下载记录
		if (downlogs.size() > 0)
			inspectTaskDownlogMapper.insertAll(downlogs);return list;
	}

		private List<PhoneTaskDetailOutData> getPhoneTaskDetailOutData(List<PhoneTaskDetailOutData> list) {
		list = list.stream()
				.collect(Collectors.collectingAndThen
						(Collectors.toCollection(() ->
										new TreeSet<>(Comparator.comparing(t -> t.getTaskid()))),
								ArrayList::new
						)
				);return list;
	}

    @Override
    public void saveTaskList(List<PhoneTaskDetailOutData> tasks) {

        List<UtInspectTask> taskInfoList = new ArrayList<>();
        List<UtBaseSiteclassdetial> taskDetailList = new ArrayList<>();

        for (PhoneTaskDetailOutData task : tasks) {

            String taskid = task.getTaskid();
            String unitid = task.getUnitid();
            String plandetailid = task.getPlandetailid();
            String siteid = task.getSiteid();
            String siteclassid = task.getSiteclassid();
            String sitecode = task.getSitecode();
            String starttime = task.getStarttime();
            String endtime = task.getEndtime();
            String sitename = task.getSitename();
            String changeuserid = task.getChangeuserid();
            String receiveuserid = task.getReceiveuserid();
            String ischange = task.getIschange();
            String isreceive = task.getIsreceive();
            String changetime = task.getChangetime();
            String receivetime = task.getReceivetime();

            UtInspectTask taskInfo = new UtInspectTask();
            taskInfo.setId(DataConvertUtil.parseLong(taskid));
            taskInfo.setUnitid(DataConvertUtil.parseLong(unitid));
            taskInfo.setPlandetialid(DataConvertUtil.parseLong(plandetailid));
            taskInfo.setSiteid(DataConvertUtil.parseLong(siteid));
            taskInfo.setSiteclassid(DataConvertUtil.parseLong(siteclassid));
            taskInfo.setSitecode(sitecode);
            taskInfo.setTaskstart(Util.StringToDateTime(starttime));
            taskInfo.setTaskend(Util.StringToDateTime(endtime));
            taskInfo.setSitename(sitename);
            taskInfo.setChangeuserid(Long.parseLong(changeuserid));
            taskInfo.setReceiveuserid(Long.parseLong(receiveuserid));
            taskInfo.setIschange(DataConvertUtil.parseInt(ischange));
            taskInfo.setIsreceive(DataConvertUtil.parseInt(isreceive));
            taskInfo.setChangetime(Util.StringToDateTime(changetime));
            taskInfo.setReceivetime(Util.StringToDateTime(receivetime));
            taskInfoList.add(taskInfo);

            List<SiteClassDetailOutData> siteClassDetailList = task.getSiteClassDetailList();
            for (SiteClassDetailOutData siteClassDetailOutData : siteClassDetailList) {

                String id = siteClassDetailOutData.getId();
                String checkMethod = siteClassDetailOutData.getCheckMethod();
                String checkInfo = siteClassDetailOutData.getCheckInfo();

                UtBaseSiteclassdetial taskDetail = new UtBaseSiteclassdetial();
                taskDetail.setId(DataConvertUtil.parseLong(id));
                taskDetail.setCheckinfo(checkInfo);
                taskDetail.setCheckmethod(checkMethod);
                taskDetailList.add(taskDetail);
            }
        }

        //批量更新
        for (UtInspectTask utInspectTask : taskInfoList) {
            utInspectTaskMapper.updateByPrimaryKeySelective(utInspectTask);
        }
        for (UtBaseSiteclassdetial utBaseSiteclassdetial : taskDetailList) {
            utBaseSiteclassdetialMapper.updateByPrimaryKeySelective(utBaseSiteclassdetial);
        }

    }

    @Override
    public List<FrontMaintenanceOutData> getRepairList(String userId, Integer status) throws Exception {
        FrontMaintenanceInData inData = new FrontMaintenanceInData();
        inData.setUserId(userId);
        inData.setStatus(status.toString());
        List<FrontMaintenanceOutData> unitMsg = utLzRepairMapper.getRepairList(inData);
        return unitMsg;
    }

    @Override
    public List<FrontHistoryAlarmInfoOutData> getFireHistoryList(FrontHistoryInData inData) throws Exception {
        Example example = new Example(UtBaseUseRelation.class);
        example.createCriteria().andEqualTo("userId", Long.valueOf(inData.getUserId()));
        List<UtBaseUseRelation> userId = utBaseUserRelationMapper.selectByExample(example);
        //该项为sql查找标识
        inData.setSelectType("2");
        List<FrontHistoryAlarmInfoOutData> list = utLzBjzjalarmMapper.getFireHistoryList(inData);
        if (Util.isEmpty(userId)) {
            if (!Util.isEmpty(inData.getEndDate())) {
                inData.setEndDate(UtilConv.date2Str(new Date(), UtilConv.DATE_YYYY_MM_DD_CHN + " 23:59:59"));
            }
            //该项为sql查找标识
            inData.setSelectType("1");
            list = utLzBjzjalarmMapper.getFireHistoryList(inData);
        } else {
            //该项为sql查找标识
            inData.setSelectType("3");
            List<FrontHistoryAlarmInfoOutData> listBJZJ = utLzBjzjalarmMapper.getFireHistoryList(inData);
            if (Util.isNotEmpty(listBJZJ)) {
                list.addAll(listBJZJ);
            }
        }
        for (FrontHistoryAlarmInfoOutData frontHistoryAlarmInfoOutData : list) {
            frontHistoryAlarmInfoOutData.setAlarmStatus(dealStatus(frontHistoryAlarmInfoOutData.getAlarmStatus()));
        }
        return list;
    }

    @Override
    public List<FrontHistoryAlarmInfoOutData> getInterfaceAlarmList(FrontHistoryInData inData) throws Exception {
        Example example = new Example(UtBaseUseRelation.class);
        example.createCriteria().andEqualTo("userId", Long.valueOf(inData.getUserId()));
        List<UtBaseUseRelation> userId = utBaseUserRelationMapper.selectByExample(example);
        List<FrontHistoryAlarmInfoOutData> list = null;
        if (Util.isEmpty(userId)) {
            if (!Util.isEmpty(inData.getEndDate())) {
                inData.setEndDate(UtilConv.date2Str(new Date(), UtilConv.DATE_YYYY_MM_DD_CHN) + " 23:59:59");
            }
            list = utLzFireequipmentalarmMapper.getInterfaceAlarmListForApp(inData);
        } else {
            if (!Util.isEmpty(inData.getEndDate())) {
                inData.setEndDate(UtilConv.date2Str(new Date(), UtilConv.DATE_YYYY_MM_DD_CHN) + " 23:59:59");
            }
            list = utLzFireequipmentalarmMapper.getInterfaceAlarmListForAppByBuild(inData);
        }
        return list;
    }

    @Override
    public PageInfo<FrontMaintenanceTaskOutData> getInspectTaskList(FrontMaintenanceInData inData) throws Exception {
        PageHelper.startPage(inData.getPageNumber(), inData.getPageSize());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String format = sdf.format(new Date());
        inData.setStartDate(format);
        List<FrontMaintenanceTaskOutData> list = utInspectTaskdetialMapper.getInspectTaskList(inData);
        PageInfo<FrontMaintenanceTaskOutData> pager = new PageInfo<>(list);
        return pager;
    }

    @Override
    public PageInfo<FrontMaintenanceOutData> getRepairPageList(FrontMaintenanceInData inData) throws Exception {
        PageHelper.startPage(inData.getPageNumber(), inData.getPageSize());
        List<FrontMaintenanceOutData> list = utLzRepairMapper.getRepairList(inData);
        PageInfo<FrontMaintenanceOutData> pager = new PageInfo<>(list);
        return pager;
    }

    @Override
    public void feedback(PhoneFeedbackInData inData) {
        UtFeedback utFeedback = new UtFeedback();
        BeanUtils.copyProperties(inData, utFeedback);
        utFeedback.setId(snowflakeIdWorker.nextId());
        utFeedback.setCreateDate(new Date());
        utFeedback.setCreateUser(Long.valueOf(inData.getCreateUser()));
        utFeedbackMapper.insert(utFeedback);
    }

    @Override
    public List<FrontMaintenanceFireOutData> getFireExtinguisherList(String unitId, String keyword, String status, String userId) throws Exception {
        FrontMaintenanceInData data = new FrontMaintenanceInData();
        data.setUnitId(unitId);
        data.setKeyword(keyword);
        data.setStatus(status);
        data.setUserId(userId);
        List<FrontMaintenanceFireOutData> list = utEqExtinguisherMapper.getFireExtinguisherList(data);
        return list;
    }

    @Override
    public void updateTaskDetail(PhoneUploadProblem inData) throws Exception {
        UtInspectTaskdetial detail = new UtInspectTaskdetial();
        detail.setId(snowflakeIdWorker.nextId());
        detail.setCreateuser(inData.getCreateUserId());
        detail.setIsok(1);
        detail.setBaddesc(inData.getBadDesc());
        detail.setCreatedate(new Date());
        detail.setPicPath(inData.getPicPath());
        if (Util.isNotEmpty(inData.getTaskid())) {
            detail.setTaskid(Long.parseLong(inData.getTaskid()));
        }
        detail.setCheckinfo(inData.getCheckInfo());
        if (Util.isNotEmpty(inData.getId())) {
            detail.setClassdetialid(Long.parseLong(inData.getId()));
        }
        Integer flag = utInspectTaskdetialMapper.insert(detail);
        if (flag < 1) {
            throw new ServiceException(UtilMessage.UPDATE_ERROR);
        }
    }

    @Override
    public void uploadDanger(PhoneUploadProblem inData) throws Exception {
        UtInspectTaskdetial detail = new UtInspectTaskdetial();
        Long id = snowflakeIdWorker.nextId();

        detail.setId(id);
        detail.setCreateuser(inData.getCreateUserId());
        detail.setIsok(1);
        if (Util.isNotEmpty(inData.getTaskid())) {
            detail.setTaskid(Long.parseLong(inData.getTaskid()));
        }
        if (Util.isNotEmpty(inData.getId())) {
            detail.setClassdetialid(Long.parseLong(inData.getId()));
        }
        detail.setCheckinfo(inData.getCheckInfo());
        detail.setBaddesc(inData.getBadDesc());
        detail.setPicPath(inData.getPicPath());
        detail.setIsneedrepair(1);
        Integer flag1 = utInspectTaskdetialMapper.insert(detail);
        if (flag1 < 1) {
            throw new ServiceException(UtilMessage.UPDATE_ERROR);
        }
        UtInspectTask task = utInspectTaskMapper.selectByPrimaryKey(Long.parseLong(inData.getTaskid()));
        UtUnitUser user = utUnitUserMapper.selectByPrimaryKey(Long.parseLong(inData.getCreateUserId()));
        if (Util.isNotEmpty(task)) {
            UtLzRepair repair = new UtLzRepair();
            String dealcode = "F" + UtilConv.date2Str(new Date(), UtilConv.DATE_TIME_FULL_PAT_17);
            repair.setRepaircode(dealcode);
            repair.setId(snowflakeIdWorker.nextId());
            repair.setBaddesc(inData.getBadDesc());
            repair.setBaseid(Long.parseLong(inData.getTaskid()));
            repair.setCreatetime(new Date());
            repair.setRepairsite(task.getSitename());
            repair.setDealstatus(0);
            repair.setDatafrom(2);
            repair.setWbclr(user.getUsername());
            repair.setPicurl(inData.getPicPath());
            repair.setUnitid(task.getUnitid());
            Integer flag = utLzRepairMapper.insert(repair);
            if (flag < 1) {
                throw new ServiceException(UtilMessage.ADD_ERROR);
            }
        } else {
            throw new ServiceException("任务不存在");
        }

    }

    /**
     * 解析图片流
     *
     * @param partFile
     * @return
     * @throws IllegalStateException
     * @throws IOException
     */
    public String explainFile(CommonsMultipartFile partFile) throws IllegalStateException, IOException {
        SimpleDateFormat dateformat = new SimpleDateFormat("yyyy/MM/dd/HH");
        /** 构建图片保存的目录 **/
        String picPathDir = "pictures/" + dateformat.format(new Date());
        // UUID
        String uuid = UUID.randomUUID().toString().replace("-", "");
        /** 得到图片保存目录的真实路径.tomcat下webapp路径 **/
        String picRealPathDir = mImagesPath + picPathDir + "/" + uuid + "/";
        /** 根据真实路径创建目录 **/
        File picSaveFile = new File(picRealPathDir);
        if (!picSaveFile.exists())
            picSaveFile.mkdirs();
        // 原始图片
        String picName = partFile.getOriginalFilename();
        /** 获取文件的后缀 **/
        String suffix = picName.substring(picName.lastIndexOf("."));

        /** 使用UUID生成文件名称 **/
        String saveFileName = new Date().getTime() + suffix;// 构建文件名称
        // String saveFileName = picName;
        /** 拼成完整的文件保存路径加文件 **/
        String fileName = picRealPathDir + saveFileName;
        File file = new File(fileName);
        String picDirPath = "/file/upload/" + picPathDir + "/" + uuid + "/";
        partFile.transferTo(file);
        return picDirPath;
    }

    @Override
    public void dangerDeal(MaintenanceDealInData inData) {
        UtLzRepairdetial detail = new UtLzRepairdetial();
        UtUnitUser user = utUnitUserMapper.selectByPrimaryKey(Long.parseLong(inData.getUserId()));

        if (Util.isNotEmpty(inData.getRepairid())) {
            UtLzRepair repair = utLzRepairMapper.selectByPrimaryKey(Long.parseLong(inData.getRepairid()));
            if (repair != null) {
                String dealcode = "F" + UtilConv.date2Str(new Date(), UtilConv.DATE_TIME_FULL_PAT_17);
                repair.setRepaircode(dealcode);
                repair.setDealstatus(3);
                repair.setWbclr(user.getUsername());
                // repair.setPicurl(inData.getPicurl());
                repair.setDealdate(Util.StringToDateTime(inData.getDealdate()));
                utLzRepairMapper.updateByPrimaryKey(repair);
                detail.setId(snowflakeIdWorker.nextId());
                detail.setDealinfo(inData.getDealinfo());
                if (inData.getDealdate() != null && inData.getDealdate() != "") {
                    detail.setDealdate(Util.StringToDateTime(inData.getDealdate()));
                }
                if (inData.getDealtype() != null && inData.getDealtype() != "") {
                    detail.setDealtype(Integer.parseInt(inData.getDealtype()));
                }
                if (inData.getUserId() != null && inData.getUserId() != "") {
                    detail.setDealuserid(Long.parseLong(inData.getUserId()));
                }
                if (inData.getOperatetime() != null && inData.getOperatetime() != "") {
                    detail.setOperatetime(Util.StringToDateTime(inData.getOperatetime()));
                }
                detail.setOperateinfo(inData.getOperateinfo());
                if (inData.getOperateuserid() != null && inData.getOperateuserid() != "") {
                    detail.setOperateuserid(Long.parseLong(inData.getOperateuserid()));
                }

				detail.setOperateusername(user.getUsername());
				detail.setPicpath(inData.getPicpath());
				if (inData.getRepairid() != null && inData.getRepairid() != "") {
					detail.setRepairid(Long.parseLong(inData.getRepairid()));
				}
				if (inData.getLongitude() != null && inData.getLongitude() != "") {
					detail.setLongitude(inData.getLongitude());
				}
				if (inData.getLatitude() != null && inData.getLatitude() != "") {
					detail.setLatitude(inData.getLatitude());
				}
				if (inData.getLocation() != null && inData.getLocation() != "") {
					detail.setLocation(inData.getLocation());
				}utLzRepairdetialMapper.insert(detail);

            } else {
                throw new ServiceException(UtilMessage.GET_MSG_ERROR);
            }
        }

    }

    @Override
    public List<PhoneSiteClassDetialBaseOutData> getSiteClassDetailBaseList(String id) throws Exception {
        List<PhoneSiteClassDetialBaseOutData> outData = utBaseSiteclassdetialbaseMapper.getSiteClassDetailBaseList(id);
        return outData;
    }

	@Override
	@Transactional
	public void submitAllInspectTask(String userId, String jsons) throws Exception {
		log.error("提交任务:" + jsons);
		if (Util.isNotEmpty(jsons)) {
			log.error("jsons不为空:");
			List<UpLoadAllTaskInData> list = JSONArray.parseArray(jsons, UpLoadAllTaskInData.class);
			for (UpLoadAllTaskInData inspectTaskInData : list) {
				UtInspectTask utInspectTask1 = utInspectTaskMapper.selectByPrimaryKey(Long.valueOf(inspectTaskInData.getTaskid()));
				UtInspectTask utInspectTask = new UtInspectTask();
				utInspectTask.setSupervisorid(utInspectTask1.getSupervisorid());
				if (Util.isNotEmpty(inspectTaskInData.getTaskid())) {
					utInspectTask.setId(Long.valueOf(inspectTaskInData.getTaskid()));
				}
				if (Util.isNotEmpty(inspectTaskInData.getInspectTime())) {
					utInspectTask.setInspecttime(
							UtilConv.str2Date(inspectTaskInData.getInspectTime(), UtilConv.DATE_YYYY_MM_DD_SS));
				}
				utInspectTask.setIsinspect(1);
				UTInspectBaseRel utInspectBaseRel = new UTInspectBaseRel();
				utInspectBaseRel.setSiteID(utInspectTask1.getSiteid());
				List<UTInspectBaseRel> select = utInspectBaseRelMapper.select(utInspectBaseRel);
				utInspectTask.setSitecount(select.size());
				utInspectTask.setOkcheckcount(utInspectTask1.getOkcheckcount() + 1);List<PhoneUploadProblem> problem = inspectTaskInData.getSiteClassDetailList();DownLoadTaskInData inData = new DownLoadTaskInData();
				inData.setTaskId(inspectTaskInData.getTaskid());
				List<PhoneTaskDetailOutData> userTaskList = utInspectTaskMapper.getUserTaskList(inData);
				userTaskList = getPhoneTaskDetailOutData(userTaskList);
				for (PhoneTaskDetailOutData phoneTaskDetailOutData : userTaskList) {
					UTInspectBaseRel utInspectBaseRels = getUtInspectBaseRel(phoneTaskDetailOutData);
				if (Util.isNotEmpty(problem)) {
					utInspectTask.setIsok(1);
					for (PhoneUploadProblem data : problem) {
						data.setCreateUserId(userId);
						data.setTaskid(inspectTaskInData.getTaskid());
						if ("1".equals(data.getPromlemType())) {RelUpdate(utInspectBaseRels, 2);
							data.setIsNeedRepair("1");
							uploadDanger(data);
						} else {RelUpdate(utInspectBaseRels, 1);
							updateTaskDetail(data);}
						}
					}
				}
				utInspectTaskMapper.updateByPrimaryKeySelective(utInspectTask);
			}
		}
	}

	private UTInspectBaseRel getUtInspectBaseRel(PhoneTaskDetailOutData phoneTaskDetailOutData) {
		Example example = new Example(UTInspectBaseRel.class);
		example.createCriteria().andEqualTo("SiteID", Long.parseLong(phoneTaskDetailOutData.getSiteid()))
				.andEqualTo("SiteClassID", Long.parseLong(phoneTaskDetailOutData.getSiteclassid()))
				.andEqualTo("SiteClassDetialID", Long.parseLong(phoneTaskDetailOutData.getSiteClassDetailid()));
		return utInspectBaseRelMapper.selectOneByExample(example);
	}

	private void RelUpdate(UTInspectBaseRel utInspectBaseRels, int i) {
		UTInspectBaseRel utInspectBaseRel = new UTInspectBaseRel();
		utInspectBaseRel.setId(utInspectBaseRels.getId());
		utInspectBaseRel.setStatus(i);
		utInspectBaseRelMapper.updateByPrimaryKeySelective(utInspectBaseRel);
	}@Override
	public FrontRepairDetailOutData getMaintenanceDetail(String id) throws Exception {
		FrontRepairDetailOutData outData = utLzRepairdetialMapper.getMaintenanceDetail(id);
		return outData;
	}

    @Override
    public List<FrontLookupUnitInfoData> getUnitTransferDevice(String unitId) throws Exception {
        return utUnitBaseinfoMapper.getUnitTransferDevice(unitId, database);
    }

    @Override
    public List<FrontInterfaceAppOutData> getWaterMoreListNew(FrontCoupletInData inData) throws Exception {
		List<FrontInterfaceAppOutData> appOutData = new ArrayList<>();
        inData.setDatabase(database);
        List<FrontInterFaceStatusOutData> list = utEqEquipmentdetialgwMapper.getInterfaceValueList(inData);
		FrontInterfaceAppOutData data;
		Map<String, FrontInterfaceAppOutData> _map = new HashMap();
		for (FrontInterFaceStatusOutData interfaceData : list) {
			if (!_map.containsKey(interfaceData.getId())) {
				data = new FrontInterfaceAppOutData();
				data.setDataList(new ArrayList<>());
                data.setData(new ArrayList<>());
				BeanUtils.copyProperties(interfaceData, data);
				_map.put(interfaceData.getId(), data);
			} else {
				data = _map.get(interfaceData.getId());
			}
            if ("数字量".equals(interfaceData.getIoType())) {
                DigitalPortVo digitalPortVo = new DigitalPortVo();
                BeanUtils.copyProperties(interfaceData, digitalPortVo);
                digitalPortVo.setIoName(interfaceData.getDetialName());
                if (Util.isNotEmpty(interfaceData.getNormalValue()) && Util.isNotEmpty(interfaceData.getCurrentValue())) {
                    int norVal = Integer.parseInt(interfaceData.getNormalValue());
                    int curVal = Integer.parseInt(interfaceData.getCurrentValue());
                    digitalPortVo.setNormal(norVal == curVal);
                }
                data.getDataList().add(digitalPortVo);
            } else {
                AnalogPortVo analogPortVo = new AnalogPortVo();
                BeanUtils.copyProperties(interfaceData, analogPortVo);
                analogPortVo.setDetailId(interfaceData.getInterfaceId());
                if (Util.isNotEmpty(interfaceData.getCurrentValue()) && Util.isNotEmpty(interfaceData.getAnalogdown()) && Util.isNotEmpty(interfaceData.getAnalogup())) {
                    double currVal = Double.parseDouble(interfaceData.getCurrentValue());
                    double up = Double.parseDouble(interfaceData.getAnalogup());
                    double down = Double.parseDouble(interfaceData.getAnalogdown());
                    analogPortVo.setNormal(currVal > down && currVal < up);
                }
                data.getData().add(analogPortVo);
            }

			Map<String, String> map = new HashMap<>();
			map.put("ioType", interfaceData.getIoType());
			map.put("normalValue", interfaceData.getNormalValue());
			map.put("goodName", interfaceData.getGoodName());
			map.put("badName", interfaceData.getBadName());
			map.put("currentValue", interfaceData.getCurrentValue());
			map.put("ioName", interfaceData.getDetialName());


        }
        appOutData.addAll(_map.values());
        return appOutData;
	}

    @Override
    public List<FrontInterFaceStatusOutData> getWaterMoreList(FrontCoupletInData inData) throws Exception {
        inData.setDatabase(database);
        List<FrontInterFaceStatusOutData> list = utEqEquipmentdetialgwMapper.getInterfaceValueList(inData);
        return list;
    }

    @Override
    public List<FrontCoupletFireAlarmOutData> getFireAlarmList(FrontCoupletInData inData) throws Exception {
        String netDeviceId = inData.getNetDeviceId();
        UtUnitNetdevice netdevice = utUnitNetdeviceMapper.selectByPrimaryKey(Long.valueOf(netDeviceId));
        inData.setUnitId(netdevice.getUnitid() + "");
        //该项为sql查找标识
        inData.setSelectType("1");
        List<FrontCoupletFireAlarmOutData> list = utLzBjzjalarmMapper.getFireAlarmList(inData);
        for (FrontCoupletFireAlarmOutData frontCoupletFireAlarmOutData : list) {
            frontCoupletFireAlarmOutData.setAlarmStatus(dealStatus(frontCoupletFireAlarmOutData.getAlarmStatus()));
        }
        return list;
    }

    @Override
    public FrontVideoOutData getVideoDetail(String videoID) throws Exception {
        return utUnitVideoMapper.getVideoDetail(videoID);
    }

    @Override
    public void updateLog(FrontUnitUserOutData inData, String type) {
        UtMaintenanceLoginLog loginLog = utMaintenanceLoginLogMapper.selectByPrimaryKey(inData.getCurrentLogId());
        if (loginLog == null) {
            return;
        }
        loginLog.setLoginOutDate(new Date());
        utMaintenanceLoginLogMapper.updateByPrimaryKey(loginLog);
    }

    @Override
    public List<FrontCoupletUnitInfo> getMaintenanceStatList(String userId, String keyword) throws Exception {
        return utEqExtinguisherMapper.getMaintenanceStatList(userId, keyword);
    }

    @Override
    public List<PhoneTaskDetailOutData> getReceiveTaskList(String userId, String inspectcycleType, String type) {
        return utInspectTaskMapper.getReceiveTaskList(userId, inspectcycleType, type);
    }

    @Override
    @Transactional
    public void changeTask(String changeUserID, String taskID, String receiveUserID) {
        List<UtInspectTask> taskList = utInspectTaskMapper.selectAll();
        String[] taskIDList = taskID.split(",");
        String errorMsg = "";
        if (Util.isEmpty(taskIDList)) {
            throw new ServiceException("请选择转单任务！");
        }
        for (UtInspectTask task : taskList) {
            for (String taskId : taskIDList) {
                if (taskId.equals(task.getId().toString())) {
                    task.setChangetime(new Date());
                    task.setChangeuserid(Long.parseLong(changeUserID));
                    task.setInspectuserid(Long.parseLong(changeUserID));
                    task.setReceiveuserid(Long.parseLong(receiveUserID));
                    task.setIschange(1);
                    Integer flag = utInspectTaskMapper.updateByPrimaryKey(task);
                    if (flag < 1) {
                        errorMsg = "转单失败！";
                    }
                }
            }
        }
        if (Util.isNotEmpty(errorMsg)) {
            throw new ServiceException(errorMsg);
        }
    }

	@Override
	@Transactional
	public void receiveTask(String receiveUserID, String taskID) {
		List<UtInspectTask> taskList = utInspectTaskMapper.selectAll();
		String[] taskIDList = taskID.split(",");
		String errorMsg = "";
		if(Util.isEmpty(taskIDList)) {
			throw new ServiceException("请选择接单任务！");
		}
		for(UtInspectTask task : taskList) {
			for(String taskId : taskIDList) {
				if(taskId.equals(task.getId().toString())) {
					task.setIsreceive(1);
					task.setReceivetime(new Date());
					task.setInspectuserid(Long.parseLong(receiveUserID));
					Integer flag = utInspectTaskMapper.updateByPrimaryKey(task);
					if(flag<1) {
						errorMsg = "接单失败！";
					}
				}
			}
		}
		if(Util.isNotEmpty(errorMsg)) {
			throw new ServiceException(errorMsg);}
		}

	@Override
	public void withdrawTask(String userId, String taskID) {
		List<UtInspectTask> taskList = utInspectTaskMapper.selectAll();
		String[] taskIDList = taskID.split(",");
		String errorMsg = "";
		if(Util.isEmpty(taskIDList)) {
			throw new ServiceException("请选择撤回任务！");
		}
		for(UtInspectTask task : taskList) {
			for(String taskId : taskIDList) {
				if(taskId.equals(task.getId().toString())&&"0".equals(task.getIsreceive().toString())) {
					task.setIschange(0);
					Integer flag = utInspectTaskMapper.updateByPrimaryKey(task);
					if(flag<1) {
						errorMsg = "撤回失败！";
					}
				}
			}
		}
		if(Util.isNotEmpty(errorMsg)) {
			throw new ServiceException(errorMsg);
		}
	}

    @Override
    public PageInfo<NetworkingUserOutData> getNetUserList(String userId, NetworkingUserInData inData) {
        UtUnitUser utUnitUser = unitUserMapper.selectByPrimaryKey(Long.parseLong(userId));
        if (Util.isNotEmpty(utUnitUser) && Util.isNotEmpty(utUnitUser.getUnitid())) {
            inData.setUnitid(utUnitUser.getUnitid().toString());
        }
        List<NetworkingUserOutData> list1 = new ArrayList<NetworkingUserOutData>();
        List<NetworkingUserOutData> list = unitUserMapper.selectAllNetworkingUser(inData);
        for (NetworkingUserOutData user : list) {
            if (!user.getId().equals(userId)) {
                list1.add(user);
            }
        }
        PageInfo<NetworkingUserOutData> pager = new PageInfo<NetworkingUserOutData>(list1);
        return pager;
    }

    @Override
    public TaskSheetOutData getTaskCount(String userId) {
        TaskSheetOutData outData = utInspectTaskMapper.getTaskCount(userId);
        if (Util.isEmpty(outData)) {
            outData = new TaskSheetOutData();
            outData.setReceviceCount("0");
            outData.setChangeCount("0");
        }
        return outData;
    }

    @Override
    public void logout(String openId, String type) throws Exception {
        Example example = new Example(UtUnitUser.class);
        if ("wechat".equals(type)) {
            example.createCriteria().andEqualTo("openid", openId);
        } else {
            example.createCriteria().andEqualTo("channelid", openId);
        }
        List<UtUnitUser> list = utUnitUserMapper.selectByExample(example);
        if (Util.isEmpty(list)) {
            return;
        }
        for (UtUnitUser utUnitUser : list) {
            if ("wechat".equals(type)) {
                utUnitUser.setOpenid(null);
            } else {
                utUnitUser.setChannelid(null);
            }
            utUnitUserMapper.updateByPrimaryKey(utUnitUser);
        }
    }

	/**
	 * 根据主键id获取灭火器详情
	 ** @param id 灭火器id
	 */
	@Override
	public FrontMaintenanceFireOutData getFeDetailById(Long id) throws Exception {
		return utEqExtinguisherMapper.selectById(id);
	}

    /**
     * 绑定NFC卡片
     *
     * @param nfcCode nfc卡唯一标识
     * @param taskID  巡查任务唯一标识
     */
    @Override
    public void bindingNFC(String nfcCode, String taskID) throws Exception {

        if (Util.isEmpty(nfcCode))
            throw new ServiceException(UtilMessage.REQUEST_DATA_FAILED);

		//查询任务是否存在
        UtInspectTask utInspectTask = utInspectTaskMapper.selectByPrimaryKey(DataConvertUtil.parseLong(taskID));
        if (Util.isEmpty(utInspectTask))
            throw new ServiceException(UtilMessage.REQUEST_DATA_FAILED);

		//查询NFC是否已使用
		Example siteExample = new Example(UtInspectSite.class);
		siteExample.createCriteria().andEqualTo("nfccode", nfcCode);
		List<UtInspectSite> utInspectSites = inspectSiteMapper.selectByExample(siteExample);
		if (Util.isNotEmpty(utInspectSites)) {
			throw new ServiceException(UtilMessage.NFC_INVALID);
		}
		//根据点位ID绑定NFC
		UtInspectSite inspectSite = new UtInspectSite();
		inspectSite.setId(utInspectTask.getSiteid());
		inspectSite.setNfccode(nfcCode);
		inspectSiteMapper.updateByPrimaryKeySelective(inspectSite);
    }

    /**
     * 通过 NFC唯一标识 获取检查项明细数据
     *
     * @param nfcCode nfc卡唯一标识
     */
    @Override
    public List<PhoneTaskDetailOutData> getInspectDetail(String nfcCode) {

        List<PhoneTaskDetailOutData> list = utInspectTaskdetialMapper.getInspectTaskDetailByNfc(nfcCode);
        for (PhoneTaskDetailOutData data : list) {
            List<SiteClassDetailOutData> outData = utInspectTaskMapper.getSiteClassDetail(data.getSiteclassid());
            data.setSiteClassDetailList(outData);
        }
        return list;
    }
}
