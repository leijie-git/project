package com.gw.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.gw.common.BaseMapper;
import com.gw.front.index.data.FrontPatrolAbnormalOutData;
import com.gw.front.maintenance.data.FrontMaintenanceInData;
import com.gw.front.maintenance.data.FrontMaintenanceStatOutData;
import com.gw.front.maintenance.data.FrontMaintenanceTaskOutData;
import com.gw.mapper.entity.UtInspectTaskdetial;
import com.gw.wechat.data.PhoneTaskDetailOutData;

public interface UtInspectTaskdetialMapper extends BaseMapper<UtInspectTaskdetial> {

	/**
	 * 巡查异常
	 * 
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	FrontPatrolAbnormalOutData getPatrolAbnormal(@Param("userId")String userId) throws Exception;

	/**
	 * 查询巡查任务进度
	 * 
	 * @param unitId
	 * @return
	 * @throws Exception
	 */
	FrontMaintenanceStatOutData getMaintenanceStatusStat(FrontMaintenanceInData inData) throws Exception;

	/**
	 * 查询巡查记录
	 * 
	 * @param inData
	 * @return
	 * @throws Exception
	 */
	List<FrontMaintenanceTaskOutData> getInspectTaskList(FrontMaintenanceInData inData) throws Exception;

	/**
	 * 根据任务id查询对应信息
	 * @param id
	 * @return
	 * @throws Exception
	 */
	List<PhoneTaskDetailOutData> getTaskDetailList(@Param("id") String id) throws Exception;

	/**
	 * 根据任务id获取详情
	 * @param taskID
	 * @return
	 * @throws Exception
	 */
	List<FrontMaintenanceTaskOutData> getInspectTaskDetail(@Param("taskID") String taskID) throws Exception;

	/**
	 * 获取该单位快过期的任务列表
	 * @param inData
	 * @return
	 */
	List<FrontMaintenanceTaskOutData> getInspectEpirationTaskList(FrontMaintenanceInData inData);

	/**
	 * 获取过期任务数量
	 * @param unitID
	 * @param nowDate
	 * @return
	 */
	Integer getInspectEpirationTaskCount(@Param("unitID") String unitID,@Param("nowDate")  String nowDate);

	/**
	 * 通过 NFC唯一标识 获取检查项明细数据
	 *
	 * @param nfcCode	nfc卡唯一标识
	 */
	List<PhoneTaskDetailOutData> getInspectTaskDetailByNfc(@Param("nfcCode") String nfcCode);
}