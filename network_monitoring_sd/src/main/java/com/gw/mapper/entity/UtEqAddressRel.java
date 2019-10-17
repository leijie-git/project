package com.gw.mapper.entity;

import java.io.Serializable;
import javax.persistence.*;

@Table(name = "Ut_EQ_address_rel")
public class UtEqAddressRel implements Serializable {
	@Id
	@Column(name = "ID")
	private Long id;

	@Column(name = "unitId")
	private Long unitId;

	@Column(name = "eqid")
	private Long eqid;

	private String partcode;

	private String adress;

	private String name;

	private String remark;

	@Column(name = "EqSystemID")
	private Long eqsystemid;

	@Column(name = "xAxis")
	private String xaxis;

	@Column(name = "yAxis")
	private String yaxis;

	@Column(name = "BuildAreaID")
	private Long buildareaid;

	@Column(name = "pointType")
	private Long pointtype;

	@Column(name = "videoId")
	private Long videoid;
	@Column(name = "isUploadA")
	private Integer isuploada;

	public Integer getIsuploada() {
		return isuploada;
	}

	public void setIsuploada(Integer isuploada) {
		this.isuploada = isuploada;
	}

	public Integer getIsuploadb() {
		return isuploadb;
	}

	public void setIsuploadb(Integer isuploadb) {
		this.isuploadb = isuploadb;
	}

	@Column(name = "isUploadB")
	private Integer isuploadb;


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


	public Long getEqid() {
		return eqid;
	}

	public void setEqid(Long eqid) {
		this.eqid = eqid;
	}

	/**
	 * @return partcode
	 */
	public String getPartcode() {
		return partcode;
	}

	/**
	 * @param partcode
	 */
	public void setPartcode(String partcode) {
		this.partcode = partcode;
	}

	/**
	 * @return adress
	 */
	public String getAdress() {
		return adress;
	}

	/**
	 * @param adress
	 */
	public void setAdress(String adress) {
		this.adress = adress;
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

	public Long getEqsystemid() {
		return eqsystemid;
	}

	public void setEqsystemid(Long eqsystemid) {
		this.eqsystemid = eqsystemid;
	}

	public String getXaxis() {
		return xaxis;
	}

	public void setXaxis(String xaxis) {
		this.xaxis = xaxis;
	}

	public String getYaxis() {
		return yaxis;
	}

	public void setYaxis(String yaxis) {
		this.yaxis = yaxis;
	}

	public Long getBuildareaid() {
		return buildareaid;
	}

	public void setBuildareaid(Long buildareaid) {
		this.buildareaid = buildareaid;
	}

	public Long getPointtype() {
		return pointtype;
	}

	public void setPointtype(Long pointtype) {
		this.pointtype = pointtype;
	}

	public Long getVideoid() {
		return videoid;
	}

	public void setVideoid(Long videoid) {
		this.videoid = videoid;
	}

	public Long getUnitId() {
		return unitId;
	}

	public void setUnitId(Long unitId) {
		this.unitId = unitId;
	}
}