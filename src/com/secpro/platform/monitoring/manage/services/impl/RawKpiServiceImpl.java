package com.secpro.platform.monitoring.manage.services.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.secpro.platform.monitoring.manage.common.services.impl.BaseService;
import com.secpro.platform.monitoring.manage.dao.RawKpiDao;
import com.secpro.platform.monitoring.manage.services.RawKpiService;

@Service("RawKpiServiceImpl")
public class RawKpiServiceImpl extends BaseService implements RawKpiService{
	private RawKpiDao dao;

	public RawKpiDao getDao() {
		return dao;
	}
	@Resource(name="RawKpiDaoImpl")
	public void setDao(RawKpiDao dao) {
		this.dao = dao;
	}
}
