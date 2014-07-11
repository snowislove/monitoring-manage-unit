package com.secpro.platform.monitoring.manage.services.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.secpro.platform.monitoring.manage.common.services.impl.BaseService;
import com.secpro.platform.monitoring.manage.dao.EventTypeDao;
import com.secpro.platform.monitoring.manage.services.EventTypeService;

@Service("EventTypeServiceImpl")
public class EventTypeServiceImpl extends BaseService implements EventTypeService{
	private EventTypeDao dao;

	public EventTypeDao getDao() {
		return dao;
	}
	@Resource(name="EventTypeDaoImpl")
	public void setDao(EventTypeDao dao) {
		this.dao = dao;
	}
	public void deleteRelevance(String eventId){
		dao.deleteRelevance(eventId);
	}
}
