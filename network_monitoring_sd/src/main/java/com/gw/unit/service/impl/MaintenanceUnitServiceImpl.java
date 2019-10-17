package com.gw.unit.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.gw.common.SnowflakeIdWorker;
import com.gw.exception.ServiceException;
import com.gw.mapper.UtMaintenanceUnitMapper;
import com.gw.mapper.entity.UtMaintenanceUnit;
import com.gw.unit.data.MaintenanceUnitInData;
import com.gw.unit.data.MaintenanceUnitOutData;
import com.gw.unit.service.MaintenanceUnitService;
import com.gw.util.Util;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;

/**
 * 维保单位服务接口实现
 *
 * @author SY
 */
@Service
public class MaintenanceUnitServiceImpl implements MaintenanceUnitService {
	@Autowired
	private UtMaintenanceUnitMapper maintenanceUnitMapper;
	@Autowired
	private SnowflakeIdWorker snowflakeIdWorker;

	@Override
	public MaintenanceUnitOutData getMaintenanceUnitList() throws Exception {
		/*List<UtMaintenanceUnit> list = maintenanceUnitMapper.selectAll();
		MaintenanceUnitOutData outData = new MaintenanceUnitOutData();
		if(!Util.isEmpty(list)){
			outData.setAddress(list.get(0).getAddress());
			outData.setContacts(list.get(0).getContacts());
			outData.setId(list.get(0).getId().toString());
			outData.setPointX(list.get(0).getPointx());
			outData.setPointY(list.get(0).getPointy());
			outData.setRemark(list.get(0).getRemark());
			outData.setTelephone(list.get(0).getTelephone());
			outData.setUnitCode(list.get(0).getUnitcode());
			outData.setUnitName(list.get(0).getUnitname());
		}
		return outData;*/
		MaintenanceUnitOutData outData = maintenanceUnitMapper.edit();
		return outData;
	}

	@Override
	public void addMaintenanceUnit(MaintenanceUnitInData inData) throws Exception {
		Example example = new Example(UtMaintenanceUnit.class);
		example.createCriteria().andEqualTo("unitcode", inData.getUnitcode());
		List<UtMaintenanceUnit> list = maintenanceUnitMapper.selectByExample(example);
		if (Util.isNotEmpty(list)) {
			throw new ServiceException("单位编号已存在！");
		}
		UtMaintenanceUnit utMaintenanceUnit = new UtMaintenanceUnit();
		if (inData.getMainType() == 1) {
			utMaintenanceUnit.setMainType(1);
		}
		if (inData.getMainType() == 2) {
			utMaintenanceUnit.setMainType(2);
		}
		BeanUtils.copyProperties(inData, utMaintenanceUnit);
		utMaintenanceUnit.setId(snowflakeIdWorker.nextId());
		utMaintenanceUnit.setCreatedate(new Date());
		//系统编号自动生成N00001格式
		int count = maintenanceUnitMapper.selectSystemNumber();
		String sysNum = "N";
		int b = count+1;
		String countStr = String.format("%05d", b);
		utMaintenanceUnit.setSystemNumber(sysNum + countStr);
		if (Util.isNotEmpty(inData.getProviceid())) {
			utMaintenanceUnit.setProviceid(Long.valueOf(inData.getProviceid()));
		}
		if (Util.isNotEmpty(inData.getCityid())) {
			utMaintenanceUnit.setCityid(Long.valueOf(inData.getCityid()));
		}
		if (Util.isNotEmpty(inData.getRegionid())) {
			utMaintenanceUnit.setRegionid(Long.valueOf(inData.getRegionid()));
		}
		maintenanceUnitMapper.insert(utMaintenanceUnit);
	}

	@Override
	public void updateMaintenanceUnit(MaintenanceUnitInData maintenanceUnitInData) throws Exception {
		Example example = new Example(UtMaintenanceUnit.class);
		example.createCriteria().andEqualTo("unitcode", maintenanceUnitInData.getUnitcode());
		List<UtMaintenanceUnit> list = maintenanceUnitMapper.selectByExample(example);
		if (Util.isNotEmpty(list) && !list.get(0).getId().equals(Long.valueOf(maintenanceUnitInData.getId()))) {
			throw new ServiceException("单位编号已存在！");
		}
		UtMaintenanceUnit utMaintenanceUnit = maintenanceUnitMapper.selectByPrimaryKey(Long.valueOf(maintenanceUnitInData.getId()));
		BeanUtils.copyProperties(maintenanceUnitInData, utMaintenanceUnit);
		if (maintenanceUnitInData.getMainType() == 1) {
			utMaintenanceUnit.setMainType(1);
		}
		if (maintenanceUnitInData.getMainType() == 2) {
			utMaintenanceUnit.setMainType(2);
		}
		if (Util.isNotEmpty(maintenanceUnitInData.getProviceid())) {
			utMaintenanceUnit.setProviceid(Long.valueOf(maintenanceUnitInData.getProviceid()));
		} else {
			utMaintenanceUnit.setProviceid(null);
		}
		if (Util.isNotEmpty(maintenanceUnitInData.getCityid())) {
			utMaintenanceUnit.setCityid(Long.valueOf(maintenanceUnitInData.getCityid()));
		} else {
			utMaintenanceUnit.setCityid(null);
		}
		if (Util.isNotEmpty(maintenanceUnitInData.getRegionid())) {
			utMaintenanceUnit.setRegionid(Long.valueOf(maintenanceUnitInData.getRegionid()));
		} else {
			utMaintenanceUnit.setRegionid(null);
		}
		if (Util.isNotEmpty(maintenanceUnitInData.getTownid())) {
			utMaintenanceUnit.setTownid(Long.valueOf(maintenanceUnitInData.getTownid()));
		} else {
			utMaintenanceUnit.setRegionid(null);
		}
		maintenanceUnitMapper.updateByPrimaryKey(utMaintenanceUnit);
	}

	@Override
	public void deleteMaintenanceUnit(String id) throws Exception {
		if (Util.isEmpty(id)) {
			return;
		}
		maintenanceUnitMapper.deleteByPrimaryKey(Long.valueOf(id));
	}

	@Override
	public PageInfo<MaintenanceUnitOutData> getMaintenanceUnitList(MaintenanceUnitInData inData) {
		PageHelper.startPage(inData.getPageNumber(), inData.getPageSize());
		List<MaintenanceUnitOutData> list = maintenanceUnitMapper.getMaintenanceUnitList(inData);
		PageInfo<MaintenanceUnitOutData> pageInfo = new PageInfo<MaintenanceUnitOutData>(list);
		return pageInfo;
	}

	@Override
	public List<MaintenanceUnitOutData> getMaintenanceUnitSelectList() {
		return maintenanceUnitMapper.getMaintenanceUnitSelectList();
	}


}
