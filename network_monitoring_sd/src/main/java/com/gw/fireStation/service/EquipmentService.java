package com.gw.fireStation.service;

import com.github.pagehelper.PageInfo;
import com.gw.fireStation.data.EquipmentInData;
import com.gw.fireStation.data.EquipmentOutData;

/**
 * 消防设施业务层接口
 * @author zfg
 *
 */
public interface EquipmentService {

	/**
	 * 获取消防设施列表
	 * @param inData
	 * @return
	 * @throws Exception 
	 */
	PageInfo<EquipmentOutData> getList(EquipmentInData inData, String equipmentname) throws Exception;

	/**
	 * 添加消防设施
	 * @param inData
	 */
	void add(EquipmentInData inData)throws Exception;

	/**
	 * 编辑消防设施
	 * @param inData
	 * @throws Exception
	 */
	void update(EquipmentInData inData)throws Exception;

	/**
	 * 删除消防设施
	 * @param id
	 * @throws Exception
	 */
	void remove(String id)throws Exception;
	
	void delete(Long id) throws Exception;

}
