package com.secpro.platform.monitoring.manage.services.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.secpro.platform.monitoring.manage.common.services.impl.BaseService;
import com.secpro.platform.monitoring.manage.dao.SysCommandDao;
import com.secpro.platform.monitoring.manage.services.SysCommandService;
@Service("SysCommandServiceImpl")
public class SysCommandServiceImpl extends BaseService implements SysCommandService{
	private SysCommandDao dao;

	public SysCommandDao getDao() {
		return dao;
	}
	@Resource(name="SysCommandDaoImpl")
	public void setDao(SysCommandDao dao) {
		this.dao = dao;
	}

	

}
