package com.secpro.platform.monitoring.manage.dao;

import com.secpro.platform.monitoring.manage.actions.forms.ResObjForm;
import com.secpro.platform.monitoring.manage.common.dao.IBaseDao;

public interface SysResObjDao extends IBaseDao{
	public String getOuterParentCityCode(final String cityCode);
	public void getResObjForm(final ResObjForm resObjForm);
	public void deleteRelevance(String resId);
}
