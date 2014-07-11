package com.secpro.platform.monitoring.manage.dao;

import com.secpro.platform.monitoring.manage.common.dao.IBaseDao;

public interface EventTypeDao extends IBaseDao{
	public void deleteRelevance(String eventId);
}
