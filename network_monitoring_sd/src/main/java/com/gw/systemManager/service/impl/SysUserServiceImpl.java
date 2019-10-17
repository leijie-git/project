package com.gw.systemManager.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.gw.common.SnowflakeIdWorker;
import com.gw.exception.ServiceException;
import com.gw.mapper.SysUserMapper;
import com.gw.mapper.SysUserRoleMapper;
import com.gw.mapper.UtUnitUserMapper;
import com.gw.mapper.entity.SysUserRole;
import com.gw.mapper.entity.UtUnitUser;
import com.gw.systemManager.data.GetUserRolesData;
import com.gw.systemManager.data.SysRoleAuthorizeInData;
import com.gw.systemManager.data.SysUserInData;
import com.gw.systemManager.data.SysUserOutData;
import com.gw.systemManager.service.SysUserService;
import com.gw.util.CodecUtil;
import com.gw.util.Util;
import com.gw.util.UtilMessage;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

@Service
public class SysUserServiceImpl implements SysUserService {

	@Autowired
	private SysUserMapper sysUserMapper;
	@Autowired
	private SysUserRoleMapper sysUserRoleMapper;
	@Autowired
	private UtUnitUserMapper utUnitUserMapper;

	@Autowired
	private SnowflakeIdWorker snowflakeIdWorker;

	@Override
	public PageInfo<SysUserOutData> getUserList(String userName, HttpServletRequest request) throws Exception {
		int pageNumber = Integer.parseInt(request.getParameter("pageNumber"));
		int pageSize = Integer.parseInt(request.getParameter("pageSize"));
		PageHelper.startPage(pageNumber, pageSize);
		List<SysUserOutData> list = sysUserMapper.selectAllUser(userName);
		PageInfo<SysUserOutData> pager = new PageInfo<SysUserOutData>(list);
		return pager;
	}

	@Override
	public void addUser(SysUserInData sysUserInData, HttpServletRequest request) throws Exception {
//		Example example = new Example(SysUser.class);
//		example.createCriteria().andEqualTo("account", sysUserInData.getAccount());
//		List<SysUser> list = sysUserMapper.selectByExample(example);
//		if(Util.isNotEmpty(list)){
//			throw new ServiceException("新增失败，该账号已存在！");
//		}
//		validateParam(sysUserInData);
//		SysUser sys = new SysUser();
//		sys.setId(snowflakeIdWorker.nextId());
//		sys.setUserName(sysUserInData.getUserName());
//		sys.setAccount(sysUserInData.getAccount());
//		sys.setPassword(CodecUtil.encryptMD5(sysUserInData.getPassword()));
//		sys.setCreateDate(new Date());
//		sys.setCreateUser(sysUserInData.getCreateUser());
//		sys.setUserHeader(sysUserInData.getUserHeader());
//		sys.setPhone(sysUserInData.getPhone());
//		sys.setAddress(sysUserInData.getAddress());
//		sys.setRemark(sysUserInData.getRemark());
//		sys.setSign(sysUserInData.getSign());
//		sys.setEthnicGroup(sysUserInData.getEthnicGroup());
//		sys.setSex(sysUserInData.getSex());
//		sys.setBirthday(sysUserInData.getBirthday());
//
//		sysUserMapper.insert(sys);

		Example example = new Example(UtUnitUser.class);
		example.createCriteria().andEqualTo("account", sysUserInData.getAccount());
		List<UtUnitUser> list = utUnitUserMapper.selectByExample(example);
		if(Util.isNotEmpty(list)){
			throw new ServiceException("新增失败，该账号已存在！");
		}
		//将注册的信息存入 UtUnitUser对象
		UtUnitUser sys = new UtUnitUser();
		//id
		sys.setId(snowflakeIdWorker.nextId());
		//用户名
		sys.setUsername(sysUserInData.getUserName());
		//账户
		sys.setAccount(sysUserInData.getAccount());
		//密码加密
		sys.setPassword(CodecUtil.encryptMD5(sysUserInData.getPassword()));
		//创建时间
		sys.setCreateDate(new Date());
		//创建人
		sys.setCreateUser(sysUserInData.getCreateUser());
		//用户头像
		sys.setUserHeader(sysUserInData.getUserHeader());
        //手机号码
		sys.setMobilephone(sysUserInData.getPhone());
		//居住地址
		sys.setAddress(sysUserInData.getAddress());
		sys.setCreateDate(new Date());
		//备注
		sys.setRemark(sysUserInData.getRemark());
		//签名
		sys.setSign(sysUserInData.getSign());
		//名族
		sys.setEthnicGroup(sysUserInData.getEthnicGroup());
		//性别
		if(sysUserInData.getSex().equals("男")){
			sys.setSex(1);
		}
		if(sysUserInData.getSex().equals("女")){
			sys.setSex(0);
		}
		//生日
		if(sysUserInData.getBirthday()!=null||sysUserInData.getBirthday()!="") {
			sys.setBirthday(Util.StringToDate(sysUserInData.getBirthday()));
		}
		System.out.println("sys"+sys);
		utUnitUserMapper.insert(sys);
	}

	@Override
	public void deleteUser(Long id) throws Exception {
		utUnitUserMapper.deleteByPrimaryKey(id);
	}

	@Override
	public void deleteManyUser(String id) throws Exception {
		if (Util.isEmpty(id)) {
			throw new ServiceException(UtilMessage.REQUEST_PARAM_EMPTY);
		}
		String[] ids = id.split(",");
		for (String s : ids) {
			if (!Util.isEmpty(s)) {
				utUnitUserMapper.deleteByPrimaryKey(Long.valueOf(s));
			}
		}
	}

	//后台用户更新
	@Override
	public void updateUser(SysUserInData sysUserInData) throws Exception {
//		Example example = new Example(SysUser.class);
//		example.createCriteria().andEqualTo("account", sysUserInData.getAccount());
//		List<SysUser> list = sysUserMapper.selectByExample(example);
//		if(Util.isNotEmpty(list)){
//			if(!list.get(0).getId().equals(Long.valueOf(sysUserInData.getId()))){
//				throw new ServiceException("修改失败，此账户名已存在！");
//			}
//		}
//		validateParam(sysUserInData);
//		SysUser sys = sysUserMapper.selectByPrimaryKey(Long.valueOf(sysUserInData.getId()));
//		sys.setUserName(sysUserInData.getUserName());
//		sys.setAccount(sysUserInData.getAccount());
//		sys.setCreateDate(new Date());
//		sys.setUserHeader(sysUserInData.getUserHeader());
//		sys.setPhone(sysUserInData.getPhone());
//		sys.setAddress(sysUserInData.getAddress());
//		sys.setRemark(sysUserInData.getRemark());
//		sys.setSign(sysUserInData.getSign());
//		sys.setEthnicGroup(sysUserInData.getEthnicGroup());
//		sys.setSex(sysUserInData.getSex());
//		sys.setBirthday(sysUserInData.getBirthday());
//		sysUserMapper.updateByPrimaryKey(sys);

		Example example = new Example(UtUnitUser.class);
		example.createCriteria().andEqualTo("account", sysUserInData.getAccount());
		List<UtUnitUser> list = utUnitUserMapper.selectByExample(example);
		//根据当前修改的名字  数据库是否重复
		if(Util.isNotEmpty(list)){
			if(!list.get(0).getId().equals(Long.valueOf(sysUserInData.getId()))){
				throw new ServiceException("修改失败，此账户名已存在！");
			}
		}


		UtUnitUser sys = utUnitUserMapper.selectByPrimaryKey(Long.valueOf(sysUserInData.getId()));


		//用户名
		sys.setUsername(sysUserInData.getUserName());
		//账户
		sys.setAccount(sysUserInData.getAccount());
	   //修改时间
		sys.setUpdateDate(new Date());
		//用户头像  如果没有新的 保持原来的头像
		if(sysUserInData.getUserHeader()==null){
			sys.setUserHeader(sysUserInData.getUserHeader());
		}

		//手机号码
		sys.setMobilephone(sysUserInData.getPhone());
		//居住地址
		sys.setAddress(sysUserInData.getAddress());
		//备注
		sys.setRemark(sysUserInData.getRemark());
		//签名
		sys.setSign(sysUserInData.getSign());
		//名族
		sys.setEthnicGroup(sysUserInData.getEthnicGroup());
		//性别
		if(sysUserInData.getSex().equals("男")){
			sys.setSex(1);
		}
		if(sysUserInData.getSex().equals("女")){
			sys.setSex(0);
		}
		//生日
		if(sysUserInData.getBirthday()!=null||sysUserInData.getBirthday()!="") {
			sys.setBirthday(Util.StringToDate(sysUserInData.getBirthday()));
		}
		utUnitUserMapper.updateByPrimaryKey(sys);


	}
//设置角色
	@Override
	public void setUserRole(SysRoleAuthorizeInData sysRoleAuthorizeInData) throws Exception {
		//通过用户id查出当前用户
		UtUnitUser sysUser = utUnitUserMapper.selectByPrimaryKey(sysRoleAuthorizeInData.getUserIdOne());
		if (sysUser == null) {
			throw new Exception(UtilMessage.LOGIN_ERROR1);
		}
		//如果已选择未空 则删除该用户的角色
		if (sysRoleAuthorizeInData.getManyRoleId() == null || sysRoleAuthorizeInData.getManyRoleId() == "") {
			sysUserRoleMapper.deleteByUserId(sysRoleAuthorizeInData.getUserIdOne());
		} else {
			//如果选择了  则删除当前用户的角色  从新设置多个角色
			sysUserRoleMapper.deleteByUserId(sysRoleAuthorizeInData.getUserIdOne());
			//获取一选择的角色ID 存进String数组
			String[] RoleId = sysRoleAuthorizeInData.getManyRoleId().split(",");
			//获取当前用户id
			Long userId = sysRoleAuthorizeInData.getUserIdOne();
			//遍历角色id 增加用户角色
			for (String roleId : RoleId) {
				SysUserRole userRole = new SysUserRole();
				userRole.setRoleid(Long.parseLong(roleId));
				userRole.setUserid(userId);
				sysUserRoleMapper.insert(userRole);
			}
		}
	}

	private void validateParam(SysUserInData sysUserInData) throws ServiceException {
		// String roleName = sysUserInData.getUserName();
		// Example example = new Example(SysUser.class);
		// example.createCriteria().andEqualTo("userName",roleName);
		//
		// sysUserMapper.selectByPrimaryKey(Long)
		// String roleName2 =
		// sysUserMapper.selectRoleName(sysUserInData.getId());
		// if (roleName.equals(roleName2) == false) {
		// List<SysUserOutData> resourceName =
		// sysUserMapper.selectAllUser(sysUserInData.getUsername());
		// for (SysUserOutData s : resourceName) {
		// if (s.getUsername().equals(sysUserInData.getUsername())) {
		// throw new ServiceException(UtilMessage.ROLE_EXISTS_ERR);
		// }
		// }
		// }
	}

	@Override
	public GetUserRolesData getUserRole(Long userId,String type) throws Exception {
		List<GetUserRolesData> hasRoles = sysUserRoleMapper.getUserHasRole(userId,type);
		List<GetUserRolesData> noRoles = sysUserRoleMapper.getUserNoRole(userId,type);
		GetUserRolesData outData = new GetUserRolesData();
		outData.setHasRoles(hasRoles);
		outData.setNoRoles(noRoles);
		return outData;
	}

	@Override
	public void resetPas(String id) throws Exception {
		UtUnitUser utUnitUser = utUnitUserMapper.selectByPrimaryKey(Long.valueOf(id));
		utUnitUser.setPassword(DigestUtils.md5Hex("123456"));
		utUnitUserMapper.updateByPrimaryKey(utUnitUser);
	}
}
