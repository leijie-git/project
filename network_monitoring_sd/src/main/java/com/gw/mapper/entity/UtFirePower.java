package com.gw.mapper.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

@Table(name = "UT_Fire_Power")
public class UtFirePower implements Serializable {
    @Id
    @Column(name = "ID")
    private Long id;

    @Column(name = "unit_id")
    private Long unitId;

    private String type;

    private String name;

    private String remark;

    @Column(name = "create_date")
    private Date createDate;

    @Column(name = "create_user")
    private Long createUser;

    @Column(name = "update_date")
    private Date updateDate;

    @Column(name = "update_user")
    private Long updateUser;

    @Column(name = "point_x")
    private String pointX;

    @Column(name = "point_y")
    private String pointY;

    private String address;
    
    private String contact;
    
    private String phone;
    
    private Long pid;
    
    private String image;

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
     * @return unit_id
     */
    public Long getUnitId() {
        return unitId;
    }

    /**
     * @param unitId
     */
    public void setUnitId(Long unitId) {
        this.unitId = unitId;
    }

    /**
     * @return type
     */
    public String getType() {
        return type;
    }

    /**
     * @param type
     */
    public void setType(String type) {
        this.type = type;
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

    /**
     * @return point_x
     */
    public String getPointX() {
        return pointX;
    }

    /**
     * @param pointX
     */
    public void setPointX(String pointX) {
        this.pointX = pointX;
    }

    /**
     * @return point_y
     */
    public String getPointY() {
        return pointY;
    }

    /**
     * @param pointY
     */
    public void setPointY(String pointY) {
        this.pointY = pointY;
    }

    /**
     * @return address
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

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Long getPid() {
		return pid;
	}

	public void setPid(Long pid) {
		this.pid = pid;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}
	
	
    
	
    
}