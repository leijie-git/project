package com.gw.mapper.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

@Table(name = "UT_Squadron")
public class UtSquadron implements Serializable {
    @Id
    @Column(name = "ID")
    private Long id;

    private String name;

    @Column(name = "high_spray")
    private Integer highSpray;

    @Column(name = "water_tank")
    private Integer waterTank;

    private Integer foam;

    @Column(name = "dry_powder")
    private Integer dryPowder;

    @Column(name = "ladder_Ladder")
    private Integer ladderLadder;

    @Column(name = "a_foam")
    private Integer aFoam;

    @Column(name = "rescue_vehicle")
    private Integer rescueVehicle;

    @Column(name = "climbing_platform")
    private Integer climbingPlatform;

    @Column(name = "pressure_smoke")
    private Integer pressureSmoke;

    private Integer combatants;

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
     * @return high_spray
     */
    public Integer getHighSpray() {
        return highSpray;
    }

    /**
     * @param highSpray
     */
    public void setHighSpray(Integer highSpray) {
        this.highSpray = highSpray;
    }

    /**
     * @return water_tank
     */
    public Integer getWaterTank() {
        return waterTank;
    }

    /**
     * @param waterTank
     */
    public void setWaterTank(Integer waterTank) {
        this.waterTank = waterTank;
    }

    /**
     * @return foam
     */
    public Integer getFoam() {
        return foam;
    }

    /**
     * @param foam
     */
    public void setFoam(Integer foam) {
        this.foam = foam;
    }

    /**
     * @return dry_powder
     */
    public Integer getDryPowder() {
        return dryPowder;
    }

    /**
     * @param dryPowder
     */
    public void setDryPowder(Integer dryPowder) {
        this.dryPowder = dryPowder;
    }

    /**
     * @return ladder_Ladder
     */
    public Integer getLadderLadder() {
        return ladderLadder;
    }

    /**
     * @param ladderLadder
     */
    public void setLadderLadder(Integer ladderLadder) {
        this.ladderLadder = ladderLadder;
    }

    /**
     * @return a_foam
     */
    public Integer getaFoam() {
        return aFoam;
    }

    /**
     * @param aFoam
     */
    public void setaFoam(Integer aFoam) {
        this.aFoam = aFoam;
    }

    /**
     * @return rescue_vehicle
     */
    public Integer getRescueVehicle() {
        return rescueVehicle;
    }

    /**
     * @param rescueVehicle
     */
    public void setRescueVehicle(Integer rescueVehicle) {
        this.rescueVehicle = rescueVehicle;
    }

    /**
     * @return climbing_platform
     */
    public Integer getClimbingPlatform() {
        return climbingPlatform;
    }

    /**
     * @param climbingPlatform
     */
    public void setClimbingPlatform(Integer climbingPlatform) {
        this.climbingPlatform = climbingPlatform;
    }

    /**
     * @return pressure_smoke
     */
    public Integer getPressureSmoke() {
        return pressureSmoke;
    }

    /**
     * @param pressureSmoke
     */
    public void setPressureSmoke(Integer pressureSmoke) {
        this.pressureSmoke = pressureSmoke;
    }

    /**
     * @return combatants
     */
    public Integer getCombatants() {
        return combatants;
    }

    /**
     * @param combatants
     */
    public void setCombatants(Integer combatants) {
        this.combatants = combatants;
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