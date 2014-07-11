package com.secpro.platform.monitoring.manage.dao;

import com.secpro.platform.monitoring.manage.common.dao.IBaseDao;

public interface SysCityDao extends IBaseDao{
	public String getTaskRegionByCityCode(String cityCode);
}
