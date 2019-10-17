package com.gw.device.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.gw.device.data.AddressRelInData;
import com.gw.device.data.AddressRelOutData;
import com.gw.device.data.CRTInData;
import com.gw.device.data.UnitAssociatedAreaOutData;
import com.gw.device.service.CRTMarkService;
import com.gw.front.unit.data.FrontUnitBuildArea;
import com.gw.front.unit.data.FrontUnitCRTOutData;
import com.gw.mapper.UtEqAddressRelMapper;
import com.gw.mapper.UtUnitBaseinfoMapper;
import com.gw.mapper.UtUnitBuildMapper;
import com.gw.mapper.UtUnitBuildareaMapper;
import com.gw.mapper.entity.UtEqAddressRel;
import com.gw.mapper.entity.UtUnitBuild;
import com.gw.mapper.entity.UtUnitBuildarea;
import com.gw.unit.data.UnitBaseInfoOutData;
import com.gw.util.Util;

@Service
public class CRTMarkServiceImpl implements CRTMarkService {
	@Autowired
	private UtUnitBuildareaMapper utUnitBuildareaMapper;
	@Autowired
	private UtUnitBaseinfoMapper utUnitBaseinfoMapper;
	@Autowired
	private UtEqAddressRelMapper utEqAddressRelMapper;
	@Autowired
	private UtUnitBuildMapper utUnitBuildMapper;

	@Override
	public List<UnitAssociatedAreaOutData> getUnitAssociatedArea(String unitName, String unitId) throws Exception {
		List<UnitAssociatedAreaOutData> outData = new ArrayList<UnitAssociatedAreaOutData>();
		List<UnitBaseInfoOutData> unitList = utUnitBaseinfoMapper.getUnitByName(unitName,unitId);
		boolean unitFlag;
		boolean buildFlag;
		Map<Long, List<UtUnitBuild>> buildMap = new HashMap<>();
		List<UtUnitBuild> selectAll = utUnitBuildMapper.selectAll();
		for (UtUnitBuild utUnitBuild : selectAll) {
			Long unitid = utUnitBuild.getUnitid();
			if (!buildMap.containsKey(unitid)) {
				buildMap.put(unitid, new ArrayList<>());
			}
			buildMap.get(unitid).add(utUnitBuild);
		}
		Map<Long, List<UtUnitBuildarea>> buildareaMap = new HashMap<>();
		List<UtUnitBuildarea> list = utUnitBuildareaMapper.selectAll();
		for (UtUnitBuildarea utUnitBuildarea : list) {
			Long buildid = utUnitBuildarea.getBuildid();
			if (!buildareaMap.containsKey(buildid)) {
				buildareaMap.put(buildid, new ArrayList<>());
			}
			buildareaMap.get(buildid).add(utUnitBuildarea);
		}
		for (UnitBaseInfoOutData unitBaseInfoOutData : unitList) {
			unitFlag = false;
			List<UtUnitBuild> buildList = buildMap.get(Long.valueOf(unitBaseInfoOutData.getId()));
			if (Util.isEmpty(buildList)) {
				continue;
			}
			for (UtUnitBuild buildOutData : buildList) {
				Long id = buildOutData.getId();
				String buildingname = buildOutData.getBuildingname();
				buildFlag = false;
				List<UtUnitBuildarea> buildAreaList = buildareaMap.get(id);
				if (Util.isNotEmpty(buildAreaList)) {
					for (UtUnitBuildarea buildAreaOutData : buildAreaList) {
						if (Util.isNotEmpty(buildAreaOutData.getBuildareabg())) {
							unitFlag = true;
							buildFlag = true;
							UnitAssociatedAreaOutData areaOutData = new UnitAssociatedAreaOutData();
							areaOutData.setId(buildAreaOutData.getId() + "");
							areaOutData.setName(buildAreaOutData.getBuildareaname());
							areaOutData.setBuildImgbg(buildAreaOutData.getBuildareabg());
							areaOutData.setPid(id + "");
							areaOutData.setIsArea(1);
							areaOutData.setBgHeight(buildAreaOutData.getBgheight().toString());
							areaOutData.setBgWidth(buildAreaOutData.getBgwidth().toString());
							outData.add(areaOutData);
						}
					}
				}
				if (buildFlag) {
					UnitAssociatedAreaOutData build = new UnitAssociatedAreaOutData();
					build.setId(id + "");
					build.setName(buildingname);
					build.setPid(unitBaseInfoOutData.getId());
					outData.add(build);
				}
			}
			if (unitFlag) {
				UnitAssociatedAreaOutData unitOutData = new UnitAssociatedAreaOutData();
				unitOutData.setId(unitBaseInfoOutData.getId());
				unitOutData.setName(unitBaseInfoOutData.getUnitname());
				unitOutData.setPid("0");
				outData.add(unitOutData);
			}
		}
		return outData;
	}

	@Override
	public PageInfo<AddressRelOutData> getAreaAssociatedEquipment(CRTInData inData) throws Exception {
		/*
		 * List<AreaAssociatedEquipmentOutData> outData = new
		 * ArrayList<AreaAssociatedEquipmentOutData>(); List<EquipmentFacOutData>
		 * equipmentList = utEqEquipmentMapper.getAreaAssociatedEquipment(buildAreaId);
		 * for (EquipmentFacOutData equipmentFacOutData : equipmentList) {
		 * AreaAssociatedEquipmentOutData equipmentOutData = new
		 * AreaAssociatedEquipmentOutData();
		 * equipmentOutData.setId(equipmentFacOutData.getId());
		 * equipmentOutData.setName(equipmentFacOutData.getEqname());
		 * equipmentOutData.setPid("0"); outData.add(equipmentOutData);
		 * 
		 * Example example = new Example(UtEqAddressRel.class);
		 * example.createCriteria().andEqualTo("eqid", equipmentFacOutData.getId());
		 * List<UtEqAddressRel> eqAddressList =
		 * utEqAddressRelMapper.selectByExample(example); for (UtEqAddressRel
		 * utEqAddressRel : eqAddressList) { AreaAssociatedEquipmentOutData
		 * addressOutData = new AreaAssociatedEquipmentOutData();
		 * addressOutData.setId(utEqAddressRel.getId().toString());
		 * addressOutData.setPid(equipmentFacOutData.getId());
		 * addressOutData.setName(utEqAddressRel.getName());
		 * addressOutData.setAdress(utEqAddressRel.getAdress());
		 * addressOutData.setPartcode(utEqAddressRel.getPartcode());
		 * addressOutData.setXaxis(utEqAddressRel.getXaxis());
		 * addressOutData.setYaxis(utEqAddressRel.getYaxis());
		 * outData.add(addressOutData); } } return outData;
		 */
		PageHelper.startPage(inData.getPageNumber(), inData.getPageSize());
		List<AddressRelOutData> list = utEqAddressRelMapper.getAddressRelByBuildAreaId(inData);
		PageInfo<AddressRelOutData> pageInfo = new PageInfo<AddressRelOutData>(list);
		return pageInfo;
	}

	@Override
	public void setEqPoint(AddressRelInData inData) {
		UtEqAddressRel utEqAddressRel = utEqAddressRelMapper.selectByPrimaryKey(Long.valueOf(inData.getId()));
		// BeanUtils.copyProperties(inData, utEqAddressRel);
		// if(Util.isNotEmpty(inData.getEqid())){
		// utEqAddressRel.setEqid(Long.valueOf(inData.getEqid()));
		// }else{
		// utEqAddressRel.setEqid(null);
		// }
		// if(Util.isNotEmpty(inData.getEqSystemID())){
		// utEqAddressRel.setEqsystemid(Long.valueOf(inData.getEqSystemID()));
		// }else{
		// utEqAddressRel.setEqsystemid(null);
		// }
		// if(Util.isNotEmpty(inData.getBuildAreaid())){
		// utEqAddressRel.setBuildareaid(Long.valueOf(inData.getBuildAreaid()));
		// }else{
		// utEqAddressRel.setBuildareaid(null);
		// }
		utEqAddressRel.setXaxis(inData.getXaxis());
		utEqAddressRel.setYaxis(inData.getYaxis());
		if (Util.isNotEmpty(inData.getPointtype())) {
			utEqAddressRel.setPointtype(Long.valueOf(inData.getPointtype()));
		} else {
			utEqAddressRel.setPointtype(null);
		}
		utEqAddressRelMapper.updateByPrimaryKey(utEqAddressRel);
	}

	@Override
	public List<FrontUnitCRTOutData> getCRTList(String unitID) throws Exception {
		return utEqAddressRelMapper.getCTRListByUnitID(unitID);
	}

	@Override
	public List<AddressRelOutData> getAllAreaAssociatedEquipment(CRTInData inData) throws Exception {
		return utEqAddressRelMapper.getAddressRelByBuildAreaId(inData);
	}

	@Override
	public void emptyPoint(String id) throws Exception {
		UtEqAddressRel utEqAddressRel = utEqAddressRelMapper.selectByPrimaryKey(Long.valueOf(id));
		utEqAddressRel.setXaxis(null);
		utEqAddressRel.setYaxis(null);
		utEqAddressRel.setPointtype(null);
		utEqAddressRelMapper.updateByPrimaryKey(utEqAddressRel);
	}

	@Override
	public void updateEqAddressRel(AddressRelInData inData) throws Exception {
		UtEqAddressRel utEqAddressRel = utEqAddressRelMapper.selectByPrimaryKey(Long.valueOf(inData.getId()));
		utEqAddressRel.setAdress(inData.getAdress());
		utEqAddressRel.setName(inData.getName());
		utEqAddressRel.setRemark(inData.getRemark());
		utEqAddressRelMapper.updateByPrimaryKey(utEqAddressRel);
	}

	@Override
	public List<FrontUnitCRTOutData> getUnitOneCRT(String buildAreaID) throws Exception {
		return utEqAddressRelMapper.getUnitOneCRT(buildAreaID);
	}

	@Override
	public FrontUnitCRTOutData getOneCRT(String addressID) throws Exception {
		return utEqAddressRelMapper.getOneCRT(addressID);
	}

	@Override
	public List<FrontUnitBuildArea> getUnitBuildArea(String unitID) throws Exception {
		return utUnitBuildareaMapper.getUnitBuildArea(unitID);
	}

}
