package com.gw.build.data;

import lombok.Data;

@Data
public class BuildOutData {

	private String ID;//建筑物ID
	
	private String UnitID;//单位ID
	
	private String unitName;//单位名称
	private String WBUnitID;//维保单位GUID
	private String IDFromData;//建筑主键GUID
	private String BuildingName;//建、构筑物名称 
	private String BuildingType;//建、构筑物类别 (见GA／T 396 2002表A．8)
	
	private String UserType;//使用性质 (见GA／T 396--2002表A．11)
	private String FireRiskGrade;//火灾危险性 (见GA／T 396--2002表A．12)
	private String RefractoryGrade;//耐火等级(见GA／T 396--2002表A．10)
	private String StructureType;//结构类型 (见GA／T 396--2002表A．9)
	
	private String BuildDate;//建造日期 
	private String BuildingHeight;//建筑高度 (单位：m，精确到小数点后2位)
	private String BuildingArea;//建筑面积 (单位：m2)
	private String BuildingOccupyarea;//占地面积 (单位：m2)
	private String StdFloorArea;//标准层面积(单位：m2)
	
	
	private String OverGroundFloors;//地上层数 
	private String OverGroundArea;//地上层面积 (单位：m2)
	private String UnderGroundFloors;//地下层数 
	private String UnderGroundArea;//地下层面积 (单位：m2)
	private String TunnelHeight;//隧道高度 (单位：m，精确到小数点后2位)
	private String TunnelLength;//隧道长度 (单位：m，精确到小数点后2位)
	private String FireRoomPosition;//消防控制室位置 
	private String RefugeNum;//避难层数量 
	private String RefugeArea;//避难层总面积 (单位：m2)
	private String RefugePosition;//避难层位置 
	private String SafeExitPosition;//安全出口位置 
	private String SafeExitNum;//安全出口数量
	private String SafeExitType;//安全出口形式 
	private String FireElevatorNum;//消防电梯数量 
	
	private String FireElevatorWeight;//消防电梯容纳总重量 (单位：kg)
	
	private String DailyPersonNum;//日常工作时间人数 
	private String AccommodatePersonMaxnum;//最大容纳人数 
	private String StoreMaterialName;//储存物名称 
	
	private String StoreMaterialNum;//储存物数量 
	private String StoreMaterialNature;//储存物性质 
	private String StoreMaterialForm;//储存物形态 
	private String StoreVolume;//储存容积 
	private String MainMaterial;//主要原料 
	private String MainProduct;//主要产品 
	private String NearBuildingSituation;//毗邻建筑物情况(毗邻建筑的使用性质、结构类型、建筑高度、与本建筑物的间距等信息)
	private String BuildingElevationMap;//建筑立面图 
	private String BuildingPlan;//建筑平面图 
	private String FacilitiesPlan;//消防设施平面布置图 
	private String Address;//建筑地址
	private String AutoFireFacilities;//自动消防设施
	private String Floors;//楼层数
	private String EvacuationStirNum;//疏散楼梯数
}
