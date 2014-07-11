package com.secpro.platform.monitoring.manage.services.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.secpro.platform.monitoring.manage.common.services.impl.BaseService;
import com.secpro.platform.monitoring.manage.dao.SysEventRuleDao;
import com.secpro.platform.monitoring.manage.services.SysEventRuleService;
@Service("SysEventRuleServiceImpl")
public class SysEventRuleServiceImpl extends BaseService implements SysEventRuleService{
	private SysEventRuleDao dao;

	public SysEventRuleDao getDao() {
		return dao;
	}
	@Resource(name="SysEventRuleDaoImpl")
	public void setDao(SysEventRuleDao dao) {
		this.dao = dao;
	}
	
}
