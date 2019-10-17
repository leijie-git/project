package com.gw.mapper;

import com.gw.mapper.entity.NetRequestLog;

public interface NetRequestLogMapper {
    int deleteByPrimaryKey(Integer reqId);

    int insert(NetRequestLog record);

    int insertSelective(NetRequestLog record);

    NetRequestLog selectByPrimaryKey(Integer reqId);

    int updateByPrimaryKeySelective(NetRequestLog record);

    int updateByPrimaryKey(NetRequestLog record);
}