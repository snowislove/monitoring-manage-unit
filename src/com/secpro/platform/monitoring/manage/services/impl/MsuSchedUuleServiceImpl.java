package com.secpro.platform.monitoring.manage.services.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.secpro.platform.monitoring.manage.common.services.impl.BaseService;
import com.secpro.platform.monitoring.manage.dao.MsuSchedUuleDao;
import com.secpro.platform.monitoring.manage.services.MsuSchedUuleService;

@Service("MsuSchedUuleServiceImpl")
public class MsuSchedUuleServiceImpl extends BaseService implements MsuSchedUuleService{
	private MsuSchedUuleDao dao;

	public MsuSchedUuleDao getDao() {
		return dao;
	}
	@Resource(name="MsuSchedUuleDaoImpl")
	public void setDao(MsuSchedUuleDao dao) {
		this.dao = dao;
	}
}
