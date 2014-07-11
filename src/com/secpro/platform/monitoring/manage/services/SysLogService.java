package com.secpro.platform.monitoring.manage.services;

import java.util.Map;

import com.secpro.platform.monitoring.manage.common.services.IBaseService;

public interface SysLogService extends IBaseService{
	public Map getLogApp();
	public long  getLogCount(String from ,String to);
}
