package com.gw.openApi.common.service.impl;

import com.gw.exception.ServiceException;
import com.gw.mapper.UtMaintenanceUnitMapper;
import com.gw.mapper.UtUnitBaseinfoMapper;
import com.gw.mapper.UtUnitUserMapper;
import com.gw.mapper.entity.UtUnitUser;
import com.gw.openApi.common.data.CheckAcountBaseData;
import com.gw.openApi.common.data.out.UserOut;
import com.gw.openApi.common.service.IUserApiService;
import com.gw.util.UtilConst;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserApiServiceImpl implements IUserApiService {

    @Autowired
    private UtUnitUserMapper unitUserMapper;
    @Autowired
    private UtMaintenanceUnitMapper maintenanceUnitMapper;
    @Autowired
    private UtUnitBaseinfoMapper unitBaseinfoMapper;

    @Override
    public UserOut getUserInfo(CheckAcountBaseData baseData) throws Exception{
        UserOut user = null;
        if(!baseData.isAccountChecked()){
            throw new ServiceException(UtilConst.ERRO_ACCOUNT_PASSWORD);
        }else{
            user = unitUserMapper.selectUserByAccount(baseData.getAccount());
            if (null != user && "0".equals(user.getUserType())) {
                user.setMaintenanceUnit(maintenanceUnitMapper.selectByPrimaryKey(Long.parseLong(user.getUnitID())));
            } else if (null != user && "1".equals(user.getUserType())) {
                user.setUnitBaseinfo(unitBaseinfoMapper.selectByPrimaryKey(Long.parseLong(user.getUnitID())));
            }
        }
        return user;
    }

    @Override
    public UtUnitUser getSysUser(CheckAcountBaseData baseData) throws Exception {

        return null;
    }
}
