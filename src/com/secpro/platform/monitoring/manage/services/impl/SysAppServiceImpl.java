package com.secpro.platform.monitoring.manage.services.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.secpro.platform.monitoring.manage.common.services.impl.BaseService;
import com.secpro.platform.monitoring.manage.dao.SysAppDao;
import com.secpro.platform.monitoring.manage.services.SysAppService;

@Service("SysAppServiceImpl")
public class SysAppServiceImpl extends BaseService implements SysAppService{
	private SysAppDao dao;

	public SysAppDao getDao() {
		return dao;
	}
	@Resource(name="SysAppDaoImpl")
	public void setDao(SysAppDao dao) {
		this.dao = dao;
	}
	public String queryAppByRoleid(String roleid){
		return dao.queryAppByRoleid(roleid);
	}
	public List getAppTree(){
		return dao.getAppTree();
	}
}
