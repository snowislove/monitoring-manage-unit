package com.secpro.platform.monitoring.manage.services.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.secpro.platform.monitoring.manage.common.services.impl.BaseService;
import com.secpro.platform.monitoring.manage.dao.RawConfigPolicyDao;
import com.secpro.platform.monitoring.manage.services.RawConfigPolicyService;

@Service("RawConfigPolicyServiceImpl")
public class RawConfigPolicyServiceImpl extends BaseService implements RawConfigPolicyService{
	private RawConfigPolicyDao dao;

	public RawConfigPolicyDao getDao() {
		return dao;
	}
	@Resource(name="RawConfigPolicyDaoImpl")
	public void setDao(RawConfigPolicyDao dao) {
		this.dao = dao;
	}
}
