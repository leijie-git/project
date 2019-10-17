package com.gw.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.gw.common.BaseMapper;
import com.gw.fireStation.data.XFZUserOutData;
import com.gw.mapper.entity.UtUnitXfzuser;

/**
 * 消防人员Mapper层
 * @author SY
 *
 */
public interface UtUnitXfzuserMapper extends BaseMapper<UtUnitXfzuser> {
	
	/**
	 * 获取所有消防站人员
	 * @param username
	 * @return
	 * @throws Exception
	 */
	List<XFZUserOutData> selectAllXFZUser(@Param("username") String username) throws Exception;
}