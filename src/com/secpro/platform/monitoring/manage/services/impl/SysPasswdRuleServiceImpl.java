package com.secpro.platform.monitoring.manage.services.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.secpro.platform.monitoring.manage.common.services.impl.BaseService;
import com.secpro.platform.monitoring.manage.dao.SysPasswdRuleDao;
import com.secpro.platform.monitoring.manage.services.SysPasswdRuleService;

@Service("SysPasswdRuleServiceImpl")
public class SysPasswdRuleServiceImpl extends BaseService implements SysPasswdRuleService{
	private SysPasswdRuleDao dao;

	public SysPasswdRuleDao getDao() {
		return dao;
	}
	@Resource(name="SysPasswdRuleDaoImpl")
	public void setDao(SysPasswdRuleDao dao) {
		this.dao = dao;
	}
}
