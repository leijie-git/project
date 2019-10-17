package com.gw.mapper.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

@Table(name = "UT_LZ_DayInspect")
public class UtLzDayinspect implements Serializable {
    @Id
    @Column(name = "ID")
    private Long id;

    @Column(name = "InspectCode")
    private String inspectcode;

    @Column(name = "InspectDate")
    private Date inspectdate;

    @Column(name = "InspectUser")
    private String inspectuser;

    @Column(name = "InspectCount")
    private Integer inspectcount;

    @Column(name = "InspectInfo")
    private String inspectinfo;

    @Column(name = "Remark")
    private String remark;

    @Column(name = "CheckDate")
    private Date checkdate;

    @Column(name = "CheckUser")
    private String checkuser;

    @Column(name = "ManageUser")
    private String manageuser;

    @Column(name = "CreateDate")
    private Date createdate;

    @Column(name = "CreateUser")
    private Long createuser;

    @Column(name = "IsOK")
    private Integer isok;

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
     * @return InspectCode
     */
    public String getInspectcode() {
        return inspectcode;
    }

    /**
     * @param inspectcode
     */
    public void setInspectcode(String inspectcode) {
        this.inspectcode = inspectcode;
    }

    /**
     * @return InspectDate
     */
    public Date getInspectdate() {
        return inspectdate;
    }

    /**
     * @param inspectdate
     */
    public void setInspectdate(Date inspectdate) {
        this.inspectdate = inspectdate;
    }

    /**
     * @return InspectUser
     */
    public String getInspectuser() {
        return inspectuser;
    }

    /**
     * @param inspectuser
     */
    public void setInspectuser(String inspectuser) {
        this.inspectuser = inspectuser;
    }

    /**
     * @return InspectCount
     */
    public Integer getInspectcount() {
        return inspectcount;
    }

    /**
     * @param inspectcount
     */
    public void setInspectcount(Integer inspectcount) {
        this.inspectcount = inspectcount;
    }

    /**
     * @return InspectInfo
     */
    public String getInspectinfo() {
        return inspectinfo;
    }

    /**
     * @param inspectinfo
     */
    public void setInspectinfo(String inspectinfo) {
        this.inspectinfo = inspectinfo;
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
     * @return CheckDate
     */
    public Date getCheckdate() {
        return checkdate;
    }

    /**
     * @param checkdate
     */
    public void setCheckdate(Date checkdate) {
        this.checkdate = checkdate;
    }

    /**
     * @return CheckUser
     */
    public String getCheckuser() {
        return checkuser;
    }

    /**
     * @param checkuser
     */
    public void setCheckuser(String checkuser) {
        this.checkuser = checkuser;
    }

    /**
     * @return ManageUser
     */
    public String getManageuser() {
        return manageuser;
    }

    /**
     * @param manageuser
     */
    public void setManageuser(String manageuser) {
        this.manageuser = manageuser;
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
     * @return IsOK
     */
    public Integer getIsok() {
        return isok;
    }

    /**
     * @param isok
     */
    public void setIsok(Integer isok) {
        this.isok = isok;
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