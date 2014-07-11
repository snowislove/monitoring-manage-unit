package com.secpro.platform.monitoring.manage.services.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.secpro.platform.monitoring.manage.common.services.impl.BaseService;
import com.secpro.platform.monitoring.manage.dao.TelnetSshDictDao;
import com.secpro.platform.monitoring.manage.services.TelnetSshDictService;

@Service("TelnetSshDictServiceImpl")
public class TelnetSshDictServiceImpl extends BaseService implements TelnetSshDictService{
	private TelnetSshDictDao dao;

	public TelnetSshDictDao getDao() {
		return dao;
	}
	@Resource(name="TelnetSshDictDaoImpl")
	public void setDao(TelnetSshDictDao dao) {
		this.dao = dao;
	}
	
}
