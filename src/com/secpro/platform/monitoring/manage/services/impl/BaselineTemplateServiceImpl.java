package com.secpro.platform.monitoring.manage.services.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.secpro.platform.monitoring.manage.common.services.impl.BaseService;
import com.secpro.platform.monitoring.manage.dao.BaselineTemplateDao;
import com.secpro.platform.monitoring.manage.services.BaselineTemplateService;
@Service("BaselineTemplateServiceImpl")
public class BaselineTemplateServiceImpl extends BaseService implements BaselineTemplateService{
	private BaselineTemplateDao dao;

	public BaselineTemplateDao getDao() {
		return dao;
	}
	@Resource(name="BaselineTemplateDaoImpl")
	public void setDao(BaselineTemplateDao dao) {
		this.dao = dao;
	}
	public List getAllBaseLineTemplate(List templateList){
		return dao.getAllBaseLineTemplate(templateList);
	}
	public boolean saveBaseLineTemplete(Long BaselineTemplateId,String[] baselineIds,Map<String,String> scores){
		return dao.saveBaseLineTemplete(BaselineTemplateId, baselineIds, scores);
	}
	public boolean deleteTemplate(String[] ids){
		return dao.deleteTemplate(ids);
	}
	public List getSelectBaseLine(Long id){
		return dao.getSelectBaseLine(id);
	}
}
