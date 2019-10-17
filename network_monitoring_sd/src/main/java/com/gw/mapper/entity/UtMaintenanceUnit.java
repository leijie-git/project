package com.gw.mapper.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Data
@Table(name = "UT_Maintenance_Unit")
public class UtMaintenanceUnit implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "ID")
    private Long id;
    @Column(name = "UnitCode")
    private String unitcode;
    @Column(name = "UnitName")
    private String unitname;
    @Column(name = "Address")
    private String address;
    @Column(name = "Contacts")
    private String contacts;
    @Column(name = "Telephone")
    private String telephone;
    @Column(name = "PointX")
    private String pointx;
    @Column(name = "PointY")
    private String pointy;
    @Column(name = "Remark")
    private String remark;
    @Column(name = "CreateDate")
    private Date createdate;
    @Column(name = "CreateUserID")
    private Long createuserid;
    @Column(name = "PID")
    private Long pid;
    @Column(name = "ProviceID")
    private Long proviceid;
    @Column(name = "CityID")
    private Long cityid;
    @Column(name = "RegionID")
    private Long regionid;
    @Column(name = "TownID")
    private Long townid;
    private String email;
    @Column(name = "logoUrl")
    private String logourl;
	@Column(name = "systemNumber")
	private String systemNumber;
    @Column(name = "mainType")
    private Integer mainType;


}