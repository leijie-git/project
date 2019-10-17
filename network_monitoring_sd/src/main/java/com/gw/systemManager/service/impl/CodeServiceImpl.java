
package com.gw.systemManager.service.impl;


import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.gw.common.SnowflakeIdWorker;
import com.gw.exception.ServiceException;
import com.gw.mapper.UtBaseCodeMapper;
import com.gw.mapper.UtBaseCodegroupMapper;
import com.gw.mapper.entity.UtBaseCode;
import com.gw.systemManager.data.CodeInData;
import com.gw.systemManager.data.CodeOutData;
import com.gw.systemManager.service.CodeService;
import com.gw.util.Util;
import com.gw.util.UtilMessage;

@Service
public class CodeServiceImpl implements CodeService{

	@Resource
	private UtBaseCodegroupMapper utBaseCodegroupMapper;
	
	@Autowired
	private UtBaseCodeMapper codeMapper;
	
	@Autowired
	private SnowflakeIdWorker snowflakeIdWorker;
	@Value("${raw.data.database}")
	private String database;
	
	@Override
	public List<CodeOutData> getListByGroupKey(String codeGroupKey) throws Exception{
		List<CodeOutData> list = utBaseCodegroupMapper.getListByGroupKey(codeGroupKey);
		if(list == null) {
			throw new ServiceException(UtilMessage.GET_MSG_ERROR);
		}
		return list;
	}
	
	/**
	 * 分页查询方法
	 */
	public PageInfo<CodeOutData> List(CodeInData inData)  throws Exception{
		PageHelper.startPage(inData.getPageNumber(), inData.getPageSize());
		List<CodeOutData> list = codeMapper.getAllList(inData);
		PageInfo<CodeOutData> pageInfo = new PageInfo<CodeOutData>(list);
		return pageInfo;
	}
	
	
	@Override
	public void add(String adder, CodeInData inData)  throws Exception{
		UtBaseCode code = new UtBaseCode();
		BeanUtils.copyProperties(inData, code);
		code.setCodeid(snowflakeIdWorker.nextId());
		if(Util.isNotEmpty(inData.getSortorder())){
			code.setSortorder(Integer.parseInt(inData.getSortorder()));
		}
		code.setIsdeleted(false);
		code.setAdder(adder);
		Date date = new Date();
		code.setAdddate(date);
		code.setUpdater(adder);
		code.setUpdatedate(date);
		if(Util.isNotEmpty(inData.getCodegroupid())){
			code.setCodegroupid(Long.valueOf(inData.getCodegroupid()));
		}
		codeMapper.insert(code);
		
	}
	
	
	@Override
	public void update(String updater, CodeInData inData)  throws Exception{
		UtBaseCode code = new UtBaseCode();
		BeanUtils.copyProperties(inData, code);
		if(Util.isNotEmpty(inData.getCodeid())){
			code.setCodeid(Long.valueOf(inData.getCodeid()));
		}
		if(Util.isNotEmpty(inData.getSortorder())){
			code.setSortorder(Integer.parseInt(inData.getSortorder()));
		}
		code.setIsdeleted(false);
		Date date = new Date();
		code.setUpdater(updater);
		code.setUpdatedate(date);
		if(Util.isNotEmpty(inData.getCodegroupid())){
			code.setCodegroupid(Long.valueOf(inData.getCodegroupid()));
		}
		codeMapper.updateByPrimaryKey(code);
	}
	
	
	@Override
	public void delete(String codeID)  throws Exception{
		UtBaseCode code = codeMapper.selectByPrimaryKey(Long.valueOf(codeID));
		code.setIsdeleted(true);
		codeMapper.updateByPrimaryKey(code);
	}

	@Override
	public List<CodeOutData> getCodeListByCodeGroupId(String codeGroupId) throws Exception {
		return codeMapper.getCodeListByCodeGroupId(codeGroupId);
	}

	@Override
	public void importCodeDatas() {
		List<CodeOutData> outData = codeMapper.getImportDatas(database);
		for (CodeOutData codeOutData : outData) {
			UtBaseCode utBaseCode = new UtBaseCode();
			utBaseCode.setCodeid(snowflakeIdWorker.nextId());
			utBaseCode.setIsdeleted(false);
			utBaseCode.setCodename(codeOutData.getCodename());
			utBaseCode.setCodevalue(codeOutData.getCodevalue());
			utBaseCode.setAdddate(new Date());
			utBaseCode.setCodegroupid(46l);
			codeMapper.insert(utBaseCode);
		}
	}

}
