package com.secpro.platform.monitoring.manage.dao;

import java.util.List;

import com.secpro.platform.monitoring.manage.common.dao.IBaseDao;
import com.secpro.platform.monitoring.manage.entity.SyslogBean;

public interface SyslogRuleDao extends IBaseDao{
	public boolean syslogRuleSave(SyslogBean syslogB);
	public boolean syslogRuleDelete(String typeCode);
	public List getRawSyslogHitPage(Long resId,String startTime,String endTime,int pageSize,int pageNo);
	public int getRawSyslogHitCount(Long resId,String startTime,String endTime);
}
