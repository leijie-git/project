package com.gw.mapper.entity;

import java.io.Serializable;
import javax.persistence.*;

@Table(name = "UT_Base_ProviceCityRegion")
public class UtBaseProvicecityregion implements Serializable {
    @Id
    @Column(name = "ID")
    private Long id;

    @Column(name = "Type")
    private Integer type;

    @Column(name = "RegionName")
    private String regionname;

    @Column(name = "RegionCode")
    private String regioncode;

    @Column(name = "ParentID")
    private Long parentid;

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
     * @return Type
     */
    public Integer getType() {
        return type;
    }

    /**
     * @param type
     */
    public void setType(Integer type) {
        this.type = type;
    }

    /**
     * @return RegionName
     */
    public String getRegionname() {
        return regionname;
    }

    /**
     * @param regionname
     */
    public void setRegionname(String regionname) {
        this.regionname = regionname;
    }

    /**
     * @return RegionCode
     */
    public String getRegioncode() {
        return regioncode;
    }

    /**
     * @param regioncode
     */
    public void setRegioncode(String regioncode) {
        this.regioncode = regioncode;
    }

    /**
     * @return ParentID
     */
    public Long getParentid() {
        return parentid;
    }

    /**
     * @param parentid
     */
    public void setParentid(Long parentid) {
        this.parentid = parentid;
    }
}