package com.secpro.platform.monitoring.manage.services.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.secpro.platform.monitoring.manage.common.services.impl.BaseService;
import com.secpro.platform.monitoring.manage.dao.SysKpiInfoDao;
import com.secpro.platform.monitoring.manage.services.SysKpiInfoService;
@Service("SysKpiInfoServiceImpl")
public class SysKpiInfoServiceImpl extends BaseService implements SysKpiInfoService{
	private SysKpiInfoDao dao;

	public SysKpiInfoDao getDao() {
		return dao;
	}
	@Resource(name="SysKpiInfoDaoImpl")
	public void setDao(SysKpiInfoDao dao) {
		this.dao = dao;
	}
}
