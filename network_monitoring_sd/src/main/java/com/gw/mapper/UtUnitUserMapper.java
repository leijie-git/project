package com.gw.mapper;

import com.gw.common.BaseMapper;
import com.gw.front.login.data.FrontUnitUserOutData;
import com.gw.mapper.entity.UtUnitUser;
import com.gw.openApi.common.data.out.UserOut;
import com.gw.systemManager.data.NotifyRelUserOutData;
import com.gw.unit.data.MaintenanceUserOutData;
import com.gw.unit.data.NetworkingUserInData;
import com.gw.unit.data.NetworkingUserOutData;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UtUnitUserMapper extends BaseMapper<UtUnitUser> {
	
	/**
	 * 查找所有维保单位人员
	 * @param username
	 * @param account 
	 * @param unitname 
	 * @return
	 */
	List<MaintenanceUserOutData> selectAllMaintenanceUser(@Param("username") String username, @Param("unitname")String unitname, @Param("account")String account,@Param("UnitID")String UnitID);
	
	/**
	 * 查找所有联网单位人员
	 * @param username
	 * @param unitId 
	 * @return
	 */
	List<NetworkingUserOutData> selectAllNetworkingUser(NetworkingUserInData inData);

	/**
	 * 根据单位查询人员
	 * @param unitID
	 * @return
	 * @throws Exception
	 */
	List<MaintenanceUserOutData> getUserList(@Param("unitID") String unitID,@Param("userrole") String userrole)throws Exception;
	
	/**
	 * 获得关联通知的人员id和name
	 * @return
	 */
	List<NotifyRelUserOutData> getAllUserSelectList() throws Exception;
	
	/**
	 * 获得未关联通知的人员
	 * @return
	 */
	List<NotifyRelUserOutData> getAllUserDisSelectList(List<String> userIds) throws Exception;
	
	/**
	 * 根据单位id获得用户列表
	 * @param unitId
	 * @return
	 * @throws Exception
	 */
	List<FrontUnitUserOutData> getUserListByUnitId(@Param("unitId")String unitId) throws Exception;

	/**
	 * 根据用户id获取单位
	 * @param id
	 * @return
	 * @throws Exception
	 */
	String getUnitIdById(@Param("id") Long id)throws Exception;

	/**
	 * 根据多个用户id获取用户信息
	 * @param userIdList
	 * @return
	 */
	List<NotifyRelUserOutData> getUserListByIds(@Param("userIdList")List<String> userIdList);

	/**
	 * 清空openid除了自己
	 * @param openId
	 */
	void updateOpenId(@Param("openId") String openId,@Param("account") String account) throws Exception;

	/**
	 * 清空所有channelId除了自己
	 * @param channelId
	 */
	void updateChannelId(@Param("channelId") String channelId,@Param("account") String account) throws Exception;

	/**
	 * 查询所有人员
	 * @return
	 */
	List<FrontUnitUserOutData> selectAllUser();

	UserOut selectUserByAccount(String account);

       //根据用户Account 查出id
       String getIdByAccount(String Account);
}
