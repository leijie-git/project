package com.gw.front.couplet.data;

import lombok.Data;

import java.util.List;

@Data
public class FrontCoupletInData {
	private String id;
	private Integer pageSize;// 每页条数
	private Integer pageNumber;// 第几页
	private String eqSystemID;// 系统
	private String eqClassID;// 系统类型
	private String upStatus;// 上传状态
	private String version;// 软件版本
	private String startDate;// 开始时间
	private String endDate;// 结束时间
	private String keyword;// 关键字
	private String userId;// 用户id
	private String unitId;// 单位id
	private String UnitName;//单位名称
	private String type;// 系统类型
	private String buildId;// 建筑Id
	private String database;// 数据库
	private String alarmStatus;// 告警类型
	private String dealResult;// 处理结果
	private String deviceId;//设备ID
	private List<String> eqIds;
	private String netDeviceId;//
	private List<String> statusList;//

	private String pointID;//端口id
	private String IOType;//模拟量数字量
	private String selectType;//sql查找标识
}
