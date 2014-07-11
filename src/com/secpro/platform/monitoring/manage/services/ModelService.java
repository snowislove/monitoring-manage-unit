package com.secpro.platform.monitoring.manage.services;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.secpro.platform.monitoring.manage.util.ApplicationConfiguration;
import com.secpro.platform.monitoring.manage.util.log.PlatformLogger;
import com.secpro.platform.monitoring.manage.util.service.IService;
import com.secpro.platform.monitoring.manage.util.service.ServiceHelper;
import com.secpro.platform.monitoring.manage.util.service.ServiceInfo;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;


/**
 * @author baiyanwei 
 * 
 * 模型管理
 */
@Entity
@ServiceInfo(description = "lookup resource from cmdb")
public class ModelService implements IService {
	@ManyToOne
	private static PlatformLogger _logger = PlatformLogger.getLogger(ModelService.class);

	public void start() throws Exception {
		// TODO Auto-generated method stub
		
	}

	public void stop() throws Exception {
		// TODO Auto-generated method stub
		
	}
	

	
}
