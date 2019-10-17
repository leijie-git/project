package com.gw.mapper.entity;

import java.io.Serializable;
import javax.persistence.*;

@Table(name = "SYS_USER_ROLE")
public class SysUserRole implements Serializable {
    @Id
    @Column(name = "UserID")
    private Long userid;

    @Id
    @Column(name = "RoleID")
    private Long roleid;

    private static final long serialVersionUID = 1L;

    /**
     * @return UserID
     */
    public Long getUserid() {
        return userid;
    }

    /**
     * @param userid
     */
    public void setUserid(Long userid) {
        this.userid = userid;
    }

    /**
     * @return RoleID
     */
    public Long getRoleid() {
        return roleid;
    }

    /**
     * @param roleid
     */
    public void setRoleid(Long roleid) {
        this.roleid = roleid;
    }
}