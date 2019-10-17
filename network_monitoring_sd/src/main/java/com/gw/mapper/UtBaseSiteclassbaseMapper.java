package com.gw.mapper;

import com.gw.common.BaseMapper;
import com.gw.mapper.entity.UtBaseSiteclassbase;

import java.util.List;

public interface UtBaseSiteclassbaseMapper extends BaseMapper<UtBaseSiteclassbase> {
    //批量插入检查对象
    int inserUtBaseSiteclassbaseList(List<UtBaseSiteclassbase> utBaseSiteclassbaseList);

//    根据检查对象查出检查id

    String selectIDbyUtBaseSiteclassbase(UtBaseSiteclassbase utBaseSiteclassbase);



}