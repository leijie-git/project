package com.gw.device.service.impl;

import java.text.SimpleDateFormat;
import java.util.*;

import com.alibaba.fastjson.JSONObject;
import com.gw.alarm.data.EmployeeForSendMsgData;
import com.gw.alarm.data.TemplateMessage;
import com.gw.apppush.PushMsgManage;
import com.gw.common.SendMsg;
import com.gw.systemManager.data.SysPropertiesOutData;
import com.gw.systemManager.service.PropertiesManageService;
import com.gw.util.PushMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.gw.common.SnowflakeIdWorker;
import com.gw.device.data.ExtinguisherImportData;
import com.gw.device.service.ExtinguisherService;
import com.gw.exception.ServiceException;
import com.gw.front.unit.data.FrontUnitExtinguisherInData;
import com.gw.front.unit.data.FrontUnitExtinguisherOutData;
import com.gw.mapper.UtEqExtinguisherMapper;
import com.gw.mapper.UtUnitUserMapper;
import com.gw.mapper.entity.UtEqExtinguisher;
import com.gw.mapper.entity.UtUnitUser;
import com.gw.util.Util;
import com.gw.util.UtilConv;

import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;

@Service
public class ExtinguisherServiceImpl implements ExtinguisherService {
	private Logger log = LoggerFactory.getLogger(ExtinguisherServiceImpl.class);
	@Autowired
	private UtEqExtinguisherMapper extinguisherMapper;
	@Autowired
	private SnowflakeIdWorker snowflakeIdWorker;
	@Autowired
	private UtUnitUserMapper utUnitUserMapper;

	@Resource
	private PropertiesManageService propertiesManageService;

	@Override
	public PageInfo<FrontUnitExtinguisherOutData> getExtinguisherList(Long userId, FrontUnitExtinguisherInData inData) throws Exception {
		UtUnitUser utUnitUser = utUnitUserMapper.selectByPrimaryKey(userId);
		if (Util.isNotEmpty(utUnitUser)) {
			inData.setUnitId(utUnitUser.getUnitid().toString());
		}
		PageHelper.startPage(inData.getPageNumber(), inData.getPageSize());
		List<FrontUnitExtinguisherOutData> list = extinguisherMapper.getExtinguisherList(inData);
		PageInfo<FrontUnitExtinguisherOutData> pageInfo = new PageInfo<FrontUnitExtinguisherOutData>(list);
		return pageInfo;
	}

	@Override
	public void addExtinguisher(Long userId, FrontUnitExtinguisherInData inData) throws Exception {
		UtUnitUser utUnitUser = utUnitUserMapper.selectByPrimaryKey(userId);
		Long unitid = utUnitUser.getUnitid();

		//判断灭火器编号是否重复
		Example example = new Example(UtEqExtinguisher.class);
		example.createCriteria().andEqualTo("unitId", unitid).andEqualTo("extinguisherCode", inData.getExtinguisherCode());
		List<UtEqExtinguisher> selectByExample = extinguisherMapper.selectByExample(example);
		if (Util.isNotEmpty(selectByExample)) {
			throw new ServiceException("添加失败，灭火器编号已存在！");
		}

		UtEqExtinguisher extinguisher = new UtEqExtinguisher();
		extinguisher.setUnitId(unitid);
		BeanUtils.copyProperties(inData, extinguisher);
		extinguisher.setId(snowflakeIdWorker.nextId());
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		if (Util.isNotEmpty(inData.getProductDate())) {
			extinguisher.setProductDate(format.parse(inData.getProductDate()));
		}
		if (Util.isNotEmpty(inData.getJcDate())) {
			extinguisher.setJcDate(format.parse(inData.getJcDate()));
		}
		if (Util.isNotEmpty(inData.getFillDate())) {
			extinguisher.setFillDate(format.parse(inData.getFillDate()));
		}
		if (Util.isNotEmpty(inData.getValidityDate())) {
			extinguisher.setValidityDate(format.parse(inData.getValidityDate()));
		}
		if (Util.isNotEmpty(inData.getExtinguisherNum())) {
			extinguisher.setExtinguisherNum(Integer.parseInt(inData.getExtinguisherNum()));
		}
		if (Util.isNotEmpty(inData.getUnitId())) {
			extinguisher.setUnitId(Long.valueOf(inData.getUnitId()));
		}
		if (Util.isNotEmpty(inData.getExpireDate())) {
			extinguisher.setExpireDate(format.parse(inData.getExpireDate()));
		}
		extinguisherMapper.insert(extinguisher);
	}

	@Override
	public void deleteExtinguisher(Long id) throws Exception {
		extinguisherMapper.deleteByPrimaryKey(id);
	}

	@Override
	public void updateExtinguisher(Long userId, FrontUnitExtinguisherInData inData) throws Exception {
		UtEqExtinguisher extinguisher = extinguisherMapper.selectByPrimaryKey(Long.valueOf(inData.getId()));
		Long unitid = extinguisher.getUnitId();

		//判断灭火器编号是否已存在
		Example example = new Example(UtEqExtinguisher.class);
		example.createCriteria().andEqualTo("unitId", unitid).andEqualTo("extinguisherCode", inData.getExtinguisherCode());
		List<UtEqExtinguisher> list = extinguisherMapper.selectByExample(example);
		if (Util.isNotEmpty(list)) {
			if (!list.get(0).getId().equals(Long.valueOf(inData.getId()))) {
				throw new ServiceException("添加失败，灭火器编号已存在！");
			}
		}

		BeanUtils.copyProperties(inData, extinguisher);
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		if (Util.isNotEmpty(inData.getProductDate())) {
			extinguisher.setProductDate(format.parse(inData.getProductDate()));
		} else {
			extinguisher.setProductDate(null);
		}
		if (Util.isNotEmpty(inData.getJcDate())) {
			extinguisher.setJcDate(format.parse(inData.getJcDate()));
		} else {
			extinguisher.setJcDate(null);
		}
		if (Util.isNotEmpty(inData.getFillDate())) {
			extinguisher.setFillDate(format.parse(inData.getFillDate()));
		} else {
			extinguisher.setFillDate(null);
		}
		if (Util.isNotEmpty(inData.getValidityDate())) {
			extinguisher.setValidityDate(format.parse(inData.getValidityDate()));
		} else {
			extinguisher.setValidityDate(null);
		}
		if (Util.isNotEmpty(inData.getExtinguisherNum())) {
			extinguisher.setExtinguisherNum(Integer.parseInt(inData.getExtinguisherNum()));
		} else {
			extinguisher.setExtinguisherNum(null);
		}
		if (Util.isNotEmpty(inData.getExpireDate())) {
			extinguisher.setExpireDate(format.parse(inData.getExpireDate()));
		} else {
			extinguisher.setExpireDate(null);
		}
		extinguisherMapper.updateByPrimaryKey(extinguisher);
	}

	@Override
	public void importData(Long userId, List<ExtinguisherImportData> projectImportDatas) throws Exception {
		if (Util.isEmpty(projectImportDatas)) {
			return;
		}
		UtUnitUser utUnitUser = utUnitUserMapper.selectByPrimaryKey(userId);
		Long unitid = utUnitUser.getUnitid();
		for (ExtinguisherImportData extinguisherImportData : projectImportDatas) {
			//判断灭火器编号是否存在，若存在，则更新，若不存在，则新增
			Example example = new Example(UtEqExtinguisher.class);
			example.createCriteria().andEqualTo("unitId", unitid).andEqualTo("extinguisherCode", extinguisherImportData.getExtinguisherCode());
			List<UtEqExtinguisher> list = extinguisherMapper.selectByExample(example);
			if (Util.isEmpty(list)) {
				UtEqExtinguisher utEqExtinguisher = new UtEqExtinguisher();
				BeanUtils.copyProperties(extinguisherImportData, utEqExtinguisher);
				utEqExtinguisher.setId(snowflakeIdWorker.nextId());
				utEqExtinguisher.setUnitId(unitid);
				if (Util.isNotEmpty(extinguisherImportData.getProductDate())) {
					utEqExtinguisher.setProductDate(UtilConv.str2Date(extinguisherImportData.getProductDate(), UtilConv.DATE_YYYY_MM_DD_CHN));
				}
				if (Util.isNotEmpty(extinguisherImportData.getJcDate())) {
					utEqExtinguisher.setJcDate(UtilConv.str2Date(extinguisherImportData.getJcDate(), UtilConv.DATE_YYYY_MM_DD_CHN));
				}
				if (Util.isNotEmpty(extinguisherImportData.getFillDate())) {
					utEqExtinguisher.setFillDate(UtilConv.str2Date(extinguisherImportData.getFillDate(), UtilConv.DATE_YYYY_MM_DD_CHN));
				}
				if (Util.isNotEmpty(extinguisherImportData.getValidityDate())) {
					utEqExtinguisher.setValidityDate(UtilConv.str2Date(extinguisherImportData.getValidityDate(), UtilConv.DATE_YYYY_MM_DD_CHN));
				}
				if (Util.isNotEmpty(extinguisherImportData.getExpireDate())) {
					utEqExtinguisher.setExpireDate(UtilConv.str2Date(extinguisherImportData.getExpireDate(), UtilConv.DATE_YYYY_MM_DD_CHN));
				}
				extinguisherMapper.insert(utEqExtinguisher);
			} else {
				UtEqExtinguisher utEqExtinguisher = list.get(0);
				BeanUtils.copyProperties(extinguisherImportData, utEqExtinguisher);
				if (Util.isNotEmpty(extinguisherImportData.getProductDate())) {
					utEqExtinguisher.setProductDate(UtilConv.str2Date(extinguisherImportData.getProductDate(), UtilConv.DATE_YYYY_MM_DD_CHN));
				} else {
					utEqExtinguisher.setProductDate(null);
				}
				if (Util.isNotEmpty(extinguisherImportData.getJcDate())) {
					utEqExtinguisher.setJcDate(UtilConv.str2Date(extinguisherImportData.getJcDate(), UtilConv.DATE_YYYY_MM_DD_CHN));
				} else {
					utEqExtinguisher.setJcDate(null);
				}
				if (Util.isNotEmpty(extinguisherImportData.getFillDate())) {
					utEqExtinguisher.setFillDate(UtilConv.str2Date(extinguisherImportData.getFillDate(), UtilConv.DATE_YYYY_MM_DD_CHN));
				} else {
					utEqExtinguisher.setFillDate(null);
				}
				if (Util.isNotEmpty(extinguisherImportData.getValidityDate())) {
					utEqExtinguisher.setValidityDate(UtilConv.str2Date(extinguisherImportData.getValidityDate(), UtilConv.DATE_YYYY_MM_DD_CHN));
				} else {
					utEqExtinguisher.setValidityDate(null);
				}
				if (Util.isNotEmpty(extinguisherImportData.getExpireDate())) {
					utEqExtinguisher.setExpireDate(UtilConv.str2Date(extinguisherImportData.getExpireDate(), UtilConv.DATE_YYYY_MM_DD_CHN));
				} else {
					utEqExtinguisher.setExpireDate(null);
				}
				extinguisherMapper.updateByPrimaryKey(utEqExtinguisher);
			}

		}
	}

	@Override
	public List<FrontUnitExtinguisherOutData> getExpireExtinguisherList() {
		return extinguisherMapper.getExpireExtinguisherList();
	}


	@Override
	public void sendExtinguisherMsg(List<EmployeeForSendMsgData> employees, boolean isFire, String status, int i) throws Exception {
		String msg = "";
		String first = "";
		String now = UtilConv.date2Str(new Date(), UtilConv.DATE_TIME_FULL);
		msg = getMsg(employees, isFire, status, i, msg, first, now);
	}

	private String getMsg(List<EmployeeForSendMsgData> employees, boolean isFire, String status, int i, String msg, String first, String now) throws Exception {
		List<String> channelIdList = new ArrayList<>();
		for (EmployeeForSendMsgData employeeOutData : employees) {
			if (i == 1) {
				msg = "灭火器(" + employeeOutData.getExtinguisherCode()
						+ "," + employeeOutData.getExtinguisherPosition() + ")" + "即将到期，请及时处理反馈！";
				first = "您好，您管理的灭火器即将到期，请前往处理！";
			}
			if (i == 2) {
				msg = "灭火器(" + employeeOutData.getExtinguisherCode()
						+ "," + employeeOutData.getExtinguisherPosition() + ")" + "即将报废,请及时处理反馈！";
				first = "您好，您管理的灭火器即将报废，请前往处理！";
			}
			if (i == 3) {
				msg = "灭火器(" + employeeOutData.getExtinguisherCode()
						+ "," + employeeOutData.getExtinguisherPosition() + ")" + "已到期,请及时处理反馈！";
				first = "您好，您管理的灭火器已到期，请前往处理！";
			}
			if (i == 4) {
				msg = "灭火器(" + employeeOutData.getExtinguisherCode()
						+ "," + employeeOutData.getExtinguisherPosition() + ")" + "已报废,请及时处理反馈！";
				first = "您好，您管理的灭火器已报废，请前往处理！";
			}
			Set<String> set = new HashSet<>();
			String mobileno = employeeOutData.getMobileno();
			if (!Util.isEmpty(mobileno)) {
				set.add(mobileno);
			} else {
				log.info(employeeOutData.getUseraccount() + " has no phone!");
			}
			if (Util.isNotEmpty(employeeOutData.getChannelid())) {
				channelIdList.add(employeeOutData.getChannelid());
			}
			String receiveAlarmType = employeeOutData.getReceiveAlarmType();
			if (Util.isEmpty(receiveAlarmType)) {// 用户没有选择接收报警类型，默认自动推送火警
				if ("1".equals(status)) {
					sendTemplateMSGToUser(employeeOutData, msg, first, now, isFire);
					PushMsgManage.pushMsg(employeeOutData.getPhoneName(), "灭火器通知", msg, employeeOutData.getChannelid());
				}
			} else {
				String[] statuss = receiveAlarmType.split(",");
				for (String s : statuss) {
					if (s.equals(status)) {
						// 微信推送
						sendTemplateMSGToUser(employeeOutData, msg, first, now, isFire);
						// app推送
						PushMsgManage.pushMsg(employeeOutData.getPhoneName(), "通知", msg, employeeOutData.getChannelid());
						break;
					}
				}
			}
		}
		return msg;
	}

	/**
	 * 微信模板推送
	 *
	 * @param employeeOutData
	 * @param msg
	 * @param now
	 * @param isFire
	 * @throws Exception
	 */
	public void sendTemplateMSGToUser(EmployeeForSendMsgData employeeOutData, String msg, String first, String now, boolean isFire)
			throws Exception {
		SysPropertiesOutData properties = propertiesManageService.getProperties();
		String openid = employeeOutData.getOpenid();
		if (Util.isEmpty(openid)) {
			log.error(employeeOutData.getUseraccount() + " has no openid!");
			return;
		}
		TemplateMessage templateMessage = new TemplateMessage();
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("first", SendMsg.getJsonData(first));
		jsonObject.put("remark",SendMsg .getJsonData(msg));
		jsonObject.put("keyword1", SendMsg.getJsonData(employeeOutData.getExtinguisherCode()));
		jsonObject.put("keyword2", SendMsg.getJsonData(employeeOutData.getExtinguisherType()));
		jsonObject.put("keyword3", SendMsg.getJsonData(employeeOutData.getExtinguisherPosition()));
		jsonObject.put("keyword4", SendMsg.getJsonData(employeeOutData.getExpireDate()));
		jsonObject.put("keyword5", SendMsg.getJsonData(employeeOutData.getValidityDate()));
		templateMessage.setTemplateId(properties.getWxTempExtinguisherId());
		templateMessage.setTopcolor("#FF0000");
		templateMessage.setTouser(openid);
		templateMessage.setTemplateData(jsonObject);
		PushMessage.sendTemplateMSGToUser(templateMessage, SendMsg.getToken());
	}

}
