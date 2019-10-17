package com.gw.build.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.gw.build.data.BuildAreaExportData;
import com.gw.build.data.BuildAreaInData;
import com.gw.build.data.BuildAreaOutData;
import com.gw.build.service.BuildAreaService;
import com.gw.common.ExportExcel;
import com.gw.common.SnowflakeIdWorker;
import com.gw.exception.ServiceException;
import com.gw.mapper.UtUnitBuildareaMapper;
import com.gw.mapper.entity.UtUnitBuildarea;
import com.gw.util.UtilMessage;


@Service
public class BuildAreaServiceImpl implements  BuildAreaService{

	@Resource
	private UtUnitBuildareaMapper utUnitBuildareaMapper;
	@Autowired
	private SnowflakeIdWorker snowflakeIdWorker;
	@Override
	public PageInfo<BuildAreaOutData> getList(BuildAreaInData inData)throws Exception {
		PageHelper.startPage(inData.getPageNumber(), inData.getPageSize());
		List<BuildAreaOutData> list = utUnitBuildareaMapper.getList(inData);
		PageInfo<BuildAreaOutData> pager = new PageInfo<BuildAreaOutData>(list);
		return pager;
	}

	@Override
	public void add(BuildAreaInData inData) throws Exception {
		UtUnitBuildarea buildArea = new UtUnitBuildarea();
		buildArea.setId(snowflakeIdWorker.nextId());
		if(inData.getUnitID()!=""&&inData.getUnitID()!=null) {
			buildArea.setUnitid(Long.parseLong(inData.getUnitID()));
		}
		buildArea.setBuildareaname(inData.getBuildAreaName());
		buildArea.setBuildareasite(inData.getBuildAreaSite());
		if(inData.getBgWidth()!=""&&inData.getBgWidth()!=null) {
			BigDecimal bgwidth = new BigDecimal(inData.getBgWidth());
			buildArea.setBgwidth(bgwidth);
		}
		if(inData.getBgHeight()!=""&&inData.getBgHeight()!=null) {
			BigDecimal getBgHeight = new BigDecimal(inData.getBgHeight());
			buildArea.setBgheight(getBgHeight);
		}
		buildArea.setBuildareabg(inData.getBuildAreabg());
		buildArea.setBuildimgbg(inData.getBuildImgbg());
		if(inData.getBuildID()!=null && inData.getBuildID()!="") {
			buildArea.setBuildid(Long.parseLong(inData.getBuildID()));
		}
		Integer flag = utUnitBuildareaMapper.insert(buildArea);
		if(flag<1) {
			throw new ServiceException(UtilMessage.SAVE_MESSAGE_ERROR);
		}
		
	}

	@Override
	public void update(BuildAreaInData inData) throws Exception {
		UtUnitBuildarea buildArea = new UtUnitBuildarea();
		buildArea.setId(Long.parseLong(inData.getId()));
		buildArea.setBuildareaname(inData.getBuildAreaName());
		buildArea.setBuildareasite(inData.getBuildAreaSite());
		if(inData.getUnitID()!=null && inData.getUnitID()!="") {
			buildArea.setUnitid(Long.parseLong(inData.getUnitID()));
		}
		if(inData.getBgWidth()!=""&&inData.getBgWidth()!=null) {
			BigDecimal bgwidth = new BigDecimal(inData.getBgWidth());
			buildArea.setBgwidth(bgwidth);
		}
		if(inData.getBgHeight()!=""&&inData.getBgHeight()!=null) {
			BigDecimal getBgHeight = new BigDecimal(inData.getBgHeight());
			buildArea.setBgheight(getBgHeight);
		}
		buildArea.setBuildareabg(inData.getBuildAreabg());
		buildArea.setBuildimgbg(inData.getBuildImgbg());
		if(inData.getBuildID()!=null && inData.getBuildID()!="") {
			buildArea.setBuildid(Long.parseLong(inData.getBuildID()));
		}
		Integer flag = utUnitBuildareaMapper.updateByPrimaryKey(buildArea);
		if(flag<1) {
			throw new ServiceException(UtilMessage.UPDATE_ERROR);
		}
		
	}

	@Override
	public void remove(String id) throws Exception {
		String[] ids = id.split(",");
		for(String key:ids) {
			System.out.println("key:"+key);
			Integer flag = utUnitBuildareaMapper.deleteByPrimaryKey(Long.parseLong(key));
			if(flag<1) {
				throw new ServiceException(UtilMessage.DEL_MESSAGE_ERROR);
			}
		}
		
	}

	@Override
	public List<BuildAreaOutData> getArrayList(BuildAreaInData inData)throws Exception {
		if("".equals(inData.getBuildID())) {
			inData = null;
		}
		return utUnitBuildareaMapper.getList(inData);
	}

	@Override
	public void exportBuildArea(HttpServletResponse response, BuildAreaInData inData) throws Exception {
		List<BuildAreaExportData> exportList = new ArrayList<>();
		List<BuildAreaOutData> list = utUnitBuildareaMapper.getBuildAreaExportData(inData);
		for (BuildAreaOutData buildAreaOutData : list) {
			BuildAreaExportData exportData = new BuildAreaExportData();
			BeanUtils.copyProperties(buildAreaOutData, exportData);
			exportList.add(exportData);
		}
		String[] header={"区域ID","区域名称","区域地址","状态","备注","单位名称","建筑名称"};
		ExportExcel<BuildAreaExportData> exportExcel = new ExportExcel<BuildAreaExportData>();
		exportExcel.exportExcel(response, exportList, header);
	}

}
