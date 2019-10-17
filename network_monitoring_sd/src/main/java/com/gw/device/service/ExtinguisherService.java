package com.gw.device.service;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.gw.alarm.data.EmployeeForSendMsgData;
import com.gw.device.data.ExtinguisherImportData;
import com.gw.front.unit.data.FrontUnitExtinguisherInData;
import com.gw.front.unit.data.FrontUnitExtinguisherOutData;

public interface ExtinguisherService {
	/**
	 * 根据单位分页查询所有灭火器设备
	 * @param userId 
	 * @param userId 
	 * 
	 * @return
	 */
	PageInfo<FrontUnitExtinguisherOutData> getExtinguisherList(Long userId, FrontUnitExtinguisherInData inData)
			throws Exception;

	/**
	 * 新增灭火器
	 * @param userId 
	 * 
	 * @param inData
	 */
	void addExtinguisher(Long userId, FrontUnitExtinguisherInData inData) throws Exception;

	/**
	 * 删除灭火器
	 * 
	 * @param inData
	 */
	void deleteExtinguisher(Long id) throws Exception;

	/**
	 * 更新灭火器信息
	 * @param userId 
	 * @param userId 
	 * 
	 * @param inData
	 */
	void updateExtinguisher(Long userId, FrontUnitExtinguisherInData inData) throws Exception;

	/**
	 * 导入灭火器信息
	 * @param id
	 * @param projectImportDatas
	 * @throws Exception 
	 */
	void importData(Long userId, List<ExtinguisherImportData> projectImportDatas) throws Exception;

	/**
	 * 灭火器推送微信模板
	 * @param employees
	 * @param isFire
	 * @param status
	 * @throws Exception
	 */
	void sendExtinguisherMsg(List<EmployeeForSendMsgData> employees, boolean isFire, String status, int i) throws Exception;

	/**
	 * 查询单位到期灭火器列表
	 *
	 * @param
	 * @return
	 */
	List<FrontUnitExtinguisherOutData> getExpireExtinguisherList();
}
