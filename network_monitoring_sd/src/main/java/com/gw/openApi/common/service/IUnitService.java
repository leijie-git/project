package com.gw.openApi.common.service;

import com.github.pagehelper.PageInfo;
import com.gw.openApi.common.data.in.UnitBaseInData;
import com.gw.openApi.common.data.out.UnitBuildingOutData;

public interface IUnitService {

    PageInfo<UnitBuildingOutData> getUnitList(UnitBaseInData baseInData) throws Exception;
}
