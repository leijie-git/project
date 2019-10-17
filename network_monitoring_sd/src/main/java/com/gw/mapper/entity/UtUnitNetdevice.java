package com.gw.mapper.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Data
@Table(name = "UT_Unit_NetDevice")
public class UtUnitNetdevice implements Serializable {
    @Id
    @Column(name = "ID")
    private Long id;

    @Column(name = "OwnerCode")
    private String ownercode;

    @Column(name = "DeviceIndex")
    private Integer deviceindex;

    @Column(name = "DeviceNo")
    private Integer deviceno;

    @Column(name = "CreateDate")
    private Date createdate;

    @Column(name = "UpdateDate")
    private String updatedate;

    @Column(name = "UnitID")
    private Long unitid;

    @Column(name = "CreateUser")
    private Long createuser;

    @Column(name = "UpdateUser")
    private Long updateuser;

    private String name;

    @Column(name = "calibration_id")
    private Long calibrationId;

    @Column(name = "isDelete")
    private Integer isdelete;

    @Column(name = "EqSystemID")
    private Long eqsystemid;

    @Column(name = "EquipmentModel")
    private Long equipmentmodel;

    @Column(name = "start_date")
    private Date startdate;

    @Column(name = "end_date")
    private Date enddate;

    @Column(name = "calibration_remark")
    private String calibrationremark;

    @Column(name = "is_independent")
    private String isIndependent;

    @Column(name = "isUploadA")
    private Integer isuploada;

    @Column(name = "isUploadB")
    private Integer isuploadb;

    @Column(name = "DeviceStatus")
    private Integer deviceStatus;

    private static final long serialVersionUID = 1L;

}