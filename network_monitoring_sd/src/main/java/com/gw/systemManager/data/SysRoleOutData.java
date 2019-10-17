package com.gw.systemManager.data;

import lombok.Data;

import java.util.List;
/**
 * 输出角色实体
 * @author zfg
 *
 */
@Data
public class SysRoleOutData {

    private String id;

    private String roleName;

    private Integer roleType;

    private Integer sortIndex;

    private List<String> resourceId;

    private String addDate;

    private String updateDate;

    private Boolean isDeleted;

    private Boolean isAdmin;

    private String type;

}
