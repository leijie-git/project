package com.gw.systemManager.service.impl;

import com.gw.common.SnowflakeIdWorker;
import com.gw.exception.ServiceException;
import com.gw.mapper.SysResourceMapper;
import com.gw.mapper.SysUserRoleMapper;
import com.gw.mapper.entity.SysResource;
import com.gw.mapper.entity.SysUserRole;
import com.gw.systemManager.data.GetUserRolesData;
import com.gw.systemManager.data.SysResourceIndata;
import com.gw.systemManager.data.SysResourceOutData;
import com.gw.systemManager.service.SysResourceService;
import com.gw.util.Util;
import com.gw.util.UtilConst;
import com.gw.util.UtilMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class SysResourceServiceImpl implements SysResourceService {

	@Resource
	private SysResourceMapper sysResourceMapper;
	@Autowired
	private SnowflakeIdWorker snowflakeIdWorker;
	@Autowired
	private SysUserRoleMapper sysUserRoleMapper;

	@Override
	public List<SysResourceOutData> getAllResource(String name, String status, String type) throws Exception {
		return sysResourceMapper.getAllResource(name, status, type);
	}

	@Override
	public void addSystemResource(SysResourceIndata sysResource) throws Exception {
		validateParam(sysResource);
		SysResource sys = new SysResource();
		sys.setId(snowflakeIdWorker.nextId());
		sys.setName(sysResource.getName());
		sys.setPid(sysResource.getPid());
		sys.setResourceType(sysResource.getResourceType());
		sys.setSeq(sysResource.getSeq());
		sys.setCreateUser(sysResource.getCreateUser());
		sys.setCreateDate(new Date());
		sys.setUrl(sysResource.getUrl());
		sys.setType(sysResource.getType());
		sysResourceMapper.insert(sys);
	}

	private void validateParam(SysResourceIndata sysResource) throws ServiceException {
		if (!Util.isEmpty(sysResource.getId()) && !Util.isEmpty(sysResource.getPid())) {
			if (sysResource.getId().equals(sysResource.getPid())) {
				throw new ServiceException(UtilMessage.SET_PID_ERROR);
			}
		}
		String respourceName = sysResourceMapper.selectResourceName(sysResource.getId());
		if (sysResource.getName().equals(respourceName) == false) {
			List<String> resourceName = sysResourceMapper.selectResourceNameByPidExcludeSelf(sysResource.getPid(),
					sysResource.getId(), sysResource.getType());
			for (String s : resourceName) {
				if (s.equals(sysResource.getName())) {
					throw new ServiceException(UtilMessage.ROLE_EXISTS_ERR);
				}
			}
			if (Util.isEmpty(sysResource.getSeq())) {
				sysResource.setSeq(UtilConst.DEFAULT_SEQ);
			}
			if ("0".equals(sysResource.getType())) {
				sysResource.setType("0");
			}
			if ("1".equals(sysResource.getType())) {
				sysResource.setType("1");
			}
		}

	}


	@Override
	public SysResourceOutData editSystemResource(String id) throws Exception {
		return sysResourceMapper.editResource(id);
	}

	@Override
	public void updateSystemResource(SysResourceIndata sysResource, Long userId) throws Exception {
		validateParam(sysResource);
		SysResource sys = new SysResource();
		sys.setId(sysResource.getId());
		sys.setName(sysResource.getName());
		sys.setPid(sysResource.getPid());
		sys.setUrl(sysResource.getUrl());
		sys.setResourceType(sysResource.getResourceType());
		sys.setSeq(sysResource.getSeq());
		sys.setUpdateUser(sysResource.getUpdateUser());
		sys.setUpdateDate(new Date());
		sys.setType(sysResource.getType());
		int i = sysResourceMapper.updateResource(sys);
		if (i <= 0) {
			throw new ServiceException(UtilMessage.MODIFY_DATA_FAILED);
		}
	}

	@Override
	public void deleteSystemResource(Long id) throws Exception {
		int i = sysResourceMapper.deleteResource(id);
		if (i <= 0) {
			throw new ServiceException(UtilMessage.DEL_MESSAGE_ERROR);
		}
	}

	@Override
	public List<SysResourceOutData> listResourcesByUserId(Long userId, String account, String type) throws Exception {
		return sysResourceMapper.listResourcesByUserId(userId, account, type);
	}

	@Override
	public List<SysResourceOutData> listStageResourcesByUserId(Long userId, String account, String type) throws Exception {
		SysUserRole sysUserRole = new SysUserRole();
		sysUserRole.setUserid(userId);
		List<GetUserRolesData> select = sysUserRoleMapper.getUserHasRole(userId,type);
		List<SysResourceOutData> sysResourceOutData = sysResourceMapper.listStageResourcesByUserId(userId, account, type);
		if (Util.isEmpty(select)) {
			sysResourceOutData = sysResourceMapper.listStageResourcesByUserId(null, account, type);
		}
		return sysResourceOutData;
	}

	@Override
	public List<SysResourceOutData> resourceList(SysResourceIndata indata) throws Exception {
		SysResource sys = new SysResource();
		sys.setName(indata.getName());
		sys.setPid(indata.getPid());
		sys.setResourceType(indata.getResourceType());
		sys.setSeq(indata.getSeq());
		sys.setUpdateUser(indata.getUpdateUser());
		sys.setUpdateDate(new Date());
		List<SysResourceOutData> list = sysResourceMapper.getResourceList(sys);
		return list;
	}

}
