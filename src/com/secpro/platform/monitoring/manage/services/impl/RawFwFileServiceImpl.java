package com.secpro.platform.monitoring.manage.services.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.secpro.platform.monitoring.manage.common.services.impl.BaseService;
import com.secpro.platform.monitoring.manage.dao.RawFwFileDao;
import com.secpro.platform.monitoring.manage.services.RawFwFileService;
@Service("RawFwFileServiceImpl")
public class RawFwFileServiceImpl extends BaseService implements RawFwFileService{
	private RawFwFileDao dao;

	public RawFwFileDao getDao() {
		return dao;
	}
	@Resource(name="RawFwFileDaoImpl")
	public void setDao(RawFwFileDao dao) {
		this.dao = dao;
	}
}
