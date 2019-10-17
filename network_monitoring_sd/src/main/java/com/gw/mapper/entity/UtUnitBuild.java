package com.gw.mapper.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.*;

@Table(name = "UT_Unit_Build")
public class UtUnitBuild implements Serializable {
    @Id
    @Column(name = "ID")
    private Long id;

    @Column(name = "BuildingName")
    private String buildingname;

    @Column(name = "BuildingType")
    private String buildingtype;

    @Column(name = "UserType")
    private String usertype;

    @Column(name = "FireRiskGrade")
    private String fireriskgrade;

    @Column(name = "RefractoryGrade")
    private String refractorygrade;

    @Column(name = "StructureType")
    private String structuretype;

    @Column(name = "BuildDate")
    private Date builddate;

    @Column(name = "BuildingHeight")
    private BigDecimal buildingheight;

    @Column(name = "BuildingArea")
    private BigDecimal buildingarea;

    @Column(name = "BuildingOccupyarea")
    private BigDecimal buildingoccupyarea;

    @Column(name = "StdFloorArea")
    private BigDecimal stdfloorarea;

    @Column(name = "OverGroundFloors")
    private BigDecimal overgroundfloors;

    @Column(name = "OverGroundArea")
    private BigDecimal overgroundarea;

    @Column(name = "UnderGroundFloors")
    private BigDecimal undergroundfloors;

    @Column(name = "UnderGroundArea")
    private BigDecimal undergroundarea;

    @Column(name = "TunnelHeight")
    private BigDecimal tunnelheight;

    @Column(name = "TunnelLength")
    private BigDecimal tunnellength;

    @Column(name = "FireRoomPosition")
    private String fireroomposition;

    @Column(name = "RefugeNum")
    private Integer refugenum;

    @Column(name = "RefugeArea")
    private BigDecimal refugearea;

    @Column(name = "RefugePosition")
    private String refugeposition;

    @Column(name = "SafeExitNum")
    private Integer safeexitnum;

    @Column(name = "SafeExitPosition")
    private String safeexitposition;

    @Column(name = "SafeExitType")
    private String safeexittype;

    @Column(name = "FireElevatorNum")
    private Integer fireelevatornum;

    @Column(name = "FireElevatorWeight")
    private BigDecimal fireelevatorweight;

    @Column(name = "DailyPersonNum")
    private Integer dailypersonnum;

    @Column(name = "AccommodatePersonMaxnum")
    private Integer accommodatepersonmaxnum;

    @Column(name = "StoreMaterialName")
    private String storematerialname;

    @Column(name = "StoreMaterialNum")
    private Integer storematerialnum;

    @Column(name = "StoreMaterialNature")
    private String storematerialnature;

    @Column(name = "StoreMaterialForm")
    private String storematerialform;

    @Column(name = "StoreVolume")
    private BigDecimal storevolume;

    @Column(name = "MainMaterial")
    private String mainmaterial;

    @Column(name = "MainProduct")
    private String mainproduct;

    @Column(name = "NearBuildingSituation")
    private String nearbuildingsituation;

    @Column(name = "BuildingElevationMap")
    private String buildingelevationmap;

    @Column(name = "BuildingPlan")
    private String buildingplan;

    @Column(name = "FacilitiesPlan")
    private String facilitiesplan;

    @Column(name = "Address")
    private String address;

    @Column(name = "AutoFireFacilities")
    private String autofirefacilities;

    @Column(name = "Floors")
    private BigDecimal floors;

    @Column(name = "EvacuationStirNum")
    private BigDecimal evacuationstirnum;

    @Column(name = "CreateDate")
    private Date createdate;

    @Column(name = "Lastupdate")
    private Date lastupdate;

    @Column(name = "UnitID")
    private Long unitid;

    @Column(name = "WBUnitID")
    private Long wbunitid;

    @Column(name = "IDFromData")
    private Long idfromdata;

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
     * @return BuildingName
     */
    public String getBuildingname() {
        return buildingname;
    }

    /**
     * @param buildingname
     */
    public void setBuildingname(String buildingname) {
        this.buildingname = buildingname;
    }

    /**
     * @return BuildingType
     */
    public String getBuildingtype() {
        return buildingtype;
    }

    /**
     * @param buildingtype
     */
    public void setBuildingtype(String buildingtype) {
        this.buildingtype = buildingtype;
    }

    /**
     * @return UserType
     */
    public String getUsertype() {
        return usertype;
    }

    /**
     * @param usertype
     */
    public void setUsertype(String usertype) {
        this.usertype = usertype;
    }

    /**
     * @return FireRiskGrade
     */
    public String getFireriskgrade() {
        return fireriskgrade;
    }

    /**
     * @param fireriskgrade
     */
    public void setFireriskgrade(String fireriskgrade) {
        this.fireriskgrade = fireriskgrade;
    }

    /**
     * @return RefractoryGrade
     */
    public String getRefractorygrade() {
        return refractorygrade;
    }

    /**
     * @param refractorygrade
     */
    public void setRefractorygrade(String refractorygrade) {
        this.refractorygrade = refractorygrade;
    }

    /**
     * @return StructureType
     */
    public String getStructuretype() {
        return structuretype;
    }

    /**
     * @param structuretype
     */
    public void setStructuretype(String structuretype) {
        this.structuretype = structuretype;
    }

    /**
     * @return BuildDate
     */
    public Date getBuilddate() {
        return builddate;
    }

    /**
     * @param builddate
     */
    public void setBuilddate(Date builddate) {
        this.builddate = builddate;
    }

    /**
     * @return BuildingHeight
     */
    public BigDecimal getBuildingheight() {
        return buildingheight;
    }

    /**
     * @param buildingheight
     */
    public void setBuildingheight(BigDecimal buildingheight) {
        this.buildingheight = buildingheight;
    }

    /**
     * @return BuildingArea
     */
    public BigDecimal getBuildingarea() {
        return buildingarea;
    }

    /**
     * @param buildingarea
     */
    public void setBuildingarea(BigDecimal buildingarea) {
        this.buildingarea = buildingarea;
    }

    /**
     * @return BuildingOccupyarea
     */
    public BigDecimal getBuildingoccupyarea() {
        return buildingoccupyarea;
    }

    /**
     * @param buildingoccupyarea
     */
    public void setBuildingoccupyarea(BigDecimal buildingoccupyarea) {
        this.buildingoccupyarea = buildingoccupyarea;
    }

    /**
     * @return StdFloorArea
     */
    public BigDecimal getStdfloorarea() {
        return stdfloorarea;
    }

    /**
     * @param stdfloorarea
     */
    public void setStdfloorarea(BigDecimal stdfloorarea) {
        this.stdfloorarea = stdfloorarea;
    }

    /**
     * @return OverGroundFloors
     */
    public BigDecimal getOvergroundfloors() {
        return overgroundfloors;
    }

    /**
     * @param overgroundfloors
     */
    public void setOvergroundfloors(BigDecimal overgroundfloors) {
        this.overgroundfloors = overgroundfloors;
    }

    /**
     * @return OverGroundArea
     */
    public BigDecimal getOvergroundarea() {
        return overgroundarea;
    }

    /**
     * @param overgroundarea
     */
    public void setOvergroundarea(BigDecimal overgroundarea) {
        this.overgroundarea = overgroundarea;
    }

    /**
     * @return UnderGroundFloors
     */
    public BigDecimal getUndergroundfloors() {
        return undergroundfloors;
    }

    /**
     * @param undergroundfloors
     */
    public void setUndergroundfloors(BigDecimal undergroundfloors) {
        this.undergroundfloors = undergroundfloors;
    }

    /**
     * @return UnderGroundArea
     */
    public BigDecimal getUndergroundarea() {
        return undergroundarea;
    }

    /**
     * @param undergroundarea
     */
    public void setUndergroundarea(BigDecimal undergroundarea) {
        this.undergroundarea = undergroundarea;
    }

    /**
     * @return TunnelHeight
     */
    public BigDecimal getTunnelheight() {
        return tunnelheight;
    }

    /**
     * @param tunnelheight
     */
    public void setTunnelheight(BigDecimal tunnelheight) {
        this.tunnelheight = tunnelheight;
    }

    /**
     * @return TunnelLength
     */
    public BigDecimal getTunnellength() {
        return tunnellength;
    }

    /**
     * @param tunnellength
     */
    public void setTunnellength(BigDecimal tunnellength) {
        this.tunnellength = tunnellength;
    }

    /**
     * @return FireRoomPosition
     */
    public String getFireroomposition() {
        return fireroomposition;
    }

    /**
     * @param fireroomposition
     */
    public void setFireroomposition(String fireroomposition) {
        this.fireroomposition = fireroomposition;
    }

    /**
     * @return RefugeNum
     */
    public Integer getRefugenum() {
        return refugenum;
    }

    /**
     * @param refugenum
     */
    public void setRefugenum(Integer refugenum) {
        this.refugenum = refugenum;
    }

    /**
     * @return RefugeArea
     */
    public BigDecimal getRefugearea() {
        return refugearea;
    }

    /**
     * @param refugearea
     */
    public void setRefugearea(BigDecimal refugearea) {
        this.refugearea = refugearea;
    }

    /**
     * @return RefugePosition
     */
    public String getRefugeposition() {
        return refugeposition;
    }

    /**
     * @param refugeposition
     */
    public void setRefugeposition(String refugeposition) {
        this.refugeposition = refugeposition;
    }

    /**
     * @return SafeExitNum
     */
    public Integer getSafeexitnum() {
        return safeexitnum;
    }

    /**
     * @param safeexitnum
     */
    public void setSafeexitnum(Integer safeexitnum) {
        this.safeexitnum = safeexitnum;
    }

    /**
     * @return SafeExitPosition
     */
    public String getSafeexitposition() {
        return safeexitposition;
    }

    /**
     * @param safeexitposition
     */
    public void setSafeexitposition(String safeexitposition) {
        this.safeexitposition = safeexitposition;
    }

    /**
     * @return SafeExitType
     */
    public String getSafeexittype() {
        return safeexittype;
    }

    /**
     * @param safeexittype
     */
    public void setSafeexittype(String safeexittype) {
        this.safeexittype = safeexittype;
    }

    /**
     * @return FireElevatorNum
     */
    public Integer getFireelevatornum() {
        return fireelevatornum;
    }

    /**
     * @param fireelevatornum
     */
    public void setFireelevatornum(Integer fireelevatornum) {
        this.fireelevatornum = fireelevatornum;
    }

    /**
     * @return FireElevatorWeight
     */
    public BigDecimal getFireelevatorweight() {
        return fireelevatorweight;
    }

    /**
     * @param fireelevatorweight
     */
    public void setFireelevatorweight(BigDecimal fireelevatorweight) {
        this.fireelevatorweight = fireelevatorweight;
    }

    /**
     * @return DailyPersonNum
     */
    public Integer getDailypersonnum() {
        return dailypersonnum;
    }

    /**
     * @param dailypersonnum
     */
    public void setDailypersonnum(Integer dailypersonnum) {
        this.dailypersonnum = dailypersonnum;
    }

    /**
     * @return AccommodatePersonMaxnum
     */
    public Integer getAccommodatepersonmaxnum() {
        return accommodatepersonmaxnum;
    }

    /**
     * @param accommodatepersonmaxnum
     */
    public void setAccommodatepersonmaxnum(Integer accommodatepersonmaxnum) {
        this.accommodatepersonmaxnum = accommodatepersonmaxnum;
    }

    /**
     * @return StoreMaterialName
     */
    public String getStorematerialname() {
        return storematerialname;
    }

    /**
     * @param storematerialname
     */
    public void setStorematerialname(String storematerialname) {
        this.storematerialname = storematerialname;
    }

    /**
     * @return StoreMaterialNum
     */
    public Integer getStorematerialnum() {
        return storematerialnum;
    }

    /**
     * @param storematerialnum
     */
    public void setStorematerialnum(Integer storematerialnum) {
        this.storematerialnum = storematerialnum;
    }

    /**
     * @return StoreMaterialNature
     */
    public String getStorematerialnature() {
        return storematerialnature;
    }

    /**
     * @param storematerialnature
     */
    public void setStorematerialnature(String storematerialnature) {
        this.storematerialnature = storematerialnature;
    }

    /**
     * @return StoreMaterialForm
     */
    public String getStorematerialform() {
        return storematerialform;
    }

    /**
     * @param storematerialform
     */
    public void setStorematerialform(String storematerialform) {
        this.storematerialform = storematerialform;
    }

    /**
     * @return StoreVolume
     */
    public BigDecimal getStorevolume() {
        return storevolume;
    }

    /**
     * @param storevolume
     */
    public void setStorevolume(BigDecimal storevolume) {
        this.storevolume = storevolume;
    }

    /**
     * @return MainMaterial
     */
    public String getMainmaterial() {
        return mainmaterial;
    }

    /**
     * @param mainmaterial
     */
    public void setMainmaterial(String mainmaterial) {
        this.mainmaterial = mainmaterial;
    }

    /**
     * @return MainProduct
     */
    public String getMainproduct() {
        return mainproduct;
    }

    /**
     * @param mainproduct
     */
    public void setMainproduct(String mainproduct) {
        this.mainproduct = mainproduct;
    }

    /**
     * @return NearBuildingSituation
     */
    public String getNearbuildingsituation() {
        return nearbuildingsituation;
    }

    /**
     * @param nearbuildingsituation
     */
    public void setNearbuildingsituation(String nearbuildingsituation) {
        this.nearbuildingsituation = nearbuildingsituation;
    }

    /**
     * @return BuildingElevationMap
     */
    public String getBuildingelevationmap() {
        return buildingelevationmap;
    }

    /**
     * @param buildingelevationmap
     */
    public void setBuildingelevationmap(String buildingelevationmap) {
        this.buildingelevationmap = buildingelevationmap;
    }

    /**
     * @return BuildingPlan
     */
    public String getBuildingplan() {
        return buildingplan;
    }

    /**
     * @param buildingplan
     */
    public void setBuildingplan(String buildingplan) {
        this.buildingplan = buildingplan;
    }

    /**
     * @return FacilitiesPlan
     */
    public String getFacilitiesplan() {
        return facilitiesplan;
    }

    /**
     * @param facilitiesplan
     */
    public void setFacilitiesplan(String facilitiesplan) {
        this.facilitiesplan = facilitiesplan;
    }

    /**
     * @return Address
     */
    public String getAddress() {
        return address;
    }

    /**
     * @param address
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * @return AutoFireFacilities
     */
    public String getAutofirefacilities() {
        return autofirefacilities;
    }

    /**
     * @param autofirefacilities
     */
    public void setAutofirefacilities(String autofirefacilities) {
        this.autofirefacilities = autofirefacilities;
    }

    /**
     * @return Floors
     */
    public BigDecimal getFloors() {
        return floors;
    }

    /**
     * @param floors
     */
    public void setFloors(BigDecimal floors) {
        this.floors = floors;
    }

    /**
     * @return EvacuationStirNum
     */
    public BigDecimal getEvacuationstirnum() {
        return evacuationstirnum;
    }

    /**
     * @param evacuationstirnum
     */
    public void setEvacuationstirnum(BigDecimal evacuationstirnum) {
        this.evacuationstirnum = evacuationstirnum;
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
     * @return WBUnitID
     */
    public Long getWbunitid() {
        return wbunitid;
    }

    /**
     * @param wbunitid
     */
    public void setWbunitid(Long wbunitid) {
        this.wbunitid = wbunitid;
    }

    /**
     * @return IDFromData
     */
    public Long getIdfromdata() {
        return idfromdata;
    }

    /**
     * @param idfromdata
     */
    public void setIdfromdata(Long idfromdata) {
        this.idfromdata = idfromdata;
    }
}