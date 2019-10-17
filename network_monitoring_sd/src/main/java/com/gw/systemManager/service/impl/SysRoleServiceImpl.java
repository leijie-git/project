package com.gw.systemManager.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.gw.common.SnowflakeIdWorker;
import com.gw.exception.ServiceException;
import com.gw.mapper.SysResourceRoleMapper;
import com.gw.mapper.SysRoleMapper;
import com.gw.mapper.SysUserRoleMapper;
import com.gw.mapper.entity.SysResourceRole;
import com.gw.mapper.entity.SysRole;
import com.gw.systemManager.data.GetUserRolesData;
import com.gw.systemManager.data.SysRoleAuthorizeInData;
import com.gw.systemManager.data.SysRoleInData;
import com.gw.systemManager.data.SysRoleOutData;
import com.gw.systemManager.service.SysRoleService;
import com.gw.util.Util;
import com.gw.util.UtilMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

/**
 * 角色业务实现类
 * @author zfg
 *
 */
@Service
public class SysRoleServiceImpl implements SysRoleService  {
	@Autowired
	private SysRoleMapper sysRoleMapper;
	
	@Autowired
	private SysUserRoleMapper sysUserRoleMapper;
	
	@Autowired
	private SysResourceRoleMapper sysResourceRoleMapper;
	@Autowired
	private SnowflakeIdWorker snowflakeIdWorker;
	
	@Override
	public PageInfo<SysRoleOutData> findAllRole(String roleName,String type,HttpServletRequest request) throws Exception {
		int pageNumber = Integer.parseInt(request.getParameter("pageNumber"));
		int pageSize = Integer.parseInt(request.getParameter("pageSize"));
		PageHelper.startPage(pageNumber, pageSize);
		List<SysRoleOutData> list = sysRoleMapper.selectRoles(roleName,type);
		PageInfo<SysRoleOutData> pager = new PageInfo<SysRoleOutData>(list);
		return pager;
	}
	
	@Override
	public SysRoleOutData editSystemRole(Long id) throws Exception {
		SysRoleOutData outData = sysRoleMapper.editRoles(id);
		List<String> list = sysResourceRoleMapper.selectResourceListByRoleId(id);
        if (!Util.isEmpty(list)) {
        	outData.setResourceId(list);
        }
		return outData;
	}

	@Override
	public void updateSystemRole(SysRoleInData sysRole) throws Exception {
		validateParam(sysRole);
		SysRole role = new SysRole();
		role.setUpdatedate(new Date());
		role.setIsadmin(sysRole.getIsadmin());
		role.setRolename(sysRole.getRolename());
		role.setRoletype(sysRole.getRoletype());
		role.setSortindex(sysRole.getSortindex());
		role.setId(sysRole.getId());
		if("0".equals(sysRole.getType())){
			role.setType("0");
		}
		if("1".equals(sysRole.getType())){
			role.setType("1");
		}
		Integer flag = sysRoleMapper.updateRole(role);
		sysResourceRoleMapper.deleteByRoleId(sysRole.getId());
		if(sysRole.getResourceId() != "") {
			String[] resource = sysRole.getResourceId().split(",");
			for(String res : resource) {
				SysResourceRole resourceRole = new SysResourceRole();
				resourceRole.setRoleId(sysRole.getId());
				resourceRole.setResourceId(Long.parseLong(res));
				sysResourceRoleMapper.insert(resourceRole);
			}
		}
		if(flag <= 0) {
			throw new ServiceException(UtilMessage.UPDATE_ERROR);
		}
	}

	@Override
	public void deleteSystemRole(Long id) throws Exception {
		sysRoleMapper.deleteByPrimaryKey(id);
	}

	@Override
	public void deleteManySystemRole(String id) throws Exception {
		if (Util.isEmpty(id)) {
            throw new ServiceException(UtilMessage.REQUEST_PARAM_EMPTY);
        }
        String[] ids = id.split(",");
        for (String s : ids) {
            int i = sysRoleMapper.deleteByPrimaryKey(Long.valueOf(s));
            if (i <= 0) {
                throw new ServiceException(UtilMessage.DEL_MESSAGE_ERROR);
            }
        }
	}

	@Override
	public void addSystemRole(SysRoleInData sysRole) throws Exception {
		validateParam(sysRole);
		SysRole role = new SysRole();
		Long id = snowflakeIdWorker.nextId();
		role.setAdddate(new Date());
		role.setId(id);
		role.setIsadmin(sysRole.getIsadmin());
		role.setRolename(sysRole.getRolename());
		role.setRoletype(sysRole.getRoletype());
		role.setSortindex(sysRole.getSortindex());
		if("0".equals(sysRole.getType())){
			role.setType("0");
		}
		if("1".equals(sysRole.getType())){
			role.setType("1");
		}
		Integer flag = sysRoleMapper.insert(role);
		if(sysRole.getResourceId() != "") {
			String[] resource = sysRole.getResourceId().split(",");
			for(String res : resource) {
				SysResourceRole resourceRole = new SysResourceRole();
				resourceRole.setRoleId(id);
				resourceRole.setResourceId(Long.parseLong(res));
				sysResourceRoleMapper.insert(resourceRole);
			}
		}
		if(flag <= 0 ) {
			throw new ServiceException(UtilMessage.UPDATE_ERROR);
		}
	}

	@Override
	public void setSystemRoletoUser(SysRoleAuthorizeInData inData) throws Exception {
		SysRole role = sysRoleMapper.selectByPrimaryKey(inData.getRoleId());
		if (role == null) {
            throw new ServiceException(UtilMessage.REQUEST_PARAM_EMPTY);
        }
		if(inData.getUserId() == null ) {
			//将该角色对应的所有用户先删除
			sysRoleMapper.deleteAllUser(inData.getRoleId());
		}
		//将该角色对应的所有用户先删除
		sysRoleMapper.deleteAllUser(inData.getRoleId());
		//再将该角色分配给对应用户
		String[] userId = inData.getUserId().split(",");
		Long roleId = inData.getRoleId();
		for(String userid : userId) {
			Integer flag = sysRoleMapper.setRoleToUser(Long.parseLong(userid),roleId);
			if(flag<=0) {
				throw new Exception(UtilMessage.ROLE_SET_ERROR);
			}
		}
	}

	@Override
	public String selectUserToRole(Long id) throws Exception{
		return sysRoleMapper.selectUserToRole(id);
	}
	
	@Override
	public String selectUserNotRole(Long id) throws Exception{
		return sysRoleMapper.selectUserNotRole(id);
	}


	@Override
	public GetUserRolesData getUserRoles(Long id,String type) throws Exception{
		List<GetUserRolesData> hasRoles = sysUserRoleMapper.getUserHasRole(id,type);
		List<GetUserRolesData> noRoles = sysUserRoleMapper.getUserNoRole(id,type);
		GetUserRolesData outData = new GetUserRolesData();
		outData.setHasRoles(hasRoles);
		outData.setNoRoles(noRoles);
		return outData;
	}
	
	private void validateParam(SysRoleInData SysRoleInData) throws ServiceException {

		String roleName = SysRoleInData.getRolename();
		String roleName2 = sysRoleMapper.selectRoleName(SysRoleInData.getId());
		if(!roleName.equals(roleName2)) {
			List<SysRoleOutData> resourceName = sysRoleMapper.selectRoles(roleName,SysRoleInData.getType());
	        for (SysRoleOutData s : resourceName) {
	            if (s.getRoleName().equals(SysRoleInData.getRolename())) {
	                throw new ServiceException(UtilMessage.ROLE_EXISTS_ERR);
	            }
	        }
		}
        
    }

}
