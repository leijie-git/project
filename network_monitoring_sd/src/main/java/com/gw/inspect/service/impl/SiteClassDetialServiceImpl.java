package com.gw.inspect.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.gw.common.SnowflakeIdWorker;
import com.gw.exception.ServiceException;
import com.gw.inspect.data.SiteClassDetialInData;
import com.gw.inspect.data.SiteClassDetialOutData;
import com.gw.inspect.service.SiteClassDetialService;
import com.gw.mapper.UtBaseSiteclassdetialMapper;
import com.gw.mapper.entity.UtBaseSiteclassdetial;
import com.gw.util.UtilMessage;

@Service
public class SiteClassDetialServiceImpl implements SiteClassDetialService {

	@Resource
	private UtBaseSiteclassdetialMapper utBaseSiteclassdetialMapper;
	@Autowired
	private SnowflakeIdWorker snowflakeIdWorker;

	@Override
	public PageInfo<SiteClassDetialOutData> getList(SiteClassDetialInData inData) {
		PageHelper.startPage(inData.getPageNumber(), inData.getPageSize());
		List<SiteClassDetialOutData> list = utBaseSiteclassdetialMapper.getList(inData);
		PageInfo<SiteClassDetialOutData> pager = new PageInfo<SiteClassDetialOutData>(list);
		return pager;
	}

	@Override
	public List<SiteClassDetialOutData> getListByUnitID(SiteClassDetialInData inData) {
		return utBaseSiteclassdetialMapper.getList(inData);
	}

	@Override
	public void add(SiteClassDetialInData inData) throws Exception {
		UtBaseSiteclassdetial detail = new UtBaseSiteclassdetial();
		detail.setId(snowflakeIdWorker.nextId());
		detail.setCheckinfo(inData.getCheckInfo());
		detail.setCheckmethod(inData.getCheckMethod());
		detail.setUnitId(Long.parseLong(inData.getUnitID()));
		Integer flag = utBaseSiteclassdetialMapper.insert(detail);
		if (flag < 1) {
			throw new ServiceException(UtilMessage.SAVE_MESSAGE_ERROR);
		}
	}

	@Override
	public void update(SiteClassDetialInData inData) throws Exception {
		UtBaseSiteclassdetial detail = new UtBaseSiteclassdetial();
		detail.setId(Long.parseLong(inData.getID()));
		detail.setCheckinfo(inData.getCheckInfo());
		detail.setCheckmethod(inData.getCheckMethod());
		detail.setUnitId(Long.parseLong(inData.getUnitID()));
		Integer flag = utBaseSiteclassdetialMapper.updateByPrimaryKey(detail);
		if (flag < 1) {
			throw new ServiceException(UtilMessage.UPDATE_ERROR);
		}
	}

	@Override
	public void remove(String id) throws Exception {
		String[] ids = id.split(",");
		for (String key : ids) {
			Integer flag = utBaseSiteclassdetialMapper.deleteByPrimaryKey(Long.parseLong(key));
			if (flag < 1) {
				throw new ServiceException(UtilMessage.DEL_MESSAGE_ERROR);
			}
		}

	}

}
