package com.gw.openApi.common.service;

import com.gw.mapper.entity.UtUnitUser;
import com.gw.openApi.common.data.out.UserOut;
import com.gw.openApi.common.data.CheckAcountBaseData;

public interface IUserApiService {

    UserOut getUserInfo(CheckAcountBaseData baseData) throws Exception;

    UtUnitUser getSysUser(CheckAcountBaseData baseData) throws Exception;
}
