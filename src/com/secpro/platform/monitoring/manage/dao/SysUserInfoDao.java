package com.secpro.platform.monitoring.manage.dao;

import java.util.List;
import java.util.Map;

import com.secpro.platform.monitoring.manage.common.dao.IBaseDao;
import com.secpro.platform.monitoring.manage.entity.SysUserInfo;

public interface SysUserInfoDao extends IBaseDao{
	public boolean createUserRoleMapping(String[] userIds,String[] roleIds);
	public List getRoleByUser(Long userId);
	public Map getAllApp(SysUserInfo user);
	public String getLastLoginDate(String account);
	public void updateLastLoginDate(String lastLoginDate,String account);
	public String getModifyPasswdDate(String account);
	public void updateModifyPasswdDate(String modifypasswd,String account);
}
