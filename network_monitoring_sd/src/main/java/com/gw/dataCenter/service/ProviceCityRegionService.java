package com.gw.dataCenter.service;

import com.gw.dataCenter.data.ProviceCityRegionInData;
import com.gw.dataCenter.data.ProviceCityRegionOutData;
import com.gw.dataCenter.data.RegionImportVo;

import java.util.List;
import java.util.Map;


public interface ProviceCityRegionService {

	/**
	 * 获取城市列表
	 * @param name
	 * @return
	 */
	List<Map<String, Object>> getAllProvice(String name) throws Exception;

	/**
	 * 删除城市
	 * @param id
	 */
	void delete(String id)throws Exception;

	/**
	 * 添加城市
	 * @param inData
	 * @throws Exception
	 */
	void add(ProviceCityRegionInData inData)throws Exception;

	/**
	 * 更新城市信息
	 * @param inData
	 */
	void update(ProviceCityRegionInData inData)throws Exception;

	/**
	 * 根据id 查询列表
	 */
	List<ProviceCityRegionOutData> listProviceByID(String type);

	/**
	 * 编辑地区信息
	 * @param id
	 * @return
	 */
	ProviceCityRegionOutData edit(String id);

	List<RegionImportVo> importRegion(List<RegionImportVo> regionImportVos);
}
