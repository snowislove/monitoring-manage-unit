package com.secpro.platform.monitoring.manage.services.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.secpro.platform.monitoring.manage.common.services.impl.BaseService;
import com.secpro.platform.monitoring.manage.dao.SysAccessRuleDao;
import com.secpro.platform.monitoring.manage.services.SysAccessRuleService;

@Service("SysAccessRuleServiceImpl")
public class SysAccessRuleServiceImpl extends BaseService implements SysAccessRuleService{
	private SysAccessRuleDao dao;

	public SysAccessRuleDao getDao() {
		return dao;
	}
	@Resource(name="SysAccessRuleDaoImpl")
	public void setDao(SysAccessRuleDao dao) {
		this.dao = dao;
	}
}
