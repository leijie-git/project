package com.gw.inspect.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.gw.common.SnowflakeIdWorker;
import com.gw.exception.ServiceException;
import com.gw.inspect.data.InspectSiteClassInData;
import com.gw.inspect.data.InspectSiteClassOutData;
import com.gw.inspect.data.InspectSiteOutData;
import com.gw.inspect.data.SiteClassDetialOutData;
import com.gw.inspect.service.InspectSiteClassService;
import com.gw.mapper.UtBaseSiteclassMapper;
import com.gw.mapper.UtBaseSiteclassdetialMapper;
import com.gw.mapper.UtInspectSiteMapper;
import com.gw.mapper.entity.UtBaseSiteclass;
import com.gw.util.Util;
import com.gw.util.UtilMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class InspectSiteClassServiceImpl implements InspectSiteClassService {

	@Resource
	private UtBaseSiteclassMapper utBaseSiteclassMapper;
	@Resource
	private UtBaseSiteclassdetialMapper utBaseSiteclassdetialMapper;
	@Resource
	private UtInspectSiteMapper utInspectSiteMapper;
	@Autowired
	private SnowflakeIdWorker snowflakeIdWorker;

	@Override
	public PageInfo<InspectSiteClassOutData> getList(InspectSiteClassInData inData) throws Exception {
		PageHelper.startPage(inData.getPageNumber(), inData.getPageSize());
		List<InspectSiteClassOutData> list = utBaseSiteclassMapper.getList(inData);
		if (list == null) {
			throw new ServiceException(UtilMessage.GET_MSG_ERROR);
		}
		PageInfo<InspectSiteClassOutData> pager = new PageInfo<InspectSiteClassOutData>(list);
		return pager;
	}

	@Override
	public void add(InspectSiteClassInData inData) throws Exception {
		UtBaseSiteclass siteClass = new UtBaseSiteclass();
		siteClass.setId(snowflakeIdWorker.nextId());
		if (Util.isNotEmpty(inData.getUnitID())) {
			siteClass.setUnitid(Long.parseLong(inData.getUnitID()));
		}
		InspectSiteClassOutData i = utBaseSiteclassMapper.getBySiteclassname(inData.getSiteClassName(), inData.getUnitID());
		if (Util.isEmpty(i)) {
			siteClass.setSiteclassname(inData.getSiteClassName());
		} else {
			throw new ServiceException(UtilMessage.SITE_NAME_ERROR);
		}
		siteClass.setSiteclassdesc(inData.getSiteClassDesc());
		siteClass.setSiteclassname(inData.getSiteClassName());
		Integer flag = utBaseSiteclassMapper.insert(siteClass);
		if (flag < 1) {
			throw new ServiceException(UtilMessage.SAVE_MESSAGE_ERROR);
		}
	}

	@Override
	public void update(InspectSiteClassInData inData) throws Exception {
		UtBaseSiteclass siteClass = new UtBaseSiteclass();
		siteClass.setId(Long.parseLong(inData.getID()));
		if (Util.isNotEmpty(inData.getUnitID())) {
			siteClass.setUnitid(Long.parseLong(inData.getUnitID()));
		}
		List<InspectSiteClassOutData> i = utBaseSiteclassMapper.getBySiteclassnameAndId(inData.getSiteClassName(), inData.getID());
		if (i.size() != 1) {
			siteClass.setSiteclassname(inData.getSiteClassName());
		} else {
			throw new ServiceException(UtilMessage.SITE_NAME_ERROR);
		}
		siteClass.setSiteclassdesc(inData.getSiteClassDesc());
		Integer flag = utBaseSiteclassMapper.updateByPrimaryKey(siteClass);
		if (flag < 1) {
			throw new ServiceException(UtilMessage.UPDATE_ERROR);
		}
	}

	@Override
	public void remove(String id) throws Exception {
		UtBaseSiteclass utBaseSiteclass = utBaseSiteclassMapper.selectByPrimaryKey(Long.parseLong(id));
		List<SiteClassDetialOutData> i = utBaseSiteclassdetialMapper.getSiteclassdetialBySiteclassid(String.valueOf(utBaseSiteclass.getId()));
		List<InspectSiteOutData> j = utInspectSiteMapper.getInspectSiteBySiteclassid(String.valueOf(utBaseSiteclass.getId()));
		if (i.size() == 0 && j.size() == 0) {
			Integer flag = utBaseSiteclassMapper.deleteByPrimaryKey(Long.parseLong(id));
			if (flag < 1) {
				throw new ServiceException(UtilMessage.DEL_MESSAGE_ERROR);
			}
		} else {
			throw new ServiceException("当前分类已经绑定检查项或巡查点，不能删除");
		}

	}

	@Override
	public List<InspectSiteClassOutData> getArrayList(InspectSiteClassInData inData) throws Exception {
		if ("0".equals(inData.getUnitID())) {
			inData = null;
		}
		List<InspectSiteClassOutData> list = utBaseSiteclassMapper.getList(inData);
		if (list == null) {
			throw new ServiceException(UtilMessage.GET_MSG_ERROR);
		}
		return list;
	}

}
