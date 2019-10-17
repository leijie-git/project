package com.gw.mapper.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "UT_Unit_Privatekey_REL")
public class UtUnitPrivatekeyRel implements Serializable {
	@Id
	@Column(name = "ID")
    private Long id;
	
    @Column(name = "private_key")
    private String privateKey;

    @Column(name = "unit_id")
    private Long unitId;

    private static final long serialVersionUID = 1L;
    
    

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPrivateKey() {
		return privateKey;
	}

	public void setPrivateKey(String privateKey) {
		this.privateKey = privateKey;
	}

	public Long getUnitId() {
		return unitId;
	}

	public void setUnitId(Long unitId) {
		this.unitId = unitId;
	}
    
    

}