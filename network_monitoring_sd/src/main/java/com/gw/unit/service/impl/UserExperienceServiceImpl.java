package com.gw.unit.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.gw.common.SnowflakeIdWorker;
import com.gw.exception.ServiceException;
import com.gw.mapper.UtUnitUserExperienceMapper;
import com.gw.mapper.UtUserInformationMapper;
import com.gw.mapper.entity.UtUnitUserExperience;
import com.gw.mapper.entity.UtUserInformation;
import com.gw.unit.data.UserExperienceInData;
import com.gw.unit.data.UserExperienceOutData;
import com.gw.unit.data.UtUserInformationOutData;
import com.gw.unit.service.UserExperienceService;
import com.gw.util.Util;
import com.gw.util.UtilConv;

import tk.mybatis.mapper.entity.Example;

@Service
public class UserExperienceServiceImpl implements UserExperienceService {
	@Autowired
	private UtUnitUserExperienceMapper userExperienceMapper;
	@Autowired
	private SnowflakeIdWorker snowflakeIdWorker;
	@Autowired
	private UtUserInformationMapper utUserInformationMapper;

	@Override
	public PageInfo<UtUserInformationOutData> pageList(UserExperienceInData inData) throws Exception {
		PageHelper.startPage(inData.getPageNumber(), inData.getPageSize());
		List<UtUserInformationOutData> list = utUserInformationMapper.getUserInformationList(inData);
		PageInfo<UtUserInformationOutData> pageInfo = new PageInfo<UtUserInformationOutData>(list);
		return pageInfo;
	}

	@Override
	public void addUserExperience(UserExperienceInData inData) throws Exception {
		UtUnitUserExperience userExperience = new UtUnitUserExperience();
		BeanUtils.copyProperties(inData, userExperience);
		userExperience.setId(snowflakeIdWorker.nextId());
		if (Util.isNotEmpty(inData.getEntryDate())) {
			userExperience.setEntryDate(UtilConv.str2Date(inData.getEntryDate(), UtilConv.DATE_YYYY_MM_DD_CHN));
		}
		if (Util.isNotEmpty(inData.getQuitDate())) {
			userExperience.setQuitDate(UtilConv.str2Date(inData.getQuitDate(), UtilConv.DATE_YYYY_MM_DD_CHN));
		}
		userExperience.setUserId(Long.valueOf(inData.getUserId()));
		userExperienceMapper.insert(userExperience);
	}

	@Override
	public void updateUserExperience(UserExperienceInData inData) throws Exception {
		UtUnitUserExperience userExperience = userExperienceMapper.selectByPrimaryKey(Long.valueOf(inData.getId()));
		BeanUtils.copyProperties(inData, userExperience);
		if (Util.isNotEmpty(inData.getEntryDate())) {
			userExperience.setEntryDate(UtilConv.str2Date(inData.getEntryDate(), UtilConv.DATE_YYYY_MM_DD_CHN));
		} else {
			userExperience.setEntryDate(null);
		}
		if (Util.isNotEmpty(inData.getQuitDate())) {
			userExperience.setQuitDate(UtilConv.str2Date(inData.getQuitDate(), UtilConv.DATE_YYYY_MM_DD_CHN));
		} else {
			userExperience.setQuitDate(null);
		}

		userExperienceMapper.updateByPrimaryKey(userExperience);
	}

	@Override
	public void deleteUserExperience(String id) throws Exception {
		userExperienceMapper.deleteByPrimaryKey(Long.valueOf(id));
	}

	@Override
	public void updateUserInformation(UtUserInformationOutData inData) throws Exception {
		String id = inData.getId();
		UtUserInformation information = utUserInformationMapper.selectByPrimaryKey(Long.valueOf(id));
		String idCard = inData.getIdCard();
		String idCard2 = information.getIdCard();
		//日期格式化
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		if (!idCard.equals(idCard2)) {// 验证身份证是否重复
			Example example = new Example(UtUserInformation.class);
			example.createCriteria().andEqualTo("idCard", idCard);
			List<UtUserInformation> selectByExample = utUserInformationMapper.selectByExample(example);
			if (Util.isNotEmpty(selectByExample)) {
				throw new ServiceException("身份证号不能重复");
			}
		}
		information.setCardNumber(inData.getCardNumber());
		information.setCardType(inData.getCardType());
		information.setCertificate(inData.getCertificate());
		information.setIdCard(idCard);
		information.setName(inData.getName());
		information.setPhone(inData.getPhone());
		information.setSex(inData.getSex());
		information.setStartTime(sdf.parse(inData.getStartTime()));
		utUserInformationMapper.updateByPrimaryKey(information);
	}

	@Override
	@Transactional
	public void delete(String id) throws Exception {
		utUserInformationMapper.deleteByPrimaryKey(Long.valueOf(id));
		// 删除人员工作经历
		userExperienceMapper.deleteByUserId(id);
	}

	@Override
	public void add(UtUserInformationOutData inData) throws Exception {
		String idCard = inData.getIdCard();
		// 验证身份证是否重复
		Example example = new Example(UtUserInformation.class);
		example.createCriteria().andEqualTo("idCard", idCard);
		List<UtUserInformation> selectByExample = utUserInformationMapper.selectByExample(example);
		if (Util.isNotEmpty(selectByExample)) {
			throw new ServiceException("身份证号不能重复");
		}
		//日期格式化
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		UtUserInformation information = new UtUserInformation();
		information.setId(snowflakeIdWorker.nextId());
		information.setCardNumber(inData.getCardNumber());
		information.setCardType(inData.getCardType());
		information.setCertificate(inData.getCertificate());
		information.setIdCard(idCard);
		information.setName(inData.getName());
		information.setPhone(inData.getPhone());
		information.setSex(inData.getSex());
		information.setStartTime(sdf.parse(inData.getStartTime()));
		utUserInformationMapper.insert(information);
	}

	@Override
	public List<UserExperienceOutData> getExperienceList(String userId) throws Exception {
		UserExperienceInData inData = new UserExperienceInData();
		inData.setUserId(userId);
		List<UserExperienceOutData> userExperienceList = userExperienceMapper.getUserExperienceList(inData);
		return userExperienceList;
	}

}
