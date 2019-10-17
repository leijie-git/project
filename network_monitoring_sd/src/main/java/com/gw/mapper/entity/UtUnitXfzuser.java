package com.gw.mapper.entity;

import java.io.Serializable;
import javax.persistence.*;

@Table(name = "UT_Unit_XFZUser")
public class UtUnitXfzuser implements Serializable {
    @Id
    @Column(name = "ID")
    private Long id;

    @Column(name = "UserPost")
    private String userpost;

    @Column(name = "UserName")
    private String username;

    @Column(name = "Tel")
    private String tel;

    @Column(name = "UnitID")
    private Long unitid;

    private static final long serialVersionUID = 1L;

    /**
     * @return ID
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return UserPost
     */
    public String getUserpost() {
        return userpost;
    }

    /**
     * @param userpost
     */
    public void setUserpost(String userpost) {
        this.userpost = userpost;
    }

    /**
     * @return UserName
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @return Tel
     */
    public String getTel() {
        return tel;
    }

    /**
     * @param tel
     */
    public void setTel(String tel) {
        this.tel = tel;
    }

    /**
     * @return UnitID
     */
    public Long getUnitid() {
        return unitid;
    }

    /**
     * @param unitid
     */
    public void setUnitid(Long unitid) {
        this.unitid = unitid;
    }
}