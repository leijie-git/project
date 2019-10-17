package com.gw.device.service;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.github.pagehelper.PageInfo;
import com.gw.device.data.PointVideoInData;
import com.gw.device.data.PointVideoOutData;

public interface PointVideoService {

	/**
	 * 新增视频点位
	 * @param inData
	 */
	void addPointVideo(PointVideoInData inData) throws Exception;

	/**
	 * 更新视频点位
	 * @param inData
	 */
	void updatePointVideo(PointVideoInData inData) throws Exception;

	/**
	 * 获取视频点位分页列表
	 * @param inData
	 * @return
	 */
	PageInfo<PointVideoOutData> getPointVideoList(PointVideoInData inData) throws Exception;

	/**
	 * 删除视频点位
	 * @param id
	 */
	void deletePointVideo(String id) throws Exception;

	/**
	 * 获取点位视频下拉
	 * @param unitId 
	 * @return
	 * @throws Exception
	 */
	List<PointVideoOutData> getPointVideoSelect(Long unitId) throws Exception;

	/**
	 * 导出视频点位功能
	 * @param response
	 * @param inData 
	 * @throws Exception 
	 */
	void exportPointVideo(HttpServletResponse response, PointVideoInData inData) throws Exception;

}
