package com.gw.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.gw.common.BaseMapper;
import com.gw.dataCenter.data.ProviceCityRegionOutData;
import com.gw.mapper.entity.UtBaseProvicecityregion;
import com.gw.unit.data.BaseProvicecityregionOutData;

public interface UtBaseProvicecityregionMapper extends BaseMapper<UtBaseProvicecityregion> {
	
	List<BaseProvicecityregionOutData> selectRegionByType(@Param("type")Integer type,@Param("parentId")String parentId);

	/**
	 * 获取省市区列表
	 * @param name
	 * @return
	 */
	List<Map<String, Object>> getAllProvice(@Param("name") String name);

	/**
	 * 获取省市区列表
	 * @return
	 */
	List<ProviceCityRegionOutData> getListProvice(@Param("type") String type);

	/**
	 * 编辑地区信息
	 * @param id
	 * @return
	 */
	ProviceCityRegionOutData getProviceByID(@Param("id") String id);
}