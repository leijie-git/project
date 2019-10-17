package com.gw.mapper.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

@Table(name = "UT_Maintenance_login_Log")
public class UtMaintenanceLoginLog implements Serializable {
    @Id
    @Column(name = "ID")
    private Long id;

    @Column(name = "login_name")
    private String loginName;

    @Column(name = "login_date")
    private Date loginDate;

    @Column(name = "login_addr")
    private String loginAddr;

    @Column(name = "login_ip")
    private String loginIp;

    @Column(name = "login_out_date")
    private Date loginOutDate;
    
    @Column(name = "data_from")
    private String dataFrom;

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
     * @return login_name
     */
    public String getLoginName() {
        return loginName;
    }

    /**
     * @param loginName
     */
    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    /**
     * @return login_date
     */
    public Date getLoginDate() {
        return loginDate;
    }

    /**
     * @param loginDate
     */
    public void setLoginDate(Date loginDate) {
        this.loginDate = loginDate;
    }

    /**
     * @return login_addr
     */
    public String getLoginAddr() {
        return loginAddr;
    }

    /**
     * @param loginAddr
     */
    public void setLoginAddr(String loginAddr) {
        this.loginAddr = loginAddr;
    }

    /**
     * @return login_ip
     */
    public String getLoginIp() {
        return loginIp;
    }

    /**
     * @param loginIp
     */
    public void setLoginIp(String loginIp) {
        this.loginIp = loginIp;
    }

    /**
     * @return login_out_date
     */
    public Date getLoginOutDate() {
        return loginOutDate;
    }

    /**
     * @param loginOutDate
     */
    public void setLoginOutDate(Date loginOutDate) {
        this.loginOutDate = loginOutDate;
    }

	public String getDataFrom() {
		return dataFrom;
	}

	public void setDataFrom(String dataFrom) {
		this.dataFrom = dataFrom;
	}
    
}