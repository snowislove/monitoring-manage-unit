package com.secpro.platform.monitoring.manage.services.impl;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.secpro.platform.monitoring.manage.common.services.impl.BaseService;
import com.secpro.platform.monitoring.manage.dao.SysLogDao;
import com.secpro.platform.monitoring.manage.services.SysLogService;

@Service("SysLogServiceImpl")
public class SysLogServiceImpl extends BaseService implements SysLogService{
	private SysLogDao dao;

	public SysLogDao getDao() {
		return dao;
	}
	@Resource(name="SysLogDaoImpl")
	public void setDao(SysLogDao dao) {
		this.dao = dao;
	}
	public Map getLogApp(){
		return dao.getLogApp();
	}
	public long  getLogCount(String from ,String to){
		return dao.getLogCount(from,to);
	}
}
