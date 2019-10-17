package com.gw.fireStation.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.github.pagehelper.PageInfo;
import com.gw.fireStation.data.XFZUserInData;
import com.gw.fireStation.data.XFZUserOutData;
import com.gw.unit.data.BaseInfoSelectOutData;

/**
 * 消防人员服务
 * @author SY
 *
 */
public interface XFZUserService {
	
	/**
	 * 分页
	 * @param request
	 * @param username
	 * @return
	 * @throws Exception
	 */
	PageInfo<XFZUserOutData> list(HttpServletRequest request, String username) throws Exception;
	
	/**
	 * 新增
	 * @param inData
	 * @throws Exception
	 */
	void add(XFZUserInData inData) throws Exception;
	
	/**
	 * 修改
	 * @param inData
	 * @throws Exception
	 */
	void update(XFZUserInData inData) throws Exception;
	
	/**
	 * 删除
	 * @param id
	 * @throws Exception
	 */
	void delete(Long id) throws Exception;
	
	/**
	 * 为下拉框准备数据
	 * @return
	 * @throws Exception
	 */
	List<BaseInfoSelectOutData> unitSelect() throws Exception;
}
