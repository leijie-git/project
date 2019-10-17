package com.gw.mapper;

import java.util.List;

import com.gw.common.BaseMapper;
import com.gw.mapper.entity.UtUnitImport;
import com.gw.unit.data.KeyPartsInData;
import com.gw.unit.data.KeyPartsOutData;

public interface UtUnitImportMapper extends BaseMapper<UtUnitImport> {

	/**
	 * 获取重点单位列表
	 * @param inData
	 * @return
	 */
	List<KeyPartsOutData> getList(KeyPartsInData inData);
}