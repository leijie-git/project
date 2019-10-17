package com.gw.mapper.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

@Table(name = "UT_LZ_Train")
public class UtLzTrain implements Serializable {
    @Column(name = "TrainDateS")
    private Date traindates;

    @Column(name = "TrainDateE")
    private Date traindatee;

    @Column(name = "TrainSite")
    private String trainsite;

    @Column(name = "TrainObject")
    private String trainobject;

    @Column(name = "JoinTrainUserName")
    private String jointrainusername;

    @Column(name = "JoinTrainUserCount")
    private Integer jointrainusercount;

    @Column(name = "TeacherInfo")
    private String teacherinfo;

    @Column(name = "TrainContent")
    private String traincontent;

    @Column(name = "TrainResult")
    private String trainresult;

    @Column(name = "CreateDate")
    private Date createdate;

    @Column(name = "CreateUser")
    private String createuser;

    @Column(name = "DataFromID")
    private String datafromid;

    @Column(name = "DataFrom")
    private Integer datafrom;

    @Column(name = "OtherCreateUserName")
    private String othercreateusername;

    @Column(name = "ID")
    private Long id;

    @Column(name = "UnitID")
    private Long unitid;

    private static final long serialVersionUID = 1L;

    /**
     * @return TrainDateS
     */
    public Date getTraindates() {
        return traindates;
    }

    /**
     * @param traindates
     */
    public void setTraindates(Date traindates) {
        this.traindates = traindates;
    }

    /**
     * @return TrainDateE
     */
    public Date getTraindatee() {
        return traindatee;
    }

    /**
     * @param traindatee
     */
    public void setTraindatee(Date traindatee) {
        this.traindatee = traindatee;
    }

    /**
     * @return TrainSite
     */
    public String getTrainsite() {
        return trainsite;
    }

    /**
     * @param trainsite
     */
    public void setTrainsite(String trainsite) {
        this.trainsite = trainsite;
    }

    /**
     * @return TrainObject
     */
    public String getTrainobject() {
        return trainobject;
    }

    /**
     * @param trainobject
     */
    public void setTrainobject(String trainobject) {
        this.trainobject = trainobject;
    }

    /**
     * @return JoinTrainUserName
     */
    public String getJointrainusername() {
        return jointrainusername;
    }

    /**
     * @param jointrainusername
     */
    public void setJointrainusername(String jointrainusername) {
        this.jointrainusername = jointrainusername;
    }

    /**
     * @return JoinTrainUserCount
     */
    public Integer getJointrainusercount() {
        return jointrainusercount;
    }

    /**
     * @param jointrainusercount
     */
    public void setJointrainusercount(Integer jointrainusercount) {
        this.jointrainusercount = jointrainusercount;
    }

    /**
     * @return TeacherInfo
     */
    public String getTeacherinfo() {
        return teacherinfo;
    }

    /**
     * @param teacherinfo
     */
    public void setTeacherinfo(String teacherinfo) {
        this.teacherinfo = teacherinfo;
    }

    /**
     * @return TrainContent
     */
    public String getTraincontent() {
        return traincontent;
    }

    /**
     * @param traincontent
     */
    public void setTraincontent(String traincontent) {
        this.traincontent = traincontent;
    }

    /**
     * @return TrainResult
     */
    public String getTrainresult() {
        return trainresult;
    }

    /**
     * @param trainresult
     */
    public void setTrainresult(String trainresult) {
        this.trainresult = trainresult;
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
     * @return CreateUser
     */
    public String getCreateuser() {
        return createuser;
    }

    /**
     * @param createuser
     */
    public void setCreateuser(String createuser) {
        this.createuser = createuser;
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
     * @return OtherCreateUserName
     */
    public String getOthercreateusername() {
        return othercreateusername;
    }

    /**
     * @param othercreateusername
     */
    public void setOthercreateusername(String othercreateusername) {
        this.othercreateusername = othercreateusername;
    }

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
}