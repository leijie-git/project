package com.gw.unit.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.gw.common.SnowflakeIdWorker;
import com.gw.exception.ServiceException;
import com.gw.mapper.UtUnitVideoMapper;
import com.gw.mapper.entity.UtUnitVideo;
import com.gw.unit.data.VideoInData;
import com.gw.unit.data.VideoOutData;
import com.gw.unit.service.VideoService;
import com.gw.util.UtilMessage;

@Service
public class VideoServiceImpl implements VideoService {

	@Resource
	private UtUnitVideoMapper utUnitVideoMapper;
	
	@Autowired
	private SnowflakeIdWorker snowflakeIdWorker;
	@Override
	public PageInfo<VideoOutData> getList(VideoInData inData) throws Exception{
		PageHelper.startPage(inData.getPageNumber(), inData.getPageSize());
		List<VideoOutData> list = utUnitVideoMapper.getList(inData);
		PageInfo<VideoOutData> page = new PageInfo<VideoOutData>(
				list);
		return page;
	}

	@Override
	public void add(VideoInData inData) throws Exception {
		Integer flag = 0;
		UtUnitVideo video = new UtUnitVideo();
		video.setVideotype(Integer.parseInt(inData.getVideoType()));
		video.setId(snowflakeIdWorker.nextId());
		video.setName(inData.getName());
		video.setBrand(inData.getBrand());
		video.setIp(inData.getIp());
		video.setManufactor(inData.getManufactor());
		video.setUsername(inData.getUserName());
		video.setPassword(inData.getPassword());
		video.setPlugintype(inData.getPlugInType());
		video.setPort(inData.getPort());
		video.setPoscode(inData.getPoscode());
		video.setPosition(inData.getPosition());
		video.setUnitid(Long.parseLong(inData.getUnitId()));
		video.setSerialnumber(inData.getSerialnumber());
		if(inData.getBuildId()!=null && inData.getBuildId()!="") {
			video.setBuildid(Long.parseLong(inData.getBuildId()));
		}
		if(inData.getBuildAreaId()!=null && inData.getBuildAreaId()!="") {
			video.setBuildareaid(Long.parseLong(inData.getBuildAreaId()));
		}
		flag = utUnitVideoMapper.insert(video);
		if(flag<1) {
			throw new ServiceException(UtilMessage.SAVE_MESSAGE_ERROR);
		}
	}

	@Override
	public void update(VideoInData inData) throws Exception {
		Integer flag = utUnitVideoMapper.update(inData);
		if(flag<1) {
			throw new ServiceException(UtilMessage.UPDATE_ERROR);
		}
	}

	@Override
	public void remove(String id) throws Exception {
		String[] ids = id.split(",");
		for(String key:ids) {
			System.out.println("key:"+key);
			Integer flag = utUnitVideoMapper.deleteByPrimaryKey(Long.parseLong(key));
			if(flag<1) {
				throw new ServiceException(UtilMessage.DEL_MESSAGE_ERROR);
			}
		}
		
	}


}
