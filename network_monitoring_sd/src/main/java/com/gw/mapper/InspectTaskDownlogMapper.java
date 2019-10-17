package com.gw.mapper;

import com.gw.mapper.entity.InspectTaskDownlog;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface InspectTaskDownlogMapper {
    int deleteByPrimaryKey(Long logId);

    int insert(InspectTaskDownlog record);

    int insertSelective(InspectTaskDownlog record);

    InspectTaskDownlog selectByPrimaryKey(Long logId);

    int updateByPrimaryKeySelective(InspectTaskDownlog record);

    int updateByPrimaryKey(InspectTaskDownlog record);

    void insertAll(List<InspectTaskDownlog> downlogs);
}