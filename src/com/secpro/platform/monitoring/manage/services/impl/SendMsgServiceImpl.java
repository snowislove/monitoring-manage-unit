package com.secpro.platform.monitoring.manage.services.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.secpro.platform.monitoring.manage.common.services.impl.BaseService;
import com.secpro.platform.monitoring.manage.dao.SendMsgDao;
import com.secpro.platform.monitoring.manage.services.SendMsgService;

@Service("SendMsgServiceImpl")
public class SendMsgServiceImpl extends BaseService implements SendMsgService{
	private SendMsgDao dao;

	public SendMsgDao getDao() {
		return dao;
	}
	@Resource(name="SendMsgDaoImpl")
	public void setDao(SendMsgDao dao) {
		this.dao = dao;
	}
	
}
