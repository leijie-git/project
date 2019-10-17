package com.gw.inspect.service.impl;


import java.util.List;

import javax.annotation.Resource;

import com.gw.mapper.UtInspectPlandetialMapper;
import com.gw.mapper.entity.UtInspectTask;
import com.gw.systemManager.service.PropertiesManageService;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.gw.exception.ServiceException;
import com.gw.inspect.data.DownLoadTaskInData;
import com.gw.inspect.data.InspectTaskInData;
import com.gw.inspect.data.InspectTaskOutData;
import com.gw.inspect.service.InspectTaskService;
import com.gw.mapper.UtInspectTaskMapper;
import com.gw.util.UtilMessage;

@Service
public class InspectTaskServiceImpl implements InspectTaskService{

	@Resource
	private UtInspectTaskMapper utInspectTaskMapper;
	@Resource
	private PropertiesManageService propertiesManageService;
	@Resource
	private UtInspectPlandetialMapper utInspectPlandetialMapper;
	
	@Override
	public PageInfo<InspectTaskOutData> getList(InspectTaskInData inData) {
		PageHelper.startPage(inData.getPageNumber(), inData.getPageSize());
		List<InspectTaskOutData> list = utInspectTaskMapper.getList(inData);
		PageInfo<InspectTaskOutData> page = new PageInfo<InspectTaskOutData>(list);
		return page;
	}

	@Override
	public void remove(String id) {
		if (id != null && !"".equals(id)) {
			String[] ids = id.split(",");
			for (String key : ids) {
				UtInspectTask planDetId = utInspectTaskMapper.getPlandetialidById(Long.parseLong(key));
				Integer i = utInspectPlandetialMapper.updateStatusById(planDetId.getPlandetialid());
				if (i < 1) {
					throw new ServiceException(UtilMessage.DEL_MESSAGE_ERROR);
				}
				Integer flag = utInspectTaskMapper.deleteByPrimaryKey(Long.parseLong(key));
				if (flag < 0) {
					throw new ServiceException(UtilMessage.DEL_MESSAGE_ERROR);
				}

			}

		}
	}

	@Override
	public void getUserTask(DownLoadTaskInData inData) throws Exception {
		//List<InspectTaskOutData> list = utInspectTaskMapper.getUserTaskList(inData);
	}

}
