package com.secpro.platform.monitoring.manage.services.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.secpro.platform.monitoring.manage.common.services.impl.BaseService;
import com.secpro.platform.monitoring.manage.dao.EventMsgDao;
import com.secpro.platform.monitoring.manage.services.EventMsgService;

@Service("EventMsgServiceImpl")
public class EventMsgServiceImpl extends BaseService implements EventMsgService{
	private EventMsgDao dao;

	public EventMsgDao getDao() {
		return dao;
	}
	@Resource(name="EventMsgDaoImpl")
	public void setDao(EventMsgDao dao) {
		this.dao = dao;
	}
	
}
