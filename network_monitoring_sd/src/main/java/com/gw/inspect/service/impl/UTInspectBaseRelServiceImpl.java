package com.gw.inspect.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.gw.common.SnowflakeIdWorker;
import com.gw.exception.ServiceException;
import com.gw.inspect.data.InspectBaseRelInData;
import com.gw.inspect.data.InspectTaskInListData;
import com.gw.inspect.data.UTInspectBaseRelOutData;
import com.gw.inspect.service.UTInspectBaseRelService;
import com.gw.mapper.UTInspectBaseRelMapper;
import com.gw.mapper.entity.UTInspectBaseRel;
import com.gw.util.Util;
import com.gw.util.UtilMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.*;

@Service
public class UTInspectBaseRelServiceImpl implements UTInspectBaseRelService {
	@Autowired
	private UTInspectBaseRelMapper utInspectBaseRelMapper;
	@Autowired
	private SnowflakeIdWorker snowflakeIdWorker;

	@Override
	public PageInfo<UTInspectBaseRelOutData> getList(InspectBaseRelInData inData) throws Exception {
		PageHelper.startPage(inData.getPageNumber(), inData.getPageSize());
		List<UTInspectBaseRelOutData> list = utInspectBaseRelMapper.getList(inData);
		PageInfo<UTInspectBaseRelOutData> pager = new PageInfo(list);
		return pager;
	}

	@Override
	@Transactional
	public void addAll(List<InspectTaskInListData> inData) throws Exception {
		for (InspectTaskInListData inDatum : inData) {
			List<UTInspectBaseRel> list = getUtInspectBaseRels(inDatum);
			if (Util.isNotEmpty(list)) {
				throw new ServiceException(UtilMessage.SAVE_MESSAGE_ERROR);
			}
			UTInspectBaseRel utInspectBaseRel = new UTInspectBaseRel();
			utInspectBaseRel.setId(snowflakeIdWorker.nextId());
			if (Util.isNotEmpty(inDatum.getSiteid())) {
				utInspectBaseRel.setSiteID(Long.parseLong(inDatum.getSiteid()));
			}
			if (Util.isNotEmpty(inDatum.getSiteClassid())) {
				utInspectBaseRel.setSiteClassID(Long.parseLong(inDatum.getSiteClassid()));
			}
			utInspectBaseRel.setSiteClassDetialID(Long.parseLong(inDatum.getSiteClassDetailid()));
			utInspectBaseRel.setLastupdate(new Date());
			utInspectBaseRel.setUnitId(Long.parseLong(inDatum.getUnitId()));
			utInspectBaseRelMapper.insertSelective(utInspectBaseRel);
		}
	}

	@Override
	@Transactional
	public void updateAll(List<InspectTaskInListData> inData, String unitId) throws Exception {
		List<InspectTaskInListData> inListExistData = new ArrayList<>();
		List<InspectTaskInListData> inListInExistData = new ArrayList<>();
		List<InspectTaskInListData> inDataList = new ArrayList<>();
		List<InspectTaskInListData> Lists = new ArrayList<>();
		//判断入参是否为空，为空就删除当前单位下配置
		if (Util.isEmpty(inData)) {
			Example example = new Example(UTInspectBaseRel.class);
			example.createCriteria().andEqualTo("unitId", Long.parseLong(unitId));
			utInspectBaseRelMapper.deleteByExample(example);
		}
		for (InspectTaskInListData inDatum : inData) {
			InspectTaskInListData inListData = new InspectTaskInListData();
			inListData.setUnitId(inDatum.getUnitId());
			//查找当前单位下已配置数据
			List<InspectTaskInListData> list1 = utInspectBaseRelMapper.getSiteAndSiteCalssList(inListData);
			Lists.addAll(list1);
			//获取新增数据
			if (!Lists.contains(inDatum)) {
				inListInExistData.add(inDatum);
			}
			//获取数据库已有数据
			if (Lists.contains(inDatum)) {
				inListExistData.add(inDatum);
			}
		}
		//添加新增数据
		addAll(inListInExistData);
		//获取数据库已有未勾选数据
		for (InspectTaskInListData inspectTaskInListData : Lists) {
			if (!inListExistData.contains(inspectTaskInListData)) {
				if (!inDataList.contains(inspectTaskInListData)) {
					inDataList.add(inspectTaskInListData);
				}
			}
		}
		//遍历删除未勾选数据
		for (InspectTaskInListData inspectTaskInListData : inDataList) {
			Example example = new Example(UTInspectBaseRel.class);
			example.createCriteria().andEqualTo("SiteClassDetialID", inspectTaskInListData.getSiteClassDetailid())
					.andEqualTo("SiteClassID", inspectTaskInListData.getSiteClassid())
					.andEqualTo("SiteID", inspectTaskInListData.getSiteid());
			utInspectBaseRelMapper.deleteByExample(example);
		}
	}


	private List<UTInspectBaseRel> getUtInspectBaseRels(InspectTaskInListData inDatum) {
		Example example = new Example(UTInspectBaseRel.class);
		example.createCriteria().andEqualTo("SiteClassDetialID", inDatum.getSiteClassDetailid())
				.andEqualTo("SiteClassID", inDatum.getSiteClassid())
				.andEqualTo("SiteID", inDatum.getSiteid());
		return utInspectBaseRelMapper.selectByExample(example);
	}


	@Override
	public void remove(String id) throws Exception {
		String ids[] = id.split(",");
		for (String s : ids) {
			Integer flag = utInspectBaseRelMapper.deleteByPrimaryKey(Long.parseLong(s));
			if (flag < 1) {
				throw new ServiceException(UtilMessage.DEL_MESSAGE_ERROR);
			}
		}
	}

	@Override
	public List<InspectTaskInListData> getRelList(InspectBaseRelInData inData) throws Exception {
		List<InspectTaskInListData> outData = new ArrayList<>();
		Map<String, InspectTaskInListData> map = new HashMap<>();
		List<UTInspectBaseRelOutData> list = utInspectBaseRelMapper.getList(inData);
		for (UTInspectBaseRelOutData utInspectBaseRelOutData : list) {
			StringBuffer sb = new StringBuffer();
			sb.append(utInspectBaseRelOutData.getSiteId());
			sb.append(utInspectBaseRelOutData.getSiteClassId());
			if (!map.containsKey(sb.toString())) {
				InspectTaskInListData inListData = new InspectTaskInListData();
				inListData.setSiteClassDetailids(new ArrayList());
				inListData.setSiteid(utInspectBaseRelOutData.getSiteId());
				inListData.setSiteClassid(utInspectBaseRelOutData.getSiteClassId());
				outData.add(inListData);
				map.put(sb.toString(), inListData);
			}
			map.get(sb.toString()).getSiteClassDetailids().add(utInspectBaseRelOutData.getSiteClassDetialId());
		}
		return outData;
	}

}


