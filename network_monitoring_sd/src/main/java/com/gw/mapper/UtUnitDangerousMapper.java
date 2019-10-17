package com.gw.mapper;

import java.util.List;

import com.gw.common.BaseMapper;
import com.gw.mapper.entity.UtUnitDangerous;
import com.gw.unit.data.DangerousInData;
import com.gw.unit.data.DangerousOutData;

public interface UtUnitDangerousMapper extends BaseMapper<UtUnitDangerous> {

	/**
	 * 获取危险品列表
	 * @param inData
	 * @return
	 */
	List<DangerousOutData> getList(DangerousInData inData);
}