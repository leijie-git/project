package com.gw.device.service.impl;

import java.util.List;

import com.gw.build.data.BuildAreaOutData;
import com.gw.mapper.UtUnitBuildareaMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.gw.common.SnowflakeIdWorker;
import com.gw.device.data.AddressRelImportData;
import com.gw.device.data.AddressRelInData;
import com.gw.device.data.AddressRelOutData;
import com.gw.device.data.EquipmentSelectData;
import com.gw.device.service.AddressRelService;
import com.gw.exception.ServiceException;
import com.gw.mapper.UtBaseCodegroupMapper;
import com.gw.mapper.UtEqAddressRelMapper;
import com.gw.mapper.entity.UtEqAddressRel;
import com.gw.systemManager.data.CodeOutData;
import com.gw.util.Util;

import tk.mybatis.mapper.entity.Example;

/**
 * 联网设备关联地址服务层
 *
 * @author SY
 * @data 2018年9月13日
 */
@Service
public class AddressRelServiceImpl implements AddressRelService {

	@Autowired
	private UtEqAddressRelMapper addressRelMapper;

	@Autowired
	private SnowflakeIdWorker snowflakeIdWorker;
	@Autowired
	private UtBaseCodegroupMapper utBaseCodegroupMapper;
	@Autowired
	private UtUnitBuildareaMapper utUnitBuildareaMapper;

	@Override
	public PageInfo<AddressRelOutData> list(Integer pageNumber, Integer pageSize, String eqid, String unitName) throws Exception {
		PageHelper.startPage(pageNumber, pageSize);
		List<AddressRelOutData> list = addressRelMapper.getAllAddressRel(eqid, unitName);
		for (AddressRelOutData addressRelOutData : list) {
			String buildAreaId = addressRelOutData.getBuildareaid();
			if (buildAreaId != null) {
				BuildAreaOutData unitidByBuildAreaId = utUnitBuildareaMapper.getUnitidByBuildAreaId(buildAreaId);
				addressRelOutData.setUnitid(unitidByBuildAreaId.getUnitID());
				addressRelOutData.setUnitname(unitidByBuildAreaId.getUnitName());
			}
		}
		PageInfo<AddressRelOutData> pageInfo = new PageInfo<AddressRelOutData>(list);
		return pageInfo;
	}

	@Override
	public void add(AddressRelInData inData) throws Exception {
		Example example = new Example(UtEqAddressRel.class);
		example.createCriteria().andEqualTo("eqid", inData.getEqid()).andEqualTo("partcode", inData.getPartcode());
		List<UtEqAddressRel> list = addressRelMapper.selectByExample(example);
		if (Util.isNotEmpty(list)) {
			throw new ServiceException("此设备下已经存在一个相同的部位号！");
		}

		UtEqAddressRel addressRel = new UtEqAddressRel();
		BeanUtils.copyProperties(inData, addressRel);
		addressRel.setId(snowflakeIdWorker.nextId());
		addressRel.setUnitId(Long.parseLong(inData.getUnitid()));
		if (Util.isNotEmpty(inData.getEqid())) {
			addressRel.setEqid(Long.valueOf(inData.getEqid()));
		}
		addressRel.setBuildareaid(Long.valueOf(inData.getBuildAreaid()));
		if (Util.isNotEmpty(inData.getEqSystemID())) {
			addressRel.setEqsystemid(Long.valueOf(inData.getEqSystemID()));
		}
		if (Util.isNotEmpty(inData.getPointtype())) {
			addressRel.setPointtype(Long.valueOf(inData.getPointtype()));
		}
		if (Util.isNotEmpty(inData.getVideoid())) {
			addressRel.setVideoid(Long.valueOf(inData.getVideoid()));
		}

		addressRelMapper.insert(addressRel);

	}

	@Override
	public void update(AddressRelInData inData) throws Exception {
		Example example = new Example(UtEqAddressRel.class);
		example.createCriteria().andEqualTo("eqid", inData.getEqid()).andEqualTo("partcode", inData.getPartcode());
		List<UtEqAddressRel> list = addressRelMapper.selectByExample(example);
		if (Util.isNotEmpty(list)) {
			if (!list.get(0).getId().equals(Long.valueOf(inData.getId()))) {
				throw new ServiceException("此设备下已经存在一个相同的部位号！");
			}
		}
		String id = inData.getId();
		UtEqAddressRel addressRel = addressRelMapper.selectByPrimaryKey(Long.valueOf(id));
		BeanUtils.copyProperties(inData, addressRel);
		addressRel.setBuildareaid(Long.valueOf(inData.getBuildAreaid()));
		if (Util.isNotEmpty(inData.getEqid())) {
			addressRel.setEqid(Long.valueOf(inData.getEqid()));
		} else {
			addressRel.setEqid(null);
		}
		addressRel.setUnitId(Long.parseLong(inData.getUnitid()));
		if (Util.isNotEmpty(inData.getEqSystemID())) {
			addressRel.setEqsystemid(Long.valueOf(inData.getEqSystemID()));
		} else {
			addressRel.setEqsystemid(null);
		}
		if (Util.isNotEmpty(inData.getPointtype())) {
			addressRel.setPointtype(Long.valueOf(inData.getPointtype()));
		} else {
			addressRel.setPointtype(null);
		}
		if (Util.isNotEmpty(inData.getVideoid())) {
			addressRel.setVideoid(Long.valueOf(inData.getVideoid()));
		} else {
			addressRel.setVideoid(null);
		}
		addressRelMapper.updateByPrimaryKey(addressRel);
	}

	@Override
	public void delete(String id) throws Exception {
		if (id != null && !"".equals(id)) {
			String[] ids = id.split(",");
			for (String s : ids) {
				addressRelMapper.deleteByPrimaryKey(Long.parseLong(s));
			}
		}
	}

	@Override
	public List<EquipmentSelectData> getEquipmentSelect(Long unitid) throws Exception {
		List<EquipmentSelectData> list = addressRelMapper.getEquipmentSelect(unitid);
		return list;
	}

	@Override
	@Transactional
	public void importData(List<AddressRelImportData> addressRelImportDatas, String importeqid) throws Exception {
		if (Util.isEmpty(addressRelImportDatas)) {
			return;
		}

		//获取点位类型
		List<CodeOutData> codeList = utBaseCodegroupMapper.getListByGroupKey("PointType");

		for (AddressRelImportData addressRelImportData : addressRelImportDatas) {
			if (Util.isEmpty(addressRelImportData.getPartcode()) || Util.isEmpty(addressRelImportData.getAdress())) {
				throw new ServiceException("部位号和真实地址不能为空！");
			}
			Example example = new Example(UtEqAddressRel.class);
			example.createCriteria().andEqualTo("eqid", Long.valueOf(importeqid)).andEqualTo("partcode",
					addressRelImportData.getPartcode());
			List<UtEqAddressRel> list = addressRelMapper.selectByExample(example);
			if (Util.isNotEmpty(list)) {
				UtEqAddressRel utEqAddressRel = list.get(0);
				BeanUtils.copyProperties(addressRelImportData, utEqAddressRel);
				Long flag = 0L;
				if (Util.isNotEmpty(addressRelImportData.getSystemname())) {
					if (addressRelImportData.getSystemname().equals("灭火系统")) {
						flag = 1L;
					} else if (addressRelImportData.getSystemname().equals("电气火灾")) {
						flag = 2L;
					} else if (addressRelImportData.getSystemname().equals("报警系统")) {
						flag = 3L;
					} else if (addressRelImportData.getSystemname().equals("防火分离")) {
						flag = 4L;
					} else if (addressRelImportData.getSystemname().equals("气体系统")) {
						flag = 5L;
					} else if (addressRelImportData.getSystemname().equals("燃气系统")) {
						flag = 6L;
					} else if (addressRelImportData.getSystemname().equals("应急疏散")) {
						flag = 7L;
					} else if (addressRelImportData.getSystemname().equals("无线烟感")) {
						flag = 8L;
					} else if (addressRelImportData.getSystemname().equals("防排烟系统")) {
						flag = 9L;
					}
					utEqAddressRel.setEqsystemid(flag);
				}
				if (Util.isNotEmpty(addressRelImportData.getBuildareaid())) {
					try {
						utEqAddressRel.setBuildareaid(Long.valueOf(addressRelImportData.getBuildareaid()));
					} catch (Exception e) {
						throw new ServiceException("导入失败，区域ID列只能输入数字！");
					}
				}
				if (Util.isNotEmpty(addressRelImportData.getVideoid())) {
					try {
						utEqAddressRel.setVideoid(Long.valueOf(addressRelImportData.getVideoid()));
					} catch (Exception e) {
						throw new ServiceException("导入失败，点位视频ID列只能输入数字！");
					}
				}
				addressRelMapper.updateByPrimaryKey(utEqAddressRel);
			} else {
				UtEqAddressRel addressRel = new UtEqAddressRel();
				addressRel.setId(snowflakeIdWorker.nextId());
				addressRel.setEqid(Long.valueOf(importeqid));
				addressRel.setAdress(addressRelImportData.getAdress());
				addressRel.setPartcode(addressRelImportData.getPartcode());
				addressRel.setXaxis(addressRelImportData.getXAxis());
				addressRel.setYaxis(addressRelImportData.getYAxis());
				addressRel.setName(addressRelImportData.getName());
				addressRel.setRemark(addressRelImportData.getRemark());
				Long flag = 0L;
				if (Util.isNotEmpty(addressRelImportData.getSystemname())) {
					if (addressRelImportData.getSystemname().equals("水系统")) {
						flag = 1L;
					} else if (addressRelImportData.getSystemname().equals("电气火灾")) {
						flag = 2L;
					} else if (addressRelImportData.getSystemname().equals("火灾自动报警系统")) {
						flag = 3L;
					} else if (addressRelImportData.getSystemname().equals("防火分离")) {
						flag = 4L;
					} else if (addressRelImportData.getSystemname().equals("气体系统")) {
						flag = 5L;
					} else if (addressRelImportData.getSystemname().equals("燃气系统")) {
						flag = 6L;
					} else if (addressRelImportData.getSystemname().equals("应急疏散")) {
						flag = 7L;
					} else if (addressRelImportData.getSystemname().equals("无线烟感")) {
						flag = 8L;
					} else if (addressRelImportData.getSystemname().equals("防排烟系统")) {
						flag = 9L;
					}
					addressRel.setEqsystemid(flag);
				}
				if (Util.isNotEmpty(addressRelImportData.getBuildareaid())) {
					try {
						addressRel.setBuildareaid(Long.valueOf(addressRelImportData.getBuildareaid()));
					} catch (Exception e) {
						throw new ServiceException("导入失败，区域ID列只能输入数字！");
					}
				}
				if (Util.isNotEmpty(addressRelImportData.getVideoid())) {
					try {
						addressRel.setVideoid(Long.valueOf(addressRelImportData.getVideoid()));
					} catch (Exception e) {
						throw new ServiceException("导入失败，点位视频ID列只能输入数字！");
					}
				}
				for (CodeOutData code : codeList) {
					if (code.getCodename().equals(addressRelImportData.getPointType().trim())) {
						addressRel.setPointtype(Long.valueOf(code.getCodeid()));
					}
				}
				addressRelMapper.insert(addressRel);
			}
		}
	}

}
