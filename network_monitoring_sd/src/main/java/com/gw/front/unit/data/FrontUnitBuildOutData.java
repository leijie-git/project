package com.gw.front.unit.data;

import lombok.Data;

@Data
public class FrontUnitBuildOutData {
	private String id;//建筑物ID
	private String unitCode;
	private String unitName;//单位ID
	private String wBUnitID;//维保单位GUID
	private String iDFromData;//建筑主键GUID
	private String buildingName;//建、构筑物名称 
	private String buildingType;//建、构筑物类别 (见GA／T 396 2002表A．8)
	
	private String bserType;//使用性质 (见GA／T 396--2002表A．11)
	private String fireRiskGrade;//火灾危险性 (见GA／T 396--2002表A．12)
	private String refractoryGrade;//耐火等级(见GA／T 396--2002表A．10)
	private String structureType;//结构类型 (见GA／T 396--2002表A．9)
	
	private String buildDate;//建造日期 
	private String buildingHeight;//建筑高度 (单位：m，精确到小数点后2位)
	private String buildingArea;//建筑面积 (单位：m2)
	private String buildingOccupyarea;//占地面积 (单位：m2)
	private String stdFloorArea;//标准层面积(单位：m2)
	
	
	private String overGroundFloors;//地上层数 
	private String overGroundArea;//地上层面积 (单位：m2)
	private String underGroundFloors;//地下层数 
	private String underGroundArea;//地下层面积 (单位：m2)
	private String tunnelHeight;//隧道高度 (单位：m，精确到小数点后2位)
	private String tunnelLength;//隧道长度 (单位：m，精确到小数点后2位)
	private String fireRoomPosition;//消防控制室位置 
	private String refugeNum;//避难层数量 
	private String refugeArea;//避难层总面积 (单位：m2)
	private String refugePosition;//避难层位置 
	private String safeExitPosition;//安全出口位置 
	private String safeExitNum;//安全出口数量
	private String safeExitType;//安全出口形式 
	private String fireElevatorNum;//消防电梯数量 
	
	private String fireElevatorWeight;//消防电梯容纳总重量 (单位：kg)
	
	private String dailyPersonNum;//日常工作时间人数 
	private String accommodatePersonMaxnum;//最大容纳人数 
	private String storeMaterialName;//储存物名称 
	
	private String storeMaterialNum;//储存物数量 
	private String storeMaterialNature;//储存物性质 
	private String storeMaterialForm;//储存物形态 
	private String storeVolume;//储存容积 
	private String mainMaterial;//主要原料 
	private String mainProduct;//主要产品 
	private String nearBuildingSituation;//毗邻建筑物情况(毗邻建筑的使用性质、结构类型、建筑高度、与本建筑物的间距等信息)
	private String buildingElevationMap;//建筑立面图 
	private String buildingPlan;//建筑平面图 
	private String facilitiesPlan;//消防设施平面布置图 
	private String address;//建筑地址
	private String autoFireFacilities;//自动消防设施
	private String floors;//楼层数
	private String evacuationStirNum;//疏散楼梯数

}
