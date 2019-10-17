package com.gw.systemManager.service;

import com.github.pagehelper.PageInfo;
import com.gw.systemManager.data.UnitPrivatekeyRelInData;
import com.gw.systemManager.data.UnitPrivatekeyRelOutData;

public interface UnitPrivatekeyRelService {

	/**
	 * 获取所有私钥
	 * @param inData
	 * @return
	 * @throws Exception 
	 */
	PageInfo<UnitPrivatekeyRelOutData> privatekeyList(UnitPrivatekeyRelInData inData) throws Exception;

	/**
	 * 新增私钥
	 * @param inData
	 */
	void addUnitPrivatekeyRel(UnitPrivatekeyRelInData inData) throws Exception;

	/**
	 * 删除私钥
	 * @param privateKey
	 */
	void deleteUnitPrivatekeyRel(String privateKey) throws Exception;

	/**
	 * 获取私钥已经关联的单位
	 * @param inData
	 * @return
	 */
	PageInfo<UnitPrivatekeyRelOutData> getAssociatedUnit(UnitPrivatekeyRelInData inData) throws Exception;

	/**
	 * 获取私钥未关联的单位
	 * @param inData
	 * @return
	 */
	PageInfo<UnitPrivatekeyRelOutData> getUnassociatedUnit(UnitPrivatekeyRelInData inData) throws Exception;

	/**
	 * 私钥绑定单位
	 * @param privateKey
	 * @param unitIds
	 */
	void bindUnit(String privateKey, String unitIds) throws Exception;

	/**
	 * 私钥解绑单位
	 * @param privateKey
	 * @param unitIds
	 * @throws Exception 
	 */
	void unbindUnit(String privateKey, String ids) throws Exception;

}
