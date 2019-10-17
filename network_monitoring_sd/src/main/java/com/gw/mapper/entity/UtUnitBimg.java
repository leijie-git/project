package com.gw.mapper.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

@Table(name = "UT_Unit_BImg")
public class UtUnitBimg implements Serializable {
    @Id
    @Column(name = "ID")
    private Long id;

    @Column(name = "ShowName")
    private String showname;

    @Column(name = "FileName")
    private String filename;

    @Column(name = "UploadDate")
    private Date uploaddate;

    @Column(name = "UploadUser")
    private Long uploaduser;

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
     * @return ShowName
     */
    public String getShowname() {
        return showname;
    }

    /**
     * @param showname
     */
    public void setShowname(String showname) {
        this.showname = showname;
    }

    /**
     * @return FileName
     */
    public String getFilename() {
        return filename;
    }

    /**
     * @param filename
     */
    public void setFilename(String filename) {
        this.filename = filename;
    }

    /**
     * @return UploadDate
     */
    public Date getUploaddate() {
        return uploaddate;
    }

    /**
     * @param uploaddate
     */
    public void setUploaddate(Date uploaddate) {
        this.uploaddate = uploaddate;
    }

    /**
     * @return UploadUser
     */
    public Long getUploaduser() {
        return uploaduser;
    }

    /**
     * @param uploaduser
     */
    public void setUploaduser(Long uploaduser) {
        this.uploaduser = uploaduser;
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