package com.gw.mapper.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

@Table(name = "UT_Base_Attachment")
public class UtBaseAttachment implements Serializable {
    @Id
    @Column(name = "ID")
    private Long id;

    @Column(name = "FileName")
    private String filename;

    @Column(name = "ShowName")
    private String showname;

    @Column(name = "BaseType")
    private Integer basetype;

    @Column(name = "UploadDate")
    private Date uploaddate;

    @Column(name = "UploadUser")
    private Long uploaduser;

    @Column(name = "UploadUserName")
    private String uploadusername;

    @Column(name = "IsDelete")
    private Integer isdelete;

    @Column(name = "PathType")
    private Integer pathtype;

    @Column(name = "BaseID")
    private Long baseid;

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
     * @return BaseType
     */
    public Integer getBasetype() {
        return basetype;
    }

    /**
     * @param basetype
     */
    public void setBasetype(Integer basetype) {
        this.basetype = basetype;
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
     * @return UploadUserName
     */
    public String getUploadusername() {
        return uploadusername;
    }

    /**
     * @param uploadusername
     */
    public void setUploadusername(String uploadusername) {
        this.uploadusername = uploadusername;
    }

    /**
     * @return IsDelete
     */
    public Integer getIsdelete() {
        return isdelete;
    }

    /**
     * @param isdelete
     */
    public void setIsdelete(Integer isdelete) {
        this.isdelete = isdelete;
    }

    /**
     * @return PathType
     */
    public Integer getPathtype() {
        return pathtype;
    }

    /**
     * @param pathtype
     */
    public void setPathtype(Integer pathtype) {
        this.pathtype = pathtype;
    }

    /**
     * @return BaseID
     */
    public Long getBaseid() {
        return baseid;
    }

    /**
     * @param baseid
     */
    public void setBaseid(Long baseid) {
        this.baseid = baseid;
    }
}