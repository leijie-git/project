package com.gw.mapper.entity;

import java.io.Serializable;
import javax.persistence.*;

@Table(name = "UT_LZ_DrillFile")
public class UtLzDrillfile implements Serializable {
    @Id
    @Column(name = "ID")
    private Long id;

    @Column(name = "FileClass")
    private Integer fileclass;

    @Column(name = "FileType")
    private Integer filetype;

    @Column(name = "FileUrl")
    private String fileurl;

    @Column(name = "ShowName")
    private String showname;

    @Column(name = "FileName")
    private String filename;

    @Column(name = "DrillID")
    private Long drillid;

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
     * @return FileClass
     */
    public Integer getFileclass() {
        return fileclass;
    }

    /**
     * @param fileclass
     */
    public void setFileclass(Integer fileclass) {
        this.fileclass = fileclass;
    }

    /**
     * @return FileType
     */
    public Integer getFiletype() {
        return filetype;
    }

    /**
     * @param filetype
     */
    public void setFiletype(Integer filetype) {
        this.filetype = filetype;
    }

    /**
     * @return FileUrl
     */
    public String getFileurl() {
        return fileurl;
    }

    /**
     * @param fileurl
     */
    public void setFileurl(String fileurl) {
        this.fileurl = fileurl;
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
     * @return DrillID
     */
    public Long getDrillid() {
        return drillid;
    }

    /**
     * @param drillid
     */
    public void setDrillid(Long drillid) {
        this.drillid = drillid;
    }
}