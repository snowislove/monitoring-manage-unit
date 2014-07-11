package com.secpro.platform.monitoring.manage.services.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.secpro.platform.monitoring.manage.actions.forms.ResObjForm;
import com.secpro.platform.monitoring.manage.common.services.impl.BaseService;
import com.secpro.platform.monitoring.manage.dao.SysResObjDao;
import com.secpro.platform.monitoring.manage.services.SysResObjService;
@Service("SysResObjServiceImpl")
public class SysResObjServiceImpl extends BaseService implements SysResObjService{
	private SysResObjDao dao;

	public SysResObjDao getDao() {
		return dao;
	}
	@Resource(name="SysResObjDaoImpl")
	public void setDao(SysResObjDao dao) {
		this.dao = dao;
	}
	public String getOuterParentCityCode(final String cityCode){
		return dao.getOuterParentCityCode(cityCode);
	}
	public void getResObjForm(final ResObjForm resObjForm){
		dao.getResObjForm(resObjForm);
	}
	public void deleteRelevance(String resId){
		dao.deleteRelevance(resId);
	}
}
