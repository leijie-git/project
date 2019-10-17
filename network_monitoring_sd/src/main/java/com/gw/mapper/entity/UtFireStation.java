package com.gw.mapper.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

@Table(name = "UT_fire_station")
public class UtFireStation implements Serializable {
    @Id
    @Column(name = "ID")
    private Long id;

    private String name;

    @Column(name = "water_gun")
    private Integer waterGun;

    @Column(name = "water_belt")
    private Integer waterBelt;

    @Column(name = "safety_rope")
    private Integer safetyRope;

    @Column(name = "hydrant_wrench")
    private Integer hydrantWrench;

    @Column(name = "fire_extinguisher")
    private Integer fireExtinguisher;

    @Column(name = "bright_light")
    private Integer brightLight;

    @Column(name = "fire_axe")
    private Integer fireAxe;

    @Column(name = "fire_helmet")
    private Integer fireHelmet;

    private Integer clothing;

    private Integer boots;

    @Column(name = "safety_belt")
    private Integer safetyBelt;

    @Column(name = "fire_gloves")
    private Integer fireGloves;

    @Column(name = "integrated_respirator")
    private Integer integratedRespirator;

    private Integer loudspeaker;

    @Column(name = "patrol_car")
    private Integer patrolCar;

    private String remark;

    @Column(name = "create_date")
    private Date createDate;

    @Column(name = "create_user")
    private Long createUser;

    @Column(name = "update_date")
    private Date updateDate;

    @Column(name = "update_user")
    private Long updateUser;
    
    @Column(name = "power_id")
    private Long powerId;

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
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return water_gun
     */
    public Integer getWaterGun() {
        return waterGun;
    }

    /**
     * @param waterGun
     */
    public void setWaterGun(Integer waterGun) {
        this.waterGun = waterGun;
    }

    /**
     * @return water_belt
     */
    public Integer getWaterBelt() {
        return waterBelt;
    }

    /**
     * @param waterBelt
     */
    public void setWaterBelt(Integer waterBelt) {
        this.waterBelt = waterBelt;
    }

    /**
     * @return safety_rope
     */
    public Integer getSafetyRope() {
        return safetyRope;
    }

    /**
     * @param safetyRope
     */
    public void setSafetyRope(Integer safetyRope) {
        this.safetyRope = safetyRope;
    }

    /**
     * @return hydrant_wrench
     */
    public Integer getHydrantWrench() {
        return hydrantWrench;
    }

    /**
     * @param hydrantWrench
     */
    public void setHydrantWrench(Integer hydrantWrench) {
        this.hydrantWrench = hydrantWrench;
    }

    /**
     * @return fire_extinguisher
     */
    public Integer getFireExtinguisher() {
        return fireExtinguisher;
    }

    /**
     * @param fireExtinguisher
     */
    public void setFireExtinguisher(Integer fireExtinguisher) {
        this.fireExtinguisher = fireExtinguisher;
    }

    /**
     * @return bright_light
     */
    public Integer getBrightLight() {
        return brightLight;
    }

    /**
     * @param brightLight
     */
    public void setBrightLight(Integer brightLight) {
        this.brightLight = brightLight;
    }

    /**
     * @return fire_axe
     */
    public Integer getFireAxe() {
        return fireAxe;
    }

    /**
     * @param fireAxe
     */
    public void setFireAxe(Integer fireAxe) {
        this.fireAxe = fireAxe;
    }

    /**
     * @return fire_helmet
     */
    public Integer getFireHelmet() {
        return fireHelmet;
    }

    /**
     * @param fireHelmet
     */
    public void setFireHelmet(Integer fireHelmet) {
        this.fireHelmet = fireHelmet;
    }

    /**
     * @return clothing
     */
    public Integer getClothing() {
        return clothing;
    }

    /**
     * @param clothing
     */
    public void setClothing(Integer clothing) {
        this.clothing = clothing;
    }

    /**
     * @return boots
     */
    public Integer getBoots() {
        return boots;
    }

    /**
     * @param boots
     */
    public void setBoots(Integer boots) {
        this.boots = boots;
    }

    /**
     * @return safety_belt
     */
    public Integer getSafetyBelt() {
        return safetyBelt;
    }

    /**
     * @param safetyBelt
     */
    public void setSafetyBelt(Integer safetyBelt) {
        this.safetyBelt = safetyBelt;
    }

    /**
     * @return fire_gloves
     */
    public Integer getFireGloves() {
        return fireGloves;
    }

    /**
     * @param fireGloves
     */
    public void setFireGloves(Integer fireGloves) {
        this.fireGloves = fireGloves;
    }

    /**
     * @return integrated_respirator
     */
    public Integer getIntegratedRespirator() {
        return integratedRespirator;
    }

    /**
     * @param integratedRespirator
     */
    public void setIntegratedRespirator(Integer integratedRespirator) {
        this.integratedRespirator = integratedRespirator;
    }

    /**
     * @return loudspeaker
     */
    public Integer getLoudspeaker() {
        return loudspeaker;
    }

    /**
     * @param loudspeaker
     */
    public void setLoudspeaker(Integer loudspeaker) {
        this.loudspeaker = loudspeaker;
    }

    /**
     * @return patrol_car
     */
    public Integer getPatrolCar() {
        return patrolCar;
    }

    /**
     * @param patrolCar
     */
    public void setPatrolCar(Integer patrolCar) {
        this.patrolCar = patrolCar;
    }

    /**
     * @return remark
     */
    public String getRemark() {
        return remark;
    }

    /**
     * @param remark
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }

    /**
     * @return create_date
     */
    public Date getCreateDate() {
        return createDate;
    }

    /**
     * @param createDate
     */
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    /**
     * @return create_user
     */
    public Long getCreateUser() {
        return createUser;
    }

    /**
     * @param createUser
     */
    public void setCreateUser(Long createUser) {
        this.createUser = createUser;
    }

    /**
     * @return update_date
     */
    public Date getUpdateDate() {
        return updateDate;
    }

    /**
     * @param updateDate
     */
    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    /**
     * @return update_user
     */
    public Long getUpdateUser() {
        return updateUser;
    }

    /**
     * @param updateUser
     */
    public void setUpdateUser(Long updateUser) {
        this.updateUser = updateUser;
    }

	public Long getPowerId() {
		return powerId;
	}

	public void setPowerId(Long powerId) {
		this.powerId = powerId;
	}
    
    
}