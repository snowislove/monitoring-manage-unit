package com.secpro.platform.monitoring.manage.services.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.secpro.platform.monitoring.manage.common.services.impl.BaseService;
import com.secpro.platform.monitoring.manage.dao.SysKpiOidDao;
import com.secpro.platform.monitoring.manage.services.SysKpiOidService;

@Service("SysKpiOidServiceImpl")
public class SysKpiOidServiceImpl extends BaseService implements SysKpiOidService{
	private SysKpiOidDao dao;

	public SysKpiOidDao getDao() {
		return dao;
	}
	@Resource(name="SysKpiOidDaoImpl")
	public void setDao(SysKpiOidDao dao) {
		this.dao = dao;
	}
}
