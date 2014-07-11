package com.secpro.platform.monitoring.manage.dao;

import java.util.List;

import com.secpro.platform.monitoring.manage.common.dao.IBaseDao;

public interface SysAppDao  extends IBaseDao{
	public String queryAppByRoleid(String roleid);
	public List getAppTree();
}
