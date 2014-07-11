package com.secpro.platform.monitoring.manage.services;

import java.util.List;

import com.secpro.platform.monitoring.manage.common.services.IBaseService;

public interface SysRoleService extends IBaseService{
	public boolean deleteRole(String[] roleid);
	public boolean createRoleAppMapping(String[] roleids,String[] appids);
	public List getAppByRole(Long roleId);
}
