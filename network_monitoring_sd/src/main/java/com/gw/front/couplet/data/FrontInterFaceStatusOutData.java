package com.gw.front.couplet.data;

import lombok.Data;

@Data
public class FrontInterFaceStatusOutData {
	private String id;//主键
	private String equipmentDetialName;// 检测设备详情名称
	private String normalValue;// 正常值
	private String alarmValue;// 报警值
	private String alarmTime;// 报警时间
	private String dealUser;// 处理人
	private String dealTime;// 处理时间
	private String dealResult;// 处理结果
	private String alarmCode;// 处理编号
	private String isDeal;// 是否处理
	private String unitName;//单位名称
	private String unitCode;//单位编号
	private String eqName;//设备名称
	private String interfaceId;//接口ID
	private String digitalNormal;//正常值
	private String classCode;//设备类型
	private String analogup;//最大值
	private String analogdown;//最小值
	private String currentValue;//当前值
	private String detialName;//信号名称
	private String lastupdate;//最后更新时间
	private String buildareaname;//区域
	private String netDeviceId;//设备Id
	private String ioType; //信号类型
	private String IOType1; //信号类型
	private String deviceNo; //子号
	private String eqSystemName; //系统名称
	private String ioPort; //端口索引
	private String scope;//参考范围
	private String analogUnit;//单位
	private String goodName;//正常名称
	private String badName;//异常名称
	private String analogWarningUp;//预警最大值（单纯最大值）
	private String analogWarningDown;//预警最小值（单纯最大值）
	private String ownercode;
	private String installposition;//安装位置
	
	private String reserve;//信号名称
	
	private Integer isuploada;
	private Integer isuploadb;
	private String unitID;
	private String className;
	private String buildImgbg;
	private String pointVideoId;
	private String tableName;
}
