package com.gw.device.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.gw.common.SnowflakeIdWorker;
import com.gw.device.data.EqClassInData;
import com.gw.device.data.EqClassOutData;
import com.gw.device.data.EqSystemOutData;
import com.gw.device.service.EqClassService;
import com.gw.exception.ServiceException;
import com.gw.mapper.UtBaseEqPortMapper;
import com.gw.mapper.UtBaseEqclassMapper;
import com.gw.mapper.UtBaseEqsystemMapper;
import com.gw.mapper.entity.UtBaseEqPort;
import com.gw.mapper.entity.UtBaseEqclass;
import com.gw.util.Util;
import com.gw.util.UtilMessage;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class EqClassServiceImpl implements EqClassService {

	@Resource
	private UtBaseEqclassMapper utBaseEqclassMapper;

	@Resource
	private UtBaseEqsystemMapper utBaseEqsystemMapper;

	@Resource
	private UtBaseEqPortMapper utBaseEqPortMapper;

	@Autowired
	private SnowflakeIdWorker snowflakeIdWorker;
	@Override
	public List<EqClassOutData> getArrayList(String systemID) throws Exception {
		List<EqClassOutData> list = utBaseEqclassMapper.getArrayList(systemID);
		if(list==null) {
			throw new ServiceException(UtilMessage.GET_MSG_ERROR);
		}
		return list;
	}
	@Override
	public List<EqSystemOutData> getSystemList() throws Exception {
		List<EqSystemOutData> list = utBaseEqsystemMapper.getEqSystemSelect();
		if(list==null) {
			throw new ServiceException(UtilMessage.GET_MSG_ERROR);
		}
		return list;
	}
	@Override
	public PageInfo<EqClassOutData> pageList(String classname, Integer pageNumber, Integer pageSize) throws Exception{
		PageHelper.startPage(pageNumber, pageSize);
		List<EqClassOutData> pageList = utBaseEqclassMapper.getPageList(classname);
		PageInfo<EqClassOutData> pageInfo = new PageInfo<EqClassOutData>(pageList);
		return pageInfo;
	}
	@Override
	public void addEqClass(EqClassInData inData) throws Exception{
		UtBaseEqclass eqclass = new UtBaseEqclass();
		BeanUtils.copyProperties(inData, eqclass);
		eqclass.setId(snowflakeIdWorker.nextId());
		if(Util.isNotEmpty(inData.getEqsystemid())){
			eqclass.setEqsystemid(Long.valueOf(inData.getEqsystemid()));
		}

		utBaseEqclassMapper.insert(eqclass);

	}
	@Override
	public void updateEqClass(EqClassInData inData) throws Exception{
		String id = inData.getId();
		UtBaseEqclass eqclass = utBaseEqclassMapper.selectByPrimaryKey(Long.valueOf(id));
		BeanUtils.copyProperties(inData, eqclass);
		if(Util.isNotEmpty(inData.getEqsystemid())){
			eqclass.setEqsystemid(Long.valueOf(inData.getEqsystemid()));
		}else{
			eqclass.setEqsystemid(null);
		}

		utBaseEqclassMapper.updateByPrimaryKey(eqclass);
	}
	@Override
	public void deleteEqClass(String id) throws Exception{
		utBaseEqclassMapper.deleteByPrimaryKey(Long.valueOf(id));
	}

	@Override
	public List<UtBaseEqPort> getportNameArrayList(Integer classCode) throws Exception {
		List<UtBaseEqPort> list = utBaseEqPortMapper.getArrayList(classCode);
		if(list==null) {
			throw new ServiceException(UtilMessage.GET_MSG_ERROR);
		}
		return list;
	}
}
