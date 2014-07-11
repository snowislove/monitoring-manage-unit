package com.secpro.platform.monitoring.manage.services.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.secpro.platform.monitoring.manage.common.services.impl.BaseService;
import com.secpro.platform.monitoring.manage.dao.SysDevTypeDao;
import com.secpro.platform.monitoring.manage.services.SysDevTypeService;

@Service("SysDevTypeServiceImpl")
public class SysDevTypeServiceImpl extends BaseService implements SysDevTypeService{
	private SysDevTypeDao dao;

	public SysDevTypeDao getDao() {
		return dao;
	}
	@Resource(name="SysDevTypeDaoImpl")
	public void setDao(SysDevTypeDao dao) {
		this.dao = dao;
	}
	public String deleteDevtypeByCompanyId(final String companyId){
		return this.dao.deleteDevtypeByCompanyId(companyId);
	}
	public String createTypeCode(String companyCode){
		return dao.createTypeCode(companyCode);
	}
	public void deleteRelevance(String typeCode){
		dao.deleteRelevance(typeCode);
	}
}
