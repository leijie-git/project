package com.gw.mapper.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

@Table(name = "UT_Base_Code")
public class UtBaseCode implements Serializable {
    @Id
    @Column(name = "CodeID")
    private Long codeid;

    @Column(name = "CodeGroupCode")
    private String codegroupcode;

    @Column(name = "PCodeValue")
    private String pcodevalue;

    @Column(name = "CodeName")
    private String codename;

    @Column(name = "CodeValue")
    private String codevalue;

    @Column(name = "Memo")
    private String memo;

    @Column(name = "SortOrder")
    private Integer sortorder;

    @Column(name = "IsDeleted")
    private Boolean isdeleted;

    @Column(name = "Adder")
    private String adder;

    @Column(name = "AddDate")
    private Date adddate;

    @Column(name = "Updater")
    private String updater;

    @Column(name = "UpdateDate")
    private Date updatedate;

    @Column(name = "CodeGroupID")
    private Long codegroupid;

    private static final long serialVersionUID = 1L;

    /**
     * @return CodeID
     */
    public Long getCodeid() {
        return codeid;
    }

    /**
     * @param codeid
     */
    public void setCodeid(Long codeid) {
        this.codeid = codeid;
    }

    /**
     * @return CodeGroupCode
     */
    public String getCodegroupcode() {
        return codegroupcode;
    }

    /**
     * @param codegroupcode
     */
    public void setCodegroupcode(String codegroupcode) {
        this.codegroupcode = codegroupcode;
    }

    /**
     * @return PCodeValue
     */
    public String getPcodevalue() {
        return pcodevalue;
    }

    /**
     * @param pcodevalue
     */
    public void setPcodevalue(String pcodevalue) {
        this.pcodevalue = pcodevalue;
    }

    /**
     * @return CodeName
     */
    public String getCodename() {
        return codename;
    }

    /**
     * @param codename
     */
    public void setCodename(String codename) {
        this.codename = codename;
    }

    /**
     * @return CodeValue
     */
    public String getCodevalue() {
        return codevalue;
    }

    /**
     * @param codevalue
     */
    public void setCodevalue(String codevalue) {
        this.codevalue = codevalue;
    }

    /**
     * @return Memo
     */
    public String getMemo() {
        return memo;
    }

    /**
     * @param memo
     */
    public void setMemo(String memo) {
        this.memo = memo;
    }

    /**
     * @return SortOrder
     */
    public Integer getSortorder() {
        return sortorder;
    }

    /**
     * @param sortorder
     */
    public void setSortorder(Integer sortorder) {
        this.sortorder = sortorder;
    }

    /**
     * @return IsDeleted
     */
    public Boolean getIsdeleted() {
        return isdeleted;
    }

    /**
     * @param isdeleted
     */
    public void setIsdeleted(Boolean isdeleted) {
        this.isdeleted = isdeleted;
    }

    /**
     * @return Adder
     */
    public String getAdder() {
        return adder;
    }

    /**
     * @param adder
     */
    public void setAdder(String adder) {
        this.adder = adder;
    }

    /**
     * @return AddDate
     */
    public Date getAdddate() {
        return adddate;
    }

    /**
     * @param adddate
     */
    public void setAdddate(Date adddate) {
        this.adddate = adddate;
    }

    /**
     * @return Updater
     */
    public String getUpdater() {
        return updater;
    }

    /**
     * @param updater
     */
    public void setUpdater(String updater) {
        this.updater = updater;
    }

    /**
     * @return UpdateDate
     */
    public Date getUpdatedate() {
        return updatedate;
    }

    /**
     * @param updatedate
     */
    public void setUpdatedate(Date updatedate) {
        this.updatedate = updatedate;
    }

    /**
     * @return CodeGroupID
     */
    public Long getCodegroupid() {
        return codegroupid;
    }

    /**
     * @param codegroupid
     */
    public void setCodegroupid(Long codegroupid) {
        this.codegroupid = codegroupid;
    }
}