package com.weiwei.jixieche.mapper;

import com.weiwei.jixieche.bean.JxcDriverHandover;
import org.apache.ibatis.annotations.Param;

public interface JxcDriverHandoverMapper extends BaseMapper<JxcDriverHandover> {

	JxcDriverHandover selectById(@Param("routeId") Long routeId);

}