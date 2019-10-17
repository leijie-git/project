package com.gw.unit.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.gw.common.SnowflakeIdWorker;
import com.gw.exception.ServiceException;
import com.gw.mapper.*;
import com.gw.mapper.entity.*;
import com.gw.unit.data.*;
import com.gw.unit.service.UnitBaseInfoService;
import com.gw.util.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 联网设备服务层实现类
 *
 * @author SY
 */
@Service
public class UnitBaseInfoServiceImpl implements UnitBaseInfoService {
	private static final Logger LOGGER = LoggerFactory.getLogger(UnitBaseInfoServiceImpl.class);
	@Autowired
	private UtUnitBaseinfoMapper unitBaseinfoMapper;
	@Resource
	private UtUnitBuildMapper unitBuildMapper;
	@Resource
	private UtUnitBuildareaMapper unitBuildareaMapper;
	@Autowired
	private UtBaseProvicecityregionMapper baseProvicecityregionMapper;
	@Autowired
	private UtBaseUserreunitMapper userreunitMapper;
	@Autowired
	private SnowflakeIdWorker snowflakeIdWorker;
	@Autowired
	private UtUnitBaseinfoRelationMapper utUnitBaseinfoRelationMapper;
	@Autowired
	private UtMaintenanceReunitMapper utMaintenanceReunitMapper;
	@Autowired
	private UtMaintenanceUnitMapper utMaintenanceUnitMapper;
	@Value("${raw.data.database}")
	private String database;

	@Override
	public PageInfo<UnitBaseInfoOutData> getBaseInfoList(UnitBaseInfoInData inData) throws Exception {
		PageInfo<UnitBaseInfoOutData> pageInfo = null;
		PageHelper.startPage(inData.getPageNumber(), inData.getPageSize());
		if (Util.isNotEmpty(inData.getId())) {
			UnitBaseInfoOutData outData = unitBaseinfoMapper.getUnitById(inData);
			outData.setTotalNode(Integer.valueOf(outData.getTotalNode() + "," + outData.getTotalNbnode()));
			List<UnitBaseInfoOutData> list = new ArrayList<>();
			list.add(outData);
			pageInfo = new PageInfo<UnitBaseInfoOutData>(list);
		} else {
			List<UnitBaseInfoOutData> baseInfoList = unitBaseinfoMapper.selectAllUnitBaseInfo(inData);
			LOGGER.info("==========分页查询单位个数：" + baseInfoList.size());
			pageInfo = new PageInfo<>(baseInfoList);
		}
		return pageInfo;
	}

	@Override
	public List<BaseProvicecityregionOutData> getProvinceList(Integer type, String parentId) throws Exception {
		List<BaseProvicecityregionOutData> list = baseProvicecityregionMapper.selectRegionByType(type, parentId);
		return list;
	}

	@Override
	@Transactional
	public void addBaseInfo(UnitBaseInfoInData inData) throws Exception {
		Example example = new Example(UtMaintenanceUnit.class);
		example.createCriteria().andEqualTo("unitcode", inData.getUnitcode());
		List<UtUnitBaseinfo> list = unitBaseinfoMapper.selectByExample(example);
		if (Util.isNotEmpty(list)) {
			throw new ServiceException("单位编号已存在！");
		}
		UtUnitBaseinfo baseinfo = new UtUnitBaseinfo();
		BeanUtils.copyProperties(inData, baseinfo);
		long unitId = snowflakeIdWorker.nextId();
		baseinfo.setId(unitId);
		baseinfo.setIsdelete(0);
		if (Util.isNotEmpty(inData.getNetworkstatus())) {
			baseinfo.setNetworkstatus(Integer.parseInt(inData.getNetworkstatus()));
		}
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		if (Util.isNotEmpty(inData.getEstablishmenttime())) {
			baseinfo.setEstablishmenttime(format.parse(inData.getEstablishmenttime()));
		}
		if (Util.isNotEmpty(inData.getFixedassets())) {
			baseinfo.setFixedassets(new BigDecimal(inData.getFixedassets()));
		}
		if (Util.isNotEmpty(inData.getArea())) {
			baseinfo.setArea(new BigDecimal(inData.getArea()));
		}
		if (Util.isNotEmpty(inData.getBuildingarea())) {
			baseinfo.setBuildingarea(new BigDecimal(inData.getBuildingarea()));
		}
		if (Util.isNotEmpty(inData.getOnlinedate())) {
			baseinfo.setOnlinedate(format.parse(inData.getOnlinedate()));
		}
		if (Util.isNotEmpty(inData.getDatafrom())) {
			baseinfo.setDatafrom(Integer.parseInt(inData.getDatafrom()));
		}
		if (Util.isNotEmpty(inData.getImportant())) {
			baseinfo.setImportant(inData.getImportant().equals("1") ? true : false);
		}
		if (Util.isNotEmpty(inData.getStaffcount())) {
			baseinfo.setStaffcount(Integer.parseInt(inData.getStaffcount()));
		}
		if (Util.isEmpty(inData.getTotalNode())) {
			baseinfo.setTotalNode(100);
			baseinfo.setTotalNbNode(100);
		} else {
			String[] nodes = inData.getTotalNode().toString().split(",");
			for (int i = 0; i < nodes.length; i++) {
				baseinfo.setTotalNode(Integer.parseInt(nodes[0]));
				baseinfo.setTotalNbNode(Integer.parseInt(nodes[1]));
			}
		}
		Date date = new Date();
		baseinfo.setCreatedate(date);
		baseinfo.setLastupdate(date);
		if (Util.isNotEmpty(inData.getLzscore())) {
			baseinfo.setLzscore(new BigDecimal(inData.getLzscore()));
		}
		if (Util.isNotEmpty(inData.getLzscorelevel())) {
			baseinfo.setLzscorelevel(Integer.parseInt(inData.getLzscorelevel()));
		}
		if (Util.isNotEmpty(inData.getFirecount())) {
			baseinfo.setFirecount(Integer.parseInt(inData.getFirecount()));
		}
		if (Util.isNotEmpty(inData.getWatercount())) {
			baseinfo.setWatercount(Integer.parseInt(inData.getWatercount()));
		}
		if (Util.isNotEmpty(inData.getElectriccount())) {
			baseinfo.setElectriccount(Integer.parseInt(inData.getElectriccount()));
		}
		if (Util.isNotEmpty(inData.getVideocount())) {
			baseinfo.setVideocount(Integer.parseInt(inData.getVideocount()));
		}
		if (Util.isNotEmpty(inData.getProviceid())) {
			baseinfo.setProviceid(Long.valueOf(inData.getProviceid()));
		}
		if (Util.isNotEmpty(inData.getCityid())) {
			baseinfo.setCityid(Long.valueOf(inData.getCityid()));
		}
		if (Util.isNotEmpty(inData.getRegionid())) {
			baseinfo.setRegionid(Long.valueOf(inData.getRegionid()));
		}
		if (Util.isNotEmpty(inData.getTownid())) {
			baseinfo.setTownid(Long.valueOf(inData.getTownid()));
		}
		if (Util.isNotEmpty(inData.getChildstationnum())) {
			baseinfo.setChildstationnum(Long.valueOf(inData.getChildstationnum()));
		}
		if (Util.isNotEmpty(inData.getInductionpointcount())) {
			baseinfo.setInductionpointcount(Integer.parseInt(inData.getInductionpointcount()));
		}
		if (Util.isNotEmpty(inData.getIsautocall()) && inData.getIsautocall() == 0) {
			baseinfo.setAutocalldelay(null);
		}
		baseinfo.setOrgcode(inData.getOrgCode());
		baseinfo.setVideoAppkey(inData.getVideoAppkey());
		baseinfo.setVideoAppsecret(inData.getVideoAppsecret());
		baseinfo.setCallTotal(inData.getCallTotal());
		baseinfo.setCallAlarmType(inData.getCallAlarmType());
		int count = unitBaseinfoMapper.selectSystemNumber();
		String sysNum = "N";
		int b = count + 1;
		String countStr = String.format("%05d", b);
		baseinfo.setSystemNumber(sysNum + countStr);
		String[] unitIds = inData.getMaintenanceunitid().split(",");
		for (String s : unitIds
				) {
			UtMaintenanceReunit utMaintenanceReunit = new UtMaintenanceReunit();
			utMaintenanceReunit.setId(snowflakeIdWorker.nextId());
			utMaintenanceReunit.setManageid(Long.valueOf(s));
			utMaintenanceReunit.setUnitid(unitId);
			utMaintenanceReunitMapper.insert(utMaintenanceReunit);
		}

		unitBaseinfoMapper.insert(baseinfo);
	}

	@Override
	@Transactional
	public void deleteBaseInfoById(String id) throws Exception {
		Example example = new Example(UtUnitBaseinfoRelation.class);
		example.createCriteria().andEqualTo("unitid", Long.valueOf(id));
		List<UtUnitBaseinfoRelation> list = utUnitBaseinfoRelationMapper.selectByExample(example);
		if (Util.isNotEmpty(list)) {
			throw new ServiceException("删除失败，该单位下存在绑定编号！");
		}

		UtUnitBaseinfo utUnitBaseinfo = unitBaseinfoMapper.selectByPrimaryKey(Long.valueOf(id));
		utUnitBaseinfo.setIsdelete(1);
		unitBaseinfoMapper.updateByPrimaryKey(utUnitBaseinfo);
		//删除维保单位关联联网单位数据
		Example example2 = new Example(UtMaintenanceReunit.class);
		example2.createCriteria().andEqualTo("unitid", Long.valueOf(id));
		utMaintenanceReunitMapper.deleteByExample(example2);

		//删除人员关联单位的数据
		Example example3 = new Example(UtBaseUserreunit.class);
		example3.createCriteria().andEqualTo("unitId", Long.valueOf(id));
		userreunitMapper.deleteByExample(example3);
//		unitBaseinfoMapper.deleteByPrimaryKey(Long.valueOf(id));
	}

	@Override
	@Transactional
	public void updateBaseInfo(UnitBaseInfoInData inData) throws Exception {
		Example example1 = new Example(UtMaintenanceUnit.class);
		example1.createCriteria().andEqualTo("unitcode", inData.getUnitcode());
		List<UtUnitBaseinfo> list = unitBaseinfoMapper.selectByExample(example1);
		if (Util.isNotEmpty(list) && !list.get(0).getId().equals(Long.valueOf(inData.getId()))) {
			throw new ServiceException("单位编号已存在！");
		}
		UtUnitBaseinfo baseinfo = unitBaseinfoMapper.selectByPrimaryKey(Long.valueOf(inData.getId()));
		BeanUtils.copyProperties(inData, baseinfo);
		if (Util.isNotEmpty(inData.getNetworkstatus())) {
			baseinfo.setNetworkstatus(Integer.parseInt(inData.getNetworkstatus()));
		} else {
			baseinfo.setNetworkstatus(null);
		}
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		if (Util.isNotEmpty(inData.getEstablishmenttime())) {
			baseinfo.setEstablishmenttime(format.parse(inData.getEstablishmenttime()));
		} else {
			baseinfo.setEstablishmenttime(null);
		}
		if (Util.isNotEmpty(inData.getFixedassets())) {
			baseinfo.setFixedassets(new BigDecimal(inData.getFixedassets()));
		} else {
			baseinfo.setFixedassets(null);
		}
		if (Util.isNotEmpty(inData.getArea())) {
			baseinfo.setArea(new BigDecimal(inData.getArea()));
		} else {
			baseinfo.setArea(null);
		}
		if (Util.isNotEmpty(inData.getBuildingarea())) {
			baseinfo.setBuildingarea(new BigDecimal(inData.getBuildingarea()));
		} else {
			baseinfo.setBuildingarea(null);
		}
		if (Util.isNotEmpty(inData.getOnlinedate())) {
			baseinfo.setOnlinedate(format.parse(inData.getOnlinedate()));
		} else {
			baseinfo.setOnlinedate(null);
		}
		if (Util.isNotEmpty(inData.getDatafrom())) {
			baseinfo.setDatafrom(Integer.parseInt(inData.getDatafrom()));
		} else {
			baseinfo.setDatafrom(null);
		}
		if (Util.isNotEmpty(inData.getImportant())) {
			baseinfo.setImportant(inData.getImportant().equals("1") ? true : false);
		} else {
			baseinfo.setImportant(null);
		}
		if (Util.isNotEmpty(inData.getStaffcount())) {
			baseinfo.setStaffcount(Integer.parseInt(inData.getStaffcount()));
		} else {
			baseinfo.setStaffcount(null);
		}
		if (Util.isEmpty(inData.getTotalNode())) {
			baseinfo.setTotalNode(100);
		} else {
			String[] nodes = inData.getTotalNode().toString().split(",");
			for (int i = 0; i < nodes.length; i++) {
				baseinfo.setTotalNode(Integer.parseInt(nodes[0]));
				baseinfo.setTotalNbNode(Integer.parseInt(nodes[1]));
			}
		}
		Date date = new Date();
		baseinfo.setLastupdate(date);
		if (Util.isNotEmpty(inData.getLzscore())) {
			baseinfo.setLzscore(new BigDecimal(inData.getLzscore()));
		} else {
			baseinfo.setLzscore(null);
		}
		if (Util.isNotEmpty(inData.getLzscorelevel())) {
			baseinfo.setLzscorelevel(Integer.parseInt(inData.getLzscorelevel()));
		} else {
			baseinfo.setLzscorelevel(null);
		}
		if (Util.isNotEmpty(inData.getFirecount())) {
			baseinfo.setFirecount(Integer.parseInt(inData.getFirecount()));
		} else {
			baseinfo.setFirecount(null);
		}
		if (Util.isNotEmpty(inData.getWatercount())) {
			baseinfo.setWatercount(Integer.parseInt(inData.getWatercount()));
		} else {
			baseinfo.setWatercount(null);
		}
		if (Util.isNotEmpty(inData.getElectriccount())) {
			baseinfo.setElectriccount(Integer.parseInt(inData.getElectriccount()));
		} else {
			baseinfo.setElectriccount(null);
		}
		if (Util.isNotEmpty(inData.getVideocount())) {
			baseinfo.setVideocount(Integer.parseInt(inData.getVideocount()));
		} else {
			baseinfo.setVideocount(null);
		}
		if (Util.isNotEmpty(inData.getProviceid())) {
			baseinfo.setProviceid(Long.valueOf(inData.getProviceid()));
		} else {
			baseinfo.setProviceid(null);
		}
		if (Util.isNotEmpty(inData.getCityid())) {
			baseinfo.setCityid(Long.valueOf(inData.getCityid()));
		} else {
			baseinfo.setCityid(null);
		}
		if (Util.isNotEmpty(inData.getRegionid())) {
			baseinfo.setRegionid(Long.valueOf(inData.getRegionid()));
		} else {
			baseinfo.setRegionid(null);
		}
		if (Util.isNotEmpty(inData.getTownid())) {
			baseinfo.setTownid(Long.valueOf(inData.getTownid()));
		} else {
			baseinfo.setTownid(null);
		}
		if (Util.isNotEmpty(inData.getChildstationnum())) {
			baseinfo.setChildstationnum(Long.valueOf(inData.getChildstationnum()));
		} else {
			baseinfo.setChildstationnum(null);
		}
		if (Util.isNotEmpty(inData.getInductionpointcount())) {
			baseinfo.setInductionpointcount(Integer.parseInt(inData.getInductionpointcount()));
		} else {
			baseinfo.setInductionpointcount(null);
		}
		if (Util.isNotEmpty(inData.getIsautocall()) && inData.getIsautocall() == 0) {
			baseinfo.setAutocalldelay(null);
		}
		baseinfo.setOrgcode(inData.getOrgCode());
		baseinfo.setVideoAppkey(inData.getVideoAppkey());
		baseinfo.setVideoAppsecret(inData.getVideoAppsecret());
		baseinfo.setCallTotal(inData.getCallTotal());
		baseinfo.setCallAlarmType(inData.getCallAlarmType());
		unitBaseinfoMapper.updateByPrimaryKey(baseinfo);
		Example example = new Example(UtMaintenanceReunit.class);
		example.createCriteria().andEqualTo("unitid", Long.valueOf(inData.getId()));
		utMaintenanceReunitMapper.deleteByExample(example);
		String[] unitIds = inData.getMaintenanceunitid().split(",");
		for (String s : unitIds
				) {
			UtMaintenanceReunit utMaintenanceReunit = new UtMaintenanceReunit();
			utMaintenanceReunit.setId(snowflakeIdWorker.nextId());
			utMaintenanceReunit.setManageid(Long.valueOf(s));
			utMaintenanceReunit.setUnitid(Long.valueOf(inData.getId()));
			utMaintenanceReunitMapper.insert(utMaintenanceReunit);
		}
	}

	@Override
	public List<BaseInfoSelectOutData> baseInfoSelect() throws Exception {
		List<BaseInfoSelectOutData> list = unitBaseinfoMapper.selectUnitNameAndId();
		return list;
	}

	@Override
	public List<BaseInfoSelectOutData> getArrayList() throws Exception {
		return unitBaseinfoMapper.getArrayList();
	}

	@Override
	public GetUnitUsersData getUnitReUser(String unitId) throws Exception {
		if (Util.isEmpty(unitId)) {
			throw new ServiceException("单位不存在！");
		}
		GetUnitUsersData data = new GetUnitUsersData();
		List<GetUnitUsersData> hasUsers = unitBaseinfoMapper.getHasUsers(unitId);
		List<GetUnitUsersData> noUsers = unitBaseinfoMapper.getNoUsers(unitId);
		data.setHasUsers(hasUsers);
		data.setNoUsers(noUsers);
		return data;
	}

	@Override
	@Transactional
	public void setUnitReUser(String unitId, String manyUserId) throws Exception {
		if (Util.isEmpty(unitId)) {
			throw new Exception("单位不存在");
		}
		if (Util.isEmpty(manyUserId)) {
			unitBaseinfoMapper.deleteByUserId(unitId);
		} else {
			unitBaseinfoMapper.deleteByUserId(unitId);
			String[] split = manyUserId.split(",");
			for (String id : split) {
				UtBaseUserreunit userreunit = new UtBaseUserreunit();
				userreunit.setUnitId(Long.valueOf(unitId));
				userreunit.setUserId(Long.valueOf(id));
				userreunitMapper.insert(userreunit);
			}
		}

	}

	@Override
	@Transactional
	public void importAllUnits() throws Exception {
		List<TemporaryBaseUnitData> list = unitBaseinfoMapper.selectAllUnitsFromOthers(database);
		for (TemporaryBaseUnitData temporaryBaseUnitData : list) {
			//新增单位
			UtUnitBaseinfo utUnitBaseinfo = new UtUnitBaseinfo();
			BeanUtils.copyProperties(temporaryBaseUnitData, utUnitBaseinfo);
			Long unitId = snowflakeIdWorker.nextId();
			Long buildId = snowflakeIdWorker.nextId();
			Long areaId = snowflakeIdWorker.nextId();
			//新增建筑/区域

			UtUnitBuild unitBuild = new UtUnitBuild();
			unitBuild.setBuildingname("建筑");
			unitBuild.setId(buildId);
			unitBuild.setUnitid(unitId);
			unitBuildMapper.insert(unitBuild);

			UtUnitBuildarea buildArea = new UtUnitBuildarea();
			buildArea.setId(areaId);
			buildArea.setBuildareaname("区域");
			buildArea.setBuildid(buildId);
			buildArea.setUnitid(unitId);
			unitBuildareaMapper.insert(buildArea);

			utUnitBaseinfo.setId(unitId);
			utUnitBaseinfo.setNetworkstatus(1);
			utUnitBaseinfo.setCreatedate(new Date());
			unitBaseinfoMapper.insert(utUnitBaseinfo);

			//新增单位绑定编号
			UtUnitBaseinfoRelation utUnitBaseinfoRelation = new UtUnitBaseinfoRelation();
			utUnitBaseinfoRelation.setId(snowflakeIdWorker.nextId());
			utUnitBaseinfoRelation.setLastupdate(new Date());
			utUnitBaseinfoRelation.setSoureaddress(temporaryBaseUnitData.getOwnercode());
			utUnitBaseinfoRelation.setUnitid(unitId);
			utUnitBaseinfoRelationMapper.insert(utUnitBaseinfoRelation);

			//新增维保单位关联联网单位数据
			UtMaintenanceReunit utMaintenanceReunit = new UtMaintenanceReunit();
			utMaintenanceReunit.setId(snowflakeIdWorker.nextId());
			List<UtMaintenanceUnit> utMaintenanceUnits = utMaintenanceUnitMapper.selectAll();
			if (Util.isEmpty(utMaintenanceUnits)) {
				throw new ServiceException("缺少维保单位");
			}
			utMaintenanceReunit.setManageid(utMaintenanceUnits.get(0).getId());
			utMaintenanceReunit.setUnitid(unitId);

			utMaintenanceReunitMapper.insert(utMaintenanceReunit);

		}
	}

	@Override
	public List<UnitBaseInfoOutData> selectAllUnitBaseInfoById(UnitBaseInfoInData inData) throws Exception {
		return unitBaseinfoMapper.selectAllUnitBaseInfoById(inData.getId());
	}

	@Override
	public List<UnitBaseInfoOutUnitData> selectAllUnitById(UnitBaseInfoInData inData) throws Exception {
		return unitBaseinfoMapper.selectAllUnitById(inData.getId());
	}

}
