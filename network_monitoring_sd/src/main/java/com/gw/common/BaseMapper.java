package com.gw.common;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

/**
 * 
 * @author lxy  
 * @date 2018年4月9日 
 * @Description:mapper基类
 */
public interface BaseMapper<T> extends Mapper<T>, MySqlMapper<T> {

}
