package com.gw.front.unit.data;

import lombok.Data;

@Data
public class FrontUnitCRTOutData {
	private String id;
	private String eqid;//设备基本表ID
	private String partcode;// 部位号
	private String adress;//真实地址
	private String eqname;// 设备名称
	private String remark;// 备注
	private String xAxis;// 坐标X
	private String yAxis;// 坐标Y
	private String videoId;//视频id
	private String EqSystemID;// 系统ID
	private String buildAreaImg;//区域位图
	private String ggWidth;//宽
	private String bgHeight;//高
	private String codeValue;//点位图
	private String buildAreaId;//区域id
	private String name;
	private String ownercode;
	private String netdeviceid;
	private Integer isuploada;
	private Integer isuploadb;
	private String unitID;
}
