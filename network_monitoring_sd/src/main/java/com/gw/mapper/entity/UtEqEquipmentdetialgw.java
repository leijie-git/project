package com.gw.mapper.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.*;

@Table(name = "UT_EQ_EquipmentDetialGW")
public class UtEqEquipmentdetialgw implements Serializable {
    @Id
    @Column(name = "ID")
    private Long id;

    @Column(name = "DetialName")
    private String detialname;

    @Column(name = "IOType")
    private Integer iotype;

    @Column(name = "IOPort")
    private Integer ioport;

    @Column(name = "AnalogUp")
    private BigDecimal analogup;

    @Column(name = "AnalogDown")
    private BigDecimal analogdown;

    @Column(name = "AnalogUnit")
    private String analogunit;

    @Column(name = "AnalogK")
    private BigDecimal analogk;

    @Column(name = "AnalogB")
    private BigDecimal analogb;

    @Column(name = "DigitalNormal")
    private Integer digitalnormal;

    @Column(name = "GoodName")
    private String goodname;

    @Column(name = "BadName")
    private String badname;

    @Column(name = "Remark")
    private String remark;

    @Column(name = "EQID")
    private Long eqid;

    @Column(name = "NetDeviceID")
    private Long netdeviceid;

    @Column(name = "AnalogWarningUp")
    private BigDecimal analogwarningup;
    
    @Column(name = "AnalogWarningDown")
    private BigDecimal analogwarningdown;
    
    private String reserve;
    
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

	@Column(name = "isUploadA")
    private Integer isuploada;
    
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

    /**
     * @return DetialName
     */
    public String getDetialname() {
        return detialname;
    }

    /**
     * @param detialname
     */
    public void setDetialname(String detialname) {
        this.detialname = detialname;
    }

    /**
     * @return IOType
     */
    public Integer getIotype() {
        return iotype;
    }

    /**
     * @param iotype
     */
    public void setIotype(Integer iotype) {
        this.iotype = iotype;
    }

    /**
     * @return IOPort
     */
    public Integer getIoport() {
        return ioport;
    }

    /**
     * @param ioport
     */
    public void setIoport(Integer ioport) {
        this.ioport = ioport;
    }

    /**
     * @return AnalogUp
     */
    public BigDecimal getAnalogup() {
        return analogup;
    }

    /**
     * @param analogup
     */
    public void setAnalogup(BigDecimal analogup) {
        this.analogup = analogup;
    }

    /**
     * @return AnalogDown
     */
    public BigDecimal getAnalogdown() {
        return analogdown;
    }

    /**
     * @param analogdown
     */
    public void setAnalogdown(BigDecimal analogdown) {
        this.analogdown = analogdown;
    }

    /**
     * @return AnalogUnit
     */
    public String getAnalogunit() {
        return analogunit;
    }

    /**
     * @param analogunit
     */
    public void setAnalogunit(String analogunit) {
        this.analogunit = analogunit;
    }

    /**
     * @return AnalogK
     */
    public BigDecimal getAnalogk() {
        return analogk;
    }

    /**
     * @param analogk
     */
    public void setAnalogk(BigDecimal analogk) {
        this.analogk = analogk;
    }

    /**
     * @return AnalogB
     */
    public BigDecimal getAnalogb() {
        return analogb;
    }

    /**
     * @param analogb
     */
    public void setAnalogb(BigDecimal analogb) {
        this.analogb = analogb;
    }

    /**
     * @return DigitalNormal
     */
    public Integer getDigitalnormal() {
        return digitalnormal;
    }

    /**
     * @param digitalnormal
     */
    public void setDigitalnormal(Integer digitalnormal) {
        this.digitalnormal = digitalnormal;
    }

    /**
     * @return GoodName
     */
    public String getGoodname() {
        return goodname;
    }

    /**
     * @param goodname
     */
    public void setGoodname(String goodname) {
        this.goodname = goodname;
    }

    /**
     * @return BadName
     */
    public String getBadname() {
        return badname;
    }

    /**
     * @param badname
     */
    public void setBadname(String badname) {
        this.badname = badname;
    }

    /**
     * @return Remark
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
     * @return EQID
     */
    public Long getEqid() {
        return eqid;
    }

    /**
     * @param eqid
     */
    public void setEqid(Long eqid) {
        this.eqid = eqid;
    }

    /**
     * @return NetDeviceID
     */
    public Long getNetdeviceid() {
        return netdeviceid;
    }

    /**
     * @param netdeviceid
     */
    public void setNetdeviceid(Long netdeviceid) {
        this.netdeviceid = netdeviceid;
    }

	public BigDecimal getAnalogwarningup() {
		return analogwarningup;
	}

	public void setAnalogwarningup(BigDecimal analogwarningup) {
		this.analogwarningup = analogwarningup;
	}

	public BigDecimal getAnalogwarningdown() {
		return analogwarningdown;
	}

	public void setAnalogwarningdown(BigDecimal analogwarningdown) {
		this.analogwarningdown = analogwarningdown;
	}

	public String getReserve() {
		return reserve;
	}

	public void setReserve(String reserve) {
		this.reserve = reserve;
	}
    
}