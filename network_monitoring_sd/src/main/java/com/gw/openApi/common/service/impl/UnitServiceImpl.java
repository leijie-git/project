package com.gw.openApi.common.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.gw.exception.ServiceException;
import com.gw.mapper.UtUnitBaseinfoMapper;
import com.gw.openApi.common.data.in.UnitBaseInData;
import com.gw.openApi.common.data.out.UnitBuildingOutData;
import com.gw.openApi.common.service.IUnitService;
import com.gw.util.Util;
import com.gw.util.UtilConst;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UnitServiceImpl implements IUnitService {

    @Value("${TL.unitParam.defualtAccount}")
    private String accountName;
    @Autowired
    private UtUnitBaseinfoMapper unitBaseinfoMapper;
    @Override
    public PageInfo<UnitBuildingOutData> getUnitList(UnitBaseInData baseInData) throws Exception{
        if(Util.isEmpty(baseInData.getAccount()) && Util.isNotEmpty(accountName)){
            baseInData.setAccount(accountName);
        }else if(!baseInData.isAccountChecked()){
            throw new ServiceException(UtilConst.ERRO_ACCOUNT_PASSWORD);
        }
        PageHelper.startPage(baseInData.getPageNumber(),baseInData.getPageSize());
        List<UnitBuildingOutData> unitBaseList = unitBaseinfoMapper.getUnitBaseList(baseInData);
        PageInfo<UnitBuildingOutData> pagedUnitList = new PageInfo<>(unitBaseList);
        return pagedUnitList;
    }
}
