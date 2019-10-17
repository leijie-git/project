package com.gw.mapper;

import com.gw.common.BaseMapper;
import com.gw.mapper.entity.AlarmStatus;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AlarmStatusMapper extends BaseMapper<AlarmStatus> {

   List<AlarmStatus>  getAlarmStatus() throws Exception;

}
