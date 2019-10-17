package com.gw.mapper.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.*;

@Table(name = "UT_Unit_Import")
public class UtUnitImport implements Serializable {
    @Id
    @Column(name = "ID")
    private Long id;

    @Column(name = "ImportName")
    private String importname;

    @Column(name = "ImportSite")
    private String importsite;

    @Column(name = "Floor")
    private Integer floor;

    @Column(name = "Height")
    private BigDecimal height;

    @Column(name = "BuildingStructure")
    private String buildingstructure;

    @Column(name = "Used")
    private String used;

    @Column(name = "DTCount")
    private Integer dtcount;

    @Column(name = "OutCount")
    private Integer outcount;

    @Column(name = "HZWXX")
    private String hzwxx;

    @Column(name = "BuildArea")
    private BigDecimal buildarea;

    @Column(name = "NHLevel")
    private String nhlevel;

    @Column(name = "FireInfo")
    private String fireinfo;

    @Column(name = "IsDXFYZDX")
    private Integer isdxfyzdx;

    @Column(name = "IsHZFSHRYSWD")
    private Integer ishzfshryswd;

    @Column(name = "IsHZHSSD")
    private Integer ishzhssd;

    @Column(name = "IsYFSHZ")
    private Integer isyfshz;

    @Column(name = "IsOther")
    private Integer isother;

    @Column(name = "FHBZSLQK")
    private String fhbzslqk;

    @Column(name = "IsBZP")
    private Integer isbzp;

    @Column(name = "IsYHQHYJHHW")
    private Integer isyhqhyjhhw;

    @Column(name = "IsFSP")
    private Integer isfsp;

    @Column(name = "IsYSQT")
    private Integer isysqt;

    @Column(name = "IsYDP")
    private Integer isydp;

    @Column(name = "IsZXWXW")
    private Integer iszxwxw;

    @Column(name = "IsYRYT")
    private Integer isyryt;

    @Column(name = "IsFSX")
    private Integer isfsx;

    @Column(name = "IsQT")
    private Integer isqt;

    @Column(name = "IsYRGT")
    private Integer isyrgt;

    @Column(name = "ImportImageName")
    private String importimagename;

    @Column(name = "UnitID")
    private Long unitid;

    @Column(name = "BuildID")
    private Long buildid;

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
     * @return ImportName
     */
    public String getImportname() {
        return importname;
    }

    /**
     * @param importname
     */
    public void setImportname(String importname) {
        this.importname = importname;
    }

    /**
     * @return ImportSite
     */
    public String getImportsite() {
        return importsite;
    }

    /**
     * @param importsite
     */
    public void setImportsite(String importsite) {
        this.importsite = importsite;
    }

    /**
     * @return Floor
     */
    public Integer getFloor() {
        return floor;
    }

    /**
     * @param floor
     */
    public void setFloor(Integer floor) {
        this.floor = floor;
    }

    /**
     * @return Height
     */
    public BigDecimal getHeight() {
        return height;
    }

    /**
     * @param height
     */
    public void setHeight(BigDecimal height) {
        this.height = height;
    }

    /**
     * @return BuildingStructure
     */
    public String getBuildingstructure() {
        return buildingstructure;
    }

    /**
     * @param buildingstructure
     */
    public void setBuildingstructure(String buildingstructure) {
        this.buildingstructure = buildingstructure;
    }

    /**
     * @return Used
     */
    public String getUsed() {
        return used;
    }

    /**
     * @param used
     */
    public void setUsed(String used) {
        this.used = used;
    }

    /**
     * @return DTCount
     */
    public Integer getDtcount() {
        return dtcount;
    }

    /**
     * @param dtcount
     */
    public void setDtcount(Integer dtcount) {
        this.dtcount = dtcount;
    }

    /**
     * @return OutCount
     */
    public Integer getOutcount() {
        return outcount;
    }

    /**
     * @param outcount
     */
    public void setOutcount(Integer outcount) {
        this.outcount = outcount;
    }

    /**
     * @return HZWXX
     */
    public String getHzwxx() {
        return hzwxx;
    }

    /**
     * @param hzwxx
     */
    public void setHzwxx(String hzwxx) {
        this.hzwxx = hzwxx;
    }

    /**
     * @return BuildArea
     */
    public BigDecimal getBuildarea() {
        return buildarea;
    }

    /**
     * @param buildarea
     */
    public void setBuildarea(BigDecimal buildarea) {
        this.buildarea = buildarea;
    }

    /**
     * @return NHLevel
     */
    public String getNhlevel() {
        return nhlevel;
    }

    /**
     * @param nhlevel
     */
    public void setNhlevel(String nhlevel) {
        this.nhlevel = nhlevel;
    }

    /**
     * @return FireInfo
     */
    public String getFireinfo() {
        return fireinfo;
    }

    /**
     * @param fireinfo
     */
    public void setFireinfo(String fireinfo) {
        this.fireinfo = fireinfo;
    }

    /**
     * @return IsDXFYZDX
     */
    public Integer getIsdxfyzdx() {
        return isdxfyzdx;
    }

    /**
     * @param isdxfyzdx
     */
    public void setIsdxfyzdx(Integer isdxfyzdx) {
        this.isdxfyzdx = isdxfyzdx;
    }

    /**
     * @return IsHZFSHRYSWD
     */
    public Integer getIshzfshryswd() {
        return ishzfshryswd;
    }

    /**
     * @param ishzfshryswd
     */
    public void setIshzfshryswd(Integer ishzfshryswd) {
        this.ishzfshryswd = ishzfshryswd;
    }

    /**
     * @return IsHZHSSD
     */
    public Integer getIshzhssd() {
        return ishzhssd;
    }

    /**
     * @param ishzhssd
     */
    public void setIshzhssd(Integer ishzhssd) {
        this.ishzhssd = ishzhssd;
    }

    /**
     * @return IsYFSHZ
     */
    public Integer getIsyfshz() {
        return isyfshz;
    }

    /**
     * @param isyfshz
     */
    public void setIsyfshz(Integer isyfshz) {
        this.isyfshz = isyfshz;
    }

    /**
     * @return IsOther
     */
    public Integer getIsother() {
        return isother;
    }

    /**
     * @param isother
     */
    public void setIsother(Integer isother) {
        this.isother = isother;
    }

    /**
     * @return FHBZSLQK
     */
    public String getFhbzslqk() {
        return fhbzslqk;
    }

    /**
     * @param fhbzslqk
     */
    public void setFhbzslqk(String fhbzslqk) {
        this.fhbzslqk = fhbzslqk;
    }

    /**
     * @return IsBZP
     */
    public Integer getIsbzp() {
        return isbzp;
    }

    /**
     * @param isbzp
     */
    public void setIsbzp(Integer isbzp) {
        this.isbzp = isbzp;
    }

    /**
     * @return IsYHQHYJHHW
     */
    public Integer getIsyhqhyjhhw() {
        return isyhqhyjhhw;
    }

    /**
     * @param isyhqhyjhhw
     */
    public void setIsyhqhyjhhw(Integer isyhqhyjhhw) {
        this.isyhqhyjhhw = isyhqhyjhhw;
    }

    /**
     * @return IsFSP
     */
    public Integer getIsfsp() {
        return isfsp;
    }

    /**
     * @param isfsp
     */
    public void setIsfsp(Integer isfsp) {
        this.isfsp = isfsp;
    }

    /**
     * @return IsYSQT
     */
    public Integer getIsysqt() {
        return isysqt;
    }

    /**
     * @param isysqt
     */
    public void setIsysqt(Integer isysqt) {
        this.isysqt = isysqt;
    }

    /**
     * @return IsYDP
     */
    public Integer getIsydp() {
        return isydp;
    }

    /**
     * @param isydp
     */
    public void setIsydp(Integer isydp) {
        this.isydp = isydp;
    }

    /**
     * @return IsZXWXW
     */
    public Integer getIszxwxw() {
        return iszxwxw;
    }

    /**
     * @param iszxwxw
     */
    public void setIszxwxw(Integer iszxwxw) {
        this.iszxwxw = iszxwxw;
    }

    /**
     * @return IsYRYT
     */
    public Integer getIsyryt() {
        return isyryt;
    }

    /**
     * @param isyryt
     */
    public void setIsyryt(Integer isyryt) {
        this.isyryt = isyryt;
    }

    /**
     * @return IsFSX
     */
    public Integer getIsfsx() {
        return isfsx;
    }

    /**
     * @param isfsx
     */
    public void setIsfsx(Integer isfsx) {
        this.isfsx = isfsx;
    }

    /**
     * @return IsQT
     */
    public Integer getIsqt() {
        return isqt;
    }

    /**
     * @param isqt
     */
    public void setIsqt(Integer isqt) {
        this.isqt = isqt;
    }

    /**
     * @return IsYRGT
     */
    public Integer getIsyrgt() {
        return isyrgt;
    }

    /**
     * @param isyrgt
     */
    public void setIsyrgt(Integer isyrgt) {
        this.isyrgt = isyrgt;
    }

    /**
     * @return ImportImageName
     */
    public String getImportimagename() {
        return importimagename;
    }

    /**
     * @param importimagename
     */
    public void setImportimagename(String importimagename) {
        this.importimagename = importimagename;
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
}