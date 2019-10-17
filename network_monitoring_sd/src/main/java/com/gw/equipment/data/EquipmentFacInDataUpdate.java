package com.gw.equipment.data;

import lombok.Data;

@Data
public class EquipmentFacInDataUpdate {

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

	private String list;

	private String portLists;

	private String reserve;

	private Integer actionType;

	private String maxValue;//最大值

	private String minValue;//最小值

	private Integer pageNumber;//第几页

	private Integer pageSize;//每页条数

	private String netDeviceName;

	private String buildImgbg;//平面图

	private String pointVideoId;
	//设备类型 是否主机  RTU 用户传输装置
	private  String deviceindex;


}
