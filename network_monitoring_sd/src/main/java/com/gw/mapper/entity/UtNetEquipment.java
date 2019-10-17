package com.gw.mapper.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

@Table(name = "UT_net_Equipment")
public class UtNetEquipment implements Serializable {
    @Id
    @Column(name = "ID")
    private Long id;

    @Column(name = "UnitID")
    private Long unitid;

    @Column(name = "BuildID")
    private Long buildid;
    
    @Column(name = "BuildAreaID")
    private Long buildareaid;
    
    @Column(name = "EqSystemID")
    private Long eqsystemid;

    @Column(name = "NetDeviceID")
    private Long netdeviceid;

    @Column(name = "EqType")
    private Integer eqtype;

    @Column(name = "EqCode")
    private String eqcode;

    @Column(name = "EqName")
    private String eqname;

    @Column(name = "InstallSite")
    private String installsite;

    @Column(name = "PointX")
    private Integer pointx;

    @Column(name = "PointY")
    private Integer pointy;

    @Column(name = "AlarmPushUserID")
    private String alarmpushuserid;

    @Column(name = "AlarmPushUserName")
    private String alarmpushusername;

    @Column(name = "CreateDate")
    private Date createdate;

    @Column(name = "CreateUser")
    private Long createuser;

    @Column(name = "LastUpdateDate")
    private Date lastupdatedate;

    @Column(name = "LastUpdateUser")
    private Long lastupdateuser;

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

    /**
     * @return BuildID
     */
    public Long getBuildid() {
        return buildid;
    }

    /**
     * @param buildid
     */
    public void setBuildid(Long buildid) {
        this.buildid = buildid;
    }

    /**
     * @return NetDeviceID
     */
    public Long getNetdeviceid() {
        return netdeviceid;
    }

    /**
     * @param netdeviceid
     */
    public void setNetdeviceid(Long netdeviceid) {
        this.netdeviceid = netdeviceid;
    }

    /**
     * @return EqType
     */
    public Integer getEqtype() {
        return eqtype;
    }

    /**
     * @param eqtype
     */
    public void setEqtype(Integer eqtype) {
        this.eqtype = eqtype;
    }

    /**
     * @return EqCode
     */
    public String getEqcode() {
        return eqcode;
    }

    /**
     * @param eqcode
     */
    public void setEqcode(String eqcode) {
        this.eqcode = eqcode;
    }

    /**
     * @return EqName
     */
    public String getEqname() {
        return eqname;
    }

    /**
     * @param eqname
     */
    public void setEqname(String eqname) {
        this.eqname = eqname;
    }

    /**
     * @return InstallSite
     */
    public String getInstallsite() {
        return installsite;
    }

    /**
     * @param installsite
     */
    public void setInstallsite(String installsite) {
        this.installsite = installsite;
    }

    /**
     * @return PointX
     */
    public Integer getPointx() {
        return pointx;
    }

    /**
     * @param pointx
     */
    public void setPointx(Integer pointx) {
        this.pointx = pointx;
    }

    /**
     * @return PointY
     */
    public Integer getPointy() {
        return pointy;
    }

    /**
     * @param pointy
     */
    public void setPointy(Integer pointy) {
        this.pointy = pointy;
    }

    /**
     * @return AlarmPushUserID
     */
    public String getAlarmpushuserid() {
        return alarmpushuserid;
    }

    /**
     * @param alarmpushuserid
     */
    public void setAlarmpushuserid(String alarmpushuserid) {
        this.alarmpushuserid = alarmpushuserid;
    }

    /**
     * @return AlarmPushUserName
     */
    public String getAlarmpushusername() {
        return alarmpushusername;
    }

    /**
     * @param alarmpushusername
     */
    public void setAlarmpushusername(String alarmpushusername) {
        this.alarmpushusername = alarmpushusername;
    }

    /**
     * @return CreateDate
     */
    public Date getCreatedate() {
        return createdate;
    }

    /**
     * @param createdate
     */
    public void setCreatedate(Date createdate) {
        this.createdate = createdate;
    }

    /**
     * @return CreateUser
     */
    public Long getCreateuser() {
        return createuser;
    }

    /**
     * @param createuser
     */
    public void setCreateuser(Long createuser) {
        this.createuser = createuser;
    }

    /**
     * @return LastUpdateDate
     */
    public Date getLastupdatedate() {
        return lastupdatedate;
    }

    /**
     * @param lastupdatedate
     */
    public void setLastupdatedate(Date lastupdatedate) {
        this.lastupdatedate = lastupdatedate;
    }

    /**
     * @return LastUpdateUser
     */
    public Long getLastupdateuser() {
        return lastupdateuser;
    }

    /**
     * @param lastupdateuser
     */
    public void setLastupdateuser(Long lastupdateuser) {
        this.lastupdateuser = lastupdateuser;
    }

	public Long getBuildareaid() {
		return buildareaid;
	}

	public void setBuildareaid(Long buildareaid) {
		this.buildareaid = buildareaid;
	}

	public Long getEqsystemid() {
		return eqsystemid;
	}

	public void setEqsystemid(Long eqsystemid) {
		this.eqsystemid = eqsystemid;
	}
    
}