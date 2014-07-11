package com.secpro.platform.monitoring.manage.services.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.secpro.platform.monitoring.manage.common.services.impl.BaseService;
import com.secpro.platform.monitoring.manage.dao.SysEventHisDao;
import com.secpro.platform.monitoring.manage.services.SysEventHisService;

@Service("SysEventHisServiceImpl")
public class SysEventHisServiceImpl extends BaseService implements SysEventHisService {

	private SysEventHisDao dao;

	public SysEventHisDao getDao() {
		return dao;
	}
	@Resource(name="SysEventHisDaoImpl")
	public void setDao(SysEventHisDao dao) {
		this.dao = dao;
	}
	
	
}
