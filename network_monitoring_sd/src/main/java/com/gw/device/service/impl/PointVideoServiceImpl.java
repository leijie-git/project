package com.gw.device.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.gw.common.ExportExcel;
import com.gw.common.SnowflakeIdWorker;
import com.gw.device.data.PointVideoExportData;
import com.gw.device.data.PointVideoInData;
import com.gw.device.data.PointVideoOutData;
import com.gw.device.service.PointVideoService;
import com.gw.exception.ServiceException;
import com.gw.mapper.UtEqAddressRelMapper;
import com.gw.mapper.UtUnitVideoMapper;
import com.gw.mapper.entity.UtEqAddressRel;
import com.gw.mapper.entity.UtUnitVideo;
import com.gw.util.Util;

import tk.mybatis.mapper.entity.Example;

@Service
public class PointVideoServiceImpl implements PointVideoService {
	@Autowired
	private SnowflakeIdWorker snowflakeIdWorker;
	@Autowired
	private UtUnitVideoMapper utUnitVideoMapper;
	@Autowired
	private UtEqAddressRelMapper utEqAddressRelMapper;

	@Override
	public void addPointVideo(PointVideoInData inData) throws Exception {
		UtUnitVideo utUnitVideo = new UtUnitVideo();
		BeanUtils.copyProperties(inData, utUnitVideo);
		utUnitVideo.setId(snowflakeIdWorker.nextId());
		utUnitVideo.setVideotype(4);
		utUnitVideo.setLastupdate(new Date());
		utUnitVideo.setUnitid(Long.valueOf(inData.getUnitId()));
		utUnitVideoMapper.insert(utUnitVideo);
	}

	@Override
	public void updatePointVideo(PointVideoInData inData) throws Exception {
		UtUnitVideo utUnitVideo = utUnitVideoMapper.selectByPrimaryKey(Long.valueOf(inData.getId()));
		BeanUtils.copyProperties(inData, utUnitVideo);
		utUnitVideo.setUnitid(Long.valueOf(inData.getUnitId()));
		utUnitVideo.setLastupdate(new Date());
		utUnitVideoMapper.updateByPrimaryKey(utUnitVideo);
	}

	@Override
	public PageInfo<PointVideoOutData> getPointVideoList(PointVideoInData inData) throws Exception {
		PageHelper.startPage(inData.getPageNumber(), inData.getPageSize());
		List<PointVideoOutData> list = utUnitVideoMapper.getPointVideoList(inData);
		PageInfo<PointVideoOutData> pageInfo = new PageInfo<PointVideoOutData>(list);
		return pageInfo;
	}

	@Override
	public void deletePointVideo(String id) throws Exception {
		Example example = new Example(UtEqAddressRel.class);
		example.createCriteria().andEqualTo("videoid", Long.valueOf(id));
		List<UtEqAddressRel> list = utEqAddressRelMapper.selectByExample(example);
		if (Util.isNotEmpty(list)) {
			throw new ServiceException("删除失败，该视频已被点位绑定！");
		}

		utUnitVideoMapper.deleteByPrimaryKey(Long.valueOf(id));
	}

	@Override
	public List<PointVideoOutData> getPointVideoSelect(Long unitId) throws Exception {
		return utUnitVideoMapper.getPointVideoSelect(unitId);
	}

	@Override
	public void exportPointVideo(HttpServletResponse response, PointVideoInData inData) throws Exception {
		List<PointVideoExportData> exportDataList = new ArrayList<>();
		List<PointVideoOutData> list = utUnitVideoMapper.getPointVideoList(inData);
		for (PointVideoOutData pointVideoOutData : list) {
			PointVideoExportData exportData = new PointVideoExportData();
			BeanUtils.copyProperties(pointVideoOutData, exportData);
			exportDataList.add(exportData);
		}
		String[] header = new String[] { "点位视频ID","单位名称", "视频名称", "IP地址", "品牌", "生产厂家" };
		ExportExcel<PointVideoExportData> excel = new ExportExcel<PointVideoExportData>();
		excel.exportExcel(response, exportDataList, header);
	}

}
