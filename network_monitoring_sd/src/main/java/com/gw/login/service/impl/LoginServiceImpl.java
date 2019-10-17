package com.gw.login.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.gw.util.*;

import net.sf.json.JSONObject;
import org.springframework.stereotype.Service;

import com.gw.exception.ServiceException;
import com.gw.login.service.LoginService;
import com.gw.login.service.data.GetLoginInData;
import com.gw.login.service.data.GetSessionInfoOutData;
import com.gw.mapper.SysResourceMapper;
import com.gw.mapper.SysUserMapper;
import com.gw.mapper.UtUnitUserMapper;
import com.gw.mapper.entity.SysUser;
import com.gw.mapper.entity.UtUnitUser;
import com.gw.systemManager.data.SysResourceOutData;
import com.gw.util.CodecUtil;
import com.gw.util.Util;
import com.gw.util.UtilConst;
import com.gw.util.UtilConv;
import com.gw.util.UtilMessage;

import tk.mybatis.mapper.entity.Example;

@Service
public class LoginServiceImpl implements LoginService {
	@Resource
	private SysUserMapper sysUserMapper;
	@Resource
	private SysResourceMapper resourceMapper;
	@Resource
	private UtUnitUserMapper utUnitUserMapper;

	@Override
	public GetSessionInfoOutData login(HttpServletRequest request, GetLoginInData inData) throws Exception {

//		Example example = new Example(SysUser.class);
//		example.createCriteria().andEqualTo("account", inData.getLoginAccount());
//		List<SysUser> employeeList = sysUserMapper.selectByExample(example);
//		// 用户名不存在
//		if (Util.isEmptyList(employeeList)) {
//			throw new ServiceException(UtilMessage.LOGIN_ACCOUNT_ERROR);
//		}
//		SysUser entity = employeeList.get(0);
//		String password = CodecUtil.encryptMD5(inData.getPassword());
//		// 密码错误
//		if (!password.equals(entity.getPassword())) {
//			throw new ServiceException(UtilMessage.LOGIN_PASSWORD_ERROR);
//		}
//
//		//判断登录用户是否管理员
//		String unitId = null;
//		UtUnitUser utUnitUser = utUnitUserMapper.selectByPrimaryKey(entity.getId());
//		if(Util.isNotEmpty(utUnitUser)){
//			if(Util.isNotEmpty(utUnitUser.getExpirytime())){
//				Date date = UtilConv.Dateformat(new Date(), UtilConv.DATE_YYYY_MM_DD_CHN);
//				if(date.getTime()>utUnitUser.getExpirytime().getTime()){
//					throw new ServiceException("用户已过期!");
//				}
//			}
//			unitId = utUnitUser.getUnitid().toString();
//		}


//		// 登录信息存入session
//		GetSessionInfoOutData session = new GetSessionInfoOutData();
//		session.setId(entity.getId());
//		session.setAccount(entity.getAccount());
//		session.setPassword(inData.getPassword());
//		session.setPhone(entity.getPhone());
//		session.setUnitId(unitId);
//		session.setUserName(entity.getUserName());
//		session.setResourceList(getResourceList(entity.getId(), entity.getAccount(),"0"));// 可访问权限列表存入session
//		request.getSession().setAttribute(UtilConst.USER_SESSION, session);
//
//
//		return session;


		Example example = new Example(UtUnitUser.class);
		example.createCriteria().andEqualTo("account", inData.getLoginAccount());
		List<UtUnitUser> utUnitUsers = utUnitUserMapper.selectByExample(example);
		// 用户名不存在
		if (Util.isEmptyList(utUnitUsers)) {
			throw new ServiceException(UtilMessage.LOGIN_ACCOUNT_ERROR);
		}

		UtUnitUser entity = utUnitUsers.get(0);
		if(!Util.isNotEmpty(entity.getIsSysUser())){
			throw new ServiceException(UtilMessage.LOGIN_ACCOUNT_ERROR);
		}
		//加密密码 判断密码是否正确
		String password = CodecUtil.encryptMD5(inData.getPassword());
		// 密码错误
		if (!password.equals(entity.getPassword())) {
			throw new ServiceException(UtilMessage.LOGIN_PASSWORD_ERROR);
		}

		//判断登录用户是否过期

		if(Util.isNotEmpty(entity.getExpirytime())){
			Date date = UtilConv.Dateformat(new Date(), UtilConv.DATE_YYYY_MM_DD_CHN);
			if(date.getTime()>entity.getExpirytime().getTime()){
				throw new ServiceException("用户已过期!");
			}
		}

		// 登录信息存入session
		GetSessionInfoOutData session = new GetSessionInfoOutData();
		session.setId(entity.getId());
		session.setAccount(entity.getAccount());
		session.setPassword(inData.getPassword());
	    session.setPhone(entity.getMobilephone());
	    if(entity.getUnitid()==1||entity.getAccount().equals("admin")){
		session.setUnitId(null);}
	    else {
		session.setUnitId(entity.getUnitid().toString());}
		session.setUserName(entity.getUsername());
		session.setResourceList(getResourceList(entity.getId(), entity.getAccount(),"0"));// 可访问权限列表存入session
		request.getSession().setAttribute(UtilConst.USER_SESSION, session);


		return session;
	}



	private List<SysResourceOutData> getResourceList(Long userId, String account,String type) {
		return resourceMapper.listResourcesByUserId(userId, account,type);
	}

	@Override
	public void updatePassword(Long id, String passwordNew, HttpServletRequest request) throws Exception {
		if (Util.isEmpty(passwordNew)) {
			throw new ServiceException(UtilMessage.UPDATE_PWD_WRONG2);
		}
		SysUser user = sysUserMapper.selectByPrimaryKey(id);
		user.setPassword(CodecUtil.encryptMD5(passwordNew));
		user.setUpdateDate(new Date());
		int i = sysUserMapper.updateByPrimaryKeySelective(user);
		if (i <= 0) {
			throw new ServiceException(UtilMessage.MODIFY_DATA_FAILED);
		}
		sysUserMapper.updateByPrimaryKey(user);
		GetSessionInfoOutData sessionInfoUpdate = (GetSessionInfoOutData) request.getSession()
				.getAttribute(UtilConst.USER_SESSION);
		sessionInfoUpdate.setPassword(CodecUtil.encryptMD5(passwordNew));
		request.getSession().setAttribute(UtilConst.USER_SESSION, sessionInfoUpdate);

		//如果是单位管理员，则同步修改前台用户密码
		Example example = new Example(UtUnitUser.class);
		example.createCriteria().andEqualTo("userrole", 1).andEqualTo("account", user.getAccount());
		List<UtUnitUser> list = utUnitUserMapper.selectByExample(example);
		if(Util.isNotEmpty(list)){
			UtUnitUser utUnitUser = list.get(0);
			utUnitUser.setPassword(CodecUtil.encryptMD5(passwordNew));
			utUnitUserMapper.updateByPrimaryKey(utUnitUser);
		}
	}

	@Override
	public void resetPassWord(String account) throws Exception {
		Example example = new Example(SysUser.class);
		example.createCriteria().andEqualTo("account", account).andEqualTo("usertype", "0")
				.andEqualTo("isdeleted", "0");
		SysUser user = sysUserMapper.selectOneByExample(example);
		if (Util.isEmpty(user)) {
			throw new ServiceException(UtilMessage.LOGIN_ACCOUNT_ERROR);
		}
		user.setPassword(CodecUtil.encryptMD5("123456"));
		sysUserMapper.updateByPrimaryKey(user);
		//如果是单位管理员，则同步修改前台用户密码
		Example example2 = new Example(UtUnitUser.class);
		example2.createCriteria().andEqualTo("userrole", 1).andEqualTo("account", user.getAccount());
		List<UtUnitUser> list = utUnitUserMapper.selectByExample(example2);
		if(Util.isNotEmpty(list)){
			UtUnitUser utUnitUser = list.get(0);
			utUnitUser.setPassword(CodecUtil.encryptMD5("123456"));
			utUnitUserMapper.updateByPrimaryKey(utUnitUser);
		}
	}
}
