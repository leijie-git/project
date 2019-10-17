package com.gw.mapper.entity;

import java.io.Serializable;
import javax.persistence.*;

@Table(name = "SYS_RESOURCE_ROLE")
public class SysResourceRole implements Serializable {
    @Column(name = "RESOURCE_ID")
    private Long resourceId;

    @Column(name = "ROLE_ID")
    private Long roleId;

    private static final long serialVersionUID = 1L;

    /**
     * @return RESOURCE_ID
     */
    public Long getResourceId() {
        return resourceId;
    }

    /**
     * @param resourceId
     */
    public void setResourceId(Long resourceId) {
        this.resourceId = resourceId;
    }

    /**
     * @return ROLE_ID
     */
    public Long getRoleId() {
        return roleId;
    }

    /**
     * @param roleId
     */
    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }
}