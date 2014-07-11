package com.secpro.platform.monitoring.manage.services.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.secpro.platform.monitoring.manage.common.services.impl.BaseService;
import com.secpro.platform.monitoring.manage.dao.SysRoleDao;
import com.secpro.platform.monitoring.manage.services.SysRoleService;

@Service("SysRoleServiceImpl")
public class SysRoleServiceImpl extends BaseService implements SysRoleService{
	private SysRoleDao dao;

	public SysRoleDao getDao() {
		return dao;
	}
	@Resource(name="SysRoleDaoImpl")
	public void setDao(SysRoleDao dao) {
		this.dao = dao;
	}
	public boolean deleteRole(String[] roleid){
		return dao.deleteRole(roleid);
	}
	public boolean createRoleAppMapping(String[] roleids,String[] appids){
		return dao.createRoleAppMapping(roleids, appids);
	}
	public List getAppByRole(Long roleId){
		return dao.getAppByRole(roleId);
	}
}
