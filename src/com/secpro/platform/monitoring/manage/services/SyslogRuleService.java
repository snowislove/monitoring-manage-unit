package com.secpro.platform.monitoring.manage.services;

import java.util.List;

import com.secpro.platform.monitoring.manage.common.services.IBaseService;

public interface SyslogRuleService extends IBaseService {
	public String getRulePath();
	public void setRulePath(String rulePath);
	public String getTypeCode() ;
	public void setTypeCode(String typeCode);
	public boolean ruleStorage();
	public String getCrudOper();
	public void setCrudOper(String crudOper);
	public List getRawSyslogHitPage(Long resId,String startTime,String endTime,int pageSize,int pageNo);
	public int getRawSyslogHitCount(Long resId,String startTime,String endTime);
}
