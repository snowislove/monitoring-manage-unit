package com.secpro.platform.monitoring.manage.dao;

import java.util.Map;

import com.secpro.platform.monitoring.manage.common.dao.IBaseDao;

public interface SysLogDao extends IBaseDao {
	public Map getLogApp();
	public long  getLogCount(String from ,String to);
}

