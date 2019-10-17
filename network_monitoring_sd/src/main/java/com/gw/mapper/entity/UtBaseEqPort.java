package com.gw.mapper.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Table(name = "UT_Base_EqPort")
public class UtBaseEqPort implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID")
	private Long id;

	@Column(name = "ClassCode")
	private Integer classcode;

	@Column(name = "PortName")
	private String portName;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getClasscode() {
		return classcode;
	}

	public void setClasscode(Integer classcode) {
		this.classcode = classcode;
	}

	public String getPortName() {
		return portName;
	}

	public void setPortName(String portName) {
		this.portName = portName;
	}
}