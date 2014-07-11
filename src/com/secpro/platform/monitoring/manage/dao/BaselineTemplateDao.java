package com.secpro.platform.monitoring.manage.dao;

import java.util.List;
import java.util.Map;

import com.secpro.platform.monitoring.manage.common.dao.IBaseDao;

public interface BaselineTemplateDao extends IBaseDao {
	public List getAllBaseLineTemplate(List templateList);
	public boolean saveBaseLineTemplete(Long BaselineTemplateId,String[] baselineIds,Map<String,String> scores);
	public boolean deleteTemplate(String[] ids);
	public List getSelectBaseLine(Long id);
}
