package com.gw.mapper.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

@Table(name = "UT_Unit_Video")
public class UtUnitVideo implements Serializable {
    @Id
    @Column(name = "ID")
    private Long id;

    @Column(name = "VideoClass")
    private Integer videoclass;

    @Column(name = "VideoType")
    private Integer videotype;

    @Column(name = "IP")
    private String ip;

    @Column(name = "Port")
    private Integer port;

    @Column(name = "UserName")
    private String username;

    @Column(name = "Password")
    private String password;

    @Column(name = "Position")
    private String position;

    @Column(name = "PosCode")
    private String poscode;

    @Column(name = "PlugInType")
    private String plugintype;

    @Column(name = "Manufactor")
    private String manufactor;

    @Column(name = "Brand")
    private String brand;

    @Column(name = "Remark")
    private String remark;

    @Column(name = "Lastupdate")
    private Date lastupdate;

    @Column(name = "UnitID")
    private Long unitid;

    @Column(name = "BuildID")
    private Long buildid;

    @Column(name = "BuildAreaID")
    private Long buildareaid;
    
    private String name;

    @Column(name = "serialNumber")
    private String serialnumber;
    
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
     * @return VideoClass
     */
    public Integer getVideoclass() {
        return videoclass;
    }

    /**
     * @param videoclass
     */
    public void setVideoclass(Integer videoclass) {
        this.videoclass = videoclass;
    }

    /**
     * @return VideoType
     */
    public Integer getVideotype() {
        return videotype;
    }

    /**
     * @param videotype
     */
    public void setVideotype(Integer videotype) {
        this.videotype = videotype;
    }

    /**
     * @return IP
     */
    public String getIp() {
        return ip;
    }

    /**
     * @param ip
     */
    public void setIp(String ip) {
        this.ip = ip;
    }

    /**
     * @return Port
     */
    public Integer getPort() {
        return port;
    }

    /**
     * @param port
     */
    public void setPort(Integer port) {
        this.port = port;
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
     * @return Password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return Position
     */
    public String getPosition() {
        return position;
    }

    /**
     * @param position
     */
    public void setPosition(String position) {
        this.position = position;
    }

    /**
     * @return PosCode
     */
    public String getPoscode() {
        return poscode;
    }

    /**
     * @param poscode
     */
    public void setPoscode(String poscode) {
        this.poscode = poscode;
    }

    /**
     * @return PlugInType
     */
    public String getPlugintype() {
        return plugintype;
    }

    /**
     * @param plugintype
     */
    public void setPlugintype(String plugintype) {
        this.plugintype = plugintype;
    }

    /**
     * @return Manufactor
     */
    public String getManufactor() {
        return manufactor;
    }

    /**
     * @param manufactor
     */
    public void setManufactor(String manufactor) {
        this.manufactor = manufactor;
    }

    /**
     * @return Brand
     */
    public String getBrand() {
        return brand;
    }

    /**
     * @param brand
     */
    public void setBrand(String brand) {
        this.brand = brand;
    }

    /**
     * @return Remark
     */
    public String getRemark() {
        return remark;
    }

    /**
     * @param remark
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }

    /**
     * @return Lastupdate
     */
    public Date getLastupdate() {
        return lastupdate;
    }

    /**
     * @param lastupdate
     */
    public void setLastupdate(Date lastupdate) {
        this.lastupdate = lastupdate;
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
     * @return BuildAreaID
     */
    public Long getBuildareaid() {
        return buildareaid;
    }

    /**
     * @param buildareaid
     */
    public void setBuildareaid(Long buildareaid) {
        this.buildareaid = buildareaid;
    }

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSerialnumber() {
		return serialnumber;
	}

	public void setSerialnumber(String serialnumber) {
		this.serialnumber = serialnumber;
	}
    
}