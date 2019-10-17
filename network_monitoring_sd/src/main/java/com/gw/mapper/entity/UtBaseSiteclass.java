package com.gw.mapper.entity;

import lombok.Data;

import java.io.Serializable;
import javax.persistence.*;

@Data
@Table(name = "UT_Base_SiteClass")
public class UtBaseSiteclass implements Serializable {
    @Id
    @Column(name = "ID")
    private Long id;

    @Column(name = "SiteClassName")
    private String siteclassname;

    @Column(name = "SiteClassDesc")
    private String siteclassdesc;

    @Column(name = "UnitID")
    private Long unitid;

    private static final long serialVersionUID = 1L;
}