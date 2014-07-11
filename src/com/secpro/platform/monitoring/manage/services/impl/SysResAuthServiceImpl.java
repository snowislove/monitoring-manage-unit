package com.secpro.platform.monitoring.manage.services.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.secpro.platform.monitoring.manage.common.services.impl.BaseService;
import com.secpro.platform.monitoring.manage.dao.SysResAuthDao;
import com.secpro.platform.monitoring.manage.services.SysResAuthService;

@Service("SysResAuthServiceImpl")
public class SysResAuthServiceImpl extends BaseService implements SysResAuthService{
	private SysResAuthDao dao;

	public SysResAuthDao getDao() {
		return dao;
	}
	@Resource(name="SysResAuthDaoImpl")
	public void setDao(SysResAuthDao dao) {
		this.dao = dao;
	}
	
}
