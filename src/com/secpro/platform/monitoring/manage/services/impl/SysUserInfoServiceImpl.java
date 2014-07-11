package com.secpro.platform.monitoring.manage.services.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.secpro.platform.monitoring.manage.common.services.impl.BaseService;
import com.secpro.platform.monitoring.manage.dao.SysUserInfoDao;
import com.secpro.platform.monitoring.manage.entity.SysUserInfo;
import com.secpro.platform.monitoring.manage.services.SysUserInfoService;

@Service("SysUserInfoServiceImpl")
public class SysUserInfoServiceImpl extends BaseService implements SysUserInfoService{
	private SysUserInfoDao dao;

	public SysUserInfoDao getDao() {
		return dao;
	}
	@Resource(name="SysUserInfoDaoImpl")
	public void setDao(SysUserInfoDao dao) {
		this.dao = dao;
	}
	public boolean createUserRoleMapping(String[] userIds,String[] roleIds){
		return dao.createUserRoleMapping(userIds, roleIds);
	}
	public List getRoleByUser(Long userId){
		return dao.getRoleByUser(userId);
	}
	public Map getAllApp(SysUserInfo user){
		return dao.getAllApp(user);
	}
	public String getLastLoginDate(String account){
		return dao.getLastLoginDate(account);
	}
	public void updateLastLoginDate(String lastLoginDate,String account){
		dao.updateLastLoginDate(lastLoginDate, account);
	}
	public String getModifyPasswdDate(String account) {
		// TODO Auto-generated method stub
		return dao.getModifyPasswdDate(account);
	}
	public void updateModifyPasswdDate(String modifypasswd, String account) {
		// TODO Auto-generated method stub
		dao.updateModifyPasswdDate(modifypasswd, account);
	}
}
