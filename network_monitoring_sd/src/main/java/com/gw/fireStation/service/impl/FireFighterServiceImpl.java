package com.gw.fireStation.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.gw.build.data.BuildAreaInData;
import com.gw.exception.ServiceException;
import com.gw.fireStation.data.FireFighterInData;
import com.gw.fireStation.data.FireFighterOutData;
import com.gw.fireStation.service.FireFighterService;
import com.gw.util.UtilMessage;

@Service
public class FireFighterServiceImpl implements FireFighterService {

	@Override
	public PageInfo<FireFighterOutData> getList(BuildAreaInData inData) throws Exception{
		PageHelper.startPage(inData.getPageNumber(), inData.getPageSize());
		List<FireFighterOutData> list = new ArrayList<FireFighterOutData>();
		PageInfo<FireFighterOutData> pager = new PageInfo<FireFighterOutData>(list);
		return pager;	}

	@Override
	public void add(FireFighterInData inData) throws Exception{
		
	}

	@Override
	public void update(FireFighterInData inData) throws Exception{
		
	}

	@Override
	public void remove(String id) throws Exception{
		String[] ids = id.split(",");
		for(String key:ids) {
			System.out.println("key:"+key);
			Integer flag = 0;
			if(flag<1) {
				throw new ServiceException(UtilMessage.DEL_MESSAGE_ERROR);
			}
		}
		
	}

}
