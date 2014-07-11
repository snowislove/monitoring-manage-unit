package com.secpro.platform.monitoring.manage.services;

import com.secpro.platform.monitoring.manage.actions.forms.ResObjForm;
import com.secpro.platform.monitoring.manage.common.services.IBaseService;

public interface SysResObjService extends IBaseService{
	public String getOuterParentCityCode(final String cityCode);
	public void getResObjForm(final ResObjForm resObjForm);
	public void deleteRelevance(String resId);
}
