package com.secpro.platform.monitoring.manage.dao;

import java.util.List;

import com.secpro.platform.monitoring.manage.common.dao.IBaseDao;

public interface TopologyDao extends IBaseDao {
	public List<Object> queryBySql(String sql, int pageSize, int pageNo);

	public Object findById(Class<?> clazz, String id);
	
	public <T extends Object> List<T> findAll(Class<?> clazz, String whereSql, int pageNo, int pageSize, String orderKey);
}
