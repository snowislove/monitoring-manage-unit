package com.secpro.platform.monitoring.manage.services.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.secpro.platform.monitoring.manage.common.services.impl.BaseService;
import com.secpro.platform.monitoring.manage.dao.SysOrgDao;
import com.secpro.platform.monitoring.manage.services.SysOrgService;

@Service("SysOrgServiceImpl")
public class SysOrgServiceImpl extends BaseService implements SysOrgService{
	private SysOrgDao dao;

	public SysOrgDao getDao() {
		return dao;
	}
	@Resource(name="SysOrgDaoImpl")
	public void setDao(SysOrgDao dao) {
		this.dao = dao;
	}
	public List getOrgTreeByOrgId(String orgId){
		return dao.getOrgTreeByOrgId(orgId);
	}
}
