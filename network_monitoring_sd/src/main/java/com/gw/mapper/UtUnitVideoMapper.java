package com.gw.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.gw.common.BaseMapper;
import com.gw.device.data.PointVideoInData;
import com.gw.device.data.PointVideoOutData;
import com.gw.front.couplet.data.FrontCoupletBuildAreaOutData;
import com.gw.front.index.data.FrontVideoOutData;
import com.gw.front.unit.data.FrontUnitVideoOutData;
import com.gw.mapper.entity.UtUnitVideo;
import com.gw.unit.data.VideoInData;
import com.gw.unit.data.VideoOutData;

public interface UtUnitVideoMapper extends BaseMapper<UtUnitVideo> {

	/**
	 * 根据ip获取视频列表
	 * 
	 * @param ip
	 * @return
	 */
	List<VideoOutData> getList(VideoInData inData);

	/**
	 * 更新信息
	 * 
	 * @param inData
	 * @return
	 */
	Integer update(VideoInData inData);

	/**
	 * 单位建筑区域视频
	 * 
	 * @param unitId
	 * @param buildName
	 * @return
	 * @throws Exception
	 */
	List<FrontCoupletBuildAreaOutData> getUnitBuildAreas(@Param("unitId") String unitId,
			@Param("buildName") String buildName) throws Exception;

	/**
	 * 单位视频
	 * 
	 * @param unitId
	 * @param name
	 * @return
	 * @throws Exception
	 */
	List<FrontUnitVideoOutData> getUnitVideoInfo(@Param("unitId") String unitId, @Param("name") String name)
			throws Exception;

	/**
	 * 获取点位视频
	 * 
	 * @param inData
	 * @return
	 */
	List<PointVideoOutData> getPointVideoList(PointVideoInData inData) throws Exception;

	/**
	 * 获取点位视频下拉数据
	 * 
	 * @param unitId
	 * 
	 * @return
	 */
	List<PointVideoOutData> getPointVideoSelect(@Param("unitId") Long unitId) throws Exception;

	/**
	 * 根据视频id获取详细信息
	 * 
	 * @param videoID
	 * @return
	 * @throws Exception
	 */
	FrontVideoOutData getVideoDetail(@Param("videoID") String videoID) throws Exception;
}