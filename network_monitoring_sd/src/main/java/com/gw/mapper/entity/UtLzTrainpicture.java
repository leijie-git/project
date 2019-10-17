package com.gw.mapper.entity;

import java.io.Serializable;
import javax.persistence.*;

@Table(name = "UT_LZ_TrainPicture")
public class UtLzTrainpicture implements Serializable {
    @Id
    @Column(name = "ID")
    private Long id;

    @Column(name = "PictureType")
    private Integer picturetype;

    @Column(name = "PictureUrl")
    private String pictureurl;

    @Column(name = "PictureName")
    private String picturename;

    @Column(name = "FileName")
    private String filename;

    @Column(name = "TrainID")
    private Long trainid;

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
     * @return PictureType
     */
    public Integer getPicturetype() {
        return picturetype;
    }

    /**
     * @param picturetype
     */
    public void setPicturetype(Integer picturetype) {
        this.picturetype = picturetype;
    }

    /**
     * @return PictureUrl
     */
    public String getPictureurl() {
        return pictureurl;
    }

    /**
     * @param pictureurl
     */
    public void setPictureurl(String pictureurl) {
        this.pictureurl = pictureurl;
    }

    /**
     * @return PictureName
     */
    public String getPicturename() {
        return picturename;
    }

    /**
     * @param picturename
     */
    public void setPicturename(String picturename) {
        this.picturename = picturename;
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
     * @return TrainID
     */
    public Long getTrainid() {
        return trainid;
    }

    /**
     * @param trainid
     */
    public void setTrainid(Long trainid) {
        this.trainid = trainid;
    }
}