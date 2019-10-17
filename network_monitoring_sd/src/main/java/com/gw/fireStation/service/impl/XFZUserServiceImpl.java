package com.gw.fireStation.service.impl;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.gw.common.SnowflakeIdWorker;
import com.gw.fireStation.data.XFZUserInData;
import com.gw.fireStation.data.XFZUserOutData;
import com.gw.fireStation.service.XFZUserService;
import com.gw.mapper.UtUnitBaseinfoMapper;
import com.gw.mapper.UtUnitXfzuserMapper;
import com.gw.mapper.entity.UtUnitXfzuser;
import com.gw.unit.data.BaseInfoSelectOutData;
import com.gw.util.Util;

/**
 * 消防人员服务层
 * @author SY
 *
 */

@Service
public class XFZUserServiceImpl implements XFZUserService {
	
	@Autowired
	private UtUnitXfzuserMapper xfzuserMapper;
	
	@Autowired
	private UtUnitBaseinfoMapper baseinfoMapper;
	
	@Autowired
	private SnowflakeIdWorker snowflakeIdWorker;

	@Override
	public PageInfo<XFZUserOutData> list(HttpServletRequest request, String username) throws Exception {
		int pageNum = Integer.parseInt(request.getParameter("pageNumber"));
		int pageSize = Integer.parseInt(request.getParameter("pageSize"));
		PageHelper.startPage(pageNum, pageSize);
		List<XFZUserOutData> list = xfzuserMapper.selectAllXFZUser(username);
		PageInfo<XFZUserOutData> pageInfo = new PageInfo<XFZUserOutData>(list);
		return pageInfo;
	}

	@Override
	public void add(XFZUserInData inData) throws Exception {
		UtUnitXfzuser xfzuser = new UtUnitXfzuser();
		BeanUtils.copyProperties(inData, xfzuser);
		xfzuser.setId(snowflakeIdWorker.nextId());
		
		if(Util.isNotEmpty(inData.getUnitid())){
			xfzuser.setUnitid(Long.valueOf(inData.getUnitid()));
		}
		xfzuserMapper.insert(xfzuser);
		
	}

	@Override
	public void update(XFZUserInData inData) throws Exception {
		UtUnitXfzuser xfzuser = xfzuserMapper.selectByPrimaryKey(Long.valueOf(inData.getId()));
		BeanUtils.copyProperties(inData, xfzuser);
		if(Util.isNotEmpty(inData.getUnitid())){
			xfzuser.setUnitid(Long.valueOf(inData.getUnitid()));
		}else{
			xfzuser.setUnitid(null);
		}
		xfzuserMapper.updateByPrimaryKey(xfzuser);
	}

	@Override
	public void delete(Long id) throws Exception {
		xfzuserMapper.deleteByPrimaryKey(id);
	}

	@Override
	public List<BaseInfoSelectOutData> unitSelect() throws Exception {
		List<BaseInfoSelectOutData> list = baseinfoMapper.selectUnitNameAndId();
		return list;
	}

}
