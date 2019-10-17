package com.gw.mapper.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

@Table(name = "UT_Unit_Event")
public class UtUnitEvent implements Serializable {
    @Id
    @Column(name = "ID")
    private Long id;

    @Column(name = "OwnerCode")
    private String ownercode;

    @Column(name = "EventID")
    private Long eventid;

    @Column(name = "StartTime")
    private Date starttime;

    @Column(name = "EndTime")
    private Date endtime;

    @Column(name = "StartReason")
    private String startreason;

    @Column(name = "EndReason")
    private String endreason;

    @Column(name = "Reserve")
    private String reserve;

    @Column(name = "Lastupdate")
    private Date lastupdate;

    @Column(name = "DeviceIndex")
    private Integer deviceindex;

    @Column(name = "DeviceNo")
    private Integer deviceno;

    @Column(name = "UnitId")
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
     * @return OwnerCode
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
     * @return EventID
     */
    public Long getEventid() {
        return eventid;
    }

    /**
     * @param eventid
     */
    public void setEventid(Long eventid) {
        this.eventid = eventid;
    }

    /**
     * @return StartTime
     */
    public Date getStarttime() {
        return starttime;
    }

    /**
     * @param starttime
     */
    public void setStarttime(Date starttime) {
        this.starttime = starttime;
    }

    /**
     * @return EndTime
     */
    public Date getEndtime() {
        return endtime;
    }

    /**
     * @param endtime
     */
    public void setEndtime(Date endtime) {
        this.endtime = endtime;
    }

    /**
     * @return StartReason
     */
    public String getStartreason() {
        return startreason;
    }

    /**
     * @param startreason
     */
    public void setStartreason(String startreason) {
        this.startreason = startreason;
    }

    /**
     * @return EndReason
     */
    public String getEndreason() {
        return endreason;
    }

    /**
     * @param endreason
     */
    public void setEndreason(String endreason) {
        this.endreason = endreason;
    }

    /**
     * @return Reserve
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
     * @return DeviceIndex
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
     * @return DeviceNo
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
     * @return UnitId
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