package com.secpro.platform.monitoring.manage.services;

import com.secpro.platform.monitoring.manage.common.services.IBaseService;

public interface EventTypeService extends IBaseService{
	public void deleteRelevance(String eventId);
}
