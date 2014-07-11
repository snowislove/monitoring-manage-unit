package com.secpro.platform.monitoring.manage.dao;

import java.util.List;

import com.secpro.platform.monitoring.manage.common.dao.IBaseDao;

public interface SysRoleDao extends IBaseDao {
	public boolean deleteRole(String[] roleid);
	public boolean createRoleAppMapping(String[] roleids,String[] appids);
	public List getAppByRole(Long roleId);
}
