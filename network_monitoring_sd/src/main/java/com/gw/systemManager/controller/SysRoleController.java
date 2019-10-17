package com.gw.systemManager.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageInfo;
import com.gw.common.BaseController;
import com.gw.common.Json;
import com.gw.exception.ServiceException;
import com.gw.login.service.data.GetSessionInfoOutData;
import com.gw.systemManager.data.GetUserRolesData;
import com.gw.systemManager.data.SysRoleAuthorizeInData;
import com.gw.systemManager.data.SysRoleInData;
import com.gw.systemManager.data.SysRoleOutData;
import com.gw.systemManager.service.SysRoleService;
import com.gw.util.UtilConst;
import com.gw.util.UtilMessage;

@Controller
@RequestMapping("/role")
@ResponseBody
public class SysRoleController extends BaseController {
	
	@Autowired
	private SysRoleService sysRoleService;

	/**
	 * 获取所有角色
	 * @param roleName
	 * @param request
	 * @return
	 */
	@GetMapping("/roleList")
	public PageInfo<SysRoleOutData> roleList(HttpServletRequest request,@RequestParam("roleName") String roleName,@RequestParam("type") String type){
		PageInfo<SysRoleOutData> page = null;
		try {
			page = sysRoleService.findAllRole(roleName,type,request);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return page;
	}
	
	/**
	 * 编辑角色
	 * @param id
	 * @return
	 */
	@GetMapping("/editRole")
	public Json editRole(@RequestParam("id") Long id) {
        Json json = new Json();
        try {
        	SysRoleOutData getSysRoleOutData = sysRoleService.editSystemRole(id);
            json.setSuccess(true);
            json.setObj(getSysRoleOutData);
        } catch (Exception e) {
            if (e instanceof ServiceException) {
                json.setMsg(e.getMessage());
            } else {
                json.setMsg(UtilMessage.REQUEST_DATA_FAILED);
            }
            e.printStackTrace();
        }
        return json;
    }
	
	/**
	 * 删除角色
	 * @param id
	 * @return
	 */
	@GetMapping("/deleteRole")
	public Json deleteRole(@RequestParam("id") Long id) {
		Json json = new Json();
        try {
        	sysRoleService.deleteSystemRole(id);
            json.setSuccess(true);
        } catch (Exception e) {
            if (e instanceof ServiceException) {
                json.setMsg(e.getMessage());
            } else {
                json.setMsg(UtilMessage.REQUEST_DATA_FAILED);
            }
            e.printStackTrace();
        }
        return json;
	}
	
	/**
     * 批量删除角色
     * Delete many system role
     * 2017-9-26 20:01:57 lxy
     *
     * @param id String类型,用","隔开
     * @return
     */
    @RequestMapping(value = "/deleteManySystemRole")
    public Json deleteManySystemRole(@RequestParam("id") String id) {
        Json json = new Json();
        try {
        	sysRoleService.deleteManySystemRole(id);
            json.setSuccess(true);
            json.setMsg(UtilMessage.DEL_MESSAGE_SUCCESS);
        } catch (Exception e) {
            if (e instanceof ServiceException) {
                json.setMsg(e.getMessage());
            } else {
                json.setMsg(UtilMessage.DEL_MESSAGE_ERROR);
            }
            e.printStackTrace();
        }
        return json;
    }
    
	/**
	 * 添加角色
	 * @param sysRoleInData
	 * @param userId
	 * @return
	 */
	@PostMapping("/addRole")
	public Json addRole(SysRoleInData sysRoleInData) {
		Json json = new Json();
		try {
			sysRoleService.addSystemRole(sysRoleInData);
			json.setSuccess(true);
		} catch (Exception e) {
			json.setMsg(e.getMessage());
			e.printStackTrace();
		}
		return json;
	}
	
	/**
	 * 更新角色
	 * @param sysRoleInData
	 * @param request
	 * @return
	 */
	@PostMapping("/updateRole")
	public Json updateRole(SysRoleInData sysRoleInData,HttpServletRequest request) {
		Json json = new Json();
		try {
			sysRoleService.updateSystemRole(sysRoleInData);
			json.setSuccess(true);
		} catch (Exception e) {
			json.setMsg(e.getMessage());
			e.printStackTrace();
		}
		return json;
	}
	
	/**
     * 查询人员拥有的和未拥有的角色
     *
     * @param id
     * @return
     */
    @GetMapping("/getUserRoles")
	public Json getUserRoles(@RequestParam("id") Long id,@RequestParam("type")String type) throws Exception{
		Json json = new Json();
		try {
			GetUserRolesData outData = sysRoleService.getUserRoles(id,type);
			json.setObj(outData);
			json.setSuccess(true);
			json.setMsg(UtilMessage.AUTH_MESSAGE_SUCCESS);
		} catch (Exception e) {
			if (e instanceof ServiceException) {
				json.setMsg(e.getMessage());
			} else {
				json.setMsg(UtilMessage.AUTH_MESSAGE_ERROR);
			}
			e.printStackTrace();
		}
		return json;
	}
    
    /**
     * 把角色授权给员工
     *
     * @param inData
     * @param request
     * @return
     */
    @GetMapping("/setRoleToUser")
    public Json setRoleToUser(SysRoleAuthorizeInData inData) {
    	Json json = new Json();
    	try {
    		sysRoleService.setSystemRoletoUser(inData);
    		json.setSuccess(true);
    		json.setMsg(UtilMessage.AUTH_MESSAGE_SUCCESS);
    	} catch (Exception e) {
    		if (e instanceof ServiceException) {
    			json.setMsg(e.getMessage());
    		} else {
    			json.setMsg(UtilMessage.AUTH_MESSAGE_ERROR);
    		}
    		e.printStackTrace();
    	}
    	return json;
    }
	/**
	 * 获取SessionInfo
	 * 
	 * @param request
	 * @return
	 */
	protected GetSessionInfoOutData getSessinInfo(HttpServletRequest request) {
		// 获取sessionInfo
		GetSessionInfoOutData sessionInfo = (GetSessionInfoOutData) request.getSession()
				.getAttribute(UtilConst.USER_SESSION);
		return sessionInfo;
	}
}
