package com.gw.equipment.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.gw.common.SnowflakeIdWorker;
import com.gw.equipment.data.EquipmentDetailOutData;
import com.gw.equipment.data.EquipmentFacInData;
import com.gw.equipment.service.EquipmentDetailService;
import com.gw.exception.ServiceException;
import com.gw.mapper.UtEqEquipmentdetialgwMapper;
import com.gw.mapper.entity.UtEqAddressRel;
import com.gw.mapper.entity.UtEqEquipmentdetialgw;
import com.gw.util.Util;
import com.gw.util.UtilConv;
import com.gw.util.UtilMessage;

import tk.mybatis.mapper.entity.Example;

@Service
public class EquipmentDetailServiceImpl implements EquipmentDetailService {

	@Resource
	private UtEqEquipmentdetialgwMapper utEqEquipmentdetialgwMapper;
	@Autowired
	private SnowflakeIdWorker snowflakeIdWorker;

	@Value("${raw.data.database}")
	private String database;
	public static String regex = "-[0-9]+(.[0-9]+)?|[0-9]+(.[0-9]+)?";

	@Override
	public List<EquipmentDetailOutData> getPortList(String id) throws Exception {
		List<EquipmentDetailOutData> list = utEqEquipmentdetialgwMapper.getPortList(id);
		for (EquipmentDetailOutData equipmentDetailOutData : list) {
			equipmentDetailOutData.setAnalogb(scientificToString(equipmentDetailOutData.getAnalogb()));
			equipmentDetailOutData.setAnalogk(scientificToString(equipmentDetailOutData.getAnalogk()));
			equipmentDetailOutData.setAnalogdown(scientificToString(equipmentDetailOutData.getAnalogdown()));
			equipmentDetailOutData.setAnalogup(scientificToString(equipmentDetailOutData.getAnalogup()));
			equipmentDetailOutData.setMaxValue(scientificToString(equipmentDetailOutData.getMaxValue()));
			equipmentDetailOutData.setMinValue(scientificToString(equipmentDetailOutData.getMinValue()));
		}
		if (list == null) {
			throw new ServiceException(UtilMessage.GET_MSG_ERROR);
		}
		return list;
	}

	private String scientificToString(String data) {
		return UtilConv.scientificToString(data);
	}

	@Override
	@Transactional
	public void importData(List<EquipmentFacInData> equipmentFacInData, String importeqid) throws Exception {
		List<EquipmentFacInData> notValue = new ArrayList<EquipmentFacInData>();// 未插入的记录
		if (Util.isEmpty(equipmentFacInData)) {
			return;
		}
		if (Util.isNotEmpty(importeqid)) {
			Example example = new Example(UtEqAddressRel.class);
			example.createCriteria().andEqualTo("eqid", importeqid);
			utEqEquipmentdetialgwMapper.deleteByExample(example);
		}
		Pattern pattern = Pattern.compile(regex);// 把规则编译成模式对象
		for (EquipmentFacInData inData : equipmentFacInData) {
			UtEqEquipmentdetialgw gw = new UtEqEquipmentdetialgw();
			if (Util.isEmpty(inData.getDetialname()) || Util.isEmpty(inData.getIoport())
					|| Util.isEmpty(inData.getAnalogk()) || Util.isEmpty(inData.getAnalogb())
					|| Util.isEmpty(inData.getAnalogup()) || Util.isEmpty(inData.getAnalogdown())
					|| Util.isEmpty(inData.getMaxValue()) || Util.isEmpty(inData.getMinValue())
					|| Util.isEmpty(inData.getAnalogunit())) {
				throw new ServiceException("请将一个端口信息填写完整！");
			}
			if (StringUtils.isNumeric(inData.getIoport()) && pattern.matcher(inData.getAnalogk()).matches()
					&& pattern.matcher(inData.getAnalogb()).matches() && pattern.matcher(inData.getAnalogup()).matches()
					&& pattern.matcher(inData.getAnalogdown()).matches()
					&& pattern.matcher(inData.getMaxValue()).matches()
					&& pattern.matcher(inData.getMinValue()).matches()) {
				gw.setIoport(Integer.parseInt(inData.getIoport()));
				Long.parseLong(inData.getAnalogk());
				BigDecimal analogk = new BigDecimal(inData.getAnalogk());
				Long.parseLong(inData.getAnalogb());
				BigDecimal analogb = new BigDecimal(inData.getAnalogb());
				Long.parseLong(inData.getAnalogup());
				BigDecimal analogup = new BigDecimal(inData.getAnalogup());
				Long.parseLong(inData.getAnalogdown());
				BigDecimal analogdown = new BigDecimal(inData.getAnalogdown());
				Long.parseLong(inData.getAnalogdown());
				BigDecimal maxValue = new BigDecimal(inData.getMaxValue());
				Long.parseLong(inData.getAnalogdown());
				BigDecimal minValue = new BigDecimal(inData.getMinValue());
				if (minValue.compareTo(maxValue) == -1 && analogdown.compareTo(minValue) == -1
						&& analogup.compareTo(analogdown) == -1 && maxValue.compareTo(analogup) == -1) {
					gw.setId(snowflakeIdWorker.nextId());
					gw.setDetialname(inData.getDetialname());
					gw.setReserve(inData.getReserve());
					gw.setAnalogwarningdown(minValue);
					gw.setAnalogwarningup(maxValue);
					gw.setAnalogdown(analogdown);
					gw.setAnalogup(analogup);
					gw.setAnalogb(analogb);
					gw.setAnalogk(analogk);
					Integer flag = utEqEquipmentdetialgwMapper.insert(gw);
					if (flag < 1) {
						throw new ServiceException(UtilMessage.ADD_ERROR);
					}
				}
			} else {
				notValue.add(inData);
				continue;
			}

		}
	}

	@Override
	public PageInfo<EquipmentDetailOutData> getPortListPage(EquipmentFacInData inData) throws Exception {
		PageHelper.startPage(inData.getPageNumber(), inData.getPageSize());
		List<EquipmentDetailOutData> list = utEqEquipmentdetialgwMapper.getPortList(inData.getId());
		for (EquipmentDetailOutData equipmentDetailOutData : list) {
			equipmentDetailOutData.setAnalogb(scientificToString(equipmentDetailOutData.getAnalogb()));
			equipmentDetailOutData.setAnalogk(scientificToString(equipmentDetailOutData.getAnalogk()));
			equipmentDetailOutData.setAnalogdown(scientificToString(equipmentDetailOutData.getAnalogdown()));
			equipmentDetailOutData.setAnalogup(scientificToString(equipmentDetailOutData.getAnalogup()));
			equipmentDetailOutData.setMaxValue(scientificToString(equipmentDetailOutData.getMaxValue()));
			equipmentDetailOutData.setMinValue(scientificToString(equipmentDetailOutData.getMinValue()));
		}
		if (list == null) {
			throw new ServiceException(UtilMessage.GET_MSG_ERROR);
		}
		PageInfo<EquipmentDetailOutData> page = new PageInfo<EquipmentDetailOutData>(list);
		return page;
	}

	@Override
	public void deletePort(String id) throws Exception {
		Integer flag = utEqEquipmentdetialgwMapper.deleteByPrimaryKey(Long.parseLong(id));
		if (flag < 1) {
			throw new ServiceException(UtilMessage.DEL_MESSAGE_ERROR);
		}
	}

	@Override
	public void editPort(EquipmentFacInData inData) throws Exception {
		List<EquipmentDetailOutData> detail = utEqEquipmentdetialgwMapper.getListByNetDeviceid(inData.getNetdeviceid());
		UtEqEquipmentdetialgw gw = utEqEquipmentdetialgwMapper.selectByPrimaryKey(Long.parseLong(inData.getDetailid()));
		Set<Integer> MNValue = new HashSet<Integer>();// 模拟量端口集合
		Set<Integer> SZValue = new HashSet<Integer>();// 数字量端口集合
		if (Util.isNotEmpty(detail)) {
			for (EquipmentDetailOutData d : detail) {
				if (d.getIotype() == 1) {
					MNValue.add(d.getIoport());
				} else {
					SZValue.add(d.getIoport());
				}
			}
		}
		if (MNValue.contains(Integer.parseInt(inData.getIoport())) && inData.getIotype().toString().equals("1")
				&& Integer.parseInt(inData.getIoport()) != gw.getIoport()) {
			throw new ServiceException("模拟量端口重复(" + inData.getIoport() + ")");
		}
		if (SZValue.contains(Integer.parseInt(inData.getIoport())) && inData.getIotype().toString().equals("2")
				&& Integer.parseInt(inData.getIoport()) != gw.getIoport()) {
			throw new ServiceException("数字量端口重复(" + inData.getIoport() + ")");
		}
		if (Util.isNotEmpty(inData.getAnalogb())) {
			BigDecimal analogb = new BigDecimal(inData.getAnalogb());
			gw.setAnalogb(analogb);
		}
		if (Util.isNotEmpty(inData.getAnalogk())) {
			BigDecimal analogk = new BigDecimal(inData.getAnalogk());
			gw.setAnalogk(analogk);
		}
		if (Util.isNotEmpty(inData.getAnalogup())) {
			BigDecimal analogup = new BigDecimal(inData.getAnalogup());
			gw.setAnalogup(analogup);
		}

		if (Util.isNotEmpty(inData.getAnalogdown())) {
			BigDecimal analogdown = new BigDecimal(inData.getAnalogdown());
			gw.setAnalogdown(analogdown);
		}

		if (Util.isNotEmpty(inData.getMaxValue())) {
			BigDecimal maxValue = new BigDecimal(inData.getMaxValue());
			gw.setAnalogwarningup(maxValue);
		}
		if (Util.isNotEmpty(inData.getMinValue())) {
			BigDecimal minDataDataValue = new BigDecimal(inData.getMinValue());
			gw.setAnalogwarningdown(minDataDataValue);
		}
		if (Util.isNotEmpty(inData.getIoport())) {
			gw.setIoport(Integer.parseInt(inData.getIoport()));
		}
		gw.setIotype(inData.getIotype());
		gw.setDetialname(inData.getDetialname());
		gw.setAnalogunit(inData.getAnalogunit());
		gw.setGoodname(inData.getGoodname());
		gw.setBadname(inData.getBadname());
		if (Util.isNotEmpty(inData.getDigitalnormal())) {
			gw.setDigitalnormal(Integer.parseInt(inData.getDigitalnormal()));
		}
		gw.setReserve(inData.getReserve());
		gw.setRemark(inData.getRemark());
		Integer flag = utEqEquipmentdetialgwMapper.updateByPrimaryKey(gw);
		if (flag < 1) {
			throw new ServiceException(UtilMessage.UPDATE_ERROR);
		}
	}

	@Override
	public void addPort(EquipmentFacInData inData) throws Exception {
		List<EquipmentDetailOutData> detail = utEqEquipmentdetialgwMapper.getListByNetDeviceid(inData.getNetdeviceid());

		Set<Integer> MNValue = new HashSet<Integer>();// 模拟量端口集合
		Set<Integer> SZValue = new HashSet<Integer>();// 数字量端口集合
		if (Util.isNotEmpty(detail)) {
			for (EquipmentDetailOutData d : detail) {
				if (d.getIotype() == 1) {
					MNValue.add(d.getIoport());
				} else {
					SZValue.add(d.getIoport());
				}
			}
		}
		if (MNValue.contains(Integer.parseInt(inData.getIoport())) && inData.getIotype().toString().equals("1")) {
			throw new ServiceException("模拟量端口重复(" + inData.getIoport() + ")");
		}
		if (SZValue.contains(Integer.parseInt(inData.getIoport())) && inData.getIotype().toString().equals("2")) {
			throw new ServiceException("数字量端口重复(" + inData.getIoport() + ")");
		}
		UtEqEquipmentdetialgw gw = new UtEqEquipmentdetialgw();
		gw.setId(snowflakeIdWorker.nextId());
		BigDecimal analogb = new BigDecimal(inData.getAnalogb());
		gw.setAnalogb(analogb);
		BigDecimal analogk = new BigDecimal(inData.getAnalogk());
		gw.setAnalogk(analogk);
		BigDecimal analogup = new BigDecimal(inData.getAnalogup());
		gw.setAnalogup(analogup);
		BigDecimal analogdown = new BigDecimal(inData.getAnalogdown());
		gw.setAnalogdown(analogdown);
		BigDecimal maxValue = new BigDecimal(inData.getMaxValue());
		gw.setAnalogwarningup(maxValue);
		BigDecimal minDataValue = new BigDecimal(inData.getMinValue());
		gw.setAnalogwarningdown(minDataValue);
		gw.setIoport(Integer.parseInt(inData.getIoport()));
		gw.setIotype(inData.getIotype());
		gw.setDetialname(inData.getDetialname());
		gw.setAnalogunit(inData.getAnalogunit());
		gw.setGoodname(inData.getGoodname());
		gw.setBadname(inData.getBadname());
		gw.setDigitalnormal(Integer.parseInt(inData.getDigitalnormal()));
		gw.setNetdeviceid(Long.parseLong(inData.getNetdeviceid()));
		gw.setReserve(inData.getReserve());
		gw.setRemark(inData.getRemark());
		gw.setEqid(Long.parseLong(inData.getEqid()));
		Integer flag = utEqEquipmentdetialgwMapper.insert(gw);
		if (flag < 1) {
			throw new ServiceException(UtilMessage.SAVE_MESSAGE_ERROR);
		}
	}


}
