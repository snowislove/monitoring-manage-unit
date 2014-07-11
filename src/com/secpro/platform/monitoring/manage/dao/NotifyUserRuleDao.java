package com.secpro.platform.monitoring.manage.dao;

import com.secpro.platform.monitoring.manage.common.dao.IBaseDao;

public interface NotifyUserRuleDao extends IBaseDao{
	public boolean deleteAllNotifyUser(String resId,String ruleId);
	public void deleteRelevance(String ruleId);
}
