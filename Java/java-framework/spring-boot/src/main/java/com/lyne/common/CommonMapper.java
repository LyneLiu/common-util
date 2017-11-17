package com.lyne.common;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

/**
 * @author nn_liu
 * @Created 2017-07-27-19:02
 */

public interface CommonMapper<T> extends Mapper<T>, MySqlMapper<T> {

}
