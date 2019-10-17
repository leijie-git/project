package com.gw.mapper;

import java.util.List;

import com.gw.common.BaseMapper;
import com.gw.mapper.entity.UtUnitPrivatekeyRel;
import com.gw.systemManager.data.UnitPrivatekeyRelInData;
import com.gw.systemManager.data.UnitPrivatekeyRelOutData;

public interface UtUnitPrivatekeyRelMapper extends BaseMapper<UtUnitPrivatekeyRel> {

	/**
	 * 获取私钥列表
	 * @param inData
	 * @return
	 */
	List<UnitPrivatekeyRelOutData> privatekeyList(UnitPrivatekeyRelInData inData) throws Exception;

	/**
	 * 获取私钥已经关联的单位
	 * @param inData
	 * @return
	 */
	List<UnitPrivatekeyRelOutData> getAssociatedUnit(UnitPrivatekeyRelInData inData) throws Exception;

	/**
	 * 获取私钥未关联的单位
	 * @param inData
	 * @return
	 */
	List<UnitPrivatekeyRelOutData> getUnassociatedUnit(UnitPrivatekeyRelInData inData) throws Exception;
	
}