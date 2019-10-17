package com.gw.mapper.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

@Table(name = "UT_Base_CodeGroup")
public class UtBaseCodegroup implements Serializable {
    @Id
    @Column(name = "CodeGroupID")
    private Long codegroupid;

    @Column(name = "CodeGroupKey")
    private String codegroupkey;

    @Column(name = "CodeGroupName")
    private String codegroupname;

    @Column(name = "SortOrder")
    private Integer sortorder;

    @Column(name = "Memo")
    private String memo;

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

    private static final long serialVersionUID = 1L;

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

    /**
     * @return CodeGroupKey
     */
    public String getCodegroupkey() {
        return codegroupkey;
    }

    /**
     * @param codegroupkey
     */
    public void setCodegroupkey(String codegroupkey) {
        this.codegroupkey = codegroupkey;
    }

    /**
     * @return CodeGroupName
     */
    public String getCodegroupname() {
        return codegroupname;
    }

    /**
     * @param codegroupname
     */
    public void setCodegroupname(String codegroupname) {
        this.codegroupname = codegroupname;
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
}