package com.gw.front.unit.service.impl;

import com.gw.front.analysis.data.FrontAnalysisInData;
import com.gw.front.couplet.data.FrontCoupletCommonOutData;
import com.gw.front.history.data.FrontHistoryAlarmInfoOutData;
import com.gw.front.history.data.FrontHistoryInData;
import com.gw.front.history.data.FrontHistoryInterfaceAlarmData;
import com.gw.front.maintenance.data.FrontMaintenanceInData;
import com.gw.front.maintenance.data.FrontMaintenanceStatOutData;
import com.gw.front.unit.data.UnitAlarmGradeOutData;
import com.gw.front.unit.data.UnitGradeInData;
import com.gw.front.unit.data.UnitGradeOutData;
import com.gw.front.unit.service.UnitGradeService;
import com.gw.mapper.*;
import com.gw.mapper.entity.UtUnitBaseinfo;
import com.gw.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.lang.reflect.Field;
import java.util.*;

/**
 * 单位评分
 *
 * @author yja
 * @date 2019年8月6日13点47分
 */
@Service
public class UnitGradeServiceImpl implements UnitGradeService {

	@Autowired
	private UtUnitBaseinfoMapper unitBaseinfoMapper;
	@Resource
	private UtInspectTaskdetialMapper utInspectTaskdetialMapper;
	@Resource
	private UtLzRepairMapper utLzRepairMapper;
	@Resource
	private UtUnitLookupLogMapper utUnitLookupLogMapper;
	@Autowired
	private UtLzBjzjalarmMapper utLzBjzjalarmMapper;
	@Autowired
	private UtLzFireequipmentalarmMapper utLzFireequipmentalarmMapper;
	@Autowired
	private UtEqEquipmentdetialgwMapper utEqEquipmentdetialgwMapper;
	// 源数据所在数据库
	@Value("${raw.data.database}")
	private String database;


	@Override
	public UnitGradeOutData getUnitGrade(UnitGradeInData inData) throws Exception {
		UnitGradeOutData outData = new UnitGradeOutData();
		if (Util.isNotEmpty(inData.getUnitId())) {
			UtUnitBaseinfo utUnitBaseinfo = unitBaseinfoMapper.selectByPrimaryKey(Long.valueOf(inData.getUnitId()));
			Map<String, Object> map = objToMap(utUnitBaseinfo);
			//资料完整性得分
			double countMaterialGrade = map.size() - 1;
			outData.setMaterialGrade(15 * (countMaterialGrade / 57));
			//系统接入量得分
			double countEqSystem = unitBaseinfoMapper.getCountEqSystem(inData.getUnitId());
			outData.setSystemAccessGrade(30 * (countEqSystem / 9));
			//巡检系统得分
			getPatrol(inData, outData);
			//维修系统得分
			getMaintain(inData, outData);
			//值班查岗得分
			getSentries(inData, outData);
		}
		return outData;
	}

	@Override
	public UnitAlarmGradeOutData getAlarmGrade(UnitGradeInData inData) throws Exception {
		UnitAlarmGradeOutData outData = new UnitAlarmGradeOutData();
		if (Util.isNotEmpty(inData.getUnitId())) {
			FrontHistoryInData frontHistoryInData = new FrontHistoryInData();
			frontHistoryInData.setSqlType("1");
			frontHistoryInData.setUnitId(inData.getUnitId());
			frontHistoryInData.setUserId(inData.getUserId());
			frontHistoryInData.setStartDate(inData.getStartDate());
			frontHistoryInData.setEndDate(inData.getEndDate());
			//火灾报警评分
			if ("3".equals(inData.getType())) {
				getAlarm(inData, outData, frontHistoryInData);
			} else if ("1".equals(inData.getType())) {
				//水系统评分
				getInterface(inData, outData, frontHistoryInData);
			} else if ("2".equals(inData.getType())) {
				//电气火灾评分
				getInterface(inData, outData, frontHistoryInData);
			} else {
				//NB设备评分
				getAlarm(inData, outData, frontHistoryInData);
			}
		}
		return outData;
	}

	private void getInterface(UnitGradeInData inData, UnitAlarmGradeOutData outData, FrontHistoryInData frontHistoryInData) throws Exception {
		frontHistoryInData.setType(inData.getType());
		List<FrontHistoryInterfaceAlarmData> InterfaceList = utLzFireequipmentalarmMapper.getInterfaceAlarmList(frontHistoryInData);
		for (FrontHistoryInterfaceAlarmData frontHistoryInterfaceAlarmData : InterfaceList) {
			if ("1".equals(inData.getType())) {
				int count = utEqEquipmentdetialgwMapper.getDetailCountByUnitId(inData.getUnitId(), inData.getType());
				if (count == 0) {
					outData.setWaterGrade(0.0);
				} else {
					int count1 = frontHistoryInterfaceAlarmData.getCount();
					outData.setWaterGrade(30 * ((double) count - (double) count1) / (double) count);
				}
			} else {
				int count = utEqEquipmentdetialgwMapper.getDetailCountByUnitId(inData.getUnitId(), inData.getType());
				if (count == 0) {
					outData.setElectricalGrade(0.0);
				} else {
					int count1 = frontHistoryInterfaceAlarmData.getCount();
					outData.setElectricalGrade(30 * ((double) count - (double) count1) / (double) count);
				}
			}

		}
	}

	private void getAlarm(UnitGradeInData inData, UnitAlarmGradeOutData outData, FrontHistoryInData frontHistoryInData) throws Exception {
		frontHistoryInData.setType(inData.getType());
		List<FrontHistoryAlarmInfoOutData> list = utLzBjzjalarmMapper.getFireUnitList(frontHistoryInData);
		UtUnitBaseinfo utUnitBaseinfo = unitBaseinfoMapper.selectByPrimaryKey(Long.valueOf(inData.getUnitId()));
		for (FrontHistoryAlarmInfoOutData frontHistoryAlarmInfoOutData : list) {
			if ("3".equals(inData.getType())) {
				if (Util.isEmpty(utUnitBaseinfo.getTotalNode())) {
					double totalNode = 100;
					double v2 = totalNode - (double) frontHistoryAlarmInfoOutData.getCount();
					outData.setAlarmGrade(30 * (v2 / totalNode));
				} else {
					double v = (double) utUnitBaseinfo.getTotalNode() - (double) frontHistoryAlarmInfoOutData.getCount();
					outData.setAlarmGrade(30 * (v / (double) utUnitBaseinfo.getTotalNode()));
				}
			} else {
				if (Util.isEmpty(utUnitBaseinfo.getTotalNbNode())) {
					double totalNode = 100;
					double v2 = totalNode - (double) frontHistoryAlarmInfoOutData.getCount();
					outData.setNBGrade(30 * (v2 / totalNode));
				} else {
					double v1 = (double) utUnitBaseinfo.getTotalNbNode() - (double) frontHistoryAlarmInfoOutData.getCount();
					outData.setNBGrade(10 * (v1 / (double) utUnitBaseinfo.getTotalNbNode()));
				}

			}

		}
	}

	private void getSentries(UnitGradeInData inData, UnitGradeOutData outData) throws Exception {
		FrontAnalysisInData inData1 = new FrontAnalysisInData();
		inData1.setDatabase(database);
		inData1.setUnitId(inData.getUnitId());
		inData1.setUserId(inData.getUserId());
		inData1.setStartDate(inData.getStartDate());
		inData1.setEndDate(inData.getEndDate());
		FrontCoupletCommonOutData uplookCout = utUnitLookupLogMapper.getUplookCout(inData1);
		String coupletValue = uplookCout.getCoupletValue();
		String coupletKey = uplookCout.getCoupletKey();
		if (Util.isNotEmpty(uplookCout)) {
			if (Util.isEmpty(coupletValue)) {
				coupletValue = "0";
			}
			if (Util.isEmpty(coupletKey)) {
				coupletKey = "0";
			}
			if ("0".equals(coupletValue)) {
				outData.setSentriesGrade(0.0);
			} else {
				outData.setSentriesGrade(15 * (Double.parseDouble(coupletKey) / Double.parseDouble(coupletValue)));
			}
		} else {
			outData.setSentriesGrade(0.0);
		}

	}

	private void getMaintain(UnitGradeInData inData, UnitGradeOutData outData) throws Exception {
		FrontMaintenanceInData inData1 = new FrontMaintenanceInData();
		inData1.setUnitId(inData.getUnitId());
		inData1.setUserId(inData.getUserId());
		inData1.setStartDate(inData.getStartDate());
		inData1.setEndDate(inData.getEndDate());
		FrontMaintenanceStatOutData outData1 = utLzRepairMapper.getMaintenanceStatusStat(inData1);
		if (Util.isNotEmpty(outData1)) {
			String wbNodeal = outData1.getWbNodeal();
			String wbdealed = outData1.getWbdealed();
			Double s = Double.parseDouble(wbNodeal) + Double.parseDouble(wbdealed);
			outData.setMaintainGrade(20 * (Double.parseDouble(wbdealed) / s));
		} else {
			outData.setMaintainGrade(0.0);
		}
	}

	private void getPatrol(UnitGradeInData inData, UnitGradeOutData outData) throws Exception {
		FrontMaintenanceInData inData1 = new FrontMaintenanceInData();
		inData1.setUnitId(inData.getUnitId());
		inData1.setUserId(inData.getUserId());
		inData1.setStartDate(inData.getStartDate());
		inData1.setEndDate(inData.getEndDate());
		FrontMaintenanceStatOutData outData2 = utInspectTaskdetialMapper.getMaintenanceStatusStat(inData1);
		if (Util.isNotEmpty(outData2)) {
			String xcdealed = outData2.getXcdealed();
			if (Util.isEmpty(xcdealed)) {
				xcdealed = "0";
			}
			String totalCount = outData2.getTotalCount();
			if ("0".equals(totalCount)) {
				outData.setPatrolGrade(0.0);
			} else {
				outData.setPatrolGrade(20 * (Double.parseDouble(xcdealed) / Double.parseDouble(totalCount)));
			}
		} else {
			outData.setPatrolGrade(0.0);
		}
	}

	//将对象转成map
	private static Map<String, Object> objToMap(Object obj) {
		Map<String, Object> map = new HashMap<String, Object>();
		// 获取f对象对应类中的所有属性域
		Field[] fields = getAllFields(obj);
		for (int i = 0, len = fields.length; i < len; i++) {
			String varName = fields[i].getName();
			varName = varName.toLowerCase();//将key置为小写，默认为对象的属性
			try {
				// 获取原来的访问控制权限
				boolean accessFlag = fields[i].isAccessible();
				// 修改访问控制权限
				fields[i].setAccessible(true);
				// 获取在对象f中属性fields[i]对应的对象中的变量
				Object o = fields[i].get(obj);
				if (Util.isNotEmpty(o))
					map.put(varName, o.toString());
				// System.out.println("传入的对象中包含一个如下的变量：" + varName + " = " + o);
				// 恢复访问控制权限
				fields[i].setAccessible(accessFlag);
			} catch (IllegalArgumentException ex) {
				ex.printStackTrace();
			} catch (IllegalAccessException ex) {
				ex.printStackTrace();
			}
		}
		return map;
	}

	/**
	 * 返回对象的Field（包括私有的和父类的）
	 */
	public static Field[] getAllFields(Object object) {
		Class clazz = object.getClass();
		List<Field> fieldList = new ArrayList<>();
		while (clazz != null) {
			fieldList.addAll(new ArrayList<>(Arrays.asList(clazz.getDeclaredFields())));
			clazz = clazz.getSuperclass();
		}
		Field[] fields = new Field[fieldList.size()];
		fieldList.toArray(fields);
		return fields;
	}
}
