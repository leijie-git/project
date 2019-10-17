package com.gw.mapper.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

@Table(name = "UT_Base_UserCard")
public class UtBaseUsercard implements Serializable {
    @Id
    @Column(name = "ID")
    private Long id;

    @Column(name = "CardNo")
    private String cardno;

    @Column(name = "ShowName")
    private String showname;

    @Column(name = "FileName")
    private String filename;

    @Column(name = "UploadDate")
    private Date uploaddate;

    @Column(name = "UploadUser")
    private Long uploaduser;

    @Column(name = "UserID")
    private Long userid;

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
     * @return CardNo
     */
    public String getCardno() {
        return cardno;
    }

    /**
     * @param cardno
     */
    public void setCardno(String cardno) {
        this.cardno = cardno;
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
     * @return UploadDate
     */
    public Date getUploaddate() {
        return uploaddate;
    }

    /**
     * @param uploaddate
     */
    public void setUploaddate(Date uploaddate) {
        this.uploaddate = uploaddate;
    }

    /**
     * @return UploadUser
     */
    public Long getUploaduser() {
        return uploaduser;
    }

    /**
     * @param uploaduser
     */
    public void setUploaduser(Long uploaduser) {
        this.uploaduser = uploaduser;
    }

    /**
     * @return UserID
     */
    public Long getUserid() {
        return userid;
    }

    /**
     * @param userid
     */
    public void setUserid(Long userid) {
        this.userid = userid;
    }
}