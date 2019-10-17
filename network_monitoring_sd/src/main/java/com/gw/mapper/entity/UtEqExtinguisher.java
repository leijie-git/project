package com.gw.mapper.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

@Table(name = "UT_EQ_Extinguisher")
public class UtEqExtinguisher implements Serializable {
    @Id
    @Column(name = "ID")
    private Long id;

    @Column(name = "extinguisher_type")
    private String extinguisherType;

    @Column(name = "extinguisher_position")
    private String extinguisherPosition;

    @Column(name = "product_date")
    private Date productDate;

    @Column(name = "jc_date")
    private Date jcDate;

    @Column(name = "fill_date")
    private Date fillDate;

    @Column(name = "validity_date")
    private Date validityDate;

    @Column(name = "Extinguisher_Num")
    private Integer extinguisherNum;

    @Column(name = "Product_UnitName")
    private String productUnitname;

    @Column(name = "Product_UnitPhone")
    private String productUnitphone;

    private String remark;
    
    @Column(name = "unit_id")
    private Long unitId;

    @Column(name = "expire_date")
    private Date expireDate;
    
    @Column(name = "extinguisher_code")
    private String extinguisherCode;
    
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
     * @return extinguisher_type
     */
    public String getExtinguisherType() {
        return extinguisherType;
    }

    /**
     * @param extinguisherType
     */
    public void setExtinguisherType(String extinguisherType) {
        this.extinguisherType = extinguisherType;
    }

    /**
     * @return extinguisher_position
     */
    public String getExtinguisherPosition() {
        return extinguisherPosition;
    }

    /**
     * @param extinguisherPosition
     */
    public void setExtinguisherPosition(String extinguisherPosition) {
        this.extinguisherPosition = extinguisherPosition;
    }

    /**
     * @return product_date
     */
    public Date getProductDate() {
        return productDate;
    }

    /**
     * @param productDate
     */
    public void setProductDate(Date productDate) {
        this.productDate = productDate;
    }

    /**
     * @return jc_date
     */
    public Date getJcDate() {
        return jcDate;
    }

    /**
     * @param jcDate
     */
    public void setJcDate(Date jcDate) {
        this.jcDate = jcDate;
    }

    /**
     * @return fill_date
     */
    public Date getFillDate() {
        return fillDate;
    }

    /**
     * @param fillDate
     */
    public void setFillDate(Date fillDate) {
        this.fillDate = fillDate;
    }

    /**
     * @return validity_date
     */
    public Date getValidityDate() {
        return validityDate;
    }

    /**
     * @param validityDate
     */
    public void setValidityDate(Date validityDate) {
        this.validityDate = validityDate;
    }

    /**
     * @return Extinguisher_Num
     */
    public Integer getExtinguisherNum() {
        return extinguisherNum;
    }

    /**
     * @param extinguisherNum
     */
    public void setExtinguisherNum(Integer extinguisherNum) {
        this.extinguisherNum = extinguisherNum;
    }

    /**
     * @return Product_UnitName
     */
    public String getProductUnitname() {
        return productUnitname;
    }

    /**
     * @param productUnitname
     */
    public void setProductUnitname(String productUnitname) {
        this.productUnitname = productUnitname;
    }

    /**
     * @return Product_UnitPhone
     */
    public String getProductUnitphone() {
        return productUnitphone;
    }

    /**
     * @param productUnitphone
     */
    public void setProductUnitphone(String productUnitphone) {
        this.productUnitphone = productUnitphone;
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

	public Long getUnitId() {
		return unitId;
	}

	public void setUnitId(Long unitId) {
		this.unitId = unitId;
	}

	public Date getExpireDate() {
		return expireDate;
	}

	public void setExpireDate(Date expireDate) {
		this.expireDate = expireDate;
	}

	public String getExtinguisherCode() {
		return extinguisherCode;
	}

	public void setExtinguisherCode(String extinguisherCode) {
		this.extinguisherCode = extinguisherCode;
	}
	
	
    
}