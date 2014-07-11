package com.secpro.platform.monitoring.manage.services;

import com.secpro.platform.monitoring.manage.common.services.IBaseService;

public interface CityTreeService extends IBaseService{
	public String getTaskRegionByCityCode(String cityCode);
}
