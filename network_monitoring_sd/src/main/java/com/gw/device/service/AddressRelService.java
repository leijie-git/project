package com.gw.device.service;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.gw.device.data.AddressRelImportData;
import com.gw.device.data.AddressRelInData;
import com.gw.device.data.AddressRelOutData;
import com.gw.device.data.EquipmentSelectData;

/**
 * 联网设备关联地址服务层接口
 * @author SY
 * @data 2018年9月13日
 */
public interface AddressRelService {
	
	/**
	 * 分页查询方法
	 * @param pageNumber
	 * @param pageSize
	 * @param unitName 
	 * @param netdeviceid
	 * @return
	 */
	PageInfo<AddressRelOutData> list(Integer pageNumber,Integer pageSize, String eqid, String unitName) throws Exception;
	
	/**
	 * 添加方法
	 * @param inData
	 */
	void add(AddressRelInData inData) throws Exception;
	
	/**
	 * 更新
	 * @param inData
	 */
	void update(AddressRelInData inData) throws Exception;
	
	/**
	 * 删除
	 * @param id
	 */
	void delete(String id) throws Exception;
	
	/**
	 * 获得设备信息下拉框数据
	 * @param unitid
	 * @return
	 * @throws Exception 
	 */
	List<EquipmentSelectData> getEquipmentSelect(Long unitid) throws Exception;
	
	/**
	 * 导入
	 * @param addressRelImportDatas
	 * @param importunitid
	 * @param importeqid
	 * @throws Exception 
	 */
	void importData(List<AddressRelImportData> addressRelImportDatas, String importeqid) throws Exception;
}
