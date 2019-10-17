package com.gw.unit.service;

import com.github.pagehelper.PageInfo;
import com.gw.unit.data.VideoInData;
import com.gw.unit.data.VideoOutData;

/**
 * 视频业务层接口
 * @author zfg
 *
 */
public interface VideoService {

	/**
	 * 获取视频列表
	 * @param inData
	 * @return
	 * @throws Exception 
	 */
	PageInfo<VideoOutData> getList(VideoInData inData) throws Exception;

	/**
	 * 添加视频
	 * @param inData
	 * @throws Exception
	 */
	void add(VideoInData inData) throws Exception;

	/**
	 * 更新视频信息
	 * @param inData
	 * @throws Exception
	 */
	void update(VideoInData inData) throws Exception;

	/**
	 * 删除视频信息
	 * @param id
	 * @throws Exception
	 */
	void remove(String id) throws Exception;


}
