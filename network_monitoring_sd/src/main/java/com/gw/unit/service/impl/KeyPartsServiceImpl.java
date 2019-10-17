package com.gw.unit.service.impl;

import java.math.BigDecimal;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.gw.common.SnowflakeIdWorker;
import com.gw.exception.ServiceException;
import com.gw.mapper.UtUnitImportMapper;
import com.gw.mapper.entity.UtUnitImport;
import com.gw.unit.data.KeyPartsInData;
import com.gw.unit.data.KeyPartsOutData;
import com.gw.unit.service.KeyPartsService;
import com.gw.util.Util;
import com.gw.util.UtilMessage;
/**
 * 重点单位实现类
 * @author zfg
 *
 */
@Service
public class KeyPartsServiceImpl implements KeyPartsService{

	@Resource
	private UtUnitImportMapper utUnitImportMapper;
	@Autowired
	private SnowflakeIdWorker snowflakeIdWorker;
	@Override
	public PageInfo<KeyPartsOutData> getList(KeyPartsInData inData) throws Exception{
		PageHelper.startPage(inData.getPageNumber(), inData.getPageSize());
		List<KeyPartsOutData> list = utUnitImportMapper.getList(inData);
		PageInfo<KeyPartsOutData> pager = new PageInfo<KeyPartsOutData>(list);
		return pager;
	}

	@Override
	public void add(KeyPartsInData inData) throws Exception {
		UtUnitImport data = new UtUnitImport();
		data.setId(snowflakeIdWorker.nextId());
		data.setBuildid(Long.parseLong(inData.getBuildId()));
		data.setBuildingstructure(inData.getBuildingStructure());
		if(Util.isNotEmpty(inData.getDTCount())) {
			data.setDtcount(Integer.parseInt(inData.getDTCount()));
		}
		data.setFhbzslqk(inData.getFHBZSLQK());
		data.setFireinfo(inData.getFireInfo());
		if(Util.isNotEmpty(inData.getHeight())) {
			BigDecimal he = new BigDecimal(inData.getHeight());
			data.setHeight(he);
		}
		data.setFloor(inData.getFloor());
		data.setHzwxx(inData.getHZWXX());
		if(Util.isNotEmpty(inData.getBuildArea())) {
			BigDecimal ar = new BigDecimal(inData.getBuildArea());
			data.setBuildarea(ar);
		}
		data.setImportimagename(inData.getImportImageName());
		data.setImportname(inData.getImportName());
		data.setImportsite(inData.getImportSite());
		if(Util.isNotEmpty(inData.getIsBZP())) {
			data.setIsbzp(Integer.parseInt(inData.getIsBZP()));
		}
		if(Util.isNotEmpty(inData.getIsDXFYZDX())) {
			data.setIsdxfyzdx(Integer.parseInt(inData.getIsDXFYZDX()));
		}
		if(Util.isNotEmpty(inData.getIsFSP())) {
			data.setIsfsp(Integer.parseInt(inData.getIsFSP()));
		}
		if(Util.isNotEmpty(inData.getIsFSX())) {
			data.setIsfsx(Integer.parseInt(inData.getIsFSX()));
		}
		if(Util.isNotEmpty(inData.getIsHZFSHRYSWD())) {
			data.setIshzfshryswd(Integer.parseInt(inData.getIsHZFSHRYSWD()));
		}
		if(Util.isNotEmpty(inData.getIsHZHSSD())) {
			data.setIshzhssd(Integer.parseInt(inData.getIsHZHSSD()));
		}
		if(Util.isNotEmpty(inData.getIsOther())) {
			data.setIsother(Integer.parseInt(inData.getIsOther()));
		}
		if(Util.isNotEmpty(inData.getIsQT())) {
			data.setIsqt(Integer.parseInt(inData.getIsQT()));
		}
		if(Util.isNotEmpty(inData.getIsYDP())) {
			data.setIsydp(Integer.parseInt(inData.getIsYDP()));
		}
		if(Util.isNotEmpty(inData.getIsYFSHZ())) {
			data.setIsyfshz(Integer.parseInt(inData.getIsYFSHZ()));
		}
		if(Util.isNotEmpty(inData.getIsYHQHYJHHW())) {
			data.setIsyhqhyjhhw(Integer.parseInt(inData.getIsYHQHYJHHW()));
		}
		if(Util.isNotEmpty(inData.getUnitId())) {
			data.setUnitid(Long.parseLong(inData.getUnitId()));
		}
		if(Util.isNotEmpty(inData.getOutCount())) {
			data.setOutcount(Integer.parseInt(inData.getOutCount()));
		}
		if(Util.isNotEmpty(inData.getIsZXWXW())) {
			data.setIszxwxw(Integer.parseInt(inData.getIsZXWXW()));
		}
		if(Util.isNotEmpty(inData.getIsYSQT())) {
			data.setIsysqt(Integer.parseInt(inData.getIsYSQT()));
		}
		if(Util.isNotEmpty(inData.getIsZXWXW())) {
			data.setIsyryt(Integer.parseInt(inData.getIsYRYT()));
		}
		if(Util.isNotEmpty(inData.getIsYRGT())) {
			data.setIsyrgt(Integer.parseInt(inData.getIsYRGT()));
		}
		data.setUsed(inData.getUsed());
		data.setNhlevel(inData.getNHLevel());
		Integer flag = utUnitImportMapper.insert(data);
		if(flag<1) {
			throw new ServiceException(UtilMessage.SAVE_MESSAGE_ERROR);
		}
		
	}

	@Override
	public void update(KeyPartsInData inData) throws Exception {
		UtUnitImport data = new UtUnitImport();
		data.setId(Long.parseLong(inData.getID()));
		data.setBuildid(Long.parseLong(inData.getBuildId()));
		if(Util.isNotEmpty(inData.getDTCount())) {
			data.setDtcount(Integer.parseInt(inData.getDTCount()));
		}
		data.setFhbzslqk(inData.getFHBZSLQK());
		data.setFireinfo(inData.getFireInfo());
		if(Util.isNotEmpty(inData.getHeight())) {
			BigDecimal he = new BigDecimal(inData.getHeight());
			data.setHeight(he);
		}
		data.setFloor(inData.getFloor());
		data.setHzwxx(inData.getHZWXX());
		if(Util.isNotEmpty(inData.getBuildArea())) {
			BigDecimal ar = new BigDecimal(inData.getBuildArea());
			data.setBuildarea(ar);
		}
		data.setImportimagename(inData.getImportImageName());
		data.setImportname(inData.getImportName());
		data.setImportsite(inData.getImportSite());
		if(Util.isNotEmpty(inData.getIsBZP())) {
			data.setIsbzp(Integer.parseInt(inData.getIsBZP()));
		}
		if(Util.isNotEmpty(inData.getIsDXFYZDX())) {
			data.setIsdxfyzdx(Integer.parseInt(inData.getIsDXFYZDX()));
		}
		if(Util.isNotEmpty(inData.getIsFSP())) {
			data.setIsfsp(Integer.parseInt(inData.getIsFSP()));
		}
		if(Util.isNotEmpty(inData.getIsFSX())) {
			data.setIsfsx(Integer.parseInt(inData.getIsFSX()));
		}
		if(Util.isNotEmpty(inData.getIsHZFSHRYSWD())) {
			data.setIshzfshryswd(Integer.parseInt(inData.getIsHZFSHRYSWD()));
		}
		if(Util.isNotEmpty(inData.getIsHZHSSD())) {
			data.setIshzhssd(Integer.parseInt(inData.getIsHZHSSD()));
		}
		if(Util.isNotEmpty(inData.getIsOther())) {
			data.setIsother(Integer.parseInt(inData.getIsOther()));
		}
		if(Util.isNotEmpty(inData.getIsQT())) {
			data.setIsqt(Integer.parseInt(inData.getIsQT()));
		}
		if(Util.isNotEmpty(inData.getIsYDP())) {
			data.setIsydp(Integer.parseInt(inData.getIsYDP()));
		}
		if(Util.isNotEmpty(inData.getIsYFSHZ())) {
			data.setIsyfshz(Integer.parseInt(inData.getIsYFSHZ()));
		}
		if(Util.isNotEmpty(inData.getIsYHQHYJHHW())) {
			data.setIsyhqhyjhhw(Integer.parseInt(inData.getIsYHQHYJHHW()));
		}
		if(Util.isNotEmpty(inData.getUnitId())) {
			data.setUnitid(Long.parseLong(inData.getUnitId()));
		}
		if(Util.isNotEmpty(inData.getOutCount())) {
			data.setOutcount(Integer.parseInt(inData.getOutCount()));
		}
		if(Util.isNotEmpty(inData.getIsZXWXW())) {
			data.setIszxwxw(Integer.parseInt(inData.getIsZXWXW()));
		}
		if(Util.isNotEmpty(inData.getIsYSQT())) {
			data.setIsysqt(Integer.parseInt(inData.getIsYSQT()));
		}
		if(Util.isNotEmpty(inData.getIsZXWXW())) {
			data.setIsyryt(Integer.parseInt(inData.getIsYRYT()));
		}
		if(Util.isNotEmpty(inData.getIsYRGT())) {
			data.setIsyrgt(Integer.parseInt(inData.getIsYRGT()));
		}
		data.setUsed(inData.getUsed());
		data.setNhlevel(inData.getNHLevel());
		Integer flag = utUnitImportMapper.updateByPrimaryKey(data);
		if(flag<1) {
			throw new ServiceException(UtilMessage.UPDATE_ERROR);
		}
	}

	@Override
	public void remove(String id) throws Exception {
		String[] ids = id.split(",");
		for(String key:ids) {
			System.out.println("key:"+key);
			Integer flag = utUnitImportMapper.deleteByPrimaryKey(Long.parseLong(key));
			if(flag<1) {
				throw new ServiceException(UtilMessage.DEL_MESSAGE_ERROR);
			}
		}
	}


}
