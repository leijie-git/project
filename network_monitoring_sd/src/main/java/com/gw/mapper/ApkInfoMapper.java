package com.gw.mapper;

import com.gw.common.BaseMapper;
import com.gw.mapper.entity.ApkInfo;
import org.springframework.stereotype.Repository;

@Repository
public interface ApkInfoMapper extends BaseMapper<ApkInfo> {

	/**
	 * 获取最新的一条记录
	 * last
	 */
	ApkInfo selectLastApkInfo() throws Exception;

}