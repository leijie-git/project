package com.gw.unit.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.gw.common.SnowflakeIdWorker;
import com.gw.exception.ServiceException;
import com.gw.mapper.UtUnitDangerousMapper;
import com.gw.mapper.entity.UtUnitDangerous;
import com.gw.unit.data.DangerousInData;
import com.gw.unit.data.DangerousOutData;
import com.gw.unit.service.DangerousService;
import com.gw.util.UtilMessage;

@Service
public class DangerousServiceImpl implements DangerousService{

	@Resource
	private UtUnitDangerousMapper utUnitDangerousMapper;
	@Autowired
	private SnowflakeIdWorker snowflakeIdWorker;
	@Override
	public PageInfo<DangerousOutData> getList(DangerousInData inData) throws Exception{
		PageHelper.startPage(inData.getPageNumber(), inData.getPageSize());
		List<DangerousOutData> list = utUnitDangerousMapper.getList(inData);
		PageInfo<DangerousOutData> pager = new PageInfo<DangerousOutData>(list);
		return pager;
	}

	@Override
	public void add(DangerousInData inData) throws Exception {
		UtUnitDangerous dangerous = new UtUnitDangerous();
		dangerous.setId(snowflakeIdWorker.nextId());
		dangerous.setAqglr(inData.getAQGLR());
		dangerous.setAqglrdh(inData.getAQGLRDH());
		dangerous.setCountunit(inData.getCountUnit());
		dangerous.setDangerouscount(Integer.parseInt(inData.getDangerousCount()));
		dangerous.setDangerousczcs(inData.getDangerousCZCS());
		dangerous.setDangerousimagename(inData.getDangerousImageName());
		dangerous.setDangerouslevel(inData.getDangerousLevel());
		dangerous.setDangerousstate(inData.getDangerousState());
		dangerous.setDangerousname(inData.getDangerousName());
		dangerous.setDangeroustype(inData.getDangerousType());
		dangerous.setSite(inData.getSite());
		dangerous.setOperatedesc(inData.getOperateDesc());
		dangerous.setJsfzrdh(inData.getJSFZRDH());
		dangerous.setJsfzr(inData.getJSFZR());
		dangerous.setUnitid(Long.parseLong(inData.getUnitID()));
		dangerous.setIsxfbs(Integer.parseInt(inData.getIsXFBS()));
		dangerous.setDangerousused(inData.getDangerousUsed());
		Integer flag = utUnitDangerousMapper.insert(dangerous);
		if(flag<1) {
			throw new ServiceException(UtilMessage.SAVE_MESSAGE_ERROR);
		}
		
	}

	@Override
	public void update(DangerousInData inData) throws Exception {

		UtUnitDangerous dangerous = new UtUnitDangerous();
		dangerous.setId(Long.parseLong(inData.getID()));
		dangerous.setAqglr(inData.getAQGLR());
		dangerous.setAqglrdh(inData.getAQGLRDH());
		dangerous.setCountunit(inData.getCountUnit());
		dangerous.setDangerouscount(Integer.parseInt(inData.getDangerousCount()));
		dangerous.setDangerousczcs(inData.getDangerousCZCS());
		dangerous.setDangerousimagename(inData.getDangerousImageName());
		dangerous.setDangerouslevel(inData.getDangerousLevel());
		dangerous.setDangerousstate(inData.getDangerousState());
		dangerous.setDangerousname(inData.getDangerousName());
		dangerous.setDangeroustype(inData.getDangerousType());
		dangerous.setSite(inData.getSite());
		dangerous.setOperatedesc(inData.getOperateDesc());
		dangerous.setJsfzrdh(inData.getJSFZRDH());
		dangerous.setJsfzr(inData.getJSFZR());
		dangerous.setUnitid(Long.parseLong(inData.getUnitID()));
		dangerous.setIsxfbs(Integer.parseInt(inData.getIsXFBS()));
		dangerous.setDangerousused(inData.getDangerousUsed());
		Integer flag = utUnitDangerousMapper.updateByPrimaryKey(dangerous);
		if(flag<1) {
			throw new ServiceException(UtilMessage.UPDATE_ERROR);
		}
	}

	@Override
	public void remove(String id) throws Exception {
		Integer flag = utUnitDangerousMapper.deleteByPrimaryKey(Long.parseLong(id));
		if(flag<1) {
			throw new ServiceException(UtilMessage.DEL_MESSAGE_ERROR);
		}
	}

}
