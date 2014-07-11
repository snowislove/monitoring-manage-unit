package com.secpro.platform.monitoring.manage.services.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.secpro.platform.monitoring.manage.common.services.impl.BaseService;
import com.secpro.platform.monitoring.manage.dao.SysEventDao;
import com.secpro.platform.monitoring.manage.services.SysEventService;

@Service("SysEventServiceImpl")
public class SysEventServiceImpl extends BaseService implements SysEventService{
	private SysEventDao dao;

	public SysEventDao getDao() {
		return dao;
	}
	@Resource(name="SysEventDaoImpl")
	public void setDao(SysEventDao dao) {
		this.dao = dao;
	}
	
}
