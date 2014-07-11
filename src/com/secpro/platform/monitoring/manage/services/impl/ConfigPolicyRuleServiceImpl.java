package com.secpro.platform.monitoring.manage.services.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.secpro.platform.monitoring.manage.common.services.impl.BaseService;
import com.secpro.platform.monitoring.manage.dao.ConfigPolicyRuleDao;
import com.secpro.platform.monitoring.manage.services.ConfigPolicyRuleService;

@Service("ConfigPolicyRuleServiceImpl")
public class ConfigPolicyRuleServiceImpl extends BaseService implements ConfigPolicyRuleService{
	private ConfigPolicyRuleDao dao;

	public ConfigPolicyRuleDao getDao() {
		return dao;
	}
	@Resource(name="ConfigPolicyRuleDaoImpl")
	public void setDao(ConfigPolicyRuleDao dao) {
		this.dao = dao;
	}
	
}
