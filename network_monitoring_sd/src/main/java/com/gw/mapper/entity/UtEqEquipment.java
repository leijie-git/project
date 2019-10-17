package com.gw.mapper.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.*;

@Table(name = "UT_EQ_Equipment")
public class UtEqEquipment implements Serializable {
    @Id
    @Column(name = "ID")
    private Long id;

    @Column(name = "ProtocolType")
    private Integer protocoltype;

    @Column(name = "EqName")
    private String eqname;

    @Column(name = "EqModel")
    private String eqmodel;

    @Column(name = "PartCode")
    private String partcode;

    @Column(name = "LoopCode")
    private String loopcode;

    @Column(name = "PositionNumber")
    private String positionnumber;

    @Column(name = "InstallDate")
    private Date installdate;

    @Column(name = "InstallPosition")
    private String installposition;

    @Column(name = "PointX")
    private String pointx;

    @Column(name = "PointY")
    private String pointy;

    @Column(name = "Manufacturer")
    private String manufacturer;

    @Column(name = "ManufacturerPhone")
    private String manufacturerphone;

    @Column(name = "Brand")
    private String brand;

    @Column(name = "ValidityDate")
    private Date validitydate;

    @Column(name = "ProductDate")
    private Date productdate;

    @Column(name = "Supplier")
    private String supplier;

    @Column(name = "PointCode")
    private String pointcode;

    @Column(name = "Status")
    private Integer status;

    @Column(name = "StatusTime")
    private Date statustime;

    @Column(name = "UseTime")
    private Date usetime;

    @Column(name = "PipeDiameter")
    private BigDecimal pipediameter;

    @Column(name = "EqCapacity")
    private BigDecimal eqcapacity;

    @Column(name = "EqFlow")
    private BigDecimal eqflow;

    @Column(name = "EqLift")
    private BigDecimal eqlift;

    @Column(name = "PipeType")
    private String pipetype;

    @Column(name = "EqSpace")
    private BigDecimal eqspace;

    @Column(name = "AirVolume")
    private BigDecimal airvolume;

    @Column(name = "EqPower")
    private BigDecimal eqpower;

    @Column(name = "EqChangeDate")
    private Date eqchangedate;

    @Column(name = "Lastupdate")
    private Date lastupdate;

    @Column(name = "OwnerCode")
    private Long ownercode;

    @Column(name = "SystemType")
    private Long systemtype;

    @Column(name = "SystemAdd")
    private Long systemadd;

    @Column(name = "DataFrom")
    private Integer datafrom;

    @Column(name = "DataFromID")
    private String datafromid;

    @Column(name = "IsNeedInspect")
    private Integer isneedinspect;

    @Column(name = "QRCode")
    private String qrcode;

    @Column(name = "Floors")
    private Integer floors;

    @Column(name = "EqSystemID")
    private Long eqsystemid;

    @Column(name = "EqClassID")
    private Long eqclassid;

    @Column(name = "UnitID")
    private Long unitid;

    @Column(name = "BuildID")
    private Long buildid;

    @Column(name = "BuildAreaID")
    private Long buildareaid;

    @Column(name = "NetDeviceID")
    private Long netdeviceid;

    @Column(name = "pointVideoId")
    private Long pointVideoId;

    @Column(name = "isDelete")
    private Integer isdelete;

    @Column(name = "BuildImgbg")
    private String buildImgbg;
//类型
    @Column(name = "deviceindex")
    private String deviceindex;

    public String getDeviceindex() {
        return deviceindex;
    }

    public void setDeviceindex(String deviceindex) {
        this.deviceindex = deviceindex;
    }

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
     * @return ProtocolType
     */
    public Integer getProtocoltype() {
        return protocoltype;
    }

    /**
     * @param protocoltype
     */
    public void setProtocoltype(Integer protocoltype) {
        this.protocoltype = protocoltype;
    }

    /**
     * @return EqName
     */
    public String getEqname() {
        return eqname;
    }

    /**
     * @param eqname
     */
    public void setEqname(String eqname) {
        this.eqname = eqname;
    }

    /**
     * @return EqModel
     */
    public String getEqmodel() {
        return eqmodel;
    }

    /**
     * @param eqmodel
     */
    public void setEqmodel(String eqmodel) {
        this.eqmodel = eqmodel;
    }

    /**
     * @return PartCode
     */
    public String getPartcode() {
        return partcode;
    }

    /**
     * @param partcode
     */
    public void setPartcode(String partcode) {
        this.partcode = partcode;
    }

    /**
     * @return LoopCode
     */
    public String getLoopcode() {
        return loopcode;
    }

    /**
     * @param loopcode
     */
    public void setLoopcode(String loopcode) {
        this.loopcode = loopcode;
    }

    /**
     * @return PositionNumber
     */
    public String getPositionnumber() {
        return positionnumber;
    }

    /**
     * @param positionnumber
     */
    public void setPositionnumber(String positionnumber) {
        this.positionnumber = positionnumber;
    }

    /**
     * @return InstallDate
     */
    public Date getInstalldate() {
        return installdate;
    }

    /**
     * @param installdate
     */
    public void setInstalldate(Date installdate) {
        this.installdate = installdate;
    }

    /**
     * @return InstallPosition
     */
    public String getInstallposition() {
        return installposition;
    }

    /**
     * @param installposition
     */
    public void setInstallposition(String installposition) {
        this.installposition = installposition;
    }

    /**
     * @return PointX
     */
    public String getPointx() {
        return pointx;
    }

    /**
     * @param pointx
     */
    public void setPointx(String pointx) {
        this.pointx = pointx;
    }

    /**
     * @return PointY
     */
    public String getPointy() {
        return pointy;
    }

    /**
     * @param pointy
     */
    public void setPointy(String pointy) {
        this.pointy = pointy;
    }

    /**
     * @return Manufacturer
     */
    public String getManufacturer() {
        return manufacturer;
    }

    /**
     * @param manufacturer
     */
    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    /**
     * @return ManufacturerPhone
     */
    public String getManufacturerphone() {
        return manufacturerphone;
    }

    /**
     * @param manufacturerphone
     */
    public void setManufacturerphone(String manufacturerphone) {
        this.manufacturerphone = manufacturerphone;
    }

    /**
     * @return Brand
     */
    public String getBrand() {
        return brand;
    }

    /**
     * @param brand
     */
    public void setBrand(String brand) {
        this.brand = brand;
    }

    /**
     * @return ValidityDate
     */
    public Date getValiditydate() {
        return validitydate;
    }

    /**
     * @param validitydate
     */
    public void setValiditydate(Date validitydate) {
        this.validitydate = validitydate;
    }

    /**
     * @return ProductDate
     */
    public Date getProductdate() {
        return productdate;
    }

    /**
     * @param productdate
     */
    public void setProductdate(Date productdate) {
        this.productdate = productdate;
    }

    /**
     * @return Supplier
     */
    public String getSupplier() {
        return supplier;
    }

    /**
     * @param supplier
     */
    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    /**
     * @return PointCode
     */
    public String getPointcode() {
        return pointcode;
    }

    /**
     * @param pointcode
     */
    public void setPointcode(String pointcode) {
        this.pointcode = pointcode;
    }

    /**
     * @return Status
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * @param status
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * @return StatusTime
     */
    public Date getStatustime() {
        return statustime;
    }

    /**
     * @param statustime
     */
    public void setStatustime(Date statustime) {
        this.statustime = statustime;
    }

    /**
     * @return UseTime
     */
    public Date getUsetime() {
        return usetime;
    }

    /**
     * @param usetime
     */
    public void setUsetime(Date usetime) {
        this.usetime = usetime;
    }

    /**
     * @return PipeDiameter
     */
    public BigDecimal getPipediameter() {
        return pipediameter;
    }

    /**
     * @param pipediameter
     */
    public void setPipediameter(BigDecimal pipediameter) {
        this.pipediameter = pipediameter;
    }

    /**
     * @return EqCapacity
     */
    public BigDecimal getEqcapacity() {
        return eqcapacity;
    }

    /**
     * @param eqcapacity
     */
    public void setEqcapacity(BigDecimal eqcapacity) {
        this.eqcapacity = eqcapacity;
    }

    /**
     * @return EqFlow
     */
    public BigDecimal getEqflow() {
        return eqflow;
    }

    /**
     * @param eqflow
     */
    public void setEqflow(BigDecimal eqflow) {
        this.eqflow = eqflow;
    }

    /**
     * @return EqLift
     */
    public BigDecimal getEqlift() {
        return eqlift;
    }

    /**
     * @param eqlift
     */
    public void setEqlift(BigDecimal eqlift) {
        this.eqlift = eqlift;
    }

    /**
     * @return PipeType
     */
    public String getPipetype() {
        return pipetype;
    }

    /**
     * @param pipetype
     */
    public void setPipetype(String pipetype) {
        this.pipetype = pipetype;
    }

    /**
     * @return EqSpace
     */
    public BigDecimal getEqspace() {
        return eqspace;
    }

    /**
     * @param eqspace
     */
    public void setEqspace(BigDecimal eqspace) {
        this.eqspace = eqspace;
    }

    /**
     * @return AirVolume
     */
    public BigDecimal getAirvolume() {
        return airvolume;
    }

    /**
     * @param airvolume
     */
    public void setAirvolume(BigDecimal airvolume) {
        this.airvolume = airvolume;
    }

    /**
     * @return EqPower
     */
    public BigDecimal getEqpower() {
        return eqpower;
    }

    /**
     * @param eqpower
     */
    public void setEqpower(BigDecimal eqpower) {
        this.eqpower = eqpower;
    }

    /**
     * @return EqChangeDate
     */
    public Date getEqchangedate() {
        return eqchangedate;
    }

    /**
     * @param eqchangedate
     */
    public void setEqchangedate(Date eqchangedate) {
        this.eqchangedate = eqchangedate;
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
     * @return OwnerCode
     */
    public Long getOwnercode() {
        return ownercode;
    }

    /**
     * @param ownercode
     */
    public void setOwnercode(Long ownercode) {
        this.ownercode = ownercode;
    }

    /**
     * @return SystemType
     */
    public Long getSystemtype() {
        return systemtype;
    }

    /**
     * @param systemtype
     */
    public void setSystemtype(Long systemtype) {
        this.systemtype = systemtype;
    }

    /**
     * @return SystemAdd
     */
    public Long getSystemadd() {
        return systemadd;
    }

    /**
     * @param systemadd
     */
    public void setSystemadd(Long systemadd) {
        this.systemadd = systemadd;
    }

    /**
     * @return DataFrom
     */
    public Integer getDatafrom() {
        return datafrom;
    }

    /**
     * @param datafrom
     */
    public void setDatafrom(Integer datafrom) {
        this.datafrom = datafrom;
    }

    /**
     * @return DataFromID
     */
    public String getDatafromid() {
        return datafromid;
    }

    /**
     * @param datafromid
     */
    public void setDatafromid(String datafromid) {
        this.datafromid = datafromid;
    }

    /**
     * @return IsNeedInspect
     */
    public Integer getIsneedinspect() {
        return isneedinspect;
    }

    /**
     * @param isneedinspect
     */
    public void setIsneedinspect(Integer isneedinspect) {
        this.isneedinspect = isneedinspect;
    }

    /**
     * @return QRCode
     */
    public String getQrcode() {
        return qrcode;
    }

    /**
     * @param qrcode
     */
    public void setQrcode(String qrcode) {
        this.qrcode = qrcode;
    }

    /**
     * @return Floors
     */
    public Integer getFloors() {
        return floors;
    }

    /**
     * @param floors
     */
    public void setFloors(Integer floors) {
        this.floors = floors;
    }

    /**
     * @return EqSystemID
     */
    public Long getEqsystemid() {
        return eqsystemid;
    }

    /**
     * @param eqsystemid
     */
    public void setEqsystemid(Long eqsystemid) {
        this.eqsystemid = eqsystemid;
    }

    /**
     * @return EqClassID
     */
    public Long getEqclassid() {
        return eqclassid;
    }

    /**
     * @param eqclassid
     */
    public void setEqclassid(Long eqclassid) {
        this.eqclassid = eqclassid;
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

    /**
     * @return BuildID
     */
    public Long getBuildid() {
        return buildid;
    }

    /**
     * @param buildid
     */
    public void setBuildid(Long buildid) {
        this.buildid = buildid;
    }

    /**
     * @return BuildAreaID
     */
    public Long getBuildareaid() {
        return buildareaid;
    }

    /**
     * @param buildareaid
     */
    public void setBuildareaid(Long buildareaid) {
        this.buildareaid = buildareaid;
    }

    /**
     * @return NetDeviceID
     */
    public Long getNetdeviceid() {
        return netdeviceid;
    }

    /**
     * @param netdeviceid
     */
    public void setNetdeviceid(Long netdeviceid) {
        this.netdeviceid = netdeviceid;
    }

    public Integer getIsdelete() {
        return isdelete;
    }

    public void setIsdelete(Integer isdelete) {
        this.isdelete = isdelete;
    }

    public String getBuildImgbg() {
        return buildImgbg;
    }

    public void setBuildImgbg(String buildImgbg) {
        this.buildImgbg = buildImgbg;
    }

    public Long getPointVideoId() {
        return pointVideoId;
    }

    public void setPointVideoId(Long pointVideoId) {
        this.pointVideoId = pointVideoId;
    }
}