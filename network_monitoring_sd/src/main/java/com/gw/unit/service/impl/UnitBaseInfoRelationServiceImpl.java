package com.gw.unit.service.impl;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.gw.unit.data.NetworkingUserOutData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.gw.common.SnowflakeIdWorker;
import com.gw.exception.ServiceException;
import com.gw.mapper.UtUnitBaseinfoRelationMapper;
import com.gw.mapper.UtUnitNetdeviceMapper;
import com.gw.mapper.entity.UtUnitBaseinfoRelation;
import com.gw.mapper.entity.UtUnitNetdevice;
import com.gw.unit.data.UnitBaseInfoRelationInData;
import com.gw.unit.data.UnitBaseInfoRelationOutData;
import com.gw.unit.service.UnitBaseInfoRelationService;
import com.gw.util.Util;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

/**
 * 联网单位绑定编号服务层
 * @author SY
 *
 */
@Service
public class UnitBaseInfoRelationServiceImpl implements UnitBaseInfoRelationService {
	@Autowired
	private UtUnitBaseinfoRelationMapper unitBaseinfoRelationMapper;
	@Autowired
	private UtUnitNetdeviceMapper utUnitNetdeviceMapper;
	@Autowired
	private SnowflakeIdWorker snowflakeIdWorker;

	@Override
	public PageInfo<UnitBaseInfoRelationOutData> list(HttpServletRequest request, String unitId) throws Exception {
		int pageNumber = Integer.parseInt(request.getParameter("pageNumber"));
		int pageSize = Integer.parseInt(request.getParameter("pageSize"));
		PageHelper.startPage(pageNumber, pageSize);
		List<UnitBaseInfoRelationOutData> list = unitBaseinfoRelationMapper.selectBaseinfoRelationByUnitId(unitId);
		PageInfo<UnitBaseInfoRelationOutData> pageInfo = new PageInfo<UnitBaseInfoRelationOutData>(list);
		return pageInfo;
	}

	@Override
	public void addBaseInfoRelation(UnitBaseInfoRelationInData inData) throws Exception{
		String soureaddress = inData.getSoureaddress();
		//判断soureaddress是否重复
		Example example = new Example(UtUnitBaseinfoRelation.class);
		Criteria criteria = example.createCriteria();
		criteria.andEqualTo("soureaddress", soureaddress);
		List<UtUnitBaseinfoRelation> selectByExample = unitBaseinfoRelationMapper.selectByExample(example);
		if(selectByExample!=null && selectByExample.size()>0){
			throw new ServiceException("绑定编号已存在");
		}
		
		UtUnitBaseinfoRelation baseinfoRelation = new UtUnitBaseinfoRelation();
		baseinfoRelation.setId(snowflakeIdWorker.nextId());
		baseinfoRelation.setSoureaddress(inData.getSoureaddress());
		baseinfoRelation.setUnitid(Long.valueOf(inData.getUnitid()));
		baseinfoRelation.setLastupdate(new Date());
		unitBaseinfoRelationMapper.insert(baseinfoRelation);
	}

	@Override
	public void updateBaseInfoRelation(UnitBaseInfoRelationInData inData) throws Exception{
		String soureaddress = inData.getSoureaddress();
		Example example = new Example(UtUnitBaseinfoRelation.class);
		Criteria criteria = example.createCriteria();
		criteria.andEqualTo("soureaddress", soureaddress);
		List<UtUnitBaseinfoRelation> selectByExample = unitBaseinfoRelationMapper.selectByExample(example);
		if(selectByExample!=null && selectByExample.size()>0){
			UtUnitBaseinfoRelation baseinfoRelation = selectByExample.get(0);
			long id = baseinfoRelation.getId();
			if(id != Long.parseLong(inData.getId())){
				throw new ServiceException("绑定编号已存在");
			}
		}
		
		UtUnitBaseinfoRelation baseinfoRelation = unitBaseinfoRelationMapper.selectByPrimaryKey(Long.valueOf(inData.getId()));
		baseinfoRelation.setSoureaddress(inData.getSoureaddress());
		if(Util.isNotEmpty(inData.getUnitid())){
			baseinfoRelation.setUnitid(Long.valueOf(inData.getUnitid()));
		}else{
			baseinfoRelation.setUnitid(null);
		}
		baseinfoRelation.setLastupdate(new Date());
		unitBaseinfoRelationMapper.updateByPrimaryKeySelective(baseinfoRelation);
	}

	@Override
	public void deleteBaseInfoRelation(String id) throws Exception{
		UtUnitBaseinfoRelation utUnitBaseinfoRelation = unitBaseinfoRelationMapper.selectByPrimaryKey(Long.valueOf(id));
		Example example = new Example(UtUnitNetdevice.class);
		example.createCriteria().andEqualTo("ownercode", utUnitBaseinfoRelation.getSoureaddress()).andEqualTo("isdelete", 0);;
		List<UtUnitNetdevice> list = utUnitNetdeviceMapper.selectByExample(example);
		if(Util.isNotEmpty(list)){
			throw new ServiceException("删除失败！该编号下存在设备未删除！");
		}
		unitBaseinfoRelationMapper.deleteByPrimaryKey(Long.valueOf(id));
	}

	@Override
	public List<UnitBaseInfoRelationOutData> soureaddressSelect(String unitid) throws Exception {
		List<UnitBaseInfoRelationOutData> list = unitBaseinfoRelationMapper.soureaddressSelect(unitid);
		return list;
	}

	@Override
	public PageInfo<UnitBaseInfoRelationOutData> getAllUnitRel(UnitBaseInfoRelationInData inData) throws Exception{
		PageHelper.startPage(inData.getPageNumber(),inData.getPageSize());
		List<UnitBaseInfoRelationOutData> list = unitBaseinfoRelationMapper.getAllUnitRel(inData);
		PageInfo<UnitBaseInfoRelationOutData> pageInfo = new PageInfo<UnitBaseInfoRelationOutData>(list);
		return pageInfo;
	}

	/**
	 * 提供根据单位查询单位用户接口
	 * */
	@Override
	public List<NetworkingUserOutData> selectAccountByUnitID(String UnitID,String role) {
		return unitBaseinfoRelationMapper.selectAccountByUnitID(UnitID,role);
	}
}
