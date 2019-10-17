package com.gw.firePower.service;

import com.github.pagehelper.PageInfo;
import com.gw.firePower.data.SquadronInData;
import com.gw.firePower.data.SquadronOutData;

public interface SquadronService {

	/**
	 * 获取消防中队配置数据
	 * @param pageSize 
	 * @param pageNumber 
	 * @param name
	 * @param powerName 
	 * @return
	 * @throws Exception 
	 */
	PageInfo<SquadronOutData> getSquadronList(Integer pageNumber, Integer pageSize, String name, String powerName) throws Exception;

	/**
	 * 新增中队配置
	 * @param id
	 * @param inData
	 * @throws Exception 
	 */
	void addSquadron(Long id, SquadronInData inData) throws Exception;

	/**
	 * 修改中队配置
	 * @param id
	 * @param inData
	 * @throws Exception 
	 */
	void updateSquadron(Long id, SquadronInData inData) throws Exception;

	/**
	 * 删除中队配置
	 * @param id
	 * @throws Exception 
	 */
	void deleteSquadron(String id) throws Exception;

}
