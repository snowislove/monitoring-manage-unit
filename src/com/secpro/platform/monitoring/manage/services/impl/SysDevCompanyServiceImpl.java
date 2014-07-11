package com.secpro.platform.monitoring.manage.services.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.secpro.platform.monitoring.manage.common.services.impl.BaseService;
import com.secpro.platform.monitoring.manage.dao.SysDevCompanyDao;
import com.secpro.platform.monitoring.manage.services.SysDevCompanyService;

@Service("SysDevCompanyServiceImpl")
public class SysDevCompanyServiceImpl extends BaseService implements SysDevCompanyService{
	private SysDevCompanyDao dao;

	public SysDevCompanyDao getDao() {
		return dao;
	}
	@Resource(name="SysDevCompanyDaoImpl")
	public void setDao(SysDevCompanyDao dao) {
		this.dao = dao;
	}
	public String createCompanyCode(){
		return dao.createCompanyCode();
	}
	
}
