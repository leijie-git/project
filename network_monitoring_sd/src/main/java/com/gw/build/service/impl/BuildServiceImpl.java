package com.gw.build.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.gw.build.data.BuildInData;
import com.gw.build.data.BuildOutData;
import com.gw.build.service.BuildService;
import com.gw.common.SnowflakeIdWorker;
import com.gw.exception.ServiceException;
import com.gw.mapper.UtUnitBuildMapper;
import com.gw.mapper.entity.UtUnitBuild;
import com.gw.util.Util;
import com.gw.util.UtilMessage;

@Service
public class BuildServiceImpl implements BuildService {

	@Resource
	private UtUnitBuildMapper utUnitBuildMapper;
	@Autowired
	private SnowflakeIdWorker snowflakeIdWorker;
	@Override
	public PageInfo<BuildOutData> getList(BuildInData inData)throws Exception {
		PageHelper.startPage(inData.getPageNumber(), inData.getPageSize());
		List<BuildOutData> list = utUnitBuildMapper.getList(inData);
		for(BuildOutData data : list) {
			if(data.getBuildDate()!=null && data.getBuildDate()!="") {
				data.setBuildDate(data.getBuildDate().substring(0, data.getBuildDate().indexOf(" ")));
			}
		}
		PageInfo<BuildOutData> pager = new PageInfo<BuildOutData>(list);
		return pager;
	}

	@Override
	public void add(BuildInData inData) throws Exception {
		UtUnitBuild build = new UtUnitBuild();
		build.setId(snowflakeIdWorker.nextId());
		if(inData.getAccommodatePersonMaxnum()!=""&&inData.getAccommodatePersonMaxnum()!=null) {
			build.setAccommodatepersonmaxnum(Integer.parseInt(inData.getAccommodatePersonMaxnum()));
		}
		if(inData.getUnitID()!=""&&inData.getUnitID()!=null) {
			build.setUnitid(Long.parseLong(inData.getUnitID()));
		}
		
		build.setAddress(inData.getAddress());
		build.setAutofirefacilities(inData.getAutoFireFacilities());
		if(inData.getBuildDate()!=""&&inData.getBuildDate()!=null) {
			build.setBuilddate(Util.StringToDate(inData.getBuildDate()));
		}
		if(inData.getBuildingArea()!=""&&inData.getBuildingArea()!=null) {
			BigDecimal area = new BigDecimal(inData.getBuildingArea());
			build.setBuildingarea(area);
		}
		build.setBuildingelevationmap(inData.getBuildingElevationMap());
		if(inData.getBuildingHeight()!=""&&inData.getBuildingHeight()!=null) {
			BigDecimal height = new BigDecimal(inData.getBuildingHeight());
			build.setBuildingheight(height);
		}
		build.setBuildingname(inData.getBuildingName());
		if(inData.getBuildingOccupyarea()!=""&&inData.getBuildingOccupyarea()!=null) {
			BigDecimal buildingoccupyarea = new BigDecimal(inData.getBuildingOccupyarea());
			build.setBuildingoccupyarea(buildingoccupyarea);
		}
		build.setBuildingplan(inData.getBuildingPlan());
		build.setBuildingtype(inData.getBuildingType());
		if(inData.getStdFloorArea()!=""&&inData.getStdFloorArea()!=null) {
			BigDecimal stdfloorarea = new BigDecimal(inData.getStdFloorArea());
			build.setStdfloorarea(stdfloorarea);
		}
		build.setCreatedate(new Date());
		if(inData.getDailyPersonNum()!=""&&inData.getDailyPersonNum()!=null) {
			build.setDailypersonnum(Integer.parseInt(inData.getDailyPersonNum()));
		}
		if(inData.getEvacuationStirNum()!=""&&inData.getEvacuationStirNum()!=null) {
			BigDecimal evacuationstirnum = new BigDecimal(inData.getEvacuationStirNum());
			build.setEvacuationstirnum(evacuationstirnum);
		}
		build.setUsertype(inData.getUserType());
		if(inData.getUnitID()!=""&&inData.getUnitID()!=null) {
			build.setUnitid(Long.parseLong(inData.getUnitID()));
		}
		if(inData.getOverGroundFloors()!=""&&inData.getOverGroundFloors()!=null) {
			BigDecimal OverGroundFloors = new BigDecimal(inData.getOverGroundFloors());
			build.setOvergroundfloors(OverGroundFloors);
		}
		if(inData.getOverGroundArea()!=""&&inData.getOverGroundArea()!=null) {
			BigDecimal OverGroundArea = new BigDecimal(inData.getOverGroundArea());
			build.setOvergroundarea(OverGroundArea);
		}
		if(inData.getUnderGroundFloors()!=""&&inData.getUnderGroundFloors()!=null) {
			BigDecimal undergroundfloors = new BigDecimal(inData.getUnderGroundFloors());
			build.setUndergroundfloors(undergroundfloors);
		}
		if(inData.getUnderGroundArea()!=""&&inData.getUnderGroundArea()!=null) {
			BigDecimal undergroundarea = new BigDecimal(inData.getUnderGroundArea());
			build.setUndergroundarea(undergroundarea);
		}
		if(inData.getTunnelLength()!=""&&inData.getTunnelLength()!=null) {
			BigDecimal tunnellength = new BigDecimal(inData.getTunnelLength());
			build.setTunnellength(tunnellength);
		}
		if(inData.getTunnelHeight()!=""&&inData.getTunnelHeight()!=null) {
			BigDecimal tunnelheight = new BigDecimal(inData.getTunnelHeight());
			build.setTunnelheight(tunnelheight);
		}
		build.setStructuretype(inData.getStructureType());
		build.setFireroomposition(inData.getFireRoomPosition());
		if(inData.getFireElevatorNum()!=""&&inData.getFireElevatorNum()!=null) {
			build.setFireelevatornum(Integer.parseInt(inData.getFireElevatorNum()));
		}
		if(inData.getFireElevatorWeight()!=""&&inData.getFireElevatorWeight()!=null) {
			BigDecimal fireelevatorweight = new BigDecimal(inData.getFireElevatorWeight());
			build.setFireelevatorweight(fireelevatorweight);
		}
		build.setRefractorygrade(inData.getRefractoryGrade());
		if(inData.getRefugeArea()!=""&&inData.getRefugeArea()!=null) {
			BigDecimal refugearea = new BigDecimal(inData.getRefugeArea());
			build.setRefugearea(refugearea);
		}
		if(inData.getRefugeNum()!=""&&inData.getRefugeNum()!=null) {
			build.setRefugenum(Integer.parseInt(inData.getRefugeNum()));
		}
		build.setRefugeposition(inData.getRefugePosition());
		if(inData.getStoreVolume()!=""&&inData.getStoreVolume()!=null) {
			BigDecimal storevolume = new BigDecimal(inData.getStoreVolume());
			build.setStorevolume(storevolume);
		}
		if(inData.getStoreMaterialNum()!=""&&inData.getStoreMaterialNum()!=null) {
			build.setStorematerialnum(Integer.parseInt(inData.getStoreMaterialNum()));
		}
		build.setStorematerialform(inData.getStoreMaterialForm());
		build.setStorematerialname(inData.getStoreMaterialName());
		build.setStorematerialnature(inData.getStoreMaterialNature());
		if(inData.getSafeExitNum()!=""&&inData.getSafeExitNum()!=null) {
			build.setSafeexitnum(Integer.parseInt(inData.getSafeExitNum()));
		}
		build.setFireriskgrade(inData.getFireRiskGrade());
		build.setSafeexitposition(inData.getSafeExitPosition());
		build.setSafeexittype(inData.getSafeExitType());
		build.setMainmaterial(inData.getMainMaterial());
		build.setMainproduct(inData.getMainProduct());
		build.setBuildingplan(inData.getBuildingPlan());
		build.setNearbuildingsituation(inData.getNearBuildingSituation());
		if(inData.getFloors()!=""&&inData.getFloors()!=null) {
			BigDecimal floors = new BigDecimal(inData.getFloors());
			build.setFloors(floors);
		}
		build.setFacilitiesplan(inData.getFacilitiesPlan());
		Integer flag  = utUnitBuildMapper.insert(build);
		if(flag<1) {
			throw new ServiceException(UtilMessage.ADD_ERROR);
		}
	}

	@Override
	@Transactional
	public void update(BuildInData inData) throws Exception {
		UtUnitBuild build = new UtUnitBuild();
		build.setId(Long.parseLong(inData.getID()));
		if(inData.getAccommodatePersonMaxnum()!=""&&inData.getAccommodatePersonMaxnum()!=null) {
			build.setAccommodatepersonmaxnum(Integer.parseInt(inData.getAccommodatePersonMaxnum()));
		}
		if(inData.getUnitID()!=""&&inData.getUnitID()!=null) {
			build.setUnitid(Long.parseLong(inData.getUnitID()));
		}
		build.setAddress(inData.getAddress());
		build.setAutofirefacilities(inData.getAutoFireFacilities());
		if(inData.getBuildDate()!=""&&inData.getBuildDate()!=null) {
			build.setBuilddate(Util.StringToDate(inData.getBuildDate()));
		}
		if(inData.getBuildingArea()!=""&&inData.getBuildingArea()!=null) {
			BigDecimal area = new BigDecimal(inData.getBuildingArea());
			build.setBuildingarea(area);
		}
		build.setBuildingelevationmap(inData.getBuildingElevationMap());
		if(inData.getBuildingHeight()!=""&&inData.getBuildingHeight()!=null) {
			BigDecimal height = new BigDecimal(inData.getBuildingHeight());
			build.setBuildingheight(height);
		}
		build.setBuildingname(inData.getBuildingName());
		if(inData.getBuildingOccupyarea()!=""&&inData.getBuildingOccupyarea()!=null) {
			BigDecimal buildingoccupyarea = new BigDecimal(inData.getBuildingOccupyarea());
			build.setBuildingoccupyarea(buildingoccupyarea);
		}
		build.setBuildingplan(inData.getBuildingPlan());
		build.setBuildingtype(inData.getBuildingType());
		if(inData.getStdFloorArea()!=""&&inData.getStdFloorArea()!=null) {
			BigDecimal stdfloorarea = new BigDecimal(inData.getStdFloorArea());
			build.setStdfloorarea(stdfloorarea);
		}
		build.setCreatedate(new Date());
		if(inData.getDailyPersonNum()!=""&&inData.getDailyPersonNum()!=null) {
			build.setDailypersonnum(Integer.parseInt(inData.getDailyPersonNum()));
		}
		if(inData.getEvacuationStirNum()!=""&&inData.getEvacuationStirNum()!=null) {
			BigDecimal evacuationstirnum = new BigDecimal(inData.getEvacuationStirNum());
			build.setEvacuationstirnum(evacuationstirnum);
		}
		build.setUsertype(inData.getUserType());
		if(inData.getUnitID()!=""&&inData.getUnitID()!=null) {
			build.setUnitid(Long.parseLong(inData.getUnitID()));
		}
		if(inData.getOverGroundFloors()!=""&&inData.getOverGroundFloors()!=null) {
			BigDecimal OverGroundFloors = new BigDecimal(inData.getOverGroundFloors());
			build.setOvergroundfloors(OverGroundFloors);
		}
		if(inData.getOverGroundArea()!=""&&inData.getOverGroundArea()!=null) {
			BigDecimal OverGroundArea = new BigDecimal(inData.getOverGroundArea());
			build.setOvergroundarea(OverGroundArea);
		}
		if(inData.getUnderGroundFloors()!=""&&inData.getUnderGroundFloors()!=null) {
			BigDecimal undergroundfloors = new BigDecimal(inData.getUnderGroundFloors());
			build.setUndergroundfloors(undergroundfloors);
		}
		if(inData.getUnderGroundArea()!=""&&inData.getUnderGroundArea()!=null) {
			BigDecimal undergroundarea = new BigDecimal(inData.getUnderGroundArea());
			build.setUndergroundarea(undergroundarea);
		}
		if(inData.getTunnelLength()!=""&&inData.getTunnelLength()!=null) {
			BigDecimal tunnellength = new BigDecimal(inData.getTunnelLength());
			build.setTunnellength(tunnellength);
		}
		if(inData.getTunnelHeight()!=""&&inData.getTunnelHeight()!=null) {
			BigDecimal tunnelheight = new BigDecimal(inData.getTunnelHeight());
			build.setTunnelheight(tunnelheight);
		}
		build.setStructuretype(inData.getStructureType());
		build.setFireroomposition(inData.getFireRoomPosition());
		if(inData.getFireElevatorNum()!=""&&inData.getFireElevatorNum()!=null) {
			build.setFireelevatornum(Integer.parseInt(inData.getFireElevatorNum()));
		}
		if(inData.getFireElevatorWeight()!=""&&inData.getFireElevatorWeight()!=null) {
			BigDecimal fireelevatorweight = new BigDecimal(inData.getFireElevatorWeight());
			build.setFireelevatorweight(fireelevatorweight);
		}
		build.setRefractorygrade(inData.getRefractoryGrade());
		if(inData.getRefugeArea()!=""&&inData.getRefugeArea()!=null) {
			BigDecimal refugearea = new BigDecimal(inData.getRefugeArea());
			build.setRefugearea(refugearea);
		}
		if(inData.getRefugeNum()!=""&&inData.getRefugeNum()!=null) {
			build.setRefugenum(Integer.parseInt(inData.getRefugeNum()));
		}
		build.setRefugeposition(inData.getRefugePosition());
		if(inData.getStoreVolume()!=""&&inData.getStoreVolume()!=null) {
			BigDecimal storevolume = new BigDecimal(inData.getStoreVolume());
			build.setStorevolume(storevolume);
		}
		if(inData.getStoreMaterialNum()!=""&&inData.getStoreMaterialNum()!=null) {
			build.setStorematerialnum(Integer.parseInt(inData.getStoreMaterialNum()));
		}
		build.setFireriskgrade(inData.getFireRiskGrade());
		build.setStorematerialform(inData.getStoreMaterialForm());
		build.setStorematerialname(inData.getStoreMaterialName());
		build.setStorematerialnature(inData.getStoreMaterialNature());
		if(inData.getSafeExitNum()!=""&&inData.getSafeExitNum()!=null) {
			build.setSafeexitnum(Integer.parseInt(inData.getSafeExitNum()));
		}
		build.setSafeexitposition(inData.getSafeExitPosition());
		build.setSafeexittype(inData.getSafeExitType());
		build.setMainmaterial(inData.getMainMaterial());
		build.setMainproduct(inData.getMainProduct());
		build.setNearbuildingsituation(inData.getNearBuildingSituation());
		if(inData.getFloors()!=""&&inData.getFloors()!=null) {
			BigDecimal floors = new BigDecimal(inData.getFloors());
			build.setFloors(floors);
		}
		build.setFacilitiesplan(inData.getFacilitiesPlan());
		Integer flag  = utUnitBuildMapper.updateByPrimaryKey(build);
		if(flag<1) {
			throw new ServiceException(UtilMessage.UPDATE_ERROR);
		}
	}
	

	@Override
	public void remove(String id) throws Exception {
			Integer flag = utUnitBuildMapper.deleteByPrimaryKey(Long.parseLong(id));
			if(flag<1) {
				throw new ServiceException(UtilMessage.DEL_MESSAGE_ERROR);
			}
	}
	
	@Override
	public List<BuildOutData> getArrayList(BuildInData inData) {
		List<BuildOutData> list = utUnitBuildMapper.getList(inData);
		if(list ==null) {
			throw new ServiceException(UtilMessage.GET_MSG_ERROR);
		}
		return list;
	}

}
