package com.gw.mapper.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

@Table(name = "UT_hd_siterwell")
public class UtHdSiterwell implements Serializable {
    @Id
    @Column(name = "ID")
    private Long id;

    private Integer isrelation;

    private String ownercode;

    private Integer deviceindex;

    private Integer deviceno;

    private Integer usingtype;

    private Integer gatewayid;

    private String gatewaycode;

    private String reserve;

    private Date lastupate;

    private String devicecode;

    private String installaddr;

    private String notifyphone;

    private Integer currentstatus;

    private String devicetype;

    private String protocoltype;

    private String model;

    private String manufacturename;

    private String imsi;

    private Float lon;

    private Float lat;

    private Integer battery;

    private Integer signal;

    private Integer isphone;

    private Date firsttime;

    private Long heartbeat;
    
    private String netdeviceid;
    
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
     * @return isrelation
     */
    public Integer getIsrelation() {
        return isrelation;
    }

    /**
     * @param isrelation
     */
    public void setIsrelation(Integer isrelation) {
        this.isrelation = isrelation;
    }

    /**
     * @return ownercode
     */
    public String getOwnercode() {
        return ownercode;
    }

    /**
     * @param ownercode
     */
    public void setOwnercode(String ownercode) {
        this.ownercode = ownercode;
    }

    /**
     * @return deviceindex
     */
    public Integer getDeviceindex() {
        return deviceindex;
    }

    /**
     * @param deviceindex
     */
    public void setDeviceindex(Integer deviceindex) {
        this.deviceindex = deviceindex;
    }

    /**
     * @return deviceno
     */
    public Integer getDeviceno() {
        return deviceno;
    }

    /**
     * @param deviceno
     */
    public void setDeviceno(Integer deviceno) {
        this.deviceno = deviceno;
    }

    /**
     * @return usingtype
     */
    public Integer getUsingtype() {
        return usingtype;
    }

    /**
     * @param usingtype
     */
    public void setUsingtype(Integer usingtype) {
        this.usingtype = usingtype;
    }

    /**
     * @return gatewayid
     */
    public Integer getGatewayid() {
        return gatewayid;
    }

    /**
     * @param gatewayid
     */
    public void setGatewayid(Integer gatewayid) {
        this.gatewayid = gatewayid;
    }

    /**
     * @return gatewaycode
     */
    public String getGatewaycode() {
        return gatewaycode;
    }

    /**
     * @param gatewaycode
     */
    public void setGatewaycode(String gatewaycode) {
        this.gatewaycode = gatewaycode;
    }

    /**
     * @return reserve
     */
    public String getReserve() {
        return reserve;
    }

    /**
     * @param reserve
     */
    public void setReserve(String reserve) {
        this.reserve = reserve;
    }

    /**
     * @return lastupate
     */
    public Date getLastupate() {
        return lastupate;
    }

    /**
     * @param lastupate
     */
    public void setLastupate(Date lastupate) {
        this.lastupate = lastupate;
    }

    /**
     * @return devicecode
     */
    public String getDevicecode() {
        return devicecode;
    }

    /**
     * @param devicecode
     */
    public void setDevicecode(String devicecode) {
        this.devicecode = devicecode;
    }

    /**
     * @return installaddr
     */
    public String getInstalladdr() {
        return installaddr;
    }

    /**
     * @param installaddr
     */
    public void setInstalladdr(String installaddr) {
        this.installaddr = installaddr;
    }

    /**
     * @return notifyphone
     */
    public String getNotifyphone() {
        return notifyphone;
    }

    /**
     * @param notifyphone
     */
    public void setNotifyphone(String notifyphone) {
        this.notifyphone = notifyphone;
    }

    /**
     * @return currentstatus
     */
    public Integer getCurrentstatus() {
        return currentstatus;
    }

    /**
     * @param currentstatus
     */
    public void setCurrentstatus(Integer currentstatus) {
        this.currentstatus = currentstatus;
    }

    /**
     * @return devicetype
     */
    public String getDevicetype() {
        return devicetype;
    }

    /**
     * @param devicetype
     */
    public void setDevicetype(String devicetype) {
        this.devicetype = devicetype;
    }

    /**
     * @return protocoltype
     */
    public String getProtocoltype() {
        return protocoltype;
    }

    /**
     * @param protocoltype
     */
    public void setProtocoltype(String protocoltype) {
        this.protocoltype = protocoltype;
    }

    /**
     * @return model
     */
    public String getModel() {
        return model;
    }

    /**
     * @param model
     */
    public void setModel(String model) {
        this.model = model;
    }

    /**
     * @return manufacturename
     */
    public String getManufacturename() {
        return manufacturename;
    }

    /**
     * @param manufacturename
     */
    public void setManufacturename(String manufacturename) {
        this.manufacturename = manufacturename;
    }

    /**
     * @return imsi
     */
    public String getImsi() {
        return imsi;
    }

    /**
     * @param imsi
     */
    public void setImsi(String imsi) {
        this.imsi = imsi;
    }

    /**
     * @return lon
     */
    public Float getLon() {
        return lon;
    }

    /**
     * @param lon
     */
    public void setLon(Float lon) {
        this.lon = lon;
    }

    /**
     * @return lat
     */
    public Float getLat() {
        return lat;
    }

    /**
     * @param lat
     */
    public void setLat(Float lat) {
        this.lat = lat;
    }

    /**
     * @return battery
     */
    public Integer getBattery() {
        return battery;
    }

    /**
     * @param battery
     */
    public void setBattery(Integer battery) {
        this.battery = battery;
    }

    /**
     * @return signal
     */
    public Integer getSignal() {
        return signal;
    }

    /**
     * @param signal
     */
    public void setSignal(Integer signal) {
        this.signal = signal;
    }

    /**
     * @return isphone
     */
    public Integer getIsphone() {
        return isphone;
    }

    /**
     * @param isphone
     */
    public void setIsphone(Integer isphone) {
        this.isphone = isphone;
    }

    /**
     * @return firsttime
     */
    public Date getFirsttime() {
        return firsttime;
    }

    /**
     * @param firsttime
     */
    public void setFirsttime(Date firsttime) {
        this.firsttime = firsttime;
    }

    /**
     * @return heartbeat
     */
    public Long getHeartbeat() {
        return heartbeat;
    }

    /**
     * @param heartbeat
     */
    public void setHeartbeat(Long heartbeat) {
        this.heartbeat = heartbeat;
    }
    
    public String getNetdeviceid() {
		return netdeviceid;
	}

	public void setNetdeviceid(String netdeviceid) {
		this.netdeviceid = netdeviceid;
	}

}