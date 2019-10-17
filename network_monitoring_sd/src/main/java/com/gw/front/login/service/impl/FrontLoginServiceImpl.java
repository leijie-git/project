package com.gw.front.login.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gw.common.SnowflakeIdWorker;
import com.gw.exception.ServiceException;
import com.gw.front.index.data.FrontLoginLogOutData;
import com.gw.front.login.data.CheckAccountMsg;
import com.gw.front.login.data.FrontUnitUserOutData;
import com.gw.front.login.service.FrontLoginService;
import com.gw.mapper.SysUserMapper;
import com.gw.mapper.UtMaintenanceLoginLogMapper;
import com.gw.mapper.UtMaintenanceUnitMapper;
import com.gw.mapper.UtUnitBaseinfoMapper;
import com.gw.mapper.UtUnitUserMapper;
import com.gw.mapper.entity.SysUser;
import com.gw.mapper.entity.UtMaintenanceLoginLog;
import com.gw.mapper.entity.UtMaintenanceUnit;
import com.gw.mapper.entity.UtUnitBaseinfo;
import com.gw.mapper.entity.UtUnitUser;
import com.gw.systemManager.data.SysPropertiesOutData;
import com.gw.systemManager.service.PropertiesManageService;
import com.gw.util.Util;
import com.gw.util.UtilConst;
import com.gw.util.UtilConv;

import tk.mybatis.mapper.entity.Example;

@Service
public class FrontLoginServiceImpl implements FrontLoginService {

	@Resource
	private UtUnitUserMapper utUnitUserMapper;
	@Resource
	private UtMaintenanceLoginLogMapper utMaintenanceLoginLogMapper;
	@Resource
	private SnowflakeIdWorker snowflakeIdWorker;
	@Resource
	private UtUnitBaseinfoMapper utUnitBaseinfoMapper;
	@Resource
	private SysUserMapper sysUserMapper;
	@Resource
	private UtMaintenanceUnitMapper utMaintenanceUnitMapper;
	@Resource
	private PropertiesManageService propertiesManageService;
//	@Value("${map.center}")
//	private String mapCenter;
//	@Value("${map.level}")
//	private String mapLevel;

	@Override
	@Transactional
	public FrontUnitUserOutData login(HttpServletRequest request, FrontUnitUserOutData inData) throws Exception {
		SysPropertiesOutData properties = propertiesManageService.getProperties();
		String password = inData.getPassword();
		//获取加密的密码
		String md5Hex = DigestUtils.md5Hex(password);
		Example example = new Example(UtUnitUser.class);
		//将账号密码传进后台 是否存在 是否删除
		example.createCriteria().andEqualTo("account", inData.getAccount()).andEqualTo("password", md5Hex)
				.andEqualTo("isdelete", "0");
		List<UtUnitUser> userList = utUnitUserMapper.selectByExample(example);
		if (Util.isEmpty(userList)) {
			throw new ServiceException(UtilConst.ERRO_ACCOUNT_PASSWORD);
		}
		if (Util.isNotEmpty(userList.get(0).getExpirytime())) {
			Date date = UtilConv.Dateformat(new Date(), UtilConv.DATE_YYYY_MM_DD_CHN);
			if (date.getTime() > userList.get(0).getExpirytime().getTime()) {
				throw new ServiceException("该用户已过期!");
			}
		}
		//通过账号密码获取这个账户对象
		UtUnitUser user = userList.get(0);
		FrontLoginLogOutData lastLog = null;
		//通过用户id和 wep端 获取 用户登记表
		List<FrontLoginLogOutData> loginLogs = utMaintenanceLoginLogMapper.getLoginLogs(user.getId() + "", "1");
		if (Util.isNotEmpty(loginLogs)) {
			//获取这个对象
			lastLog = loginLogs.get(0);
			//获取数据来源 数据来源（1web端2微信3app）
			String dataFrom = lastLog.getDataFrom();
			if ("2".equals(dataFrom)) {
				lastLog.setDataFrom("微信");
			} else if ("3".equals(dataFrom)) {
				lastLog.setDataFrom("APP");
			} else {
				lastLog.setDataFrom("PC");
			}

		}
		// 保存登录记录
		UtMaintenanceLoginLog log = new UtMaintenanceLoginLog();
		//获取IP
		String ip = inData.getIp();
		log.setLoginIp(ip);
		//通过ip获取登录地址
		String address = utMaintenanceLoginLogMapper.selectAddressByIP(ip);
		if (Util.isEmpty(address)) {
			address = UtilConv.baiduGetCityCode(ip);
			if (Util.isNotEmpty(address)) {
				utMaintenanceLoginLogMapper.insetIPAddress(ip, address);
			}
		}
		// log.setLoginAddr(inData.getCurrAddress());
		log.setLoginAddr(address);
		Date loginDate = new Date();
		log.setLoginDate(loginDate);
		// log.setLoginOutDate(new Date(loginDate.getTime() + 1800000));// 默认退出时间半个小时以后
		log.setLoginName(inData.getAccount());
		long nextId = snowflakeIdWorker.nextId();
		log.setId(nextId);
		log.setDataFrom("1");
		utMaintenanceLoginLogMapper.insert(log);
		// request.getSession().setAttribute("utMaintenanceLoginLog", log);

		FrontUnitUserOutData outData = new FrontUnitUserOutData();
		outData.setLog(lastLog);
		outData.setCurrentLogId(String.valueOf(nextId));
		outData.setLoginTime(UtilConv.date2Str(new Date(), UtilConv.DATE_YYYY_MM_DD_SS));
		outData.setAccount(user.getAccount());
		Integer usertype = user.getUsertype();
		outData.setUsertype(usertype + "");
		outData.setUsername(user.getUsername());
		outData.setUserrole(user.getUserrole() + "");
		outData.setEmail(user.getEmail());
		outData.setMobilephone(user.getMobilephone());
		outData.setOpenId(user.getOpenid());
		outData.setSex(user.getSex());
		Long unitid = user.getUnitid();
		outData.setUnitid(unitid + "");
		String mapCenter = null;
		if(usertype==1) {	//联网单位
			UtUnitBaseinfo unitInfo = utUnitBaseinfoMapper.selectByPrimaryKey(unitid);
			if(unitInfo != null) {
				outData.setUnitName(unitInfo.getUnitname());
				outData.setLogourl(unitInfo.getLogourl());

				String unitaddress = unitInfo.getUnitaddress();
				mapCenter = unitaddress.substring(0, unitaddress.lastIndexOf("市")+1);
			}
		}else if(usertype==0){	//维保单位
			UtMaintenanceUnit unit = utMaintenanceUnitMapper.selectByPrimaryKey(unitid);
			if(unit != null) {
				outData.setUnitName(unit.getUnitname());
				outData.setLogourl(unit.getLogourl());

				String unitaddress = unit.getAddress();
				mapCenter = unitaddress.substring(0, unitaddress.lastIndexOf("市")+1);
			}
		}
		// outData.setUserid(user.getUserid()+"");
		outData.setId(user.getId() + "");
		outData.setCurrAddress(log.getLoginAddr());
		outData.setMapCenter(Util.isEmpty(mapCenter)? properties.getMapCenter() : mapCenter);
		outData.setMapLevel(properties.getMapLevel());
		outData.setCertificatestype(user.getCertificatestype());
		outData.setCertificatesno(user.getCertificatesno());
		outData.setIp(ip);
		request.getSession().setAttribute(UtilConst.FRONT_USER_SESSION, outData);
		return outData;
	}

	@Override
	public void updateLog(Long currentLogId) throws Exception {
		UtMaintenanceLoginLog loginLog = utMaintenanceLoginLogMapper.selectByPrimaryKey(currentLogId);
		if (loginLog == null) {
			return;
		}
		loginLog.setLoginOutDate(new Date());
		utMaintenanceLoginLogMapper.updateByPrimaryKey(loginLog);
	}

	@Override
	public void updatePhone(HttpServletRequest request, String phone, String id, String oldPassword, String newPassword)
			throws Exception {
		UtUnitUser user = utUnitUserMapper.selectByPrimaryKey(Long.parseLong(id));
		user.setMobilephone(phone);
		Integer userrole = user.getUserrole();
		SysUser sysUser = null;
//		if (userrole == 1) {
//			sysUser = sysUserMapper.selectByPrimaryKey(user.getId());
//			sysUser.setPhone(phone);
//		}
		if (Util.isNotEmpty(oldPassword) && Util.isNotEmpty(newPassword)) {
			String md5Hex = DigestUtils.md5Hex(oldPassword);
			String password = DigestUtils.md5Hex(newPassword);
			if (user.getPassword().equals(md5Hex)) {
				user.setPassword(password);
			} else {
				throw new ServiceException("原密码不匹配！");
			}
//			if (userrole == 1) {// 单位管理人
//				sysUser.setPassword(password);
//				sysUser.setUpdateDate(new Date());
//				sysUser.setUpdateUser(id.toString());
//			}
		}
		utUnitUserMapper.updateByPrimaryKey(user);
//		if (userrole == 1) {
//			sysUserMapper.updateByPrimaryKey(sysUser);
//		}
	}

	@Override
	public CheckAccountMsg checkAccount(HttpServletRequest request, FrontUnitUserOutData inData) {
		CheckAccountMsg msg = new CheckAccountMsg();
		
		Example example2 = new Example(UtUnitUser.class);
		example2.createCriteria().andEqualTo("account", inData.getAccount()).andEqualTo("isdelete", "0");
		List<UtUnitUser> accountList = utUnitUserMapper.selectByExample(example2);
		if(Util.isEmpty(accountList)) {
			//账号不存在
			msg.setIsAccount("1");
		}
		String password = inData.getPassword();
		String md5Hex = DigestUtils.md5Hex(password);
		Example example = new Example(UtUnitUser.class);
		example.createCriteria().andEqualTo("account", inData.getAccount()).andEqualTo("password", md5Hex)
				.andEqualTo("isdelete", "0");
		List<UtUnitUser> userList = utUnitUserMapper.selectByExample(example);
		if (Util.isEmpty(userList)) {
			//账号存在密码不对应
			msg.setIsPassword("1");
		}
		return msg;
	}

}
