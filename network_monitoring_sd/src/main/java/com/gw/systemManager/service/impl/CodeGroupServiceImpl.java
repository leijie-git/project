package com.gw.systemManager.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.gw.common.SnowflakeIdWorker;
import com.gw.mapper.UtBaseCodegroupMapper;
import com.gw.mapper.entity.UtBaseCodegroup;
import com.gw.systemManager.data.CodeGroupInData;
import com.gw.systemManager.data.CodeGroupOutData;
import com.gw.systemManager.service.CodeGroupService;
import com.gw.util.Util;

@Service
public class CodeGroupServiceImpl implements CodeGroupService {
	@Autowired
	private UtBaseCodegroupMapper codegroupMapper;
	
	@Autowired
	private SnowflakeIdWorker snowflakeIdWorker;

	@Override
	public PageInfo<CodeGroupOutData> list(Integer pageNumber, Integer pageSize, String codeGroupName, String codegroupkey) throws Exception{
		PageHelper.startPage(pageNumber, pageSize);
		List<CodeGroupOutData> list = codegroupMapper.selectAllCodeGroup(codeGroupName,codegroupkey);
		PageInfo<CodeGroupOutData> pageInfo = new PageInfo<CodeGroupOutData>(list);
		return pageInfo;
	}

	@Override
	public void add(String userId, CodeGroupInData inData) throws Exception{
		UtBaseCodegroup codegroup = new UtBaseCodegroup();
		BeanUtils.copyProperties(inData, codegroup);
		codegroup.setCodegroupid(snowflakeIdWorker.nextId());
		if(Util.isNotEmpty(inData.getSortorder())){
			codegroup.setSortorder(Integer.parseInt(inData.getSortorder()));
		}
		codegroup.setIsdeleted(false);
		Date date = new Date();
		codegroup.setAdddate(date);
		codegroup.setAdder(userId);
		codegroup.setUpdatedate(date);
		codegroup.setUpdater(userId);
		codegroupMapper.insert(codegroup);
	}

	@Override
	public void update(String userId, CodeGroupInData inData) throws Exception{
		UtBaseCodegroup codegroup = new UtBaseCodegroup();
		BeanUtils.copyProperties(inData, codegroup);
		codegroup.setCodegroupid(Long.valueOf(inData.getCodegroupid()));
		if(Util.isNotEmpty(inData.getSortorder())){
			codegroup.setSortorder(Integer.parseInt(inData.getSortorder()));
		}
		codegroup.setIsdeleted(false);
		Date date = new Date();
		codegroup.setUpdatedate(date);
		codegroup.setUpdater(userId);
		codegroupMapper.updateByPrimaryKey(codegroup);

	}

	@Override
	public void delete(Long codeGroupID) throws Exception{
		UtBaseCodegroup codegroup = new UtBaseCodegroup();
		codegroup.setIsdeleted(true);
		codegroup.setCodegroupid(codeGroupID);
		codegroupMapper.updateByPrimaryKeySelective(codegroup);
	}

	@Override
	public List<CodeGroupOutData> getCodeGroupSelectList() throws Exception{
		List<CodeGroupOutData> list = codegroupMapper.getCodeGroupSelectList();
		return list;
	}

}
