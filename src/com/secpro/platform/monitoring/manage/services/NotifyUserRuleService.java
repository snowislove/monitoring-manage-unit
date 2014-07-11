package com.secpro.platform.monitoring.manage.services;

import com.secpro.platform.monitoring.manage.common.services.IBaseService;

public interface NotifyUserRuleService extends IBaseService{
	public boolean deleteAllNotifyUser(String resId,String ruleId);
	public void deleteRelevance(String ruleId);
}
