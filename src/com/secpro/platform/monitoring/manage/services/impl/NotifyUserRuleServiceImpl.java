package com.secpro.platform.monitoring.manage.services.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.secpro.platform.monitoring.manage.common.services.impl.BaseService;
import com.secpro.platform.monitoring.manage.dao.NotifyUserRuleDao;
import com.secpro.platform.monitoring.manage.services.NotifyUserRuleService;
@Service("NotifyUserRuleServiceImpl")
public class NotifyUserRuleServiceImpl extends BaseService implements NotifyUserRuleService{
	private NotifyUserRuleDao dao;

	public NotifyUserRuleDao getDao() {
		return dao;
	}
	@Resource(name="NotifyUserRuleDaoImpl")
	public void setDao(NotifyUserRuleDao dao) {
		this.dao = dao;
	}
	public boolean deleteAllNotifyUser(String resId,String ruleId){
		return dao.deleteAllNotifyUser(resId, ruleId);
	}
	public void deleteRelevance(String ruleId){
		dao.deleteRelevance(ruleId);
	}
}
