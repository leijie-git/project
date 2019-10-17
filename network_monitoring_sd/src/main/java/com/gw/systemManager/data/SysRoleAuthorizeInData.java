package com.gw.systemManager.data;

import lombok.Data;

@Data
public class SysRoleAuthorizeInData {
	
	private Long userIdOne;
    /**
     * 用户id, 用,分隔
     */
    private String userId;

    /**
     * 角色id, 唯一
     */
    private Long roleId;

    /**
     * 很多角色id, 用,分隔
     */
    private String manyRoleId;


}
