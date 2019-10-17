package com.gw.equipment.data;

import lombok.Data;

import java.util.List;
import java.util.Objects;

@Data
public class EquipmentFacInData {

	private String id;

	private Integer protocoltype;

	private String eqname;

	private String eqmodel;

	private String partcode;

	private String loopcode;

	private String positionnumber;

	private String installdate;

	private String installposition;

	private String pointx;

	private String pointy;

	private String manufacturer;

	private String manufacturerphone;

	private String brand;

	private String validitydate;

	private String productdate;

	private String supplier;

	private String pointcode;

	private Integer status;

	private String statustime;

	private String usetime;

	private String pipediameter;

	private String eqcapacity;

	private String eqflow;

	private String eqlift;

	private String pipetype;

	private String eqspace;

	private String airvolume;

	private String eqpower;

	private String eqchangedate;

	private String lastupdate;

	private String ownercode;

	private String systemtype;

	private String systemadd;

	private Integer datafrom;

	private String datafromid;

	private String isneedinspect;

	private String qrcode;

	private String floors;

	private String eqsystemid;

	private String eqsystemcode;


	private String eqclassid;

	private String eqclassname;

	private String unitid;

	private String unitName;

	private String buildid;

	private String buildareaid;

	private String netdeviceid;

	private String detailid;

	private String detialname;

	private String goodname;

	private String badname;

	private String digitalnormal;

	private Integer iotype;

	private String ioport;

	private String analogup;//最大值（正常范围）

	private String analogdown;//最小值（正常范围）

	private String analogunit;

	private String analogk;

	private String analogb;

	private String remark;

	private String eqid;

	private List<EquipmentRTUInData> list;

	private List<EquipmentThreeInData> portLists;

	private String portList;

	private String reserve;

	private Integer actionType;

	private String maxValue;//最大值

	private String minValue;//最小值

	private Integer pageNumber;//第几页

	private Integer pageSize;//每页条数

	private String netDeviceName;

	private String pointVideoId;

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		EquipmentFacInData facInData = (EquipmentFacInData) o;
		return Objects.equals(eqname, facInData.eqname) &&
				Objects.equals(installposition, facInData.installposition) &&
				Objects.equals(eqsystemcode, facInData.eqsystemcode) &&
				Objects.equals(eqclassid, facInData.eqclassid) &&
				Objects.equals(unitid, facInData.unitid) &&
				Objects.equals(buildid, facInData.buildid) &&
				Objects.equals(buildareaid, facInData.buildareaid) &&
				Objects.equals(netdeviceid, facInData.netdeviceid);
	}

	@Override
	public int hashCode() {

		return Objects.hash(eqname, installposition, eqsystemcode, eqclassid, unitid, buildid, buildareaid, netdeviceid);
	}
}
