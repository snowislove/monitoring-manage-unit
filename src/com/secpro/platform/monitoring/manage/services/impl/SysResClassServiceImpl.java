package com.secpro.platform.monitoring.manage.services.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.secpro.platform.monitoring.manage.common.services.impl.BaseService;
import com.secpro.platform.monitoring.manage.dao.SysResClassDao;
import com.secpro.platform.monitoring.manage.services.SysResClassService;

@Service("SysResClassServiceImpl")
public class SysResClassServiceImpl extends BaseService implements SysResClassService {
	private SysResClassDao dao;

	public SysResClassDao getDao() {
		return dao;
	}
	@Resource(name="SysResClassDaoImpl")
	public void setDao(SysResClassDao dao) {
		this.dao = dao;
	}
	
}
