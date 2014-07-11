package com.secpro.platform.monitoring.manage.services.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.secpro.platform.monitoring.manage.common.services.impl.BaseService;
import com.secpro.platform.monitoring.manage.dao.SysEventDealMsgDao;
import com.secpro.platform.monitoring.manage.entity.SysEventDealMsg;
import com.secpro.platform.monitoring.manage.services.SysEventDealMsgService;

@Service("SysEventDealMsgServiceImpl")
public class SysEventDealMsgServiceImpl extends BaseService implements SysEventDealMsgService {
	private SysEventDealMsgDao dao;

	public SysEventDealMsgDao getDao() {
		return dao;
	}
	@Resource(name="SysEventDealMsgDaoImpl")
	public void setDao(SysEventDealMsgDao dao) {
		this.dao = dao;
	}
	
}
